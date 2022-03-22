package com.soon83.springdatajpa.service;

import com.soon83.springdatajpa.interfaces.UserDto;
import com.soon83.springdatajpa.interfaces.UserResponse;
import com.soon83.springdatajpa.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getId();
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse findUserById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponse::new)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));
    }
}
