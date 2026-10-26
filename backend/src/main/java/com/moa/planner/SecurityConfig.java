package com.moa.planner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링의 환경 설정 파일임을 의미
public class SecurityConfig {

	@Bean // 메서드가 리턴한 객체를 스프링이 관리하도록 등록
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// authorizeHttpRequests(): 요청 URL의 접근 권한을 설정
				// 람다식: 매개변수 -> 실행할 코드(중괄호 내부)
				// auth: 접근 권한 설정 객체를 받는 매개변수
				// requestMatchers(): 특정 요청 경로를 지정
				// permitAll(): 누구나 접근 가능
				// anyRequest(): 앞에서 지정하지 않은 나머지 모든 요청
				// authenticated(): 인증(로그인)한 사용자만 접근 가능
				.authorizeHttpRequests(auth -> auth
				.requestMatchers("/user", "/login").permitAll()
				.anyRequest().authenticated())
				// formLogin(): Spring Security가 제공하는 기본 폼 로그인 기능을 설정
				// Customizer.withDefaults(): 기본 설정을 사용
				.formLogin(Customizer.withDefaults())
				// logout() : Spring Security의 로그아웃 기능을 설정
				.logout(Customizer.withDefaults())
				// CSRF: 사용자의 로그인 상태를 악용해 원치 않는 요청을 보내는 공격
				// disable() → CSRF 보호 기능을 비활성화
				.csrf(csrf -> csrf.disable());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() { // PasswordEncoder: 비밀번호 해시 처리 및 검증을 위한 인터페이스
		return new BCryptPasswordEncoder(); // BCryptPasswordEncoder(): BCrypt 방식으로 비밀번호를 해시 처리하는 객체 생성
	}
}
