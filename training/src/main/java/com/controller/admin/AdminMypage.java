package com.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.User;
import com.repository.UserRepository;

@Controller
public class AdminMypage {
	
	@Autowired
	private UserRepository userRep;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	//マイページ表示
	@RequestMapping("/admin/mypage")
	public String admin_mypage(
			Model model
			) {
		
		// ログイン中のuser_nameを取得
        final String user_name = SecurityContextHolder.getContext().getAuthentication().getName();
        //ログイン中のuserレコードを取得
        Optional<User> userList = userRep.findByUsername(user_name);
        User userLogin = userList.get();
        
        //パスワードを空にする
        userLogin.setPassword("");
        
        model.addAttribute("userLogin", userLogin);
		
		return "admin/mypage";
	}
	
	//マイページ変更処理
	@RequestMapping("/admin/post_mypage")
	public String admin_post_mypage(
			@ModelAttribute User userInput,
			@RequestParam(name = "nowPassword", required = false) String nowPassword,
			@RequestParam(name = "newPassword", required = false) String newPassword,
			RedirectAttributes redirect
			) {
		
		// ログイン中のuser_nameを取得
        final String user_name = SecurityContextHolder.getContext().getAuthentication().getName();
        //ログイン中のuserレコードを取得
        Optional<User> userList = userRep.findByUsername(user_name);
        User userLogin = userList.get();
        
        //エラーリストを定義
        List<String> errorMessages = new ArrayList<String>();
        List<String> successMessages = new ArrayList<String>();
       
        //user_name　空チェック
        if (userInput.getUsername().isEmpty()) {
        	
        	errorMessages.add("user_nameを入力してください");
        	
        } else {
        	//user_nameに変更がある場合
        	if (userInput.getUsername() != userLogin.getUsername()) {
        		//user_nameを更新
        		userLogin.setUsername(userInput.getUsername());
        		successMessages.add("user_nameを変更しました");
        	}	
        }
        
        //新しいパスワード入力されている場合
        if (!newPassword.isEmpty()) {
        	//パスワードをハッシュ化
        	String newPasswordEn = passwordEncoder.encode(newPassword);
        	String nowPasswordEn = passwordEncoder.encode(nowPassword);
        	
        	//現在のパスワードが一致
        	if (nowPasswordEn.equals(userLogin.getPassword())) {

        		//現在・新しいパスワードが異なる場合
        		if(!newPassword.equals(nowPassword)) {
        			//パスワードを更新
        			userLogin.setPassword(newPasswordEn);
        			successMessages.add("パスワードを変更しました");
        		}
        	} 
        	
        	errorMessages.add("現在のパスワードが一致しません");
        	
        }
        
        userRep.save(userLogin);
        
        redirect.addFlashAttribute("errorMessages", errorMessages);
        redirect.addFlashAttribute("successMessages", successMessages);
        
		
		return "redirect:/admin/mypage";
	}
	
	
	

}
