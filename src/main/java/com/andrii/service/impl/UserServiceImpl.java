package com.andrii.service.impl;

import com.andrii.dto.user.UserRegistrationRequestDto;
import com.andrii.dto.user.UserResponseDto;
import com.andrii.exception.RegistrationException;
import com.andrii.mapper.UserMapper;
import com.andrii.model.User;
import com.andrii.repository.user.UserRepository;
import com.andrii.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(
                    "Can't register user. Email already exists: " + requestDto.getEmail()
            );
        }

        User user = userMapper.toModel(requestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
