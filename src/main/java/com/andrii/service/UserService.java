package com.andrii.service;

import com.andrii.dto.user.UserRegistrationRequestDto;
import com.andrii.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
