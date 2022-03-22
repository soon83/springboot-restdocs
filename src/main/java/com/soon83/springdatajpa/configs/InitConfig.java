package com.soon83.springdatajpa.configs;

import com.soon83.springdatajpa.domain.Gender;
import com.soon83.springdatajpa.interfaces.UserDto;
import com.soon83.springdatajpa.repositories.FriendRepository;
import com.soon83.springdatajpa.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitConfig implements InitializingBean {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        UserDto user1 = UserDto.builder()
                .name("드록바")
                .gender(Gender.MALE)
                .age(20)
                .build();
        UserDto user2 = UserDto.builder()
                .name("세브첸코")
                .gender(Gender.MALE)
                .age(22)
                .build();


        //userRepository.save(user1.toEntity());
        //userRepository.save(user2.toEntity());
    }
}
