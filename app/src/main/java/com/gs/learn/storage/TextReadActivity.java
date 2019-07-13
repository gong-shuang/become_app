package com.gs.learn.storage;

import java.io.File;
import java.util.ArrayList;

import com.gs.learn.common.util.FileUtil;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/1.
 */
public class TextReadActivity extends AppCompatActivity implements OnClickListener {

	private final static String TAG = "TextReadActivity";
	private TextView tv_text;
	private Spinner sp_file;
	private String mPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_read);
		tv_text = (TextView) findViewById(R.id.tv_text);
		sp_file = (Spinner) findViewById(R.id.sp_file);
		findViewById(R.id.btn_delete).setOnClickListener(this);
		mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
			refreshSpinner();
		} else {
			showToast("未发现已挂载的SD卡，请检查");
		}
	}
	
	private void refreshSpinner() {
		ArrayList<File> fileAlllist = FileUtil.getFileList(mPath, new String[]{".txt"});
		if (fileAlllist.size() > 0) {
			fileArray = new String[fileAlllist.size()];
			for (int i=0; i<fileAlllist.size(); i++) {
				fileArray[i] = fileAlllist.get(i).getName();
			}
			ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
					R.layout.item_select, fileArray);
			typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
			sp_file.setPrompt("请选择文本文件");
			sp_file.setAdapter(typeAdapter);
			sp_file.setSelection(0);
			sp_file.setOnItemSelectedListener(new FileSelectedListener());
		} else {
			fileArray = null;
			fileArray = new String[1];
			fileArray[0] = "";
			ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
					R.layout.item_select, fileArray);
			sp_file.setPrompt(null);
			sp_file.setAdapter(typeAdapter);
			sp_file.setOnItemSelectedListener(null);
			tv_text.setText("");
		}
	}

	private String[] fileArray;
	class FileSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			String file_path = mPath + fileArray[arg2];
			String content = FileUtil.openText(file_path);
			tv_text.setText("文件内容如下：\n"+content);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_delete) {
			for (int i=0; i<fileArray.length; i++) {
				String file_path = mPath + fileArray[i];
				File f = new File(file_path);
				boolean result = f.delete();
				if (result != true) {
					Log.d(TAG, "file_path="+file_path+", delete failed");
				}
			}
			refreshSpinner();
			showToast("已删除临时目录下的所有文本文件");
		}
	}

	private void showToast(String desc) {
		Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
	}
	
}
