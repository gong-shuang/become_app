package com.gs.learn.media;

import com.gs.learn.media.adapter.PhotoAdapter;
import com.gs.learn.media.widget.RecyclerExtras.OnItemClickListener;
import com.gs.learn.media.widget.SpacesItemDecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class RecyclerViewActivity extends AppCompatActivity implements OnItemClickListener {
	private ImageView iv_photo;
	private RecyclerView rv_photo;
	private int[] mImageRes = { 
			R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, 
			R.drawable.scene4, R.drawable.scene5, R.drawable.scene6 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler_view);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		iv_photo.setImageResource(mImageRes[0]);

		rv_photo = (RecyclerView) findViewById(R.id.rv_photo);
		LinearLayoutManager manager = new LinearLayoutManager(this);
		manager.setOrientation(LinearLayout.HORIZONTAL);
		rv_photo.setLayoutManager(manager);

		PhotoAdapter adapter = new PhotoAdapter(this, mImageRes);
		adapter.setOnItemClickListener(this);
		rv_photo.setAdapter(adapter);
		rv_photo.setItemAnimator(new DefaultItemAnimator());
		rv_photo.addItemDecoration(new SpacesItemDecoration(20));
	}

	@Override
	public void onItemClick(View view, int position) {
		iv_photo.setImageResource(mImageRes[position]);
		rv_photo.scrollToPosition(position);
	}

}
