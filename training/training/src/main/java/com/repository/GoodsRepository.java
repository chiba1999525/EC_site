package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Goods;


@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

	
	// カスタムクエリメソッドを追加する場合はここに定義します
    Goods findByIdAndGenreIdAndGoodsNameAndTextAndPriceAndTaxPriceAndStockAndMaxBuyAndBuyNumAndImage
    (int id, int genreId, String goodsName, String text, int price,int taxPrice, int stock,int maxBuy, int buyNum, byte[] image);
    
    Goods findById(int id);
    
    
}
