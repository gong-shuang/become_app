package com.gs.learn.storage;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gs.learn.MainApplication;
import com.gs.learn.storage.bean.CartInfo;
import com.gs.learn.storage.bean.GoodsInfo;
import com.gs.learn.storage.database.CartDBHelper;
import com.gs.learn.storage.database.GoodsDBHelper;
import com.gs.learn.common.util.FileUtil;
import com.gs.learn.common.util.SharedUtil;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/1.
 */
public class ShoppingCartActivity extends Activity implements OnClickListener {

	private final static String TAG = "ShoppingCartActivity";
	private ImageView iv_menu;
	private TextView tv_title;
	private TextView tv_count;
	private TextView tv_total_price;
	private LinearLayout ll_content;
	private LinearLayout ll_cart;
	private LinearLayout ll_empty;
	private int mCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shopping_cart);
		iv_menu = (ImageView) findViewById(R.id.iv_menu);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_total_price = (TextView) findViewById(R.id.tv_total_price);
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		ll_cart = (LinearLayout) findViewById(R.id.ll_cart);
		ll_empty = (LinearLayout) findViewById(R.id.ll_empty);
		
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
			ll_cart.removeAllViews();
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
			ll_cart.removeAllViews();
			SharedUtil.getIntance(this).writeShared("count", "0");
			showCount(0);
			mCartGoods.clear();
			mGoodsMap.clear();
			Toast.makeText(this, "购物车已清空", Toast.LENGTH_SHORT).show();
		} else if (id == R.id.menu_return) {
			finish();
		}
		return true;
	}

	private HashMap<Integer, CartInfo> mCartGoods = new HashMap<Integer, CartInfo>();
	private View mContextView;
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		mContextView = v;
		getMenuInflater().inflate(R.menu.menu_goods, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		CartInfo info = mCartGoods.get(mContextView.getId());
		int id = item.getItemId();
		if (id == R.id.menu_detail) {
			//跳转到查看商品详情页面
			goDetail(info.goods_id);
		} else if (id == R.id.menu_delete) {
			//从购物车删除商品的数据库操作
			long goods_id = info.goods_id;
			mCartHelper.delete("goods_id="+goods_id);
			ll_cart.removeView(mContextView);
			//更新购物车中的商品数量
			int left_count = mCount-info.count;
			for (int i=0; i<mCartArray.size(); i++) {
				if (goods_id == mCartArray.get(i).goods_id) {
					left_count = mCount-mCartArray.get(i).count;
					mCartArray.remove(i);
					break;
				}
			}
			SharedUtil.getIntance(this).writeShared("count", ""+left_count);
			showCount(left_count);
			Toast.makeText(this, "已从购物车删除"+mGoodsMap.get(goods_id).name, Toast.LENGTH_SHORT).show();
			mGoodsMap.remove(goods_id);
			refreshTotalPrice();
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
		downloadGoods();
		SharedUtil.getIntance(this).writeShared("first", "false");
		showCart();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mGoodsHelper.closeLink();
		mCartHelper.closeLink();
	}

	private int mBeginViewId = 0x7F24FFF0;
	private ArrayList<CartInfo> mCartArray = new ArrayList<CartInfo>();
	private HashMap<Long, GoodsInfo> mGoodsMap = new HashMap<Long, GoodsInfo>();
	private void showCart() {
		mCartArray = mCartHelper.query("1=1");
		Log.d(TAG, "mCartArray.size()="+mCartArray.size());
		if (mCartArray==null || mCartArray.size()<=0) {
			return;
		}
		ll_cart.removeAllViews();
		LinearLayout ll_row = newLinearLayout(LinearLayout.HORIZONTAL, LayoutParams.WRAP_CONTENT);
		ll_row.addView(newTextView(0, 2, Gravity.CENTER, "图片", Color.BLACK, 15));
		ll_row.addView(newTextView(0, 3, Gravity.CENTER, "名称", Color.BLACK, 15));
		ll_row.addView(newTextView(0, 1, Gravity.CENTER, "数量", Color.BLACK, 15));
		ll_row.addView(newTextView(0, 1, Gravity.CENTER, "单价", Color.BLACK, 15));
		ll_row.addView(newTextView(0, 1, Gravity.CENTER, "总价", Color.BLACK, 15));
		ll_cart.addView(ll_row);
		for (int i=0; i<mCartArray.size(); i++) {
			final CartInfo info = mCartArray.get(i);
			GoodsInfo goods = mGoodsHelper.queryById(info.goods_id);
			Log.d(TAG, "name="+goods.name+",price="+goods.price+",desc="+goods.desc);
			mGoodsMap.put(info.goods_id, goods);
			ll_row = newLinearLayout(LinearLayout.HORIZONTAL, LayoutParams.WRAP_CONTENT);
			ll_row.setId(mBeginViewId+i);
			//添加商品小图
			ImageView iv_thumb = new ImageView(this);
			LinearLayout.LayoutParams iv_params = new LinearLayout.LayoutParams(
					0, LayoutParams.WRAP_CONTENT, 2);
			iv_thumb.setLayoutParams(iv_params);
			iv_thumb.setScaleType(ScaleType.FIT_CENTER);
			iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.goods_id));
			ll_row.addView(iv_thumb);
			//添加商品名称与描述
			LinearLayout ll_name = new LinearLayout(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					0, LayoutParams.MATCH_PARENT, 3);
			ll_name.setLayoutParams(params);
			ll_name.setOrientation(LinearLayout.VERTICAL);
			ll_name.addView(newTextView(-3, 1, Gravity.LEFT, goods.name, Color.BLACK, 17));
			ll_name.addView(newTextView(-3, 1, Gravity.LEFT, goods.desc, Color.GRAY, 12));
			ll_row.addView(ll_name);
			//添加商品数量、单价和总价
			ll_row.addView(newTextView(1, 1, Gravity.CENTER, ""+info.count, Color.BLACK, 17));
			ll_row.addView(newTextView(1, 1, Gravity.RIGHT, ""+(int)goods.price, Color.BLACK, 15));
			ll_row.addView(newTextView(1, 1, Gravity.RIGHT, ""+(int)(info.count*goods.price), Color.RED, 17));
			//给商品行添加点击事件
			ll_row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					goDetail(info.goods_id);
				}
			});
			//给商品行注册上下文菜单
			unregisterForContextMenu(ll_row);
			registerForContextMenu(ll_row);
			mCartGoods.put(ll_row.getId(), info);
			ll_cart.addView(ll_row);
		}
		refreshTotalPrice();
	}
	
	private void refreshTotalPrice() {
		int total_price = 0;
		for (CartInfo info : mCartArray) {
			GoodsInfo goods = mGoodsMap.get(info.goods_id);
			total_price += goods.price*info.count;
		}
		tv_total_price.setText(""+total_price);
	}
	
	private LinearLayout newLinearLayout(int orientation, int height) {
		LinearLayout ll_new = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, height);
		//params.setMargins(2, 2, 2, 2);
		ll_new.setLayoutParams(params);
		ll_new.setOrientation(orientation);
		ll_new.setBackgroundColor(Color.WHITE);
		return ll_new;
	}

	private TextView newTextView(int height, float weight, int gravity, String text, int textColor, int textSize) {
		TextView tv_new = new TextView(this);
		if (height == -3) {  //垂直排列
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, weight);
			tv_new.setLayoutParams(params);
		} else {  //水平排列
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					0, (height==0)?LayoutParams.WRAP_CONTENT:LayoutParams.MATCH_PARENT, weight);
			tv_new.setLayoutParams(params);
		}
		tv_new.setText(text);
		tv_new.setTextColor(textColor);
		tv_new.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		tv_new.setGravity(Gravity.CENTER|gravity);
		return tv_new;
	}

	private String[] mNameArray = {
			"iphone7", "Mate8", "小米5", "vivo X6S", "OPPO R9plus", "魅族Pro6"
			};
	private String[] mDescArray = {
			"Apple iPhone 7 128GB 玫瑰金色 移动联通电信4G手机",
			"华为 HUAWEI Mate8 3GB+32GB版 移动联通4G手机（月光银）",
			"小米手机5 全网通 高配版 3GB内存 64GB 白色",
			"vivo X6S 金色 全网通4G 双卡双待 4GB+64GB",
			"OPPO R9plus 4GB+64GB内存版 金色 全网通4G手机 双卡双待",
			"魅族Pro6全网通公开版 4+32GB 银白色 移动联通电信4G手机 双卡双待"
			};
	private float[] mPriceArray = {5888, 2499, 1799, 2298, 2499, 2199};
	private int[] mThumbArray = {
			R.drawable.iphone_s, R.drawable.huawei_s, R.drawable.xiaomi_s,
			R.drawable.vivo_s, R.drawable.oppo_9p_s, R.drawable.meizu_s
			};
	private int[] mPicArray = {
			R.drawable.iphone, R.drawable.huawei, R.drawable.xiaomi,
			R.drawable.vivo, R.drawable.oppo_9p, R.drawable.meizu
			};
	//模拟网络数据，初始化数据库中的商品信息
	private void downloadGoods() {
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
		if (mFirst.equals("true")) {
			for (int i=0; i<mNameArray.length; i++) {
				GoodsInfo info = new GoodsInfo();
				info.name = mNameArray[i];
				info.desc = mDescArray[i];
				info.price = mPriceArray[i];
				long rowid = mGoodsHelper.insert(info);
				info.rowid = rowid;
				//往全局内存写入商品小图
				Bitmap thumb = BitmapFactory.decodeResource(getResources(), mThumbArray[i]);
				MainApplication.getInstance().mIconMap.put(rowid, thumb);
				String thumb_path = path + rowid + "_s.jpg";
				FileUtil.saveImage(thumb_path, thumb);
				info.thumb_path = thumb_path;
				//往SD卡保存商品大图
				Bitmap pic = BitmapFactory.decodeResource(getResources(), mPicArray[i]);
				String pic_path = path + rowid + ".jpg";
				FileUtil.saveImage(pic_path, pic);
				pic.recycle();
				info.pic_path = pic_path;
				mGoodsHelper.update(info);
			}
		} else {
			ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
			for (int i=0; i<goodsArray.size(); i++) {
				GoodsInfo info = goodsArray.get(i);
				Bitmap thumb = BitmapFactory.decodeFile(info.thumb_path);
				MainApplication.getInstance().mIconMap.put(info.rowid, thumb);
			}
		}
	}

}
