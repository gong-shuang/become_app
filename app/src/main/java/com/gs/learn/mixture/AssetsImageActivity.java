package com.gs.learn.mixture;

import com.gs.learn.R;
import com.gs.learn.mixture.util.AssetsUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class AssetsImageActivity extends AppCompatActivity {
	private String mFilePath = "file/water.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assets_image);
		TextView tv_image_path = (TextView) findViewById(R.id.tv_image_path);
		ImageView iv_assets_image = (ImageView) findViewById(R.id.iv_assets_image);
		tv_image_path.setText("下面图像来源于资产文件"+mFilePath);
		iv_assets_image.setImageBitmap(AssetsUtil.getImgFromAssets(this, mFilePath));
	}
	
}
