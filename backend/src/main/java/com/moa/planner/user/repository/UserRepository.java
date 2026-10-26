package com.moa.planner.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moa.planner.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByEmail(String email); // existsByEmail(): DB에 해당 이메일이 존재하는지 확인 (true/false)
	
	Optional<User> findByEmail(String email); // DB에서 해당 이메일을 가진 User 객체 조회 (없으면 빈 Optional 반환)
}
