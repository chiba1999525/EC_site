package com.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Goods;
import com.entity.GoodsGenre;
import com.entity.GoodsImage;
import com.repository.GoodsGenreRepository;
import com.repository.GoodsRepository;

@Controller
public class AdminEditGoodsController {
	
	@Autowired//商品
	private GoodsRepository goodsRep;
	
	@Autowired//商品ジャンル
	private GoodsGenreRepository goodsGenreRep;
	
	//商品投稿・編集ページ表示処理
	@RequestMapping("/admin/edit_goods")
	public String admin_edit_goods(
			@RequestParam(name = "id", required = false, defaultValue = "0") int id,
			Model model
			) {
		
		//商品インスタンスを定義
		Goods goods = new Goods();
		
		if (id != 0) {
			//指定のレコードを取り出す
			goods = goodsRep.findById(id);
		}
		//全商品ジャンルを取り出す
		List<GoodsGenre> goodsGenreAll = goodsGenreRep.findAll();
		
		//商品画像リストを定義
		GoodsImage goodsImage = new GoodsImage();
		
		//モデルを定義
		model.addAttribute("goodsGenreAll", goodsGenreAll);
		model.addAttribute("goodsImageList", goodsImage);
		model.addAttribute("goods", goods);
		
		return "/admin/goods/edit_goods";
	}
	
	//商品編集処理
	@RequestMapping("/admin/post_edit_goods")
	public String admin_post_goods(
			@Validated @ModelAttribute Goods goodsInput,
			@RequestParam(name = "files", required = false) List<MultipartFile> files,
		    @RequestParam(name = "taxPrice", required = false) int taxPrice,
			RedirectAttributes redirect
			) {
		
		//エラーメッセージリストを定義
		List<String> errorMessages = new ArrayList<>();
		
		//商品ジャンル　空チェック
		if (goodsInput.getGenreId() == 0) {
			errorMessages.add("ジャンルを選択してください");
		}
		
		//商品名　空チェック
		if (goodsInput.getGoodsName().isEmpty()) {
			errorMessages.add("商品名を入力してください");
		}
		
		//商品説明　空チェック
		if (goodsInput.getText().isEmpty()) {
			errorMessages.add("説明を入力してください");
		}
		
		//画像　空チェック
		if (files.isEmpty()) {
			errorMessages.add("画像をアップロードしてください");
		}
				
		//価格　空チェック
		if (goodsInput.getPrice() == 0) {
			errorMessages.add("価格を入力してください");
		}
		
		//商品数　空チェック
		if (goodsInput.getStock() == 0) {
			errorMessages.add("商品数を入力してください");
		}
		
		//最大購入数　空チェック
		if (goodsInput.getMaxBuy() == 0) {
			errorMessages.add("最大購入数を入力してください");
		}

		//エラーがある場合
		if (!errorMessages.isEmpty()) {
			//リダイレクト
			redirect.addFlashAttribute("errorMessages", errorMessages);
			
			return "redirect:/admin/edit_goods";
		}
		
		//ファイルアップロード
		for (MultipartFile file : files) {
			try {
		    	 // MultipartFileからバイト配列を取得
	            byte[] goods_byte = file.getBytes();
	            //配列をcustomerテーブルに紐づける
	        	goodsInput.setImage(goods_byte);	    	
		    } catch (IOException e)  {
		    	// エラーが発生した場合の処理を記述
		        e.printStackTrace(); // エラー情報を出力
		    }
		}
	    
		
		//新規登録処理
		if (goodsInput.getId() == 0) {
			//保存処理
			goodsRep.save(goodsInput);
			
		} else {//更新処理
			//指定のレコードを取り出す
			Goods editgoods = goodsRep.findById(goodsInput.getId());
			
			editgoods.setGoodsName(goodsInput.getGoodsName());
			editgoods.setText(goodsInput.getText());
			editgoods.setImage(goodsInput.getImage());
			editgoods.setPrice(goodsInput.getPrice());
			editgoods.setTaxPrice(goodsInput.getTaxPrice());
			editgoods.setMaxBuy(goodsInput.getMaxBuy());
			
			//更新
			goodsRep.save(editgoods);
			
		}
		
		//保存成功メッセージの定義
    	String successMessages = "投稿成功しました";
		//投稿成功メッセージ
		redirect.addFlashAttribute("successMessages", successMessages);
		
		return "redirect:/admin/top";
	}

}
