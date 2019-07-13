package com.gs.learn.middle;

import com.gs.learn.R;
import com.gs.learn.common.util.ViewUtil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by ouyangshen on 2016/9/24.
 */
public class EditHideActivity extends AppCompatActivity implements OnClickListener {

	private LinearLayout ll_hide;
	private EditText et_phone;
	private EditText et_password;
	private EditText et_other;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_hide);
		
		ll_hide= (LinearLayout) findViewById(R.id.ll_hide);
		et_phone= (EditText) findViewById(R.id.et_phone);
		et_password= (EditText) findViewById(R.id.et_password);
		et_other= (EditText) findViewById(R.id.et_other);
		
		ll_hide.setOnClickListener(this);
		et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
		et_password.addTextChangedListener(new HideTextWatcher(et_password));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ll_hide) {
			//实际上不只是et_other的软键盘会关闭，其它编辑框的软键盘也会关闭
			//因为方法内部去获取视图的WindowToken，这个Token在每个页面上都是唯一的
			ViewUtil.hideOneInputMethod(EditHideActivity.this, et_other);
		}
	}

	private class HideTextWatcher implements TextWatcher {
		private EditText mView;
		private int mMaxLength;
		private CharSequence mStr;

		public HideTextWatcher(EditText v) {
			super();
			mView = v;
			mMaxLength = ViewUtil.getMaxLength(v);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mStr = s;
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (mStr == null || mStr.length() == 0)
				return;
			if (mStr.length() == 11 && mMaxLength == 11) {
				ViewUtil.hideAllInputMethod(EditHideActivity.this);
			}
			if (mStr.length() == 6 && mMaxLength == 6) {
				ViewUtil.hideOneInputMethod(EditHideActivity.this, mView);
			}
		}
	}
}
