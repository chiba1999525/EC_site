package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Goods;
import com.entity.GoodsGenre;

@Repository
public interface GoodsGenreRepository extends JpaRepository<GoodsGenre, Integer> {

	
	// カスタムクエリメソッドを追加する場合はここに定義します
    Goods findByIdAndGenreName
    (int id, String genreName);
    
    GoodsGenre findById(int id);
    
    
}
