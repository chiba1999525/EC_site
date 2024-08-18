package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "goods_genre")
public class GoodsGenre {//商品ジャンルテーブル
	
	@Id
	@Column
	private int id;
	
	@Column(name = "genre_name")
	private String genreName;//ジャンル名

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

}
