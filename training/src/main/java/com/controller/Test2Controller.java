package com.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.City;
import com.form.Test2Class;
import com.repository.CityRepository;
/**
 *　test2.html用のコントローラー
 */
@Controller
public class Test2Controller {
	
	// Cityテーブル用リポジトリ
	@Autowired private CityRepository cityRep;
	// 画面名
	private static final String PAGE_NAME = "test2";

	// アクセスURL定義(アクセスURL:http://localhost:8080/test2)
	@RequestMapping("/test2")
	// トランザクション
	@Transactional
	public String disp(Model model) {
		
	/* データベースアクセス処理 start */
		
		/* select */
		
		// カラム[ID]で、単一レコード取得
		City cityById = cityRep.findById(6).orElse(null);
		// カラム[Name]で、単一レコード取得
		City cityByName = cityRep.findByName("Kabul");
		// カラム[Countrycode]で、複数レコード取得
		List<City> citiesByCountrycode = cityRep.findByCountrycode("AFG");
		// カラム[Name]で、レコード存在可否を取得
		boolean exiestsCityByName = cityRep.existsByName("Kabul");
		
		/* update */
		
		// ID:5 で取得した単一レコードデータの memo　に"テスト"を設定
		cityById.setMemo("test");
		// DBに反映(該当IDが存在しない場合はレコード追加)
		cityRep.save(cityById);
		
		/* insert */
		
		// 追加データ生成
		City cityInsert = new City();
		// 各カラムに値を設定
		cityInsert.setName("test");
		cityInsert.setCountrycode("ABW");
		cityInsert.setDistrict("test");
		cityInsert.setPopulation(1);
		cityInsert.setMemo("test");
		// DBに反映・追加後にIDが割り振られた対象インスタンスを取得
		cityInsert = cityRep.save(cityInsert);
		
		/* delete */
		
		// 対象IDに該当するレコードを削除
		// cityRep.delete(cityById);
		
	/* データベースアクセス処理 end */
		
		// インスタンス生成
		Test2Class test2Class = new Test2Class();
		// モデル設定
		model.addAttribute("Test2Class", test2Class);
		// 画面遷移
		return PAGE_NAME;
	}
	
	// POST・GETリクエストの両方を許容
	@RequestMapping("/input_3")
	public String input3(HttpServletRequest req, Model model,
						// クラスとして受け取る
						Test2Class test2Class) {
		
		// 入力値
		String input3 = test2Class.getInput_3();
		// クラスの表示値に設定
		test2Class.setDisp_3("出力:" + input3);
		
		// モデル設定
		model.addAttribute("Test2Class", test2Class);
		// 画面遷移
		return PAGE_NAME;
	}
	
}
