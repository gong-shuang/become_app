package com.gs.learn.media;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class CardViewActivity extends AppCompatActivity {
	private CardView cv_card;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_view);
		cv_card = (CardView) findViewById(R.id.cv_card);
		initCardSpinner();
	}

	private void initCardSpinner() {
		ArrayAdapter<String> cardAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select_media, cardArray);
		Spinner sp_card = (Spinner) findViewById(R.id.sp_card);
		sp_card.setPrompt("请选择卡片视图类型");
		sp_card.setAdapter(cardAdapter);
		sp_card.setOnItemSelectedListener(new CardSelectedListener());
		sp_card.setSelection(0);
	}

	private String[] cardArray={"圆角与阴影均为3", "圆角与阴影均为6", "圆角与阴影均为10", 
			"圆角与阴影均为15", "圆角与阴影均为20", "圆角与阴影均为30", "圆角与阴影均为50"};
	private int[] radiusArray = {3, 6, 10, 15, 20, 30, 50};
	class CardSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int interval = radiusArray[arg2];
			cv_card.setRadius(interval);
			cv_card.setCardElevation(interval);
			MarginLayoutParams params = (MarginLayoutParams) cv_card.getLayoutParams();
			params.setMargins(interval, interval, interval, interval);
			cv_card.setLayoutParams(params);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

}
