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
			""") // :는 JPQL에서 매개변수를 나타내는 기호
	List<Event> findEventsByDateRange( 
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
}
