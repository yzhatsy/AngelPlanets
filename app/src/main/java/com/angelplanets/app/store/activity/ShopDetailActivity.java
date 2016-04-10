package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.angelplanets.app.login.LoginActivity;
import com.angelplanets.app.R;
import com.angelplanets.app.store.bean.ShopDetailBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.ShareWare;
import com.angelplanets.app.utils.URLUtils;
import com.angelplanets.app.utils.common.CommonPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 商品详情页面
 * Created by 123 on 2016/3/22.
 */
public class ShopDetailActivity extends Activity implements View.OnClickListener {

    private RelativeLayout mBack;        //返回
    private TextView mTitle;          //标题
    private ImageButton mShoppingCart;//购物车
    private ViewPager mViewpager;     //商品图片viewpager
    private TextView mShopName;       //商品说明
    private LinearLayout mStar;       //星级推荐的容器
    private TextView mSale;           //现价
    private TextView mOrigPrice;      //原价
    private TextView mBrand;          //品牌
    private TextView mProperty;       //产品属性
    private TextView mMaterial;       // 材质
    private TextView mColor;          //颜色分类
    private WebView mWebview;         //商品描述的Webview
    private WebSettings mSettings;    //Webview的属性设置
    private int mCommodityId;         //商品id
    private String mUrl;              //商品详情的url
    private ShopDetailBean detailBean;
    private int mCartCount;           //购物车总数
    private RelativeLayout mCart;   //右上角购物车
    private List<String> picures;     //ViewPager图片的集合
    private ImageOptions mImageOptions;//图片处理属性
    private TextView mShowCount;  //显示购物车数目
    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        mImageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(240), DensityUtil.dip2px(240))
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();

        //得到携带的商品id
        mCommodityId = getIntent().getIntExtra("COMMODITY_ID",-1);
        mCartCount = getIntent().getIntExtra("CART_COUNT",-1);
        mUserId = CacheUtils.getIntFromCache(this, Constant.LOGIN_FLAG);
        mUrl = URLUtils.SHOP_DETAIL_URL+mCommodityId+URLUtils.USERID+mUserId;
        initView();
        setListener();
        getDataFromNet();

        //设置Webview 的支持
        mSettings = mWebview.getSettings();
        mSettings.setJavaScriptEnabled(true);  //设置支持javaScript

    }
    /**
     * 初始化view
     */
    private void initView() {
        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mShoppingCart = (ImageButton) findViewById(R.id.tv_common_search);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("猫咪专属");
        mShoppingCart.setVisibility(View.VISIBLE);
        mShoppingCart.setImageResource(R.drawable.group);
        mViewpager = (ViewPager) findViewById(R.id.vp_shop_photo);
        mShopName = (TextView) findViewById(R.id.tv_shop_name);
        mStar = (LinearLayout) findViewById(R.id.ll_star_group);
        mSale = (TextView) findViewById(R.id.tv_sale);
        mOrigPrice = (TextView) findViewById(R.id.tv_original_cost);
        mBrand = (TextView) findViewById(R.id.tv_brand);
        mProperty = (TextView) findViewById(R.id.tv_shop_property);
        mMaterial = (TextView) findViewById(R.id.tv_material);
        mColor = (TextView) findViewById(R.id.tv_color);
        mWebview = (WebView) findViewById(R.id.wv_shop_detail);
        mCart = (RelativeLayout) findViewById(R.id.rl_cart);
        mShowCount = (TextView) findViewById(R.id.tv_cart_count);
        //是否显示购物车数目
        if (mCartCount != 0){
            mShowCount.setVisibility(View.VISIBLE);
            mShowCount.setText(""+mCartCount);
        }else {
            mShowCount.setVisibility(View.GONE);
        }
        picures = new ArrayList<>();
    }

    /**
     * 请求网络获取数据
     */
    private void getDataFromNet() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                Log.e("TAG", "商品详情请求成功..............： s=" + s);
                analysisData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                Log.e("TAG", "商品详情请求失败： volleyError=" + volleyError);
                Toast.makeText(ShopDetailActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

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
        queue.add(request);
    }

    /**
     * 解析json数据
     * @param str
     */
    private void analysisData(String str) {
        detailBean = CUtils.getGson().fromJson(str, ShopDetailBean.class);
        picures = detailBean.getData().getPicures();

        //设置星级数
        addStarToGroup();

        //设置商品名
        mShopName.setText(detailBean.getData().getName());
        //设置价格
        mSale.setText(""+detailBean.getData().getPrice());
        Spannable spanStrikethrough = new SpannableString("￥"+detailBean.getData().getOldPrice());
        StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
        spanStrikethrough.setSpan(stSpan, 0, ("￥" + detailBean.getData().getOldPrice()).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mOrigPrice.setText(spanStrikethrough);

        //  设置产品说明
        try {
            JSONObject json = new JSONObject(str);
            String DataStr = json.optString("data");
            JSONObject dataJson = new JSONObject(DataStr);
            String specifiStr = dataJson.optString("specification");
            if (!TextUtils.isEmpty(specifiStr)) {
                Type type = new TypeToken<HashMap<String, String>>() {}.getType();
                HashMap map = new Gson().fromJson(specifiStr, type);

                Iterator iterator = map.entrySet().iterator();
                ArrayList<String> keyStr = new ArrayList<>();
                ArrayList<String> valueStr = new ArrayList<>();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                    keyStr.add(entry.getKey());
                    valueStr.add(entry.getValue());
                }
                mColor.setText(keyStr.get(0) + ": " + valueStr.get(0));
                mProperty.setText(keyStr.get(1) + ": " + valueStr.get(1));
                mBrand.setText(keyStr.get(2) + ": " + valueStr.get(2));
                mMaterial.setText(keyStr.get(3) + ": " + valueStr.get(3));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //设置viewPager的适配器
        mViewpager.setAdapter(new ShopViewpageAdapter(picures));
        //webView加载网页
        String htmlData = "<style type=\"text/css\"> body{color:#848484;font-size:12px;text-align:justify;text-justify:inter-ideograph;letter-spacing:0.4px} p{margin:3px}  br{margin:8px} </style>"+ detailBean.getData().getDetail();
        mWebview.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
    }

    /**
     * 设置星级数，把星添加到容器
     */
    private void addStarToGroup() {
        int starCount = detailBean.getData().getStars();
        for (int i=0; i<starCount; i++){
            ImageView starImg = new ImageView(ShopDetailActivity.this);
            starImg.setImageResource(R.drawable.icon_star);

            //设置星星的宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(12), DensityUtil.dip2px(12));
            params.leftMargin = DensityUtil.dip2px(5);
            starImg.setLayoutParams(params);

            //添加到容器
            mStar.addView(starImg);
        }
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        mBack.setOnClickListener(this);
        mCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){            //返回
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else if (v == mCart){     //购物车
            if (mUserId != -1){
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                intent.putExtra(Constant.LOGIN_FLAG, mUserId);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }else {
                startActivityForResult(new Intent(this, LoginActivity.class),Constant.REQUEST_CODE_LOGIN);
            }
        }
    }

    /**
     * 立即购买的button
     * @param v
     */
    public void purchase(View v){
        if (mUserId != -1){
            String httpurl = URLUtils.UPDATE_CART;
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,httpurl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("TAG", "response shop_detail_activity-> " + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            }) {
                @Override
                protected Map<String,String> getParams() {
                    //在这里设置需要post的参数
                    Map<String,String> map = new HashMap();
                    map.put("customerId",mUserId+"");
                    map.put("commodityId", mCommodityId+"");
                    map.put("status",1+"");
                    return map;
                }
            };
            requestQueue.add(stringRequest);
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            intent.putExtra(Constant.LOGIN_FLAG, mUserId);
            intent.putExtra("COMMODITY_ID",mCommodityId);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }else {
            startActivityForResult(new Intent(this, LoginActivity.class),Constant.REQUEST_CODE_LOGIN);
        }
    }

    /**
     * 当返回该页面是在次调用
     */
    @Override
    protected void onResume() {
        Log.e("TAG", "onResume..................");

        mCartCount = ShareWare.getInstance().getData().size();
        if (mCartCount != 0){

            mShowCount.setText(mCartCount+"");
            mShowCount.setVisibility(View.VISIBLE);
        }else {
            mShowCount.setText(0+"");
            mShowCount.setVisibility(View.GONE);
        }

        super.onResume();
    }

    /**
     * 自定义PagerAdapter
     */
    public class ShopViewpageAdapter extends CommonPagerAdapter<String> {
        public ShopViewpageAdapter(List<String> picures) {
            super(picures);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        protected View getView(int position) {
            ImageView imageView = new ImageView(ShopDetailActivity.this);
            //使用xUtil联网请求图片
            x.image().bind(imageView, URLUtils.rootUrl + picures.get(position), mImageOptions);
            return imageView;
        }
    }

    /**
     * 带回掉的返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    protected void onDestroy() {
        View view_null = View.inflate(this,R.layout.view_null,null);
        setContentView(view_null);
        super.onDestroy();
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
