package com.gs.learn.media;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class SpannableActivity extends AppCompatActivity {
	private TextView tv_spannable;
	private String mText = "为人民服务";
	private String mKey = "人民";
	private int mBeginPos, mEndPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spannable);
		tv_spannable = (TextView) findViewById(R.id.tv_spannable);
		tv_spannable.setText(mText);
		mBeginPos = mText.indexOf(mKey);
		mEndPos = mBeginPos + mKey.length();
		initSpannableSpinner();
	}

	private void initSpannableSpinner() {
		ArrayAdapter<String> spannableAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select_media, spannableArray);
		Spinner sp_spannable = (Spinner) findViewById(R.id.sp_spannable);
		sp_spannable.setPrompt("请选择可变字符串样式");
		sp_spannable.setAdapter(spannableAdapter);
		sp_spannable.setOnItemSelectedListener(new SpannableSelectedListener());
		sp_spannable.setSelection(0);
	}

	private String[] spannableArray={
			"增大字号", "加粗字体", "前景红色", "背景绿色", "下划线", "表情图片"
		};
	class SpannableSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			SpannableString spanText = new SpannableString(mText);
			if (arg2 == 0) {
				spanText.setSpan(new RelativeSizeSpan(1.5f), mBeginPos, mEndPos, 
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 1) {
				spanText.setSpan(new StyleSpan(Typeface.BOLD), mBeginPos, mEndPos, 
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 2) {
				spanText.setSpan(new ForegroundColorSpan(Color.RED), mBeginPos, 
						mEndPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 3) {
				spanText.setSpan(new BackgroundColorSpan(Color.GREEN), mBeginPos, 
						mEndPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 4) {
				spanText.setSpan(new UnderlineSpan(), mBeginPos, mEndPos, 
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 5) {
				spanText.setSpan(new ImageSpan(SpannableActivity.this, R.drawable.people),
						mBeginPos, mEndPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			tv_spannable.setText(spanText);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

}
