package com.gs.learn.media;

import java.util.List;

import com.gs.learn.R;
import com.gs.learn.media.adapter.AlbumAdapter;
import com.gs.learn.media.task.GestureTask;
import com.gs.learn.media.task.GestureTask.GestureCallback;
import com.gs.learn.media.util.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.PaletteAsyncListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ViewSwitcher.ViewFactory;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class AlbumActivity extends AppCompatActivity implements
		OnTouchListener, OnItemClickListener, GestureCallback {
	private ImageSwitcher is_album;
	private Gallery gl_album;
	private int[] mImageRes = { 
			R.drawable.scene1, R.drawable.scene2, R.drawable.scene3,
			R.drawable.scene4, R.drawable.scene5, R.drawable.scene6 };
	private int[] mBackColors = { Color.WHITE, Color.WHITE, Color.WHITE,
			Color.WHITE, Color.WHITE, Color.WHITE };
	private GestureDetector mGesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		is_album = (ImageSwitcher) findViewById(R.id.is_album);
		is_album.setFactory(new ViewFactoryImpl());
		is_album.setImageResource(mImageRes[0]);
		GestureTask gestureListener = new GestureTask(this);
		mGesture = new GestureDetector(this, gestureListener);
		gestureListener.setGestureCallback(this);
		is_album.setOnTouchListener(this);

		int dip_pad = Utils.dip2px(this, 20);
		gl_album = (Gallery) findViewById(R.id.gl_album);
		gl_album.setPadding(0, dip_pad, 0, dip_pad);
		gl_album.setSpacing(dip_pad);
		gl_album.setUnselectedAlpha(0.5f);
		gl_album.setOnItemClickListener(this);
		gl_album.setAdapter(new AlbumAdapter(this, mImageRes, mBackColors));

		initPalette();
	}
	
	private void initPalette() {
		for (int i=0; i<mImageRes.length; i++) {
			Drawable drawable = getResources().getDrawable(mImageRes[i]);
			Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
			Palette.Builder builder = Palette.from(bitmap);
			builder.generate(new MyPaletteListener(i));
		}
	}
	
	private class MyPaletteListener implements PaletteAsyncListener {
		private int mPos;
		public MyPaletteListener(int pos) {
			mPos = pos;
		}

		@Override
		public void onGenerated(Palette palette) {
			Palette.Swatch swatch = palette.getVibrantSwatch();
			if (swatch != null) {
				mBackColors[mPos] = swatch.getRgb();
			} else {  //getVibrantSwatch有时会返回null，此时从getSwatches取第一条颜色
				List<Palette.Swatch> swatches = palette.getSwatches();
				for (int i=0; i<swatches.size(); i++) {
					Palette.Swatch item = swatches.get(i);
					mBackColors[mPos] = item.getRgb();
					break;
				}
			}
			gl_album.setAdapter(new AlbumAdapter(AlbumActivity.this, mImageRes, mBackColors));
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		is_album.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		is_album.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
		is_album.setImageResource(mImageRes[position]);
	}

	public class ViewFactoryImpl implements ViewFactory {
		@Override
		public View makeView() {
			ImageView iv = new ImageView(AlbumActivity.this);
			iv.setBackgroundColor(0xFFFFFFFF);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return iv;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mGesture.onTouchEvent(event);
		return true;
	}

	@Override
	public void gotoNext() {
		is_album.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
		is_album.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
		int next_pos = (int) (gl_album.getSelectedItemId() + 1);
		if (next_pos >= mImageRes.length) {
			next_pos = 0;
		}
		is_album.setImageResource(mImageRes[next_pos]);
		gl_album.setSelection(next_pos);
	}

	@Override
	public void gotoPre() {
		is_album.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
		is_album.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
		int pre_pos = (int) (gl_album.getSelectedItemId() - 1);
		if (pre_pos < 0) {
			pre_pos = mImageRes.length - 1;
		}
		is_album.setImageResource(mImageRes[pre_pos]);
		gl_album.setSelection(pre_pos);
	}

}
