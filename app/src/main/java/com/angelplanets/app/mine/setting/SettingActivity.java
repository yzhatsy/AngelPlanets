package com.angelplanets.app.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;

/**
 * 设置页面
 * Created by 123 on 2016/4/6.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mBack;
    private TextView mTitle;
    private RelativeLayout mResponse;//反馈
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mTitle.setText("设置");
        mResponse = (RelativeLayout) findViewById(R.id.rl_help_response);

        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:     //返回
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            case R.id.rl_help_response:   //反馈
                startActivity(new Intent(this,ResponseActivity.class));
                break;
        }
    }
}
