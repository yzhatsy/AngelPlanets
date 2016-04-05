package com.angelplanets.app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angelplanets.app.R;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 注册页面
 * Created by 123 on 2016/4/5.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private static final int WHAT_DELAY = 1;
    private RelativeLayout mBack;
    private TextView mTitle;

    private EditText mPhoneNumber;
    private EditText mCode;
    private EditText mPassword;
    private EditText mConfirmPwd;
    private TextView mObtainCode;
    private RequestQueue requestQueue;
    private int mResultCode;
    private int time = 60;
    private String mPhone; //用户号码
    /**
     * 自定义Handler
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_DELAY:
                    time--;
                    if (time>0){
                        mObtainCode.setText("已发送(" + time + ")");
                        sendEmptyMessageDelayed(WHAT_DELAY, 1000);
                    }else {
                        mObtainCode.setText("获取验证码");
                        mObtainCode.setClickable(true);
                        time = 60;
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        requestQueue = Volley.newRequestQueue(this);
        setListener();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mTitle.setText("注册");
        mPhoneNumber = (EditText) findViewById(R.id.et_register_phone);
        mCode = (EditText) findViewById(R.id.et_register_code);
        mPassword = (EditText) findViewById(R.id.et_register_pwd);
        mConfirmPwd = (EditText) findViewById(R.id.et_register_confirm_pwd);
        mObtainCode = (TextView) findViewById(R.id.tv_register_obtain_code);
    }

    /**
     * 点击监听
     */
    private void setListener() {
        mBack.setOnClickListener(this);
        mObtainCode.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;

            case R.id.tv_register_obtain_code:
                obtainCode();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void obtainCode() {
        mPhone = mPhoneNumber.getText().toString().trim();
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$");
        if(TextUtils.isEmpty(mPhone)){
            Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }else if (!p.matcher(mPhone).find()){
            Toast.makeText(RegisterActivity.this, "请输入正确格式的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        //设置状态
        handler.removeMessages(WHAT_DELAY);
        mObtainCode.setText("已发送(60)");
        mObtainCode.setClickable(false);
        //发送延时消息
        handler.sendEmptyMessageDelayed(WHAT_DELAY,1000);

        StringRequest request = new StringRequest(Request.Method.GET, URLUtils.OBTAIN_CODE_URL+mPhone, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                Log.e("TAG", "获取验证码返回..............： s=" + s);
                analysisData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                Toast.makeText(RegisterActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String data = new String(response.data,"UTF-8");
                    return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(request);


    }

    /**
     * 获取验证码后返回数据的解析
     * @param s
     */
    private void analysisData(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String message = jsonObject.optString("message");
            int statusCode = jsonObject.optInt("statusCode");
            boolean success = jsonObject.optBoolean("success");
            ObtainCodeBean bean = new ObtainCodeBean();
            bean.message = message;
            bean.statusCode = statusCode;
            bean.success = success;
            if (bean.success){
                String jsonData = jsonObject.optString("data");
                JSONObject data = new JSONObject(jsonData);
                int identifyCode = data.optInt("identifyCode");
                mResultCode = identifyCode;

            }else if (bean.statusCode == 201){
                Toast.makeText(RegisterActivity.this,bean.message,Toast.LENGTH_SHORT).show();
                handler.removeMessages(WHAT_DELAY);
                mObtainCode.setText("获取验证码");
                mObtainCode.setClickable(true);
                time = 60;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 下一步
     * @param view
     */
    public void nextStep(View view){
        String obtainCode = mCode.getText().toString().trim();
        String pwd = mPassword.getText().toString().trim();
        String confirmPwd = mConfirmPwd.getText().toString().trim();
        Pattern pPwd = Pattern.compile("^[a-zA-Z]\\w{5,19}$");

        if (!obtainCode.equals(mResultCode+"")){
            Toast.makeText(RegisterActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pwd)){
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (!pPwd.matcher(pwd).find()){
            Toast.makeText(RegisterActivity.this, "密码错误，密码必须以字母开头，长度在6~20之间，只能包含字符，数字和下划线", Toast.LENGTH_SHORT).show();
            return;
        }else if (!pwd.equals(confirmPwd)){
            Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        //加密
        String md5Pwd = CUtils.md5(pwd);
        //注册
        register(mPhone,md5Pwd);
    }

    /**
     * 注册
     * @param phone
     * @param pwd
     */
    private void register(final String phone, final String pwd) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUtils.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "register---- " + response.toString());
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                Toast.makeText(RegisterActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String,String> getParams() {
                //在这里设置需要post的参数
                Map<String,String> map = new HashMap();
                map.put("phonenumber",phone);
                map.put("password", pwd);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 登录成功解析
     * @param response
     */
    private void parseJson(String response) {

        try {
            JSONObject jsonStr = new JSONObject(response);
            int userId = jsonStr.optInt("data");
            Intent intent = new Intent(RegisterActivity.this,PerfectInformationActivity.class);
            intent.putExtra(Constant.LOGIN_FLAG,userId);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
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

class ObtainCodeBean {
    DataEntity data;
    String message;
    int statusCode;
    boolean success;

    public static class DataEntity {
        int identifyCode;
        String identifyPhoneNumber;
    }
}
