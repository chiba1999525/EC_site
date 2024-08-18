package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {
	
	//topページ表示処理
	@GetMapping("/")
	public String top() {
		
		return "top";
	}
	
	@GetMapping("/login")
    public String login() {
        return "login"; // ログインページ
    }
	
	@GetMapping("/logout")
    public String logout() {
        return "login"; // ログインページ
    }

}
