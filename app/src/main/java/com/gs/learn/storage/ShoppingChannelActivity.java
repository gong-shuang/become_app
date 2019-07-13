package com.gs.learn.storage;

import java.util.ArrayList;

import com.gs.learn.MainApplication;
import com.gs.learn.storage.bean.CartInfo;
import com.gs.learn.storage.bean.GoodsInfo;
import com.gs.learn.storage.database.CartDBHelper;
import com.gs.learn.storage.database.GoodsDBHelper;
import com.gs.learn.common.util.DateUtil;
import com.gs.learn.common.util.SharedUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/1.
 */
public class ShoppingChannelActivity extends AppCompatActivity implements OnClickListener {

	private final static String TAG = "ShoppingChannelActivity";
	private TextView tv_title;
	private TextView tv_count;
	private LinearLayout ll_channel;
	private int mCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_channel);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_count = (TextView) findViewById(R.id.tv_count);
		ll_channel = (LinearLayout) findViewById(R.id.ll_channel);
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

	private LayoutParams mFullParams, mHalfParams;
	private void showGoods() {
		ll_channel.removeAllViews();
		mFullParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mHalfParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		mHalfParams.setMargins(2, 2, 2, 2);
		LinearLayout ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);
		ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
		int i=0;
		for (; i<goodsArray.size(); i++) {
			final GoodsInfo info = goodsArray.get(i);
			LinearLayout ll_goods = newLinearLayout(LinearLayout.VERTICAL, 1);
			ll_goods.setBackgroundColor(Color.WHITE);
			//添加商品标题
			TextView tv_name = new TextView(this);
			tv_name.setLayoutParams(mFullParams);
			tv_name.setGravity(Gravity.CENTER);
			tv_name.setText(info.name);
			tv_name.setTextColor(Color.BLACK);
			tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
			ll_goods.addView(tv_name);
			//添加商品小图
			ImageView iv_thumb = new ImageView(this);
			iv_thumb.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, 200));
			iv_thumb.setScaleType(ScaleType.FIT_CENTER);
			iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.rowid));
			iv_thumb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ShoppingChannelActivity.this, ShoppingDetailActivity.class);
					intent.putExtra("goods_id", info.rowid);
					startActivity(intent);
				}
			});
			ll_goods.addView(iv_thumb);
			//添加商品价格
			LinearLayout ll_bottom = newLinearLayout(LinearLayout.HORIZONTAL, 0);
			TextView tv_price = new TextView(this);
			tv_price.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
			tv_price.setGravity(Gravity.CENTER);
			tv_price.setText(""+(int)info.price);
			tv_price.setTextColor(Color.RED);
			tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			ll_bottom.addView(tv_price);
			//添加购物车按钮
			Button btn_add = new Button(this);
			btn_add.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 3));
			btn_add.setGravity(Gravity.CENTER);
			btn_add.setText("加入购物车");
			btn_add.setTextColor(Color.BLACK);
			btn_add.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			btn_add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addToCart(info.rowid);
					Toast.makeText(ShoppingChannelActivity.this, 
							"已添加一部"+info.name+"到购物车", Toast.LENGTH_SHORT).show();
				}
			});
			ll_bottom.addView(btn_add);
			ll_goods.addView(ll_bottom);
			//添加商品项目
			ll_row.addView(ll_goods);
			if (i%2 == 1) {
				ll_channel.addView(ll_row);
				ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);
			}
		}
		if (i%2 == 0) {
			ll_row.addView(newLinearLayout(LinearLayout.VERTICAL, 1));
			ll_channel.addView(ll_row);
		}
	}

	private LinearLayout newLinearLayout(int orientation, int weight) {
		LinearLayout ll_new = new LinearLayout(this);
		ll_new.setLayoutParams((weight==0)?mFullParams:mHalfParams);
		ll_new.setOrientation(orientation);
		return ll_new;
	}

}
