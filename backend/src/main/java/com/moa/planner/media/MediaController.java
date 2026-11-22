package com.moa.planner.media;

import java.io.IOException;
import java.security.Principal;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
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
	
	// ResponseEntity: 컨트롤러에서 브라우저에 보낼 답장을 직접 정할 때 사용하는 클래스
	// Principal: 현재 로그인한 사용자를 나타낸다(컨트롤러에서 사용)
	@GetMapping("/media/files/{filename}")
	public ResponseEntity<Resource> openMediaFile(@PathVariable("filename") String filename, Principal principal){
		MediaService.LoadedMedia media = mediaService.openMediaFile(principal.getName(), filename);
		
		// MediaType: HTTP로 주고받는 데이터의 형식을 표현 예) image/jpeg
		// MediaTypeFactory: MediaType을 찾아주는 스프링 클래스
		// getMediaType(): 파일 확장자(.jpg 등)를 보고 MediaType을 찾아주는 메서드 예) .jpg → image/jpeg
		// filter(앞에서 나온 값을 받을 변수 → 그 값을 검사할 조건)
		org.springframework.http.MediaType contentType = MediaTypeFactory.getMediaType(media.originalFilename())
				.filter(type -> switch(type.toString()){
                case "image/png", "image/jpeg", "image/gif", "image/webp",
                "video/mp4", "audio/mpeg", "audio/wav", "audio/x-wav" -> true;
                default -> false; 
                // APPLICATION_OCTET_STREAM: 파일 종류를 모르거나 목록에 없으면 기본값을 사용한다.
				}).orElse(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
				
		// ok(): 상태 코드를 200 OK로 설정함
		// contentType()은 Content-Type(헤더 이름)의 값을 괄호 안 변수의 값으로 설정
		// header(헤더 이름, 값)으로 헤더 설정
		// nosniff는 브라우저가 파일 내용을 보고 형식을 추측하는 동작을 제한함
		// body(): 전달받은 값을 본문에 담음
		return ResponseEntity.ok().contentType(contentType).header("X-Content-Type-Options, nosniff").body(media.file());
	}
	

	@DeleteMapping("/media/{mediaId}")
	public void deleteMediaFile(@PathVariable("mediaId") Long mediaId) throws IOException {
		mediaService.deleteMediaFile(mediaId);
	}
}
