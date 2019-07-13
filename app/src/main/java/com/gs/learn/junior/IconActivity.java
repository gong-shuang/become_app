package com.gs.learn.junior;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/9/15.
 */
public class IconActivity extends AppCompatActivity implements View.OnClickListener {
	private Button btn_icon;
	private Drawable drawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icon);
		btn_icon = (Button) findViewById(R.id.btn_icon);
		drawable = getResources().getDrawable(R.mipmap.ic_launcher);
		// 必须设置图片大小，否则不显示图片
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		Button btn_left = (Button) findViewById(R.id.btn_left);
		Button btn_top = (Button) findViewById(R.id.btn_top);
		Button btn_right = (Button) findViewById(R.id.btn_right);
		Button btn_bottom = (Button) findViewById(R.id.btn_bottom);
		btn_left.setOnClickListener(this);
		btn_top.setOnClickListener(this);
		btn_right.setOnClickListener(this);
		btn_bottom.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_left) {
			btn_icon.setCompoundDrawables(drawable, null, null, null);
		} else if (v.getId() == R.id.btn_top) {
			btn_icon.setCompoundDrawables(null, drawable, null, null);
		} else if (v.getId() == R.id.btn_right) {
			btn_icon.setCompoundDrawables(null, null, drawable, null);
		} else if (v.getId() == R.id.btn_bottom) {
			btn_icon.setCompoundDrawables(null, null, null, drawable);
		}
	}
}
