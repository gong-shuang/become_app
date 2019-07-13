package com.gs.learn.junior;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gs.learn.R;

public class JuniorMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_junior);

        Button btn_px = (Button) findViewById(R.id.btn_px);
        Button btn_color = (Button) findViewById(R.id.btn_color);
        Button btn_screen = (Button) findViewById(R.id.btn_screen);
        btn_px.setOnClickListener(this);
        btn_color.setOnClickListener(this);
        btn_screen.setOnClickListener(this);

        Button btn_margin = (Button) findViewById(R.id.btn_margin);
        Button btn_gravity = (Button) findViewById(R.id.btn_gravity);
        Button btn_scroll = (Button) findViewById(R.id.btn_scroll);
        btn_margin.setOnClickListener(this);
        btn_gravity.setOnClickListener(this);
        btn_scroll.setOnClickListener(this);

        Button btn_marquee = (Button) findViewById(R.id.btn_marquee);
        Button btn_bbs = (Button) findViewById(R.id.btn_bbs);
        Button btn_click = (Button) findViewById(R.id.btn_click);
        btn_marquee.setOnClickListener(this);
        btn_bbs.setOnClickListener(this);
        btn_click.setOnClickListener(this);

        Button btn_scale = (Button) findViewById(R.id.btn_scale);
        Button btn_capture = (Button) findViewById(R.id.btn_capture);
        Button btn_icon = (Button) findViewById(R.id.btn_icon);
        btn_scale.setOnClickListener(this);
        btn_capture.setOnClickListener(this);
        btn_icon.setOnClickListener(this);

        Button btn_state = (Button) findViewById(R.id.btn_state);
        Button btn_shape = (Button) findViewById(R.id.btn_shape);
        Button btn_nine = (Button) findViewById(R.id.btn_nine);
        btn_state.setOnClickListener(this);
        btn_shape.setOnClickListener(this);
        btn_nine.setOnClickListener(this);

        Button btn_calculator = (Button) findViewById(R.id.btn_calculator);
        btn_calculator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_px) {
            Intent intent = new Intent(this, PxActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_color) {
            Intent intent = new Intent(this, ColorActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_screen) {
            Intent intent = new Intent(this, ScreenActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_margin) {
            Intent intent = new Intent(this, MarginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_gravity) {
            Intent intent = new Intent(this, GravityActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_scroll) {
            Intent intent = new Intent(this, ScrollActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_marquee) {
            Intent intent = new Intent(this, MarqueeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_bbs) {
            Intent intent = new Intent(this, BbsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_click) {
            Intent intent = new Intent(this, ClickActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_scale) {
            Intent intent = new Intent(this, ScaleActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_capture) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_icon) {
            Intent intent = new Intent(this, IconActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_state) {
            Intent intent = new Intent(this, StateActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_shape) {
            Intent intent = new Intent(this, ShapeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_nine) {
            Intent intent = new Intent(this, NineActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_calculator) {
            Intent intent = new Intent(this, CalculatorActivity.class);
            startActivity(intent);
        }
    }

}
