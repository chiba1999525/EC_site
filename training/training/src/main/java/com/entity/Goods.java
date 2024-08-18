package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name = "goods")
public class Goods {//商品テーブル
	
	@Id
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
	
	@Column(name = "image")//商品画像
	private byte[] image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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
