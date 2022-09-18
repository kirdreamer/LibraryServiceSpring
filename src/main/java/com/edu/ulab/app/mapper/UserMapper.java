package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserResponse userDtoToUserResponse(UserDto userDto);

    UserEntity userDtoToUserEntity(UserDto userDto);

    UserDto userEntityToUserDto(UserEntity userDto);
}
