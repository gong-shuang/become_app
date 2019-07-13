package com.gs.learn.senior;

import java.util.ArrayList;

import com.gs.learn.MainApplication;
import com.gs.learn.senior.adapter.CartAdapter;
import com.gs.learn.senior.bean.CartInfo;
import com.gs.learn.senior.bean.GoodsInfo;
import com.gs.learn.senior.database.CartDBHelper;
import com.gs.learn.senior.database.GoodsDBHelper;
import com.gs.learn.senior.util.FileUtil;
import com.gs.learn.senior.util.SharedUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class ShoppingCartActivity extends Activity implements 
		OnClickListener, OnItemClickListener, OnItemLongClickListener {

	private final static String TAG = "ShoppingCartActivity";
	private ImageView iv_menu;
	private TextView tv_title;
	private TextView tv_count;
	private TextView tv_total_price;
	private LinearLayout ll_content;
	private LinearLayout ll_empty;
	private ListView lv_cart;
	private int mCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shopping_cart_senior);
		iv_menu = (ImageView) findViewById(R.id.iv_menu);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_total_price = (TextView) findViewById(R.id.tv_total_price);
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		ll_empty = (LinearLayout) findViewById(R.id.ll_empty);
		lv_cart = (ListView) findViewById(R.id.lv_cart);
		
		iv_menu.setOnClickListener(this);
		findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
		findViewById(R.id.btn_settle).setOnClickListener(this);
		iv_menu.setVisibility(View.VISIBLE);
		tv_title.setText("购物车");
	}
	
	//显示购物车图标中的商品数量
	private void showCount(int count) {
		mCount = count;
		tv_count.setText(""+mCount);
		if (mCount == 0) {
			ll_content.setVisibility(View.GONE);
			ll_empty.setVisibility(View.VISIBLE);
		} else {
			ll_content.setVisibility(View.VISIBLE);
			ll_empty.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_menu) {
			openOptionsMenu();
		} else if (v.getId() == R.id.btn_shopping_channel) {
			Intent intent = new Intent(this, ShoppingChannelActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_settle) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("结算商品");
			builder.setMessage("客官抱歉，支付功能尚未开通，请下次再来");
			builder.setPositiveButton("我知道了", null);
			builder.create().show();
		}
	}

	private CartInfo mCurrentGood;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mCurrentGood = mCartArray.get(position);
		goDetail(mCurrentGood.goods_id);
	}

	private View mCurrentView;
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		mCurrentGood = mCartArray.get(position);
		mCurrentView = view;
		mHandler.postDelayed(mPopupMenu, 100);
		return true;
	}
	
	private Handler mHandler = new Handler();
	private Runnable mPopupMenu = new Runnable() {
		@Override
		public void run() {
			lv_cart.setOnItemLongClickListener(null);
			registerForContextMenu(mCurrentView);
			openContextMenu(mCurrentView);
			unregisterForContextMenu(mCurrentView);
			lv_cart.setOnItemLongClickListener(ShoppingCartActivity.this);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_cart, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_shopping) {
			Intent intent = new Intent(this, ShoppingChannelActivity.class);
			startActivity(intent);
		} else if (id == R.id.menu_clear) {
			//清空购物车数据库
			mCartHelper.deleteAll();
			SharedUtil.getIntance(this).writeShared("count", "0");
			showCount(0);
			ll_content.setVisibility(View.GONE);
			Toast.makeText(this, "购物车已清空", Toast.LENGTH_SHORT).show();
		} else if (id == R.id.menu_return) {
			finish();
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.menu_goods, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_detail) {
			//跳转到查看商品详情页面
			goDetail(mCurrentGood.goods_id);
		} else if (id == R.id.menu_delete) {
			//从购物车删除商品的数据库操作
			mCartHelper.delete("goods_id="+mCurrentGood.goods_id);
			//更新购物车中的商品数量
			int left_count = mCount-mCurrentGood.count;
			SharedUtil.getIntance(this).writeShared("count", ""+left_count);
			showCount(left_count);
			Toast.makeText(this, "已从购物车删除"+mCurrentGood.goods.name, Toast.LENGTH_SHORT).show();
			showCart();
		}
		return true;
	}
	
	private void goDetail(long rowid) {
		Intent intent = new Intent(this, ShoppingDetailActivity.class);
		intent.putExtra("goods_id", rowid);
		startActivity(intent);
	}

	private GoodsDBHelper mGoodsHelper;
	private CartDBHelper mCartHelper;
	private String mFirst = "true";
	
	@Override
	protected void onResume() {
		super.onResume();
		mCount = Integer.parseInt(SharedUtil.getIntance(this).readShared("count", "0"));
		showCount(mCount);
		mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
		mGoodsHelper.openWriteLink();
		mCartHelper = CartDBHelper.getInstance(this, 1);
		mCartHelper.openWriteLink();
		mFirst = SharedUtil.getIntance(this).readShared("first", "true");
		downloadGoods(this, mFirst, mGoodsHelper);
		SharedUtil.getIntance(this).writeShared("first", "false");
		showCart();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mGoodsHelper.closeLink();
		mCartHelper.closeLink();
	}

	private ArrayList<CartInfo> mCartArray = new ArrayList<CartInfo>();
	private void showCart() {
		mCartArray = mCartHelper.query("1=1");
		if (mCartArray==null || mCartArray.size()<=0) {
			return;
		}
		for (int i=0; i<mCartArray.size(); i++) {
			CartInfo info = mCartArray.get(i);
			GoodsInfo goods = mGoodsHelper.queryById(info.goods_id);
			info.goods = goods;
			mCartArray.set(i, info);
		}
		CartAdapter adapter = new CartAdapter(this, mCartArray);
		lv_cart.setAdapter(adapter);
		lv_cart.setOnItemClickListener(this);
		lv_cart.setOnItemLongClickListener(this);
		refreshTotalPrice();
	}
	
	private void refreshTotalPrice() {
		int total_price = 0;
		for (CartInfo info : mCartArray) {
			total_price += info.goods.price*info.count;
		}
		tv_total_price.setText(""+total_price);
	}
	
	//模拟网络数据，初始化数据库中的商品信息
	public static void downloadGoods(Context ctx, String isFirst, GoodsDBHelper helper) {
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
		if (isFirst.equals("true")) {
			ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList();
			for (int i=0; i<goodsList.size(); i++) {
				GoodsInfo info = goodsList.get(i);
				long rowid = helper.insert(info);
				info.rowid = rowid;
				//往全局内存写入商品小图
				Bitmap thumb = BitmapFactory.decodeResource(ctx.getResources(), info.thumb);
				MainApplication.getInstance().mIconMap.put(rowid, thumb);
				String thumb_path = path + rowid + "_s.jpg";
				FileUtil.saveImage(thumb_path, thumb);
				info.thumb_path = thumb_path;
				//往SD卡保存商品大图
				Bitmap pic = BitmapFactory.decodeResource(ctx.getResources(), info.pic);
				String pic_path = path + rowid + ".jpg";
				FileUtil.saveImage(pic_path, pic);
				pic.recycle();
				info.pic_path = pic_path;
				helper.update(info);
			}
		} else {
			ArrayList<GoodsInfo> goodsArray = helper.query("1=1");
			for (int i=0; i<goodsArray.size(); i++) {
				GoodsInfo info = goodsArray.get(i);
				Log.d(TAG, "rowid="+info.rowid+", thumb_path="+info.thumb_path);
				Bitmap thumb = BitmapFactory.decodeFile(info.thumb_path);
				MainApplication.getInstance().mIconMap.put(info.rowid, thumb);
			}
		}
	}

}
