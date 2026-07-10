package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.user.UserRegistrationRequestDto;
import com.andrii.dto.user.UserResponseDto;
import com.andrii.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toUserResponse(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
