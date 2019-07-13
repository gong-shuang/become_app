package com.gs.learn.animation;

import android.animation.RectEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class ObjectAnimActivity extends AppCompatActivity {
	private ImageView iv_object_anim;
	private ObjectAnimator alphaAnim, translateAnim, scaleAnim, rotateAnim, clipAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_anim);
		iv_object_anim = (ImageView) findViewById(R.id.iv_object_anim);
		initAnim();
		initObjectSpinner();
	}

	private void initAnim() {
		alphaAnim = ObjectAnimator.ofFloat(iv_object_anim, "alpha", 1f, 0.1f, 1f);
		translateAnim = ObjectAnimator.ofFloat(iv_object_anim, "translationX", 0f, -200f, 0f, 200f, 0f);
		scaleAnim = ObjectAnimator.ofFloat(iv_object_anim, "scaleY", 1f, 0.5f, 1f);
		rotateAnim = ObjectAnimator.ofFloat(iv_object_anim, "rotation", 0f, 360f, 0f);
	}

	private void initObjectSpinner() {
		ArrayAdapter<String> objectAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, objectArray);
		Spinner sp_object = (Spinner) findViewById(R.id.sp_object);
		sp_object.setPrompt("请选择属性动画类型");
		sp_object.setAdapter(objectAdapter);
		sp_object.setOnItemSelectedListener(new ObjectSelectedListener());
		sp_object.setSelection(0);
	}

	private String[] objectArray={"灰度动画", "平移动画", "缩放动画", "旋转动画", "裁剪动画"};
	class ObjectSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			showAnimation(arg2);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void showAnimation(int type) {
		ObjectAnimator anim = null;
		if (type == 0) {
			anim = alphaAnim;
		} else if (type == 1) {
			anim = translateAnim;
		} else if (type == 2) {
			anim = scaleAnim;
		} else if (type == 3) {
			anim = rotateAnim;
		} else if (type == 4) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				int width = iv_object_anim.getWidth();
				int height = iv_object_anim.getHeight();
				clipAnim = ObjectAnimator.ofObject(iv_object_anim, "clipBounds", 
					new RectEvaluator(), new Rect(0,0,width,height), 
					new Rect(width/3,height/3,width/3*2,height/3*2), 
					new Rect(0,0,width,height));
				anim = clipAnim;
			} else {
				Toast.makeText(this, 
						"裁剪动画要求Android为4.3以上版本", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		if (anim != null) {
			anim.setDuration(3000);
			anim.start();
		}
	}
	
}
