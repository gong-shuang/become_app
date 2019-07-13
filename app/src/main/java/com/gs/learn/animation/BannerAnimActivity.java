package com.gs.learn.animation;

import java.util.ArrayList;

import com.gs.learn.animation.util.DisplayUtil;
import com.gs.learn.animation.widget.BannerFlipper;
import com.gs.learn.animation.widget.BannerFlipper.BannerClickListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class BannerAnimActivity extends AppCompatActivity implements BannerClickListener {
	private static final String TAG = "BannerAnimActivity";
	private TextView tv_flipper;
	private BannerFlipper mBanner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner_anim);
		tv_flipper = (TextView) findViewById(R.id.tv_flipper);

		mBanner = (BannerFlipper) findViewById(R.id.banner_flipper);
		LayoutParams params = (LayoutParams) mBanner.getLayoutParams();
		params.height = (int) (DisplayUtil.getSreenWidth(this) * 250f/ 640f);
		mBanner.setLayoutParams(params);
		
		ArrayList<Integer> imageList = new ArrayList<Integer>();
		imageList.add(Integer.valueOf(R.drawable.banner_1));
		imageList.add(Integer.valueOf(R.drawable.banner_2));
		imageList.add(Integer.valueOf(R.drawable.banner_3));
		imageList.add(Integer.valueOf(R.drawable.banner_4));
		imageList.add(Integer.valueOf(R.drawable.banner_5));
		mBanner.setImage(imageList);
		mBanner.setOnBannerListener(this);
	}

	@Override
	public void onBannerClick(int position) {
		String desc = String.format("您点击了第%d张图片", position+1);
		tv_flipper.setText(desc);
	}

}
