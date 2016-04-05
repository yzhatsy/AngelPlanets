package com.angelplanets.app.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.view.CircleImageView;

/**
 * 完善个人信息页面
 * Created by 123 on 2016/4/5.
 */
public class PerfectInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mBack;
    private TextView mTitle;
    private TextView mConfirm;

    private RelativeLayout rl_icon;  //头像
    private RelativeLayout rl_gender;  //性别
    private RelativeLayout rl_star;  //星球
    private EditText et_name;//昵称
    private CircleImageView iv_animal_icon;
    private TextView tv_name;
    private TextView tv_star;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_information);
        initView();
        setlistener();
    }

    /**
     * 设置监听事件
     */
    private void setlistener() {
        mBack.setOnClickListener(this);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mConfirm = (TextView) findViewById(R.id.tv_common_confirm);
        mConfirm.setVisibility(View.VISIBLE);
        mConfirm.setText("完成");
        mTitle.setText("完善信息");

        rl_icon = (RelativeLayout) findViewById(R.id.rl_icon);
        rl_gender = (RelativeLayout) findViewById(R.id.rl_gender);
        rl_star = (RelativeLayout) findViewById(R.id.rl_star);
        et_name = (EditText) findViewById(R.id.et_name);
        iv_animal_icon = (CircleImageView) findViewById(R.id.iv_animal_icon);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_star = (TextView) findViewById(R.id.tv_star);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK ){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return false;
        }
        return false;
    }
}
