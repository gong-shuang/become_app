package com.gs.learn.senior;

import java.util.ArrayList;

import com.gs.learn.senior.adapter.ImagePagerAdapater;
import com.gs.learn.senior.bean.GoodsInfo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class ViewPagerActivity extends AppCompatActivity implements OnPageChangeListener {
	private ArrayList<GoodsInfo> goodsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		ViewPager vp_content = (ViewPager) findViewById(R.id.vp_content);
		goodsList = GoodsInfo.getDefaultList();
		ImagePagerAdapater adapter = new ImagePagerAdapater(this, goodsList);
		vp_content.setAdapter(adapter);
		vp_content.setCurrentItem(0);
		vp_content.addOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		Toast.makeText(this, "您翻到的手机品牌是："+goodsList.get(arg0).name, Toast.LENGTH_SHORT).show();
	}
	
}
