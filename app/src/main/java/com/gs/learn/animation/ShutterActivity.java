package com.gs.learn.animation;

import com.gs.learn.animation.widget.ShutterView;

import android.animation.ObjectAnimator;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class ShutterActivity extends AppCompatActivity {
	private ShutterView sv_shutter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shutter);
		sv_shutter = (ShutterView) findViewById(R.id.sv_shutter);
		sv_shutter.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bdg03));
		initShutterSpinner();
	}

	private void initShutterSpinner() {
		ArrayAdapter<String> shutterAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, shutterArray);
		Spinner sp_shutter = (Spinner) findViewById(R.id.sp_shutter);
		sp_shutter.setPrompt("请选择百叶窗动画类型");
		sp_shutter.setAdapter(shutterAdapter);
		sp_shutter.setOnItemSelectedListener(new ShutterSelectedListener());
		sp_shutter.setSelection(0);
	}

	private String[] shutterArray={"水平五叶", "水平十叶", "水平二十叶", 
			"垂直五叶", "垂直十叶", "垂直二十叶"};
	class ShutterSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			sv_shutter.setOriention((arg2<3)?LinearLayout.HORIZONTAL:LinearLayout.VERTICAL);
			if (arg2 == 0 || arg2 == 3) {
				sv_shutter.setLeafCount(5);
			} else if (arg2 == 1 || arg2 == 4) {
				sv_shutter.setLeafCount(10);
			} else if (arg2 == 2 || arg2 == 5) {
				sv_shutter.setLeafCount(20);
			}
			ObjectAnimator anim = ObjectAnimator.ofInt(sv_shutter, "ratio", 0, 100);
			anim.setDuration(3000);
			anim.start();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

}
