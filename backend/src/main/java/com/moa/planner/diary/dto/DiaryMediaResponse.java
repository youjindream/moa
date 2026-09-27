package com.moa.planner.diary.dto;

import com.moa.planner.media.MediaType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryMediaResponse {
	
	private Long mediaId;
	
	private MediaType mediaType;
	
	private String fileUrl;
	
	private String originalFilename;
}
