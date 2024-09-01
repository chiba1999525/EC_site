package com.controller.admin;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Goods;
import com.entity.GoodsGenre;
import com.entity.GoodsImage;
import com.repository.GoodsGenreRepository;
import com.repository.GoodsImageRepository;
import com.repository.GoodsRepository;

@Controller
public class AdminEditGoodsController {
	
	@Autowired//商品
	private GoodsRepository goodsRep;
	
	@Autowired//商品ジャンル
	private GoodsGenreRepository goodsGenreRep;
	
	@Autowired//商品画像
	private GoodsImageRepository goodsImageRep;
	
	
	
	//商品投稿・編集ページ表示処理
	@GetMapping("/admin/edit_goods")
	public String admin_edit_goods(
			@RequestParam(name = "id", required = false, defaultValue = "0") int id,
			Model model,
			RedirectAttributes redirect
			) {
		
		//全商品ジャンルを取り出す
		List<GoodsGenre> goodsGenreAll = goodsGenreRep.findAll();
		
		if (goodsGenreAll.size() == 0) {//商品ジャンルがない場合は
			
			//エラーメッセージリストを定義
			List<String> errorMessages = new ArrayList<>();
			
			errorMessages.add("先にジャンルを定義してください");
			redirect.addFlashAttribute("errorMessages", errorMessages);
			return "redirect:/admin/edit_goods_genre";
		}
		
		//商品インスタンスを定義
		Goods goods = new Goods();
		//商品画像リストを定義
		GoodsImage goodsImage = new GoodsImage();
		
		
		
		//更新の場合
		if (id != 0) {
			//指定のレコードを取り出す
			goods = goodsRep.findById(id);
			//商品画像レコードを取り出す
			goodsImage = goodsImageRep.findByGoodsId(goods.getId());
			
			// 画像ファイル
            byte[] image = goodsImage.getImage();
            // Base64エンコード
            String encodedImage = Base64.getEncoder().encodeToString(image);
            goodsImage.setSimage(encodedImage);
			
		}
		
		
		//モデルを定義
		model.addAttribute("goodsImage", goodsImage);
		model.addAttribute("goodsGenreAll", goodsGenreAll);
		model.addAttribute("goods", goods);
		
		return "/admin/goods/edit_goods";
	}
	
	//商品編集処理
	@PostMapping("/admin/post_edit_goods")
	public String admin_post_goods(
			@Validated @ModelAttribute Goods goodsInput,
			@RequestParam(name = "file", required = false)
			MultipartFile file,
		    @RequestParam(name = "taxPrice", required = false) int taxPrice,
			RedirectAttributes redirect,
			Model model
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
		if (file == null || file.isEmpty()) {
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
			redirect.addFlashAttribute("goods", goodsInput);
			return "redirect:/admin/edit_goods?id=" + goodsInput.getId();
		}
		
		//商品画像インスタンスを定義
		GoodsImage goodsImage = new GoodsImage();
		//現在の日時を定義
		LocalDateTime now = LocalDateTime.now();

		//新規登録処理
		if (goodsInput.getId() == 0) {
			
			//投稿日時を格納
			goodsInput.setNewDateTime(now);
			
			//保存処理
			Goods goodsSave =  goodsRep.save(goodsInput);

			//ファイルアップロード
			try {
		    	 // MultipartFileからバイト配列を取得
	            byte[] goods_byte = file.getBytes();
	            
	            //配列をcustomerテーブルに紐づける
	            goodsImage.setImage(goods_byte);
	        	 //商品idを定義
	            goodsImage.setGoodsId(goodsSave.getId());
	        	 
	        	 //商品画像テーブルを保存
	            					goodsImageRep.save(goodsImage);
	        	 
		    } catch (IOException e)  {
		    	
		    	// エラーが発生した場合の処理を記述
		        e.printStackTrace(); // エラー情報を出力
		    }

		} else {//更新処理
			
			//指定のレコードを取り出す
			Goods editgoods = goodsRep.findById(goodsInput.getId());
			
			//
			editgoods.setGoodsName(goodsInput.getGoodsName());
			editgoods.setText(goodsInput.getText());
			editgoods.setPrice(goodsInput.getPrice());
			editgoods.setTaxPrice(goodsInput.getTaxPrice());
			editgoods.setStock(goodsInput.getStock());
			editgoods.setMaxBuy(goodsInput.getMaxBuy());
			editgoods.setEditDateTime(now);
			
			//商品更新
			Goods goodsSave = goodsRep.save(editgoods);
			
			//既存の商品画像を削除
			goodsImage = goodsImageRep.findByGoodsId(goodsInput.getId());
			goodsImageRep.delete(goodsImage);
			
			//ファイルアップロード
			try {
				
		    	 // MultipartFileからバイト配列を取得
	            byte[] goods_byte = file.getBytes();
	            
	            //配列をcustomerテーブルに紐づける
	        	 goodsImage.setImage(goods_byte);
	        	 //商品idを定義
	        	 goodsImage.setGoodsId(goodsSave.getId());
	        	 
	        	 //商品画像テーブルを保存
	        	 goodsImageRep.save(goodsImage);

		    } catch (IOException e)  {
		    	// エラーが発生した場合の処理を記述
		        e.printStackTrace(); // エラー情報を出力
		    }
		}

		//保存成功メッセージの定義
    	String successMessages = "投稿成功しました";
		//投稿成功メッセージ
		redirect.addFlashAttribute("successMessages", successMessages);
		
		return "redirect:/admin/top";
	}

}
