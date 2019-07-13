package com.gs.learn.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/9/24.
 */
//具体的房贷计算与控件处理逻辑，还请读者自行练习补充
public class MortgageActivity extends AppCompatActivity {
	
	private String[] yearArray = {"5年", "10年", "15年", "20年", "30年"};
	private String[] ratioArray = {
		"2015年10月24日 五年期利率 4.90%",
		"2015年08月26日 五年期利率 5.15%",
		"2015年06月28日 五年期利率 5.40%",
		"2015年05月11日 五年期利率 5.65%",
		"2015年03月01日 五年期利率 5.90%",
		"2014年11月22日 五年期利率 6.15%",
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mortgage);

		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, yearArray);
		yearAdapter.setDropDownViewResource(R.layout.item_dropdown);
		Spinner sp_year = (Spinner) findViewById(R.id.sp_year);
		sp_year.setPrompt("请选择贷款年限");
		sp_year.setAdapter(yearAdapter);
		sp_year.setSelection(0);

		ArrayAdapter<String> ratioAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, ratioArray);
		ratioAdapter.setDropDownViewResource(R.layout.item_dropdown);
		Spinner sp_ratio = (Spinner) findViewById(R.id.sp_ratio);
		sp_ratio.setPrompt("请选择基准利率");
		sp_ratio.setAdapter(ratioAdapter);
		sp_ratio.setSelection(0);
		
	}
	
}
