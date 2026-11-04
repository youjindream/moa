package com.moa.planner.search;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moa.planner.search.dto.SearchRequest;
import com.moa.planner.search.dto.SearchResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SearchController {
	
	private final SearchService searchService;
	
	@PostMapping("/search")
	public SearchResponse search(@RequestBody SearchRequest request) {
		return searchService.search(request);
	}

}
