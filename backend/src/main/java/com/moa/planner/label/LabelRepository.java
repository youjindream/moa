package com.moa.planner.label;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LabelRepository extends JpaRepository<Label, Long> {

	@Query("""
			SELECT l FROM Label l
			WHERE l.user.userId = :userId
			AND l.title LIKE CONCAT('%', :keyword, '%')
			ORDER BY l.title
				""")
	List<Label> searchLabels(
			@Param("userId") Long userId, 
			@Param("keyword") String keyword,
			@Param("searchLabel") Boolean searchLabel);
	

}
