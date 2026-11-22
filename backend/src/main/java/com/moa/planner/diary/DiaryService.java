package com.moa.planner.diary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.moa.planner.diary.dto.DiaryCreateRequest;
import com.moa.planner.diary.dto.DiaryMediaResponse;
import com.moa.planner.diary.dto.DiaryResponse;
import com.moa.planner.diary.dto.DiaryUpdateRequest;
import com.moa.planner.media.MediaFile;
import com.moa.planner.media.MediaFileRepository;
import com.moa.planner.media.TargetType;
import com.moa.planner.user.UserService;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DiaryService {

	private final DiaryRepository diaryRepository;
	private final UserRepository userRepository; 
	private final MediaFileRepository mediaFileRepository;

	public void createDiary(DiaryCreateRequest request) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();
		
		Diary diary = new Diary();

		diary.setUser(user);
		diary.setDiaryDate(request.getDiaryDate());
		diary.setContent(request.getContent());
		diary.setCreatedAt(LocalDateTime.now());
		diary.setUpdatedAt(LocalDateTime.now());

		diaryRepository.save(diary);
	}

	public DiaryResponse getDiary(Long diaryId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		
		Diary diary = diaryRepository.findById(diaryId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if (!email.equals(diary.getUser().getEmail())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	
		List<MediaFile> mediaFiles = mediaFileRepository.findMediaFiles(email, TargetType.DIARY, diaryId);
		
		List<DiaryMediaResponse> mediaResponseList = new ArrayList<>();
		
		for(MediaFile mediaFile : mediaFiles) {
			DiaryMediaResponse mediaResponse = new DiaryMediaResponse();
			mediaResponse.setMediaId(mediaFile.getMediaId());
			mediaResponse.setMediaType(mediaFile.getMediaType());
			mediaResponse.setFileUrl(mediaFile.getFileUrl());
			mediaResponse.setOriginalFilename(mediaFile.getOriginalFilename());
			
			mediaResponseList.add(mediaResponse);
		}
		
		return new DiaryResponse(diary, mediaResponseList); 
	}

	public void updateDiary(DiaryUpdateRequest request, Long diaryId) {
		Diary diary = diaryRepository.findById(diaryId).orElseThrow();
		diary.setDiaryDate(request.getDiaryDate());
		diary.setContent(request.getContent());
		diary.setUpdatedAt(LocalDateTime.now());
		diaryRepository.save(diary);
	}

	public void deleteDiary(Long diaryId) {
		diaryRepository.deleteById(diaryId);
	}

}
