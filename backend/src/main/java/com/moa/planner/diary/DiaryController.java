package com.moa.planner.diary;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moa.planner.diary.dto.DiaryCreateRequest;
import com.moa.planner.diary.dto.DiaryResponse;
import com.moa.planner.diary.dto.DiaryUpdateRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class DiaryController {

	private final DiaryService diaryService;

	@PostMapping("/diary")
	public void createDiary(@RequestBody DiaryCreateRequest request) {
		diaryService.createDiary(request);
	}

	@GetMapping("/diary/{diaryId}")
	public DiaryResponse getDiary(@PathVariable("diaryId") Long diaryId) {
		return diaryService.getDiary(diaryId);
	}

	@PutMapping("/diary/{diaryId}")
	public void updateDiary(@RequestBody DiaryUpdateRequest request, @PathVariable("diaryId") Long diaryId) {
		diaryService.updateDiary(request, diaryId);
	}

	@DeleteMapping("/diary/{diaryId}")
	public void deleteDiary(@PathVariable("diaryId") Long diaryId) {
		diaryService.deleteDiary(diaryId);
	}
}
