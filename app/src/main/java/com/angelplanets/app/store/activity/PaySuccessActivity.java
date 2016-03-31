package com.angelplanets.app.store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.MainActivity;
import com.angelplanets.app.R;

/**
 * 支付成功页面
 * Created by 123 on 2016/3/30.
 */
public class PaySuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);

        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);

        ib_common_back.setVisibility(View.VISIBLE);
        tv_common_title.setText("奢宠商城");
        ib_common_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("PAY_SUCCESS",1);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK ){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("PAY_SUCCESS",1);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return false;
        }
        return false;
    }


}
