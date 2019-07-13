package com.gs.learn.middle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/9/24.
 */
public class SpinnerIconActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner_icon);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        for (int i = 0; i < iconArray.length; i++) {  
            Map<String, Object> item = new HashMap<String, Object>();  
            item.put("icon", iconArray[i]);  
            item.put("name", starArray[i]);  
            list.add(item);  
        }
        SimpleAdapter starAdapter = new SimpleAdapter(this, list,  
                R.layout.item_select, new String[] { "icon", "name" },  
                new int[] {R.id.iv_icon, R.id.tv_name});
		starAdapter.setDropDownViewResource(R.layout.item_simple);
        
		Spinner sp = (Spinner) findViewById(R.id.sp_icon);
		sp.setPrompt("请选择行星");
		sp.setAdapter(starAdapter);
		sp.setSelection(0);
		sp.setOnItemSelectedListener(new MySelectedListener());
	}

	private int[] iconArray = {R.drawable.shuixing, R.drawable.jinxing, R.drawable.diqiu, 
			R.drawable.huoxing, R.drawable.muxing, R.drawable.tuxing};
	private String[] starArray = {"水星", "金星", "地球", "火星", "木星", "土星"};
	class MySelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Toast.makeText(SpinnerIconActivity.this, "您选择的是"+starArray[arg2], Toast.LENGTH_LONG).show();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
