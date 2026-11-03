package com.moa.planner.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.planner.home.dto.HomeResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
public class HomeController {
	
	private final HomeService homeService;
	
	@GetMapping("/home")
	public HomeResponse getHome() {
		return homeService.getHome();
	}

}
