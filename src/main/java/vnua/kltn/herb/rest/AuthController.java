package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.LoginDto;
import vnua.kltn.herb.dto.UserDto;
import vnua.kltn.herb.dto.UserRegisterDto;
import vnua.kltn.herb.dto.request.UserRequestDto;
import vnua.kltn.herb.dto.response.UserResponseDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.security.JwtTokenProvider;
import vnua.kltn.herb.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public HerbResponse<Map<String, Object>> authenticateUser(@Valid @RequestBody LoginDto loginDto) throws HerbException {
        return new HerbResponse<>(userService.authenticateUser(loginDto));
    }

    @PostMapping("/register")
    public HerbResponse<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto registerDto) throws HerbException {
        var createdUser = userService.create(registerDto);
        return new HerbResponse<>(createdUser);
    }

    @GetMapping("/profile")
    public HerbResponse<UserResponseDto> getCurrentUser() throws HerbException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        var userDto = userService.getByUsername(currentUsername);
        return new HerbResponse<>(userDto);
    }
}
