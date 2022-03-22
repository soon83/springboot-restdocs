package com.soon83.springdatajpa.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soon83.springdatajpa.domain.User;

public class UserRepositoryImpl extends CustomQuerydslRepositorySupport implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }
}