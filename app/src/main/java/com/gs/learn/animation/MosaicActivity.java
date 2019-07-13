package com.gs.learn.animation;

import com.gs.learn.animation.widget.MosaicView;

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
public class MosaicActivity extends AppCompatActivity {
	private MosaicView sv_mosaic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mosaic);
		sv_mosaic = (MosaicView) findViewById(R.id.sv_mosaic);
		sv_mosaic.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bdg03));
		initMosaicSpinner();
	}

	private void initMosaicSpinner() {
		ArrayAdapter<String> mosaicAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, mosaicArray);
		Spinner sp_mosaic = (Spinner) findViewById(R.id.sp_mosaic);
		sp_mosaic.setPrompt("请选择马赛克动画类型");
		sp_mosaic.setAdapter(mosaicAdapter);
		sp_mosaic.setOnItemSelectedListener(new MosaicSelectedListener());
		sp_mosaic.setSelection(0);
	}

	private String[] mosaicArray={"水平二十格", "水平三十格", "水平四十格", 
			"垂直二十格", "垂直三十格", "垂直四十格"};
	class MosaicSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			sv_mosaic.setOriention((arg2<3)?LinearLayout.HORIZONTAL:LinearLayout.VERTICAL);
			if (arg2 == 0 || arg2 == 3) {
				sv_mosaic.setGridCount(20);
			} else if (arg2 == 1 || arg2 == 4) {
				sv_mosaic.setGridCount(30);
			} else if (arg2 == 2 || arg2 == 5) {
				sv_mosaic.setGridCount(40);
			}
			//起始值和结束值要设得超出一些范围，这样头尾的马赛克看起来才是连贯的
			int offset = 5;
			sv_mosaic.setOffset(offset);
			ObjectAnimator anim = ObjectAnimator.ofInt(sv_mosaic, "ratio", 0-offset, 101+offset);
			anim.setDuration(5000);
			anim.start();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

}
