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
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 * 找回密码页面
 * Created by 123 on 2016/4/6.
 */
public class FindPwdActivity extends Activity implements View.OnClickListener {
    private static final int WHAT_FORGET_DELAY = 1;
    private RelativeLayout mBack;
    private TextView mTitle;
    private EditText mPhoneNumber;
    private EditText mCode;
    private int time = 60;
    private int mResultCode;
    private TextView mObtainCode;
    private int mUserId;

    /**
     * 自定义Handler
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_FORGET_DELAY:
                    time--;
                    if (time>0){
                        mObtainCode.setText("已发送(" + time + ")");
                        sendEmptyMessageDelayed(WHAT_FORGET_DELAY, 1000);
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
        setContentView(R.layout.activity_find_pwd);

        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mTitle.setText("忘记密码");
        mPhoneNumber = (EditText) findViewById(R.id.et_forget_phone);
        mCode = (EditText) findViewById(R.id.et_forget_code);
        mObtainCode = (TextView) findViewById(R.id.tv_forget_obtain_code);
        mBack.setOnClickListener(this);
        mObtainCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else if (v == mObtainCode){
            obtainCode();
        }
    }

    /**
     * 获取验证码
     */
    public void obtainCode(){
        String mPhone = mPhoneNumber.getText().toString().trim();
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$");
        if(TextUtils.isEmpty(mPhone)){
            Toast.makeText(FindPwdActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }else if (!p.matcher(mPhone).find()){
            Toast.makeText(FindPwdActivity.this, "请输入正确格式的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        //设置状态
        handler.removeMessages(WHAT_FORGET_DELAY);
        mObtainCode.setText("已发送(60)");
        mObtainCode.setClickable(false);
        //发送延时消息
        handler.sendEmptyMessageDelayed(WHAT_FORGET_DELAY,1000);

        RequestQueue requestQueue = Volley.newRequestQueue(FindPwdActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, URLUtils.OBTAIN_UPDATA_CODE_URL+mPhone, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                Log.e("TAG", "获取验证码返回..............： s=" + s);
                analysisData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                Toast.makeText(FindPwdActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
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
     * 解析
     * @param s
     */
    private void analysisData(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String message = jsonObject.optString("message");
            int statusCode = jsonObject.optInt("statusCode");
            boolean success = jsonObject.optBoolean("success");
            if (success){
                String jsonData = jsonObject.optString("data");
                JSONObject data = new JSONObject(jsonData);
                int identifyCode = data.optInt("identifyCode");
                mUserId = data.optInt("userId");
                mResultCode = identifyCode;

            }else if (statusCode != 200){
                Toast.makeText(FindPwdActivity.this,message,Toast.LENGTH_SHORT).show();
                handler.removeMessages(WHAT_FORGET_DELAY);
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
        if (!obtainCode.equals(mResultCode+"")){
            Toast.makeText(FindPwdActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this,AlterPwdActivity.class);
        intent.putExtra(Constant.LOGIN_FLAG,mUserId);
        startActivityForResult(intent, Constant.FIND_PWD_REQUEST_CODE);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

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
