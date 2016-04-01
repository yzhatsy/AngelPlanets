package com.angelplanets.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;

public class LoginActivity extends Activity {

    private RelativeLayout mBack;
    private TextView mTitle;
    private EditText mUserName;  //用户名
    private EditText mUserPwd;   //密码
    private TextView tv_forgotten_pwd;
    private TextView tv_register;
    private ImageView iv_qq_login;
    private ImageView iv_weixin_login;
    private ImageView iv_weibo_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {
        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mUserName = (EditText) findViewById(R.id.et_user_name);
        mUserPwd = (EditText) findViewById(R.id.et_user_pwd);
        mTitle.setText("登录");
    }

    public void login(View view){
        CacheUtils.setIntToCache(this, Constant.LOGIN_FLAG,167);
        finish();
    }
}
