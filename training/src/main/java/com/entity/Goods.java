package com.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name = "ecsite_goods")
public class Goods {//商品テーブル
	
	@Id
	//id自動生成
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(name = "genre_id")
	private int genreId;	//ジャンルid
	
	@Transient//DBに保存しない
	private String genreName;//ジャンル名
	
	@Column(name = "goods_name")
	private String goodsName;//商品名
	
	@Column
	private String text;//商品説明
	
	@Column
	private int price;//価格
	
	@Column(name = "tax_price")
	private int taxPrice;//税込み価格
	
	@Column
	private int stock; //在庫
	
	@Column(name = "max_buy")
	private int maxBuy;		//最大購入数
	
	@Column(name = "buy_num")
	private int buyNum;//売上個数
	
	@Column(name = "new_dateTime")//投稿日時
	private LocalDateTime newDateTime;
	
	@Column(name = "edit_dateTime")//更新日時
	private LocalDateTime editDateTime;
	
	@Transient//
	private String SNewDateTime;
	
	@Transient//
	private String SEditDateTime;

	public String getSNewDateTime() {
		return SNewDateTime;
	}


	public void setSNewDateTime(String sNewDateTime) {
		SNewDateTime = sNewDateTime;
	}


	public String getSEditDateTime() {
		return SEditDateTime;
	}


	public void setSEditDateTime(String sEditDateTime) {
		SEditDateTime = sEditDateTime;
	}


	public LocalDateTime getEditDateTime() {
		return editDateTime;
	}


	public void setEditDateTime(LocalDateTime editDateTime) {
		this.editDateTime = editDateTime;
	}


	public LocalDateTime getNewDateTime() {
		return newDateTime;
	}


	public void setNewDateTime(LocalDateTime newDateTime) {
		this.newDateTime = newDateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(int taxPrice) {
		this.taxPrice = taxPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getMaxBuy() {
		return maxBuy;
	}

	public void setMaxBuy(int maxBuy) {
		this.maxBuy = maxBuy;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	

}
