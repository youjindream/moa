package com.moa.planner.label.dto;

import com.moa.planner.label.Label;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelResponse {
	
	private Long labelId; 
	
	private String title;
	
	private String colorCode;
	
	public LabelResponse(Label label) {
		this.labelId = label.getLabelId();
		this.title = label.getTitle();
		this.colorCode = label.getColorCode();
	}

}
