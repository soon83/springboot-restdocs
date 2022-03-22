package com.soon83.springdatajpa.interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soon83.springdatajpa.domain.Gender;
import com.soon83.springdatajpa.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    @JsonProperty("userId")
    private Long id;
    private String name;
    private Gender gender;
    private int age;

    public UserResponse(Long userId) {
        this.id = userId;
    }

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.gender = entity.getGender();
        this.age = entity.getAge();
    }
}
