package com.gs.learn.senior.bean;

public class CartInfo {
	public long rowid;
	public int xuhao;
	public long goods_id;
	public int count;
	public String update_time;
	public GoodsInfo goods;
	
	public CartInfo() {
		rowid = 0l;
		xuhao = 0;
		goods_id = 0l;
		count = 0;
		update_time = "";
		goods = new GoodsInfo();
	}
}
