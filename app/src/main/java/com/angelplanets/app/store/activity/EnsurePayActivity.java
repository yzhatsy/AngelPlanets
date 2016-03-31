package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.utils.URLUtils;
import com.google.gson.Gson;
import com.pingplusplus.android.PingppLog;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 支付页面
 * Created by 123 on 2016/3/29.
 */
public class EnsurePayActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_CODE_PAYMENT = 1; //请求码

    /**
     * 微信支付渠道
     */
    private static final int CHANNEL_WECHAT = 1;
    /**
     * 支付支付渠道
     */
    private static final int CHANNEL_ALIPAY = 0;

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;
    private TextView tv_pay_count;  //总金额
    private RelativeLayout rl_weixin_pay; //微信支付
    private RelativeLayout rl_alipay;  //支付宝
    private String PAY_URL = URLUtils.PAY_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensure_pay);

        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);
        tv_pay_count = (TextView) findViewById(R.id.tv_pay_count);
        rl_weixin_pay = (RelativeLayout) findViewById(R.id.rl_weixin_pay);
        rl_alipay = (RelativeLayout) findViewById(R.id.rl_alipay);

        ib_common_back.setVisibility(View.VISIBLE);
        tv_common_title.setText("确认支付");

        PingppLog.DEBUG = true;

        setListener();
    }

    /**
     * 点击监听
     */
    private void setListener() {
        ib_common_back.setOnClickListener(this);
        rl_weixin_pay.setOnClickListener(this);
        rl_alipay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
        String cleanString = "145".replaceAll(replaceable, "");
        int amount = Integer.valueOf(new BigDecimal(cleanString).toString());

        int customerId = 167;
        String addressId = "1";
        String phonenumber = "18720932369";
        String address= "北京市朝阳区";
        String customerName = "果爷";
        Map map = new HashMap();
        map.put("commodityId",24);
        map.put("count",1);
        map.put("price",145);
        List orderCommodityList = new ArrayList();
        orderCommodityList.add(map);

        switch (v.getId()){
            case R.id.ib_common_back:    //返回
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;

            case R.id.rl_weixin_pay:    //微信
                new PaymentTask().execute(new PaymentRequest( customerId, addressId, amount, CHANNEL_WECHAT, phonenumber,address,customerName, orderCommodityList));
                break;

            case R.id.rl_alipay:       //支付宝
                new PaymentTask().execute(new PaymentRequest( customerId, addressId, amount, CHANNEL_ALIPAY, phonenumber,address,customerName, orderCommodityList));
                break;
        }
    }

    class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

        @Override
        protected void onPreExecute() {

            //按键点击之后的禁用，防止重复点击
            rl_weixin_pay.setOnClickListener(null);
            rl_alipay.setOnClickListener(null);
        }

        @Override
        protected String doInBackground(PaymentRequest... pr) {

            PaymentRequest paymentRequest = pr[0];
            String data = null;
            String json = new Gson().toJson(paymentRequest);
            Log.e("TAG","json....="+json);
            PingppLog.a(json);

            try {
                //向Your Ping++ Server SDK请求数据，返回请求信息
                data = postJson(PAY_URL, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
            if(null==data){
                showMsg("请求出错", "请检查URL", "URL无法获取charge");
                return;
            }
            Log.d("charge", data);
            String jsonStr = null;
            try {
                JSONObject jsonObject = new JSONObject(data);
                jsonStr = jsonObject.optString("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(EnsurePayActivity.this, com.pingplusplus.android.PaymentActivity.class);
            intent.putExtra(com.pingplusplus.android.PaymentActivity.EXTRA_CHARGE, jsonStr);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        }

    }


    /**
     * 带回调的返回
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        rl_weixin_pay.setOnClickListener(this);
        rl_alipay.setOnClickListener(this);

        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                if ("success".equals(result)){

                    startActivity(new Intent(this,PaySuccessActivity.class));
                }
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
            }
        }
    }
    /**
     * 支付信息
     */
    class PaymentRequest {
       // String channel;
       // int amount;
        int customerId;
        String addressId;
        double amount;
        int payType;
        String phonenumber;
        String address;
        String customerName;
        List orderCommodityList = new ArrayList();


        public PaymentRequest(int customerId, String addressId,double amount,int payType, String phonenumber,String address,String customerName,  List orderCommodityList) {
            /*this.channel = channel;
            this.amount = amount;*/

            this.customerId = customerId;
            this.addressId = addressId;
            this.amount = amount;
            this.payType = payType;
            this.phonenumber = phonenumber;
            this.address = address;
            this.customerName = customerName;
            this.orderCommodityList = orderCommodityList;
        }
    }

    /**
     * 向服务器发起post请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    private static String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).post(body).build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }





    /**
     * 提示信息
     * @param title
     * @param msg1
     * @param msg2
     */
    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null !=msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null !=msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(EnsurePayActivity.this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }

    /**
     * 返回
     * @param keyCode
     * @param event
     * @return
     */
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
