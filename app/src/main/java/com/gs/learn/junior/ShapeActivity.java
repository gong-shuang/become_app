package com.gs.learn.junior;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/9/15.
 */
public class ShapeActivity extends AppCompatActivity implements View.OnClickListener {

    private View v_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        v_content = (View) findViewById(R.id.v_content);

        Button btn_rect = (Button) findViewById(R.id.btn_rect);
        Button btn_oval = (Button) findViewById(R.id.btn_oval);
        btn_rect.setOnClickListener(this);
        btn_oval.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_rect) {
            v_content.setBackgroundResource(R.drawable.shape_rect_gold);
        } else if (v.getId() == R.id.btn_oval) {
            v_content.setBackgroundResource(R.drawable.shape_oval_rose);
        }
    }

}
