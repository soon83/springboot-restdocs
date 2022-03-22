package com.soon83.springdatajpa.repositories;

import com.soon83.springdatajpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
