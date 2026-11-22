package com.moa.planner.media;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long>{
	
	
	@Query("""
			SELECT m
			FROM MediaFile m
			WHERE m.user.email = :email
			AND m.targetType = :targetType
			AND m.targetId = :targetId
			""")
	List<MediaFile> findMediaFiles(
			@Param("email") String email,
			@Param("targetType") TargetType targetType,
			@Param("targetId") Long targetId);
	
	
	@Query("""
			SELECT m
			FROM MediaFile m
			WHERE m.user.email = :email
			AND m.fileUrl = :fileUrl
			""")
	Optional<MediaFile> findMediaByUrl(
			@Param("email") String email,
			@Param("fileUrl") String fileUrl
			);
	

}
