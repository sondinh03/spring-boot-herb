package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.LoginDto;
import vnua.kltn.herb.dto.UserRegisterDto;
import vnua.kltn.herb.dto.response.UserResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.security.JwtTokenProvider;
import vnua.kltn.herb.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestUserController {
    private final UserService userService;

    @GetMapping("/search")
    public HerbResponse<Page<UserResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(userService.search(dto));
    }
}
