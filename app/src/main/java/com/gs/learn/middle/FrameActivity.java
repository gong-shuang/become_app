package com.gs.learn.middle;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/9/24.
 */
public class FrameActivity extends AppCompatActivity implements OnClickListener {

	private FrameLayout fl_content;
	private int[] mColorArray = {
			Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN, 
			Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GRAY, Color.DKGRAY
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame);

		fl_content = (FrameLayout) findViewById(R.id.fl_content);
		findViewById(R.id.btn_add_frame).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_add_frame) {
			int random = (int) (Math.random()*10 % 10);
			View vv = new View(this);
			vv.setBackgroundColor(mColorArray[random]);
			LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (random+1)*50);
			vv.setLayoutParams(ll_params);
			vv.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View vvv) {
					fl_content.removeView(vvv);
					return true;
				}
			});
			fl_content.addView(vv);
		}
	}

}
