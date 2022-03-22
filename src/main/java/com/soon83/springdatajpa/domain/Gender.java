package com.soon83.springdatajpa.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Gender {

    MALE("남"),
    FEMALE("여");

    private final String title;

    public static Gender of(String title) {
        return Arrays.stream(values())
                .filter(v -> v.getTitle().equals(title))
                .findFirst()
                .orElse(null);
        //.orElseThrow(() -> new BusinessException(ErrorCode.BIND_ERROR_ENUM, Gender.class.getName() + "=" + Arrays.deepToString(Gender.values())));
    }
}
