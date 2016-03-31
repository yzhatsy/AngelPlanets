package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angelplanets.app.R;
import com.angelplanets.app.store.adapter.ShoppingCatAdapter;
import com.angelplanets.app.store.bean.ShoppingCartBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.ShareWare;
import com.angelplanets.app.utils.URLUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ShoppingCartActivity extends Activity implements ShoppingCatAdapter.OnItemCountModify, ShoppingCatAdapter.OnItemChecked, View.OnClickListener {

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;
    private String shoppingUrl;//购物车商品url
    private ListView mCartListView;
    private CheckBox cb_total; //全选chexkbox
    private TextView tv_pay;    //需要支付的总金额
    private RelativeLayout Rl_pay;  //结算
    private TextView tv_total_pay;
    private List<ShoppingCartBean.DataEntity> mCartData; //购物车数据集合
    private int mUserId;
    private int mCommidityIdFromDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        initData();
        ib_common_back.setVisibility(View.VISIBLE);
        tv_common_title.setText("购物车");
        mCartData = new ArrayList<>();


        //获得商品列表的url
        mUserId = getIntent().getIntExtra(Constant.LOGIN_FLAG, -1);
        shoppingUrl = URLUtils.SHOPPING_CART+mUserId+URLUtils.PAGECOUNT+1;
        setListener();
        getDataFromNet();
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        //设置adapter
        ib_common_back.setOnClickListener(this);
        Rl_pay.setOnClickListener(this);
        cb_total.setOnClickListener(this);

    }

    private void initData() {

        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);
        mCartListView = (ListView) findViewById(R.id.lv_cart);
        cb_total = (CheckBox) findViewById(R.id.cb_total);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        Rl_pay = (RelativeLayout) findViewById(R.id.Rl_pay);
        tv_total_pay = (TextView) findViewById(R.id.tv_total_pay);

        mCommidityIdFromDetail = getIntent().getIntExtra("COMMODITY_ID",-1);
    }

    /**
     * 从网络中请求数据
     */
    private void getDataFromNet() {
        RequestQueue queue = Volley.newRequestQueue(this);
        //请求数据
        StringRequest request = new StringRequest(Request.Method.GET, shoppingUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                Log.e("TAG", "请求成功..............： s=" + s);

                //请求成功，解析数据
                analysisData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                Log.e("TAG", "请求失败： volleyError=" + volleyError);
            }
        }){
            /**
             * 解决乱码问题
             * @param response
             * @return
             */
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
        //添加到请求队列
        queue.add(request);
    }
    private ShoppingCatAdapter mAdapter;
    /**
     * 解析数据
     * @param str
     */
    private void analysisData(String str) {

        //判断返回的数据中购物车是否为空
        try {
            JSONObject jsonObject = new JSONObject(str);
            String data = jsonObject.optString("data");
            if ("".equals(data)){
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mjsonStr =str;

        //得到数据封装对象
        ShoppingCartBean cartBean = CUtils.getGson().fromJson(str, ShoppingCartBean.class);
        //设置数据到共享仓库
        ShareWare.getInstance().setData(cartBean.getData());
        mCartData = ShareWare.getInstance().getData();
        //添加到选中商品的集合
        for (int i=0; i<mCartData.size(); i++){

            try {
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                JSONObject object = jsonArray.getJSONObject(i);
                String jsonStr = object.optString("specification");
                if (!TextUtils.isEmpty(jsonStr)) {
                    Type type = new TypeToken<HashMap<String, String>>() {}.getType();
                    Map<String,String> map = CUtils.getGson().fromJson(jsonStr, type);
                    mCartData.get(i).setMap(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            checkShops.add(mCartData.get(i));
        }
        //创建适配器
        mAdapter = new ShoppingCatAdapter(this,mCartData,mjsonStr);
        if (mCommidityIdFromDetail != -1){
            ShoppingCartBean.DataEntity dataEntity = null;
            for (int i=0; i<mCartData.size(); i++){
                if (mCommidityIdFromDetail == mCartData.get(i).getCommodityId()){
                    mCountPrice = mCartData.get(i).getPrice()*mCartData.get(i).getCount();
                    dataEntity = mCartData.get(i);
                }else {
                    mCartData.get(i).setIsCheckable(false);
                }
            }
            mCount = 1;
            checkShops.clear();
            if (dataEntity != null){
                checkShops.add(dataEntity);
            }
            cb_total.setChecked(false);
            mCommidityIdFromDetail = -1;
        }else {
            //设置商品数量
            for (int i=0; i<mCartData.size(); i++) {
                mCountPrice += (mCartData.get(i).getPrice()*mCartData.get(i).getCount());
            }
            mCount = mCartData.size();
        }
        mCartListView.setAdapter(mAdapter);
        mAdapter.setmOnCountModify(this);
        mAdapter.setmOnItemChecked(this);

        tv_total_pay.setText("(" + mCount+")");
        tv_pay.setText("￥"+mCountPrice);

    }

    private String mjsonStr; //暂时保存的json数据
    private double mCountPrice; //总价
    private int mCount;         //商品的总数
    private TreeSet<ShoppingCartBean.DataEntity> checkShops = new TreeSet<>(); //选中的商品

    /**
     * view的点击监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            case R.id.cb_total:  //全选
                doCheckAll();
                break;
            case R.id.Rl_pay:    //结算
                Intent intent = new Intent(this,PaymentActivity.class);
                intent.putExtra(Constant.LOGIN_FLAG, mUserId);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CHECK_SHOPS",(Serializable)checkShops);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }
    }

    @Override
    public void doIncrease(int itemPosition, View showCountView, boolean isChecked) {
        //得到当前行数据
        ShoppingCartBean.DataEntity itemData = (ShoppingCartBean.DataEntity) mAdapter.getItem(itemPosition);
        //当前行商品总数
        int currCount = itemData.getCount();
        currCount++;
        itemData.setCount(currCount);
        ((TextView)showCountView).setText(currCount + "");
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int itemPosition, View showCountView, boolean isChecked) {
        ShoppingCartBean.DataEntity itemData = mAdapter.getItem(itemPosition);
        //当前行商品总数
        int currCount = itemData.getCount();
        if(currCount == 1){
            return;
        }
        currCount--;
        itemData.setCount(currCount);
        ((TextView)showCountView).setText(currCount + "");
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void onItemChecked(int itemPosition, boolean isChecked) {

        for (int i=0; i<mCartData.size(); i++){

            if(mCartData.get(i).isCheckable() != isChecked){
                break;
            }
        }
        if (isChecked){
            checkShops.add(mAdapter.getItem(itemPosition));
        }else {
            checkShops.remove(mAdapter.getItem(itemPosition));
        }

        if(isAllCheck()){
            cb_total.setChecked(true);
        }else {
            cb_total.setChecked(false);
        }
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 是否是全选状态
     * @return
     */
    private boolean isAllCheck() {
        for (ShoppingCartBean.DataEntity itemData : mCartData){
            if(!itemData.isCheckable()){
                return false;
            }
        }
        return true;
    }


    /**
     * 全选或者全不选
     */
    private void doCheckAll() {
        for (int i=0; i<mCartData.size(); i++){
            mCartData.get(i).setIsCheckable(cb_total.isChecked());
        }
        calculate();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 计算数量和价格<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部view进行数据填充<br>
     */
    private void calculate() {
        mCount = 0;
        mCountPrice = 0;
        for (int i=0; i<mCartData.size(); i++){
            ShoppingCartBean.DataEntity itemData = mCartData.get(i);
            if(itemData.isCheckable()){
                mCount++;
                mCountPrice += itemData.getPrice()*itemData.getCount();
            }
        }

        if (mCount == 0){
            Rl_pay.setClickable(false);
            Rl_pay.setBackgroundColor(Color.parseColor("#c7c3c3"));
        }else {
            Rl_pay.setClickable(true);
            Rl_pay.setBackgroundColor(Color.parseColor("#F99996"));
        }
        tv_total_pay.setText("(" + mCount + ")");
        tv_pay.setText("￥" + mCountPrice);
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
