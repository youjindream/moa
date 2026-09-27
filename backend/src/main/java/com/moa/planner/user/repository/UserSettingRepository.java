package com.moa.planner.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moa.planner.user.entity.UserSetting;

public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {

}
