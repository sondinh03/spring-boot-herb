package vnua.kltn.herb.service.mapper;

import ch.qos.logback.core.model.ComponentModel;
import org.mapstruct.Mapper;
import vnua.kltn.herb.dto.UserDto;
import vnua.kltn.herb.dto.request.UserRequestDto;
import vnua.kltn.herb.dto.response.UserResponseDto;
import vnua.kltn.herb.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto entityToResponse(User user);

    User requestToEntity(UserRequestDto userRequestDto);
}
