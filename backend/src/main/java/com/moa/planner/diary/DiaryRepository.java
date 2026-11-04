package com.moa.planner.diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	@Query("""
			SELECT d FROM Diary d
			WHERE d.user.userId = :userId
			AND d.diaryDate = :diaryDate
			""")
	Optional<Diary> findDiaryByDate( 
			@Param("userId") Long userId, 
			@Param("diaryDate") LocalDate diaryDate);
	
	@Query("""
			SELECT d FROM Diary d
			WHERE d.user.userId = :userId
			AND (:searchDiary = true AND d.content LIKE CONCAT('%', :keyword, '%'))
			ORDER BY d.diaryDate
			""")
	List<Diary> searchDiaries(
			@Param("userId") Long userId,
			@Param("keyword") String keyword,
			@Param("searchDiary") Boolean searchDiary);
}
