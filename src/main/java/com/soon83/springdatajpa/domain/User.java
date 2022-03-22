package com.soon83.springdatajpa.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 31)
    private Gender gender;

    private int age;

    //@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    //private Friend friend;
}
