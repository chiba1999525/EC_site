package com.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.User;
import com.repository.UserRepository;

@Controller
public class AdminTopController{
	
	@Autowired
	private UserRepository userRep;
	
	@RequestMapping("/admin/top")//adminTopページ
	public String admin_top(
			Model model
			) {
		
		// これだけでユーザ名を取得可能！
        final String name = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Optional<User> login_userO = userRep.findByUsername(name);
        
        User user = login_userO.get();
		
		model.addAttribute("user", user);
		
		return "admin/top";
	}

}
