package com.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminTopController{
	
	
	@RequestMapping("/admin/top")//adminTopページ
	public String admin_top(
			Model model
			) {

		return "admin/top";
	}

}
