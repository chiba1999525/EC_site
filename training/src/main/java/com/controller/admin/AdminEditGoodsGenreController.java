package com.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.GoodsGenre;
import com.repository.GoodsGenreRepository;

@Controller
public class AdminEditGoodsGenreController {
	
	@Autowired
	private GoodsGenreRepository ggRep;
	
	//商品ジャンル表示処理
	@RequestMapping("/admin/edit_goods_genre")
	public String admin_edit_goods_genre(
			@RequestParam(name = "id", required = false, defaultValue = "0") int id,
			Model model,
			RedirectAttributes redirect
			) {
		
		//商品ジャンルインスタンスを定義
		GoodsGenre gg = new GoodsGenre();
		
		//編集の場合
		if (id != 0) {
			//指定のレコードを入れる
			gg = ggRep.findById(id);
			
			//レコードが存在しない場合
			if (gg == null) {
				//エラーメッセージのリストを定義
		    	List<String> errorMessages = new ArrayList<String>();
		    	errorMessages.add("ジャンルが存在しません");
				redirect.addFlashAttribute("errorMessages", errorMessages);
				//ジャンル新規ページに遷移
				return "redirect:/admin/edit_goods_genre";	
			}
			
		}
		
		//商品ジャンルすべて取り出す
		List<GoodsGenre> ggAll = ggRep.findAll();
		
		//商品ジャンルが存在しない場合
		if (ggAll.size() == 0) {
			ggAll = null;				
		}
		
		//modelを定義
		model.addAttribute("goodsGenre", gg);
		model.addAttribute("ggAll", ggAll);
		
		//商品ジャンルテンプレート
		return "/admin/goods_genre/edit_goods_genre";
	}
	
	//商品ジャンル編集処理
	@PostMapping("/admin/post_edit_goods_genre")
	public String admin_post_edit_goods_genre(
			@Validated @ModelAttribute GoodsGenre goodsGenreInput,
			RedirectAttributes redirect
			) {
		
		//エラーメッセージのリストを定義
    	List<String> errorMessages = new ArrayList<String>();
    	
		//ジャンル名空チェック
		if (goodsGenreInput.getGenreName().isEmpty()) {
			errorMessages.add("ジャンル名を入力してください");
		}
		
		//エラーがある場合
		if (!errorMessages.isEmpty()){
			
			redirect.addFlashAttribute("errorMessages", errorMessages);
			//
			return "redirect:/admin/edit_goods_genre?id=" + goodsGenreInput.getId();
		}
		
		//新規の場合
		if (goodsGenreInput.getId() == 0) {
			//入力データを保存
			ggRep.save(goodsGenreInput);
			
		} else {//更新の場合
			//更新されたIdと紐づくレコードを取り出す
			GoodsGenre goodsGenre = ggRep.findById(goodsGenreInput.getId());
			//ジャンル名を更新
			goodsGenre.setGenreName(goodsGenreInput.getGenreName());
			//レコードを更新
			ggRep.save(goodsGenre);
		}
		
		//保存成功メッセージの定義
    	String successMessages = "投稿成功しました";
		
		//投稿成功メッセージ
		redirect.addFlashAttribute("successMessages", successMessages);
		//topページへリダイレクト
		return "redirect:/admin/edit_goods_genre";
	}
	
	//商品ジャンル削除処理
	@RequestMapping("/admin/delete_goods_genre/{id}")
	public String admin_delete_goods_genre(
			@PathVariable("id") int id
			) {
		
		//指定の商品ジャンルを削除
		ggRep.deleteById(id);
		
		return "redirect:/admin/edit_goods_genre";
	}

}
