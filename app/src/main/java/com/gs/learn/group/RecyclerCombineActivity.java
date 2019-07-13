package com.gs.learn.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.learn.group.adapter.CombineAdapter;
import com.gs.learn.group.bean.GoodsInfo;
import com.gs.learn.group.widget.SpacesItemDecoration;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/21.
 */
public class RecyclerCombineActivity extends AppCompatActivity {
	
	private RecyclerView rv_combine;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler_combine);
		rv_combine = (RecyclerView) findViewById(R.id.rv_combine);
		
		GridLayoutManager manager = new GridLayoutManager(this, 4);
		//以下占位规则的意思是：第一项和第二项占两列，其它项占一列
		//如果网格的列数为四，那么第一项和第二项平分第一行，第二行开始每行有四项
		manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
            	if (position == 0 || position==1) {
                    return 2;
            	} else {
                    return 1;
            	}
            }
        });
		rv_combine.setLayoutManager(manager);
		
		CombineAdapter adapter = new CombineAdapter(this, GoodsInfo.getDefaultCombine());
		adapter.setOnItemClickListener(adapter);
		adapter.setOnItemLongClickListener(adapter);
		rv_combine.setAdapter(adapter);
		rv_combine.setItemAnimator(new DefaultItemAnimator());
		rv_combine.addItemDecoration(new SpacesItemDecoration(1));
	}
	
}
