package com.moa.planner.label;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moa.planner.label.dto.LabelCreateRequest;
import com.moa.planner.label.dto.LabelResponse;
import com.moa.planner.label.dto.LabelUpdateRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LabelController {
	
	private final LabelService labelService;
	
	@PostMapping("/label")
	public void createLabel(@RequestBody LabelCreateRequest request) {
		labelService.createLabel(request);
	}
	
	@GetMapping("/label/{labelId}")
	public LabelResponse getLabel(@PathVariable("labelId") Long labelId) {
		return labelService.getLabel(labelId);
	}
	
	@PutMapping("/label/{labelId}")
	public void updateLabel(@RequestBody LabelUpdateRequest request, @PathVariable("labelId") Long labelId) {
		labelService.updateLabel(request, labelId);
	}
	
	@DeleteMapping("/label/{labelId}")
	public void deleteLabel(@PathVariable("labelId") Long labelId) {
		labelService.deleteLabel(labelId);
	}

}
