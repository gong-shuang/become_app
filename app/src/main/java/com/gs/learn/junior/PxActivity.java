package com.gs.learn.junior;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gs.learn.R;
import com.gs.learn.common.util.Utils;

/**
 * Created by ouyangshen on 2016/9/11.
 */
public class PxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px);

        int dip_10 = Utils.dip2px(this, 10L);
        TextView tv_padding = (TextView) findViewById(R.id.tv_padding);
        tv_padding.setPadding(dip_10, dip_10, dip_10, dip_10);
    }
}
