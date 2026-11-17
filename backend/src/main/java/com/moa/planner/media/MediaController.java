package com.moa.planner.media;

import java.io.IOException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MediaController {
	
	private final MediaService mediaService;
	
	@PostMapping("/media")
	public void createMediaFile(
			// @RequestParam: HTTP 요청에서 지정한 이름의 값을 찾아 메서드 매개변수에 넣어줌
			@RequestParam("targetType") TargetType targetType,
			@RequestParam("targetId") Long targetId,
			@RequestParam("file") MultipartFile file) throws IOException {
		mediaService.createMediaFile(targetType, targetId, file );
	}
	
	@GetMapping("/media/{mediaId}")
	public MediaResponse getMediaFile(@PathVariable("mediaId") Long mediaId) {
		MediaFile mediaFile = mediaService.getMediaFile(mediaId);
		return new MediaResponse(mediaFile.getMediaId(), mediaFile.getFileUrl());
	}

	@DeleteMapping("/media/{mediaId}")
	public void deleteMediaFile(@PathVariable("mediaId") Long mediaId) throws IOException {
		mediaService.deleteMediaFile(mediaId);
	}
	
	// record: 값을 담는 클래스를 짧게 만듬
	public record MediaResponse(Long mediaId, String fileUrl) {}
}
