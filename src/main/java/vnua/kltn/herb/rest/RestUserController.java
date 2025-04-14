package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.UserRequestDto;
import vnua.kltn.herb.dto.response.UserResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestUserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public HerbResponse<UserResponseDto> getById(@PathVariable(value = "id") Long id) throws HerbException {
        return new HerbResponse<>(userService.getById(id));
    }

    @GetMapping("/search")
    public HerbResponse<Page<UserResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(userService.search(dto));
    }

    @PostMapping("/create")
    public HerbResponse<UserResponseDto> create(@Valid @RequestBody UserRequestDto requestDto) throws HerbException {
        var createdUser = userService.create(requestDto);
        return new HerbResponse<>(createdUser);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#id)")
    public HerbResponse<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody UserRequestDto requestDto) throws HerbException {
        var updatedUser = userService.update(id, requestDto);
        return new HerbResponse<>(updatedUser);
    }
}
