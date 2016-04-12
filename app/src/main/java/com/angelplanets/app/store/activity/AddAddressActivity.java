package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.angelplanets.app.store.bean.AddressBean;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 新建或者修改地址
 */
public class AddAddressActivity extends Activity implements View.OnClickListener {

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;
    private EditText mName;   //姓名
    private EditText mPhone;  //电话
    private EditText mAddress;        //地址
    private TextView mSave;          //保存
    private int mUserId;
    private int mAddressId = -1;

    private String mUserName;
    private String mUserNumber;
    private String mUserAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        mUserId = getIntent().getIntExtra(Constant.LOGIN_FLAG,-1);
        Log.e("TAG","AddAddressActivity..."+mUserId);
        initView();

        AddressBean.AddressMessage addressMessage = (AddressBean.AddressMessage) getIntent().getSerializableExtra("ADDRESS");
        if (addressMessage != null){
            mName.setText(addressMessage.getName());
            mPhone.setText(addressMessage.getPhonenumber());
            mAddress.setText(addressMessage.getDetailAddress());
            mAddressId = addressMessage.getDeliveryAddressId();
        }
    }

    /**
     * 初始化操作
     */
    private void initView() {
        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);
        mName = (EditText) findViewById(R.id.et_address_name);
        mPhone = (EditText) findViewById(R.id.et_address_phone);
        mAddress = (EditText) findViewById(R.id.et_address);
        mSave = (TextView) findViewById(R.id.tv_saved);

        ib_common_back.setVisibility(View.VISIBLE);
        tv_common_title.setText("新建地址");
        ib_common_back.setOnClickListener(this);
        mSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == ib_common_back){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else if(v == mSave){
            savedAddress();
        }
    }

    /**
     * 保存地址
     */
    private void savedAddress() {
        mUserName = mName.getText().toString().trim();
        mUserNumber = mPhone.getText().toString().trim();
        mUserAddress = mAddress.getText().toString().trim();
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$");
        if (TextUtils.isEmpty(mUserName)){
            Toast.makeText(this,"请输入姓名",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(mUserNumber)){
            Toast.makeText(this,"请输入手机号码",Toast.LENGTH_SHORT).show();
        }else if (!p.matcher(mUserNumber).find()){
            Toast.makeText(this,"请输入正确手机号",Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(mUserAddress)){
            Toast.makeText(this,"请输入收货地址",Toast.LENGTH_SHORT).show();
        }else {
            if (mAddressId != -1){  //修改地址
                postJson(URLUtils.ALTER_ADDRESS_URL);
            }else {                 //添加地址
                postJson(URLUtils.ADD_ADDRESS_URL);
            }
        }
    }

    private void postJson(String addAddressUrl) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,addAddressUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "+++++++++++ " + response.toString());
                        finish();
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("TAG", error.getMessage(), error);
                Toast.makeText(AddAddressActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String,String> getParams() {
                //在这里设置需要post的参数
                Map<String,String> map = new HashMap();
               /* Log.e("TAG",mUserId+"");
                Log.e("TAG",name+"");
                Log.e("TAG", number+"");*/
                map.put("ownerId",mUserId+"");
                map.put("name", mUserName+"");
                if (mAddressId != -1){
                    map.put("deliveryAddressId",mAddressId+"");
                }
                map.put("phonenumber",mUserNumber+"");
                map.put("detailAddress",mUserAddress+"");
                map.put("postcode","000000");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


}
