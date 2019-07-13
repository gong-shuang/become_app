package com.gs.learn.junior;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/9/14.
 */
public class MarqueeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_marquee;
    private boolean bPause = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee);
        tv_marquee = (TextView) findViewById(R.id.tv_marquee);
        tv_marquee.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_marquee) {
            bPause = !bPause;
            if (bPause) {
                tv_marquee.setFocusable(false);
                tv_marquee.setFocusableInTouchMode(false);
            } else {
                tv_marquee.setFocusable(true);
                tv_marquee.setFocusableInTouchMode(true);
            }
        }
    }
}
