package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.GoodsImage;

@Repository
public interface GoodsImageRepository extends JpaRepository<GoodsImage, Integer> {

	
	// カスタムクエリメソッドを追加する場合はここに定義します
    GoodsImage findByIdAndGoodsIdAndImage(int id,int goodsId, byte[] image);
    
    List<GoodsImage> findById(int id);

	GoodsImage findByGoodsId(int id);
    
    
}
