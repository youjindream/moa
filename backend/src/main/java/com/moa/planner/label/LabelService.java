package com.moa.planner.label;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.moa.planner.label.dto.LabelCreateRequest;
import com.moa.planner.label.dto.LabelResponse;
import com.moa.planner.label.dto.LabelUpdateRequest;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {

	private final LabelRepository labelRepository;
	private final UserRepository userRepository;

	public void createLabel(LabelCreateRequest request) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();
		
		Label label = new Label();
		label.setUser(user);
		label.setTitle(request.getTitle());
		label.setColorCode(request.getColorCode());
		labelRepository.save(label);
	}
	
	public LabelResponse getLabel(Long labelId) {
		Label label = labelRepository.findById(labelId).orElseThrow();
		LabelResponse response = new LabelResponse(label);
		return response;
	}
	
	public void updateLabel(LabelUpdateRequest request, Long labelId) {
		Label label = labelRepository.findById(labelId).orElseThrow();
		label.setTitle(request.getTitle());
		label.setColorCode(request.getColorCode());
		labelRepository.save(label);
	}
	
	public void deleteLabel(Long labelId) {
		labelRepository.deleteById(labelId);
		
	}

}
