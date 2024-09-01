package com.entity;

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
@Table(name = "ecsite_goods_image")
public class GoodsImage {//商品画像

	@Id
	//id自動生成
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "goods_id")
	private int goodsId;// 商品id

	@Column
	private byte[] image;// 商品画像
	
	//(mysqlのカラムにない一時的データ)
  	@Transient 
  	private String simage;

	public String getSimage() {
		return simage;
	}

	public void setSimage(String simage) {
		this.simage = simage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
