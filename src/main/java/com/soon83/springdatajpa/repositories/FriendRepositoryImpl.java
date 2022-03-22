package com.soon83.springdatajpa.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soon83.springdatajpa.domain.Friend;
import com.soon83.springdatajpa.domain.User;

public class FriendRepositoryImpl extends CustomQuerydslRepositorySupport implements FriendRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public FriendRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Friend.class);
        this.queryFactory = queryFactory;
    }
}