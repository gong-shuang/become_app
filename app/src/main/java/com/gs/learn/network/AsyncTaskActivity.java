package com.gs.learn.network;

import com.gs.learn.R;
import com.gs.learn.network.task.ProgressAsyncTask;
import com.gs.learn.network.task.ProgressAsyncTask.OnProgressListener;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class AsyncTaskActivity extends AppCompatActivity implements OnProgressListener {
	private TextView tv_async;
	private ProgressBar pb_async;
	private ProgressDialog mDialog;
	
	public int mShowMode;
	public int BAR_HORIZONTAL = 1;
	public int DIALOG_CIRCLE = 2;
	public int DIALOG_HORIZONTAL = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_task);
		tv_async = (TextView) findViewById(R.id.tv_async);
		pb_async = (ProgressBar) findViewById(R.id.pb_async);
		
		ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, bookArray);
		Spinner sp_style = (Spinner) findViewById(R.id.sp_style);
		sp_style.setPrompt("请选择要加载的小说");
		sp_style.setAdapter(styleAdapter);
		sp_style.setOnItemSelectedListener(new StyleSelectedListener());
		sp_style.setSelection(0);
	}

	private String[] bookArray={"三国演义", "西游记", "红楼梦"};
	private int[] styleArray={BAR_HORIZONTAL, DIALOG_CIRCLE, DIALOG_HORIZONTAL};
	class StyleSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			startTask(styleArray[arg2], bookArray[arg2]);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	private void startTask(int mode, String msg) {
		mShowMode = mode;
		ProgressAsyncTask asyncTask = new ProgressAsyncTask(msg);
		asyncTask.setOnProgressListener(this);
		asyncTask.execute(msg);
	}
	
	private void closeDialog() {
		if (mDialog != null && mDialog.isShowing() == true) {
			mDialog.dismiss();
		}
	}

	@Override
	public void onFinish(String result) {
		String desc = String.format("您要阅读的《%s》已经加载完毕", result);
		tv_async.setText(desc);
		closeDialog();
	}

	@Override
	public void onCancel(String result) {
		String desc = String.format("您要阅读的《%s》已经取消加载", result);
		tv_async.setText(desc);
		closeDialog();
	}

	@Override
	public void onUpdate(String request, int progress, int sub_progress) {
		String desc = String.format("%s当前加载进度为%d%%", request, progress);
		tv_async.setText(desc);
		if (mShowMode == BAR_HORIZONTAL) {
			pb_async.setProgress(progress);
			pb_async.setSecondaryProgress(sub_progress);
		} else if (mShowMode == DIALOG_HORIZONTAL) {
			mDialog.setProgress(progress);
			mDialog.setSecondaryProgress(sub_progress);
		}
	}

	@Override
	public void onBegin(String request) {
		tv_async.setText(request+"开始加载");
		if (mDialog == null || mDialog.isShowing() != true) {
			if (mShowMode == DIALOG_CIRCLE) {
				mDialog = ProgressDialog.show(this, "稍等", request+"页面加载中……");
			} else if (mShowMode == DIALOG_HORIZONTAL) {
				mDialog = new ProgressDialog(this);
				mDialog.setTitle("稍等");
				mDialog.setMessage(request+"页面加载中……");
				mDialog.setIcon(R.drawable.ic_search);
				mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mDialog.show();
			}
		}
	}

}
