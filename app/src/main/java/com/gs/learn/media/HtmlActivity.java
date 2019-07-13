package com.gs.learn.media;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class HtmlActivity extends AppCompatActivity implements OnClickListener {
	private EditText et_html;
	private TextView tv_html;
	private String mStr = "为人民服务<br><b>为人民服务</b><br><font color='#ff0000'>为人民服务</font><br><h1><font color='#00aaaa'>为人民服务</font></h1>";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html);
		et_html = (EditText) findViewById(R.id.et_html);
		tv_html = (TextView) findViewById(R.id.tv_html);
		findViewById(R.id.btn_html).setOnClickListener(this);
		et_html.setText(mStr);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_html) {
			String htmlStr = et_html.getText().toString();
			tv_html.setText(Html.fromHtml(htmlStr));
		}
	}

}
