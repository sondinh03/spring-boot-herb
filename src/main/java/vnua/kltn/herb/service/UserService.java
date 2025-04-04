package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.dto.LoginDto;
import vnua.kltn.herb.dto.UserDto;
import vnua.kltn.herb.dto.UserRegisterDto;
import vnua.kltn.herb.dto.response.UserResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.Map;

public interface UserService {
    UserResponseDto getCurrentUser() throws HerbException;

    UserResponseDto getByUsername(String username) throws HerbException;

    @Transactional
    UserResponseDto create(UserRegisterDto registerDto) throws HerbException;
    /*
    UserDto getUserById(Integer id);
    UserDto updateUser(Integer id, UserDto userDto);
    void deleteUser(Integer id);

     */

    Map<String, Object> authenticateUser(LoginDto loginDto) throws HerbException;

    Page<UserResponseDto> search(SearchDto searchDto);
}
