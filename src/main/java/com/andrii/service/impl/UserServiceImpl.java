package com.andrii.service.impl;

import com.andrii.dto.user.UserRegistrationRequestDto;
import com.andrii.dto.user.UserResponseDto;
import com.andrii.exception.EntityNotFoundException;
import com.andrii.exception.RegistrationException;
import com.andrii.mapper.UserMapper;
import com.andrii.model.Role;
import com.andrii.model.RoleName;
import com.andrii.model.User;
import com.andrii.repository.user.RoleRepository;
import com.andrii.repository.user.UserRepository;
import com.andrii.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(
                    "Can't register user. Email already exists: " + requestDto.getEmail()
            );
        }

        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(RoleName.ROLE_USER + " not found"));
        user.setRoles(Set.of(role));

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
