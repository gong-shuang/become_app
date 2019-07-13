package com.gs.learn.event;

import java.util.ArrayList;

import com.gs.learn.event.util.DisplayUtil;
import com.gs.learn.event.util.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class ViewFlipperActivity extends AppCompatActivity implements OnClickListener {
	private Button btn_control_flipper;
	private RelativeLayout rl_content;
	private ViewFlipper vf_content;
	private RadioGroup rg_indicator;
	private int dip_15;
	private boolean bPlay = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_flipper);
		btn_control_flipper = (Button) findViewById(R.id.btn_control_flipper);
		rl_content = (RelativeLayout) findViewById(R.id.rl_content);
		vf_content = (ViewFlipper) findViewById(R.id.vf_content);
		rg_indicator = (RadioGroup) findViewById(R.id.rg_indicator);
		btn_control_flipper.setOnClickListener(this);
		findViewById(R.id.btn_pre_flipper).setOnClickListener(this);
		findViewById(R.id.btn_next_flipper).setOnClickListener(this);
		initFlipper();
	}
	
	private void initFlipper() {
		LayoutParams params = (LayoutParams) rl_content.getLayoutParams();
		params.height = (int) (DisplayUtil.getSreenWidth(this) * 250f/ 640f);
		rl_content.setLayoutParams(params);
		ArrayList<Integer> imageList = new ArrayList<Integer>();
		imageList.add(Integer.valueOf(R.drawable.banner_1));
		imageList.add(Integer.valueOf(R.drawable.banner_2));
		imageList.add(Integer.valueOf(R.drawable.banner_3));
		imageList.add(Integer.valueOf(R.drawable.banner_4));
		imageList.add(Integer.valueOf(R.drawable.banner_5));
		for (int i = 0; i < imageList.size(); i++) {
			Integer imageID = ((Integer) imageList.get(i)).intValue();
			ImageView iv_item = new ImageView(this);
			iv_item.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			iv_item.setScaleType(ImageView.ScaleType.FIT_XY);
			iv_item.setImageResource(imageID);
			vf_content.addView(iv_item);
		}
		dip_15 = Utils.dip2px(this, 15);
		for (int i = 0; i < imageList.size(); i++) {
			RadioButton radio = new RadioButton(this);
			radio.setLayoutParams(new RadioGroup.LayoutParams(dip_15, dip_15));
			radio.setGravity(Gravity.CENTER);
			radio.setButtonDrawable(R.drawable.indicator_selector);
			rg_indicator.addView(radio);
		}
		vf_content.setDisplayedChild(0);
		vf_content.setAutoStart(true);
		mHandler.postDelayed(mRefresh, 200);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_pre_flipper) {
			vf_content.showPrevious();
		} else if (v.getId() == R.id.btn_next_flipper) {
			vf_content.showNext();
		} else if (v.getId() == R.id.btn_control_flipper) {
			bPlay = !bPlay;
			if (bPlay == true) {
				vf_content.startFlipping();
				btn_control_flipper.setText("停止自动翻页");
			} else {
				vf_content.stopFlipping();
				btn_control_flipper.setText("开始自动翻页");
			}
		}
	}
	
	private Handler mHandler = new Handler();
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			int pos = vf_content.getDisplayedChild();
			((RadioButton) rg_indicator.getChildAt(pos)).setChecked(true);
			mHandler.postDelayed(this, 200);
		}
	};
	
}
