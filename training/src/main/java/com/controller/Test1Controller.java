package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *　test1.html用のコントローラー
 */
@Controller
public class Test1Controller {
	
	// 画面名
	private static final String PAGE_NAME = "test1";
	
	// アクセスURL定義(アクセスURL:http://localhost:8080/test1)
	@RequestMapping("/test1")
	public String disp(Model model) {
		
		// モデル設定
		model.addAttribute("name", "田中");
		// 画面遷移
		return PAGE_NAME;
	}
	
	// POST:リクエストURLにパラメータが付与されない
	@PostMapping("/input_1")
	public String input1(HttpServletRequest req, Model model) {
		
		// 入力値
		String input1 = req.getParameter("input_1");
		// モデル設定
		req.setAttribute("disp_1", input1);
		// 画面遷移
		return PAGE_NAME;
	}

	// GET:リクエストURLにパラメータが付与される
	@GetMapping("/input_2")
	public String input2(HttpServletRequest req, Model model,
						// 入力値
						@RequestParam("input_2") String input2) {
		// モデル設定
		req.setAttribute("disp_2", input2);
		// 画面遷移
		return PAGE_NAME;
	}
}
