package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.LoginDto;
import vnua.kltn.herb.dto.request.UserRequestDto;
import vnua.kltn.herb.dto.response.UserResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.Map;

public interface UserService {
    UserResponseDto getById(Long id) throws HerbException;

    UserResponseDto getCurrentUser() throws HerbException;

    UserResponseDto getByUsername(String username) throws HerbException;

    UserResponseDto create(UserRequestDto requestDto) throws HerbException;

    Boolean update(Long id, UserRequestDto requestDto) throws HerbException;

    /*
    UserDto getUserById(Integer id);
    UserDto updateUser(Integer id, UserDto userDto);
    void deleteUser(Integer id);

     */

    Map<String, Object> authenticateUser(LoginDto loginDto) throws HerbException;

    Page<UserResponseDto> search(SearchDto searchDto);
}
