package com.moa.planner.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override // UserDetailsService의 메서드 오버라이딩
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow();
		List<GrantedAuthority> authorities = new ArrayList<>(); // GrantedAuthority: 사용자 권한 인터페이스
		authorities.add(new SimpleGrantedAuthority(user.getRole().getValue())); // Simple...: GrantedAuthority를 구현한 클래스
		return new org.springframework.security.core.userdetails.User( // User 클래스 이름 충돌을 방지하기 위해 전체 패키지 경로 사용
				user.getEmail(), user.getPasswordHash(), authorities); // new User()와 user.getEmail()의 User는 서로 다른 클래스
	}
}