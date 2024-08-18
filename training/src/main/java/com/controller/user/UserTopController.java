package com.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserTopController {
	
	@RequestMapping("/user/top")
	public String user_top() {//ユーザーtop
		
		return "user/top";
	}

}
