package com.soon83.springdatajpa.configs;

import com.soon83.springdatajpa.domain.Gender;
import com.soon83.springdatajpa.interfaces.UserCreateRequest;
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
        UserCreateRequest user1 = UserCreateRequest.builder()
                .name("드록바")
                .gender(Gender.MALE)
                .age(20)
                .build();
        UserCreateRequest user2 = UserCreateRequest.builder()
                .name("세브첸코")
                .gender(Gender.MALE)
                .age(22)
                .build();


        //userRepository.save(user1.toEntity());
        //userRepository.save(user2.toEntity());
    }
}
