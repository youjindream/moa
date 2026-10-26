package com.moa.planner.diary;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.moa.planner.diary.dto.DiaryCreateRequest;
import com.moa.planner.diary.dto.DiaryResponse;
import com.moa.planner.diary.dto.DiaryUpdateRequest;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DiaryService {

	private final DiaryRepository diaryRepository;
	private final UserRepository userRepository; // 임시

	public void createDiary(DiaryCreateRequest request) {
		Diary diary = new Diary();

		User user = userRepository.findById(1L).orElseThrow(); // 임시
		diary.setUser(user); // 임시
		diary.setDiaryDate(request.getDiaryDate());
		diary.setContent(request.getContent());
		diary.setCreatedAt(LocalDateTime.now());
		diary.setUpdatedAt(LocalDateTime.now());

		diaryRepository.save(diary);
	}

	public DiaryResponse getDiary(Long diaryId) {
		Diary diary = diaryRepository.findById(diaryId).orElseThrow();
		return new DiaryResponse(diary); 
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
