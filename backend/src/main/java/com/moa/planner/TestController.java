package com.moa.planner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Spring Boot 정상 동작 중!";
    }
    
//    @PostMapping("/test")
//    public String testPost() {
//        return "POST 요청 성공!";
//    }
    
    @PostMapping("/test")
    public String testPost(@RequestBody TestRequest request) { // @RequestBody는 Postman 같은 곳에서 보낸 데이터를 자바 객체로 받아주는 표시
        return "받은 내용: " + request.getMessage();
    }
}