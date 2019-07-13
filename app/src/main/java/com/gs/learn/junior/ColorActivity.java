package com.gs.learn.junior;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/9/11.
 */
public class ColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        TextView tv_code_six = (TextView) findViewById(R.id.tv_code_six);
        tv_code_six.setBackgroundColor(0x00ff00);
        TextView tv_code_eight = (TextView) findViewById(R.id.tv_code_eight);
        tv_code_eight.setBackgroundColor(0xff00ff00);
    }
}
