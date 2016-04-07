package com.angelplanets.app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 修改密码页面
 * Created by 123 on 2016/4/6.
 */
public class AlterPwdActivity extends Activity implements View.OnClickListener {

    private RelativeLayout mBack;
    private TextView mTitle;
    private EditText mNewPwd;
    private EditText mConfirmPwd;
    private int mUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_pwd);

        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mTitle.setText("修改密码");
        mNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        mConfirmPwd = (EditText) findViewById(R.id.et_confirm_pwd);
        mUserId = getIntent().getIntExtra(Constant.LOGIN_FLAG,-1);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }

    public void confirm(View view){
        String newPassword = mNewPwd.getText().toString().trim();
        String confirmPwd = mConfirmPwd.getText().toString().trim();
        Pattern pPwd = Pattern.compile("^[a-zA-Z]\\w{5,19}$");

        if(TextUtils.isEmpty(newPassword)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (!pPwd.matcher(newPassword).find()){
            Toast.makeText(this, "密码错误，密码必须以字母开头，长度在6~20之间，只能包含字符，数字和下划线", Toast.LENGTH_SHORT).show();
            return;
        }else if (!newPassword.equals(confirmPwd)){
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        //把修改后的密码同步服务器
        String md5Pwd = CUtils.md5(newPassword);
        alterPassword(md5Pwd);
    }

    /**
     * 同步新密码
     */
    private void alterPassword(final String md5Pwd) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUtils.UPDATE_PWD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "alter---- " + response.toString());
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("TAG", error.getMessage(), error);
                Toast.makeText(AlterPwdActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String,String> getParams() {
                //在这里设置需要post的参数
                Map<String,String> map = new HashMap();
                map.put("id",mUserId+"");
                map.put("password", md5Pwd);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void parseJson(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String message = object.optString("message");
            boolean success = object.optBoolean("success");
            if (success){
                Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
                finishActivity(Constant.FIND_PWD_REQUEST_CODE);
            }else {
                Toast.makeText(this,"修改失败",Toast.LENGTH_SHORT).show();
            }
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
