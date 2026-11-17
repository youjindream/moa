package com.moa.planner.media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MediaService {
	
	private final MediaFileRepository mediaRepository;
	private final UserRepository userRepository;
	
	// MultipartFile: 사용자가 업로드한 파일을 나타내는 인터페이스
	// throws IOException: 파일을 저장하거나 폴더를 만드는 중 생긴 입출력 예외를 호출한 쪽으로 넘긴다는 선언
	public void createMediaFile(TargetType targetType, Long targetId, MultipartFile file ) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();
		
		MediaFile mediaFile = new MediaFile();
		
		mediaFile.setUser(user);
		mediaFile.setTargetType(targetType);
		mediaFile.setTargetId(targetId);
		
		// contentType: MultipartFile file에 담긴 파일의 미디어 타입
		String contentType = file.getContentType();
		
		if (contentType == null ) {
			throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
			// IllegalArgumentException은 메서드에 들어온 값이 처리할 수 없는 값일 때
		}
		
		if (contentType.startsWith("image/")) { // startsWith(): 문자열이 지정한 글자로 시작하는지
			mediaFile.setMediaType(MediaType.IMAGE);
		} else if (contentType.startsWith("video/")) {
			mediaFile.setMediaType(MediaType.VIDEO);
		} else if (contentType.startsWith("audio/")) {
			mediaFile.setMediaType(MediaType.AUDIO); 
		} else {
			throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
		}
		
		// Path는 파일이나 폴더가 어디에 있는지 담는 타입
		// Files: 파일이나 폴더를 생성, 복사, 이동, 삭제할 때 사용하는 자바 기본 클래스
		// createDirectories(): 폴더가 없으면 만들어서 경로를 반환, 이미 있으면 그 경로를 반환
		// Path.of(): 문자열을 파일이나 폴더의 경로로 바꾸는 메서드 ex)"uploads"라는 문자열로 경로를 만듬
		Path uploadDir = Files.createDirectories(Path.of("uploads"));
	
		// UUID: 서로 다른 대상을 구별하기 위해 쓰는, 겹칠 가능성이 매우 낮은 식별자
		// randomUUID(): 무작위 UUID를 생성하는 메서드
		// toString(): 객체를 문자열로 표현하는 메서드
		String storedFilename = UUID.randomUUID().toString();
		
		// transferTo(): 업로드한 파일을 지정한 경로에 저장
		// resolve(): 폴더 경로에 파일 이름을 붙여 파일의 전체 저장 경로를 만드는 메서드
		file.transferTo(uploadDir.resolve(storedFilename));

		mediaFile.setFileUrl("/media/files/" + storedFilename);
		mediaFile.setOriginalFilename(file.getOriginalFilename());
		mediaFile.setFileSizeBytes(file.getSize());
		mediaFile.setCreatedAt(LocalDateTime.now());
		
		mediaRepository.save(mediaFile);
	}
	
	public MediaFile getMediaFile(Long mediaId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		
		MediaFile mediaFile = mediaRepository.findById(mediaId).orElseThrow();
		
		if (!email.equals(mediaFile.getUser().getEmail())) {
			throw new IllegalArgumentException("미디어를 찾을 수 없습니다.");
		}
		return mediaFile;
	}
	
	public void deleteMediaFile(Long mediaId) throws IOException {
		MediaFile mediaFile = getMediaFile(mediaId);
		
		String fileUrl = mediaFile.getFileUrl();
		// substring(): 문자열에서 원하는 부분만 잘라 가져오는 메서드(두번째 인자 생략시 끝까지)
		// lastIndexOf('/')는 문자열에서 마지막 /가 몇 번째 칸에 있는지 알려주는 메서드
		String storedFilename = fileUrl.substring(fileUrl.lastIndexOf('/')+ 1);
		
		// deleteIfExists(): 파일이 있으면 삭제하고 true를 반환, 이미 없으면 false를 반환
		Files.deleteIfExists(Path.of("uploads").resolve(storedFilename));
		
		mediaRepository.delete(mediaFile);
	}
	
	


}
