package com.moa.planner.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moa.planner.user.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
