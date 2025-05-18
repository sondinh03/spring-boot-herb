package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.constant.enums.UserRoleEnum;
import vnua.kltn.herb.constant.enums.UserStatusEnum;
import vnua.kltn.herb.dto.LoginDto;
import vnua.kltn.herb.dto.request.UserRequestDto;
import vnua.kltn.herb.dto.response.UserResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.User;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.UserRepository;
import vnua.kltn.herb.security.JwtTokenProvider;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.UserService;
import vnua.kltn.herb.service.mapper.UserMapper;

import java.util.*;

import static vnua.kltn.herb.constant.enums.ErrorCodeEnum.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl extends BaseSearchService<User, UserResponseDto> implements UserService {
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponseDto getById(Long id) throws HerbException {
        var user = userRepo.findById(id).orElseThrow(() -> new HerbException(NOT_FOUND));
        return userMapper.entityToResponse(user);
    }

    @Override
    public UserResponseDto getCurrentUser() throws HerbException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            throw new HerbException(UNAUTHORIZED);
        }

        var username = authentication.getName();

        var user = userRepo.findByUsername(username).orElseThrow(() -> new HerbException(NOT_FOUND));
        return userMapper.entityToResponse(user);
    }

    @Override
    public UserResponseDto getByUsername(String username) throws HerbException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return userMapper.entityToResponse(user);
    }

    @Transactional
    @Override
    public UserResponseDto create(UserRequestDto requestDto) throws HerbException {
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm()))
            throw new HerbException(CONFIRM_PASSWORD_ERROR);

        // Check if username already exists
        if (userRepo.existsByUsername(requestDto.getUsername())) {
            throw new HerbException(EXISTED_USERNAME);
        }

        // Check if email already exists
        if (userRepo.existsByEmail(requestDto.getEmail())) {
            throw new HerbException(EXISTED_EMAIL);
        }

        if (Objects.equals(requestDto.getRoleType(), UserRoleEnum.USER.getType()))
            requestDto.setStatus(UserStatusEnum.ACTIVE.getType());
        else requestDto.setStatus(UserStatusEnum.PENDING.getType());

        var user = userMapper.requestToEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user = userRepo.save(user);
        return userMapper.entityToResponse(user);
    }

    @Override
    @Transactional
    public Boolean update(Long id, UserRequestDto requestDto) throws HerbException {
        var user = userRepo.findById(id).orElseThrow(() -> new HerbException(NOT_FOUND));

        // Lấy thông tin user hiện tại
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Chỉ ADMIN mới có thể cập nhật role và status
        if (!isAdmin) {
            // Reset role và status về giá trị hiện tại nếu không phải admin
            requestDto.setRoleType(user.getRoleType());
            requestDto.setStatus(user.getStatus());
        }

        if (requestDto.getEmail() != null && !requestDto.getEmail().equals(user.getEmail())) {
            Optional<User> existingUserWithEmail = userRepo.findByEmail(requestDto.getEmail());
            if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(id)) {
                throw new HerbException(EXISTED_EMAIL);
            }
        }

        userMapper.setValue(requestDto, user);

        // Xử lý cập nhật mật khẩu nếu có
        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
                throw new HerbException(CONFIRM_PASSWORD_ERROR);
            }
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }

        userRepo.save(user);
        return true;
    }

    @Override
    public Map<String, Object> authenticateUser(LoginDto loginDto) throws HerbException {
        var usernameOrEmail = loginDto.getUsername();
        String actualUsername;

        if (usernameOrEmail.contains("@")) {
            var user = userRepo.findByEmail(usernameOrEmail).orElseThrow(() -> new HerbException(NOT_FOUND));
            actualUsername = user.getUsername();
        } else {
            actualUsername = usernameOrEmail;
        }

        // Xác thực người dùng
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        actualUsername,
                        loginDto.getPassword())
        );


        // Đặt context security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = tokenProvider.generateToken(authentication);
        var refreshToken = tokenProvider.generateRefreshToken(actualUsername);

        var userDto = getByUsername(actualUsername);

        // Tạo response
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("refreshToken", refreshToken);
        response.put("tokenType", "Bearer");
        response.put("user", userDto);

        return response;
    }

    @Override
    public Boolean logout(String token) {
        SecurityContextHolder.clearContext();
        tokenProvider.blacklistToken(token);
        return true;
    }

    @Override
    public Boolean validateToken(String token) throws HerbException {
        if (!tokenProvider.validateToken(token)) {
            throw new HerbException(INVALID_TOKEN);
        }
        return true;
    }

    @Override
    public Map<String, Object> refreshToken(String refreshToken) throws HerbException {
        log.info("refresh token: {}", refreshToken);
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new HerbException(INVALID_TOKEN);
        }

        if (!tokenProvider.isRefreshToken(refreshToken)) {
            throw new HerbException(INVALID_TOKEN);
        }

        var username = tokenProvider.getUsernameFromToken(refreshToken);
        var user = userRepo.findByUsername(username).orElseThrow(() -> new HerbException(NOT_FOUND));

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.getByType(user.getRoleType()))
        );

        var authentication = new UsernamePasswordAuthenticationToken(
                username, null, authorities);

        // Tạo token mới
        String newAccessToken = tokenProvider.generateToken(authentication);
        String newRefreshToken = tokenProvider.generateRefreshToken(username);

        // Thêm token cũ vào blacklist
        tokenProvider.blacklistToken(refreshToken);

        // Tạo response
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", newRefreshToken);
        response.put("tokenType", "Bearer");
        response.put("user", userMapper.entityToResponse(user));

        return response;
    }

    @Override
    public Page<UserResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("username", "email", "fullName");
        return super.search(searchDto, userRepo, userRepo, userMapper::entityToResponse, searchableFields);
    }
}
