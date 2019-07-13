package com.gs.learn.media;

import com.gs.learn.media.adapter.GalleryAdapter;
import com.gs.learn.media.util.Utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class GalleryActivity extends AppCompatActivity implements OnItemClickListener {
	private ImageView iv_gallery;
	private Gallery gl_gallery;
	// 图片数组
	private int[] mImageRes = { 
			R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, 
			R.drawable.scene4, R.drawable.scene5, R.drawable.scene6 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		iv_gallery = (ImageView) findViewById(R.id.iv_gallery);
		iv_gallery.setImageResource(mImageRes[0]);

		int dip_pad = Utils.dip2px(this, 20);
		gl_gallery = (Gallery) findViewById(R.id.gl_gallery);
		gl_gallery.setPadding(0, dip_pad, 0, dip_pad);
		gl_gallery.setSpacing(dip_pad);
		gl_gallery.setUnselectedAlpha(0.5f);
		gl_gallery.setAdapter(new GalleryAdapter(this, mImageRes));
		gl_gallery.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		iv_gallery.setImageResource(mImageRes[position]);
	}

}
