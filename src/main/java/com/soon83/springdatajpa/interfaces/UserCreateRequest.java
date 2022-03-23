package com.soon83.springdatajpa.interfaces;

import com.soon83.springdatajpa.domain.Gender;
import com.soon83.springdatajpa.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    @Min(value = 1)
    @Max(value = 120)
    private Integer age;

    public User toEntity() {
        return User.builder()
                .name(name)
                .gender(gender)
                .age(age)
                .build();
    }
}
