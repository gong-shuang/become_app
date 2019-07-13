package com.gs.learn.junior;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/9/14.
 */
public class ClickActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_click);
		Button btn_click = (Button) findViewById(R.id.btn_click);
		btn_click.setOnClickListener(new MyOnClickListener());
		btn_click.setOnLongClickListener(new MyOnLongClickListener());
	}

	class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btn_click) {
				Toast.makeText(ClickActivity.this, "您点击了控件：" + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	class MyOnLongClickListener implements View.OnLongClickListener {
		@Override
		public boolean onLongClick(View v) {
			if (v.getId() == R.id.btn_click) {
				Toast.makeText(ClickActivity.this, "您长按了控件：" + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
			}
			return true;
		}
	}
}
