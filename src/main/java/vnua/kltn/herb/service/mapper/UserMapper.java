package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.UserRequestDto;
import vnua.kltn.herb.dto.response.UserResponseDto;
import vnua.kltn.herb.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto entityToResponse(User user);

    @Mapping(target = "password", ignore = true)
    User requestToEntity(UserRequestDto userRequestDto);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    void setValue(UserRequestDto requestDto, @MappingTarget User user);
}
