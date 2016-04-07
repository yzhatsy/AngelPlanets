package com.angelplanets.app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angelplanets.app.R;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 登录页面
 * Created by 123 on 2016/3/21.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

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
        setListener();

    }


    private void setListener() {
        mBack.setOnClickListener(this);
        tv_forgotten_pwd.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    private void initView() {
        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mUserName = (EditText) findViewById(R.id.et_user_name);
        mUserPwd = (EditText) findViewById(R.id.et_user_pwd);
        tv_forgotten_pwd = (TextView) findViewById(R.id.tv_forgotten_pwd);
        tv_register = (TextView) findViewById(R.id.tv_register);
        iv_qq_login = (ImageView) findViewById(R.id.iv_qq_login);
        iv_weixin_login = (ImageView) findViewById(R.id.iv_weixin_login);
        iv_weibo_login = (ImageView) findViewById(R.id.iv_weibo_login);
        mTitle.setText("登录");
    }

    public void login(View view){

        final String userName = mUserName.getText().toString().toString();
        String password = mUserPwd.getText().toString().toString();

        Pattern pName = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$");
        Pattern pPwd = Pattern.compile("^[a-zA-Z]\\w{5,19}$");

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if(!pName.matcher(userName).find()){
            Toast.makeText(LoginActivity.this,"请输入正确格式手机号",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (!pPwd.matcher(password).find()){
            Toast.makeText(LoginActivity.this, "密码错误，密码必须以字母开头，长度在6~20之间，只能包含字符，数字和下划线", Toast.LENGTH_SHORT).show();
            return;
        }
        //密码加密
        final String md5Pwd = CUtils.md5(password);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUtils.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "login---- " + response.toString());
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("TAG", error.getMessage(), error);
                Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String,String> getParams() {
                //在这里设置需要post的参数
                Map<String,String> map = new HashMap();
                map.put("username",userName);
                map.put("password", md5Pwd);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 登陆返回的状态
     * @param response
     */
    private void parseJson(String response) {
        LoginBean loginBean = CUtils.getGson().fromJson(response,LoginBean.class);
        int code = loginBean.getStatusCode();
        if (code == 200){
            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
            int userId = loginBean.getData().getUserInfo().getUserId();
            CacheUtils.setIntToCache(this, Constant.LOGIN_FLAG, userId);
            finish();
            //overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else {
            Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;

            case R.id.tv_register:   //注册
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.tv_forgotten_pwd:       //忘记密码
                Intent intent1 = new Intent(this,FindPwdActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }
    }
}
