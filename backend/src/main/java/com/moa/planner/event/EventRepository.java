package com.moa.planner.event;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

	@Query("""
			SELECT e FROM Event e 
			WHERE e.user.userId = :userId 
			AND e.startTime >= :startTime 
			AND e.startTime < :endTime  
			""") // :는 JPQL에서 매개변수를 나타내는 기호, SELECT e는 *와 비슷
	List<Event> findEventsByDateRange( 
			// @Param: 메서드로 받은 값을 JPQL에서 같은 이름의 매개변수에 연결
			@Param("userId") Long userId,
			@Param("startTime") LocalDateTime startTime,
			@Param("endTime") LocalDateTime endTime);

	@Query("""
			SELECT e FROM Event e
			WHERE e.user.userId = :userId
			AND e.startTime > :startTime
			ORDER BY e.startTime 
			""")
	List<Event> findUpcomingEvent(
			@Param("userId") Long userId,
			@Param("startTime") LocalDateTime startTime,
			Pageable pageable // Pageable: 조회할 데이터 개수 제한
			);
	
	// '%keyword%'는 keyword를 글자 그자체로 취급함
	// CONCAT('%', :keyword, '%')은 전달받은 검색어 앞뒤에 %를 붙임
	@Query("""
			SELECT e FROM Event e
			WHERE e.user.userId = :userId
			AND ((:searchTitle = true) AND (e.title LIKE CONCAT('%', :keyword, '%')) 
			OR (:searchContent = true) AND (e.content LIKE CONCAT('%', :keyword, '%')))
			ORDER BY e.startTime
			""") // 연산 우선순위: 괄호 → NOT → AND → OR
	List<Event> searchEvents(
			@Param("userId") Long userId,
			@Param("keyword") String keyword,
			@Param("searchTitle") Boolean searchTitle,
			@Param("searchContent") Boolean  searchContent);

	@Query("""
			SELECT e
			FROM Event e
			WHERE e.user.userId = :userId
			AND e.startTime > :now
			AND e.alarmMinutesBefore IS NOT NULL
			""")
	List<Event> findAlarmEvents(
			@Param("userId") Long userId,
			@Param("now") LocalDateTime now);

}