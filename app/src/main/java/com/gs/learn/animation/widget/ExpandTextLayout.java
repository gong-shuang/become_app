package com.gs.learn.animation.widget;

import com.gs.learn.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ExpandTextLayout extends LinearLayout implements OnClickListener {
	private Context mContext;
	private LinearLayout ll_content;
	private TextView tv_content;
	private int mNormalLines = 3;
	private boolean bSelected = false;

	public ExpandTextLayout(Context context) {
		this(context, null);
	}

	public ExpandTextLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater.from(mContext).inflate(R.layout.text_expand, this, true);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		tv_content = (TextView) findViewById(R.id.tv_content);
		ll_content.setOnClickListener(this);
		tv_content.setHeight(tv_content.getLineHeight() * mNormalLines);
	}

	public void setNormalLines(int lines) {
		mNormalLines = lines;
	}

	public void setText(String content) {
		tv_content.setText(content);
	}

	public void setText(int id) {
		setText(mContext.getResources().getString(id));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ll_content) {
			bSelected = !bSelected;
			tv_content.clearAnimation();
			final int deltaValue;
			final int startValue = tv_content.getHeight();
			if (bSelected) {
				deltaValue = tv_content.getLineHeight() * tv_content.getLineCount() - startValue;
			} else {
				deltaValue = tv_content.getLineHeight() * mNormalLines - startValue;
			}
			Animation animation = new Animation() {
				protected void applyTransformation(float interpolatedTime, Transformation t) {
					tv_content.setHeight((int) (startValue + deltaValue * interpolatedTime));
				}
			};
			animation.setDuration(500);
			tv_content.startAnimation(animation);
		}
	}
  
}
