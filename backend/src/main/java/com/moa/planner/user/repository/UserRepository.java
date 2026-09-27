package com.moa.planner.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moa.planner.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
