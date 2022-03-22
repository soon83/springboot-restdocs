package com.soon83.springdatajpa.interfaces;

import com.soon83.springdatajpa.domain.Gender;
import com.soon83.springdatajpa.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private String name;
    private Gender gender;

    @NotNull
    private int age;

    public User toEntity() {
        return User.builder()
                .name(name)
                .gender(gender)
                .age(age)
                .build();
    }
}
