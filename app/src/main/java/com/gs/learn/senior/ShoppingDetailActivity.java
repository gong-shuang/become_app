package com.gs.learn.senior;

import com.gs.learn.senior.bean.CartInfo;
import com.gs.learn.senior.bean.GoodsInfo;
import com.gs.learn.senior.database.CartDBHelper;
import com.gs.learn.senior.database.GoodsDBHelper;
import com.gs.learn.senior.util.DateUtil;
import com.gs.learn.senior.util.SharedUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class ShoppingDetailActivity extends AppCompatActivity implements OnClickListener {

	private TextView tv_title;
	private TextView tv_count;
	private TextView tv_goods_price;
	private TextView tv_goods_desc;
	private ImageView iv_goods_pic;
	private int mCount;
	private long mGoodsId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_detail);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_goods_price = (TextView) findViewById(R.id.tv_goods_price);
		tv_goods_desc = (TextView) findViewById(R.id.tv_goods_desc);
		iv_goods_pic = (ImageView) findViewById(R.id.iv_goods_pic);
		findViewById(R.id.iv_cart).setOnClickListener(this);
		findViewById(R.id.btn_add_cart).setOnClickListener(this);
		
		mCount = Integer.parseInt(SharedUtil.getIntance(this).readShared("count", "0"));
		tv_count.setText(""+mCount);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_cart) {
			Intent intent = new Intent(this, ShoppingCartActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_add_cart) {
			//添加购物车数据库
			addToCart(mGoodsId);
			Toast.makeText(this, "成功添加至购物车", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void addToCart(long goods_id) {
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
		mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
		mGoodsHelper.openReadLink();
		mCartHelper = CartDBHelper.getInstance(this, 1);
		mCartHelper.openWriteLink();
		//展示商品信息
		showDetail();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mGoodsHelper.closeLink();
		mCartHelper.closeLink();
	}
	
	private void showDetail() {
		mGoodsId = getIntent().getLongExtra("goods_id", 0l);
		if (mGoodsId > 0) {
			GoodsInfo info = mGoodsHelper.queryById(mGoodsId);
			tv_title.setText(info.name);
			tv_goods_desc.setText(info.desc);
			tv_goods_price.setText(""+info.price);
			Bitmap pic = BitmapFactory.decodeFile(info.pic_path);
			iv_goods_pic.setImageBitmap(pic);
		}
	}

}
