package com.moa.planner.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.moa.planner.diary.Diary;
import com.moa.planner.diary.DiaryRepository;
import com.moa.planner.event.Event;
import com.moa.planner.event.EventRepository;
import com.moa.planner.label.Label;
import com.moa.planner.label.LabelRepository;
import com.moa.planner.search.dto.SearchRequest;
import com.moa.planner.search.dto.SearchResponse;
import com.moa.planner.search.dto.SearchResultResponse;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final DiaryRepository diaryRepository;
	private final LabelRepository labelRepository;

	public SearchResponse search(SearchRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();

		List<Event> events = eventRepository.searchEvents(user.getUserId(), request.getKeyword(),
				request.getSearchTitle(), request.getSearchContent());

		List<Diary> diaries = diaryRepository.searchDiaries(user.getUserId(), request.getKeyword(),
				request.getSearchDiary());

		List<Label> labels = labelRepository.searchLabels(user.getUserId(), request.getKeyword(),
				request.getSearchLabel());

		List<SearchResultResponse> results = getResults(events, diaries, labels);

		SearchResponse response = new SearchResponse();

		response.setTotalCount(results.size());
		response.setEventCount(events.size());
		response.setDiaryCount(diaries.size());
		response.setLabelCount(labels.size());
		response.setResults(results);

		return response;
	}

	public List<SearchResultResponse> getResults(List<Event> events, List<Diary> diaries, List<Label> labels) {
		List<SearchResultResponse> results = new ArrayList<>();

		for (Event event : events) {
			SearchResultResponse result = new SearchResultResponse();

			result.setId(event.getEventId());
			result.setType(SearchType.EVENT); // enum 값
			result.setTitle(event.getTitle());
			result.setContent(event.getContent());
			result.setDateTime(event.getStartTime());

			results.add(result);
		}

		for (Diary diary : diaries) {
			SearchResultResponse result = new SearchResultResponse();

			result.setId(diary.getDiaryId());
			result.setType(SearchType.DIARY);
			result.setContent(diary.getContent());
			result.setDateTime(diary.getDiaryDate().atStartOfDay());

			results.add(result);
		}

		for (Label label : labels) {
			SearchResultResponse result = new SearchResultResponse();

			result.setId(label.getLabelId());
			result.setType(SearchType.LABEL);
			result.setTitle(label.getTitle());

			results.add(result);
		}

		return results;
	}

}
