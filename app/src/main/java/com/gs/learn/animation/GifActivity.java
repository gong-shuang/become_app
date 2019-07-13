package com.gs.learn.animation;

import java.io.InputStream;

import com.gs.learn.animation.util.GifImage;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class GifActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gif);
		showGifAnimation();
	}

	private void showGifAnimation() {
		ImageView iv_gif = (ImageView) findViewById(R.id.iv_gif);
		InputStream is = getResources().openRawResource(R.raw.welcome);
		GifImage gifImage = new GifImage();
		int code = gifImage.read(is);
		if (code == GifImage.STATUS_OK) {
			GifImage.GifFrame[] frameList = gifImage.getFrames();
			AnimationDrawable ad_gif = new AnimationDrawable();
			for (int i=0; i<frameList.length; i++) {
				//BitmapDrawable用于把Bitmap格式转换为Drawable格式
				BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), frameList[i].image);
				ad_gif.addFrame(bitmapDrawable, frameList[i].delay);
			}
			ad_gif.setOneShot(false);
			iv_gif.setImageDrawable(ad_gif);
			ad_gif.start();
		} else if (code == GifImage.STATUS_FORMAT_ERROR) {
			Toast.makeText(this, "该图片不是gif格式", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "gif图片读取失败:" + code, Toast.LENGTH_LONG).show();
		}
	}

}
