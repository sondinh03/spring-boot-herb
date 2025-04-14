package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    /*

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new HerbException("User not found with email: " + email));
        return mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    */

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

        var user = userMapper.requestToEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        /*
        // Create new user
        var user = User.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .fullName(requestDto.getFullName())
                .build();
         */

        // Set default role as USER
        if (requestDto.getRoleType() == null)
            user.setRoleType(UserRoleEnum.USER.getType());
        else user.setRoleType(requestDto.getRoleType());

        // Set status as ACTIVE
        if (requestDto.getStatus() == null)
            user.setStatus(UserStatusEnum.ACTIVE.getType());
        else user.setStatus(requestDto.getStatus());

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
        // Log toàn bộ thông tin nhận được
        log.info("Login Request - Username: {}", loginDto.getUsername());
        log.info("Login Request - Password Length: {}",
                loginDto.getPassword() != null ? loginDto.getPassword().length() : "null");

        // Xác thực người dùng
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword())
        );

        log.info("Authentication Details: {}", authentication);
        log.info("Authentication Principal: {}", authentication.getPrincipal());
        log.info("Authentication Credentials: {}", authentication.getCredentials());

        // Đặt context security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = tokenProvider.generateToken(authentication);

        var userDto = getByUsername(loginDto.getUsername());

        // Tạo response
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("tokenType", "Bearer");
        response.put("user", userDto);

        return response;
    }

    /*
    public Page<UserResponseDto> search(SearchDto searchDto) {
        Specification<User> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Xử lý từ khóa tìm kiếm
            if (searchDto.getKeyword() != null && !searchDto.getKeyword().isEmpty()) {
                String keyword = "%" + searchDto.getKeyword().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), keyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("scientificName")), keyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), keyword)
                ));
            }

            // Xử lý các bộ lọc động
            if (searchDto.getFilters() != null && !searchDto.getFilters().isEmpty()) {
                for (Map.Entry<String, Object> filter : searchDto.getFilters().entrySet()) {
                    String key = filter.getKey();
                    Object value = filter.getValue();

                    // Xử lý các loại filter khác nhau
                    if (value != null) {
                        if (value instanceof String) {
                            predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(key)),
                                    "%" + ((String) value).toLowerCase() + "%"
                            ));
                        } else if (value instanceof List) {
                            // Xử lý filter là danh sách (ví dụ: filter theo nhiều ID)
                            predicates.add(root.get(key).in((List<?>) value));
                        } else {
                            // Xử lý các filter so sánh bằng
                            predicates.add(criteriaBuilder.equal(root.get(key), value));
                        }
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Xử lý phân trang
        var pageable = PageUtils.getPageable(
                searchDto.getPageIndex() != null ? searchDto.getPageIndex() : 0,
                searchDto.getPageSize() != null ? searchDto.getPageSize() : 10
        );

        // Xử lý sắp xếp nếu có
        if (searchDto.getSortField() != null && !searchDto.getSortField().isEmpty()) {
            Sort.Direction direction = searchDto.getSortDirection() != null &&
                    searchDto.getSortDirection().equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            pageable = PageUtils.getPageable(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(direction, searchDto.getSortField())
            );
        }

        // Thực hiện truy vấn
        Page<User> users = userRepo.findAll(spec, pageable);
        return users.map(userMapper::entityToResponse);
    }
     */

    @Override
    public Page<UserResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("username", "email", "fullName");
        return super.search(searchDto, userRepo, userRepo, userMapper::entityToResponse, searchableFields);
    }


    /*
    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new HerbException("User not found with id: " + id));

        // Update user fields
        if (userDto.getFullName() != null) {
            user.setFullName(userDto.getFullName());
        }

        // Only admin can update role and status
        if (userDto.getRoleType() != null) {
            user.setRoleType(userDto.getRoleType());
        }

        if (userDto.getStatus() != null) {
            user.setStatus(userDto.getStatus());
        }

        // Update email if provided and not already used by another user
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            Optional<User> existingUserWithEmail = userRepo.findByEmail(userDto.getEmail());
            if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(id)) {
                throw new HerbException("Email already in use");
            }
            user.setEmail(userDto.getEmail());
        }

        User updatedUser = userRepo.save(user);
        return mapToDto(updatedUser);
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new HerbException("User not found with id: " + id));
        userRepo.delete(user);
    }

    @Override
    @Transactional
    public UserDto changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new HerbException("User not found with id: " + userId));

        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new HerbException("Incorrect old password");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        User updatedUser = userRepo.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    @Transactional
    public UserDto updateUserRole(Long userId, UserRoleEnum role) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new HerbException("User not found with id: " + userId));
        user.setRoleType(role);
        User updatedUser = userRepo.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    @Transactional
    public UserDto updateUserStatus(Long userId, UserStatusEnum status) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new HerbException("User not found with id: " + userId));
        user.setStatus(status);
        User updatedUser = userRepo.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    // Helper method to map User entity to UserDto
    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setRoleType(user.getRoleType());
        userDto.setStatus(user.getStatus());
        userDto.setCreatedAt(user.getCreateDate());
        return userDto;
    }

     */
}
