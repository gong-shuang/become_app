package com.gs.learn.media;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class MediaMainActivity extends AppCompatActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_media);

		findViewById(R.id.btn_gallery).setOnClickListener(this);
		findViewById(R.id.btn_recycler_view).setOnClickListener(this);
		findViewById(R.id.btn_image_switcher).setOnClickListener(this);
		findViewById(R.id.btn_card_view).setOnClickListener(this);
		findViewById(R.id.btn_album).setOnClickListener(this);
		findViewById(R.id.btn_video_view).setOnClickListener(this);
		findViewById(R.id.btn_video_controller).setOnClickListener(this);
		findViewById(R.id.btn_media_controller).setOnClickListener(this);
		findViewById(R.id.btn_movie_player).setOnClickListener(this);
		findViewById(R.id.btn_content_provider).setOnClickListener(this);
		findViewById(R.id.btn_content_resolver).setOnClickListener(this);
		findViewById(R.id.btn_content_observer).setOnClickListener(this);
		findViewById(R.id.btn_spannable).setOnClickListener(this);
		findViewById(R.id.btn_html).setOnClickListener(this);
		findViewById(R.id.btn_music_player).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_gallery) {
			Intent intent = new Intent(this, GalleryActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_recycler_view) {
			Intent intent = new Intent(this, RecyclerViewActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_image_switcher) {
			Intent intent = new Intent(this, ImageSwitcherActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_card_view) {
			Intent intent = new Intent(this, CardViewActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_album) {
			Intent intent = new Intent(this, AlbumActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_video_view) {
			Intent intent = new Intent(this, VideoViewActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_video_controller) {
			Intent intent = new Intent(this, VideoControllerActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_media_controller) {
			Intent intent = new Intent(this, MediaControllerActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_movie_player) {
			Intent intent = new Intent(this, MoviePlayerActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_content_provider) {
			Intent intent = new Intent(this, ContentProviderActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_content_resolver) {
			Intent intent = new Intent(this, ContentResolverActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_content_observer) {
			Intent intent = new Intent(this, ContentObserverActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_spannable) {
			Intent intent = new Intent(this, SpannableActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_html) {
			Intent intent = new Intent(this, HtmlActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_music_player) {
			Intent intent = new Intent(this, MusicPlayerActivity.class);
			startActivity(intent);
		}
	}

}
