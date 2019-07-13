package com.gs.learn.senior;

import java.util.ArrayList;

import com.gs.learn.MainApplication;
import com.gs.learn.senior.adapter.GoodsAdapter;
import com.gs.learn.senior.adapter.GoodsAdapter.addCartListener;
import com.gs.learn.senior.bean.CartInfo;
import com.gs.learn.senior.bean.GoodsInfo;
import com.gs.learn.senior.database.CartDBHelper;
import com.gs.learn.senior.database.GoodsDBHelper;
import com.gs.learn.senior.util.DateUtil;
import com.gs.learn.senior.util.SharedUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class ShoppingChannelActivity extends AppCompatActivity implements 
		OnClickListener, addCartListener {

	private final static String TAG = "ShoppingChannelActivity";
	private TextView tv_title;
	private TextView tv_count;
	private GridView gv_channel;
	private int mCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_channel_senior);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_count = (TextView) findViewById(R.id.tv_count);
		gv_channel = (GridView) findViewById(R.id.gv_channel);
		findViewById(R.id.iv_cart).setOnClickListener(this);
		tv_title.setText("手机商场");
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_cart) {
			Intent intent = new Intent(this, ShoppingCartActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void addToCart(long goods_id) {
		mCount++;
		tv_count.setText(""+mCount);
		SharedUtil.getIntance(this).writeShared("count", ""+mCount);
		CartInfo info = mCartHelper.queryByGoodsId(goods_id);
		if (info != null) {
			info.count++;
			info.update_time = DateUtil.getNowDateTime("");
			mCartHelper.update(info);
		} else {
			info = new CartInfo();
			info.goods_id = goods_id;
			info.count = 1;
			info.update_time = DateUtil.getNowDateTime("");
			mCartHelper.insert(info);
		}
	}

	private GoodsDBHelper mGoodsHelper;
	private CartDBHelper mCartHelper;
	
	@Override
	protected void onResume() {
		super.onResume();
		mCount = Integer.parseInt(SharedUtil.getIntance(this).readShared("count", "0"));
		tv_count.setText(""+mCount);
		mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
		mGoodsHelper.openReadLink();
		mCartHelper = CartDBHelper.getInstance(this, 1);
		mCartHelper.openWriteLink();
		//展示商品列表
		showGoods();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mGoodsHelper.closeLink();
		mCartHelper.closeLink();
	}

	private void showGoods() {
		if (MainApplication.getInstance().mIconMap.size() <= 0) {
			ShoppingCartActivity.downloadGoods(this, "false", mGoodsHelper);
		}
		ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
		GoodsAdapter adapter = new GoodsAdapter(this, goodsArray, this);
		gv_channel.setAdapter(adapter);
		gv_channel.setOnItemClickListener(adapter);
	}

}
