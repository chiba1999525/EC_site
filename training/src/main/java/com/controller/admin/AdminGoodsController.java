package com.controller.admin;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Goods;
import com.entity.GoodsGenre;
import com.entity.GoodsImage;
import com.repository.GoodsGenreRepository;
import com.repository.GoodsImageRepository;
import com.repository.GoodsRepository;
import com.rule.Rule;

@Controller
public class AdminGoodsController {
	
	@Autowired
	private GoodsRepository goodsRep;
	
	@Autowired
	private GoodsGenreRepository goodsGenreRep;
	
	@Autowired
	private GoodsImageRepository goodsImageRep;
	
	//admin_商品一覧画面
	@GetMapping("/admin/all_goods")
	public String admin_all_goods(
			Model model
			) {
		
		//商品情報をすべて取り出す
		List<Goods> goodsAll = goodsRep.findAll();
		
		for (Goods goods : goodsAll) {
			
			//ジャンルIDからジャンル名を取り出す
			GoodsGenre goodsGenre = goodsGenreRep.findById(goods.getGenreId());
			//商品レコードにジャンル名を入れる
			goods.setGenreName(goodsGenre.getGenreName());
			
			//投稿日時をString型に変換
			String StringNewDate = Rule.StringDate(goods.getNewDateTime());
			goods.setSNewDateTime(StringNewDate);
			
			//更新日時をString型に変換
			if (goods.getEditDateTime() != null) {//更新日時をString型に変換
				String StringEditDate = Rule.StringDate(goods.getEditDateTime());
				goods.setSEditDateTime(StringEditDate);
			}
			
		}
		
		//商品が存在しない場合
		if (goodsAll.size() == 0) {
			goodsAll = null;
		}
		
		model.addAttribute("goodsAll", goodsAll);
		
		return "admin/goods/all_goods";
	}
	
	//admin　商品詳細画面
	@GetMapping("/admin/goods/{goodsName}")
	public String admin_show_goods(
			@PathVariable(name = "goodsName") String goodsName,
			Model model,
			RedirectAttributes redirect
			) {
		
		//商品名から指定のレコードを取り出す
		Goods goods = goodsRep.findByGoodsName(goodsName);
		
		//商品インスタンスをnullの場合
		if (goods == null) {//商品一覧画面に遷移
			return "redirect:/admin/all_goods";
		}
		
		//最大購入数から購入数リストを作成
		
		//最大購入数が在庫より多い場合
		int num = 0;
		if (goods.getMaxBuy() > goods.getStock()) {
			num = goods.getStock();//在庫の数を定義
		}
		else {
			num = goods.getStock();//最大購入数を定義
		}
		
		List<String> buyList = new ArrayList<>();
		for(int i = 1; i <= num ; i++) {
			int buy = i;
			buyList.add(String.valueOf(buy));
		}
		model.addAttribute("buyList", buyList);
		
		//商品レコードに紐づく商品ジャンルを取り出す
		GoodsGenre goodsGenre = goodsGenreRep.findById(goods.getGenreId());
		goods.setGenreName(goodsGenre.getGenreName());
		
		//商品レコードに紐づく商品画像を取り出す
		GoodsImage goodsImage = goodsImageRep.findByGoodsId(goods.getId());
		
    	// 画像ファイル
        byte[] image = goodsImage.getImage();
        if (image != null) {
            // Base64エンコード
            String encodedImage = Base64.getEncoder().encodeToString(image);
            goodsImage.setSimage(encodedImage);
        }
		
        
		model.addAttribute("goodsImage", goodsImage);
		model.addAttribute("goods", goods);
		
		return "/admin/goods/show_goods";
	}
	
	//商品削除処理
	@RequestMapping("/admin/delete_goods/{id}")
	public String admin_delete_goods(
			@PathVariable(name = "id")int id
			) {
		//商品idに紐づく商品レコードを取り出す
		Goods goods = goodsRep.findById(id);
		
		//商品idに紐づく商品画像を取り出す
		GoodsImage goodsImage = goodsImageRep.findByGoodsId(id);
		
		//削除処理
		goodsRep.delete(goods);
		goodsImageRep.delete(goodsImage);
		
		return "redirect:/admin/all_goods";
		
	}
	
}
