package com.gs.learn.group;

import com.gs.learn.group.adapter.LinearAdapter;
import com.gs.learn.group.bean.GoodsInfo;
import com.gs.learn.group.widget.SpacesItemDecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/21.
 */
public class RecyclerLinearActivity extends AppCompatActivity {
	
	private RecyclerView rv_linear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler_linear);
		rv_linear = (RecyclerView) findViewById(R.id.rv_linear);
		
		LinearLayoutManager manager = new LinearLayoutManager(this);
		manager.setOrientation(LinearLayout.VERTICAL);
		rv_linear.setLayoutManager(manager);

		LinearAdapter adapter = new LinearAdapter(this, GoodsInfo.getDefaultList());
		adapter.setOnItemClickListener(adapter);
		adapter.setOnItemLongClickListener(adapter);
		rv_linear.setAdapter(adapter);
		rv_linear.setItemAnimator(new DefaultItemAnimator());
		rv_linear.addItemDecoration(new SpacesItemDecoration(1));
	}
	
}
