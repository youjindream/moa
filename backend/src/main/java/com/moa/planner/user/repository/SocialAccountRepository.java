package com.moa.planner.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moa.planner.user.entity.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long>{

}
