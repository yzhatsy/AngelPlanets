package com.angelplanets.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;

public class LoginActivity extends Activity {

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        ib_common_back.setVisibility(View.VISIBLE);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);
        tv_common_title.setText("登录");
    }

    public void login(View view){
        CacheUtils.setIntToCache(this, Constant.LOGIN_FLAG,167);
        finish();
    }
}
