package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
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
import com.angelplanets.app.store.adapter.ShoppingAdapter;
import com.angelplanets.app.store.bean.ShopBean;
import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.ShareWare;
import com.angelplanets.app.utils.URLUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 猫咪专属详情页面
 * Created by 123 on 2016/3/21.
 */
public class CatExcluActivity extends Activity implements View.OnClickListener {

    private  int mUserId ;
    private static final int PAGE_COUNT = 1;
    private RelativeLayout mBack;//返回
    private TextView mTitle;//标题
    private ImageButton mSearch;//搜索
    private TextView mTextPoint; //红圈
    private TextView tv_red_point; //动画红点
    private ImageButton mCart; //购物车
    private PullToRefreshListView xlv_cat_exclus;
    private List<ShopBean.DataEntity.CommodityListEntity> mCommoditylist;//商品信息的集合

    private int pageCount = URLUtils.getPageCount();//分页的页数
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_exclu);
        //初始化view
        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mSearch = (ImageButton) findViewById(R.id.tv_common_search);
        mTextPoint = (TextView) findViewById(R.id.tv_shopping_count);
        tv_red_point = (TextView) findViewById(R.id.tv_red_point);
        mCart = (ImageButton) findViewById(R.id.ib_detail_cart);
        xlv_cat_exclus = (PullToRefreshListView) findViewById(R.id.xlv_cat_exclus);
        mCommoditylist = new ArrayList<>();

        initData();
        setListener();
        xlv_cat_exclus.setMode(PullToRefreshBase.Mode.BOTH);
        xlv_cat_exclus.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String currentTime = DateUtils.formatDateTime(CatExcluActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(currentTime);

                ILoadingLayout loadingLayoutProxy = xlv_cat_exclus.getLoadingLayoutProxy(true, false);
                // 刚下拉时，显示的提示
                refreshView.setPullLabel("下拉刷新...", PullToRefreshBase.Mode.PULL_FROM_START);
                loadingLayoutProxy.setRefreshingLabel("正在载入...");// 刷新时
                loadingLayoutProxy.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示
                int TAGURL = 1;
                getDataFromNet(mType, mUserId, PAGE_COUNT);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageCount++;
                getDataFromNet(mType, mUserId, pageCount);
            }
        });

        //首次进入加载数据
        getDataFromNet(mType, mUserId, PAGE_COUNT);
    }

    private List<Integer> shopCount;//商品总类的集合
    private int mCartCount;
    /**
     * 初始化数据
     */
    private void initData() {
        mBack.setVisibility(View.VISIBLE);
        mSearch.setVisibility(View.VISIBLE);
        mTitle.setText("猫咪专属");

        mType = getIntent().getIntExtra("TYPE",-1);
        mUserId = CacheUtils.getIntFromCache(this,Constant.LOGIN_FLAG);
    }


    /**
     * 从网络中获取数据
     */
    private void getDataFromNet(int type,int userId,int pageCount) {
        if (pageCount == 1){
            this.pageCount = 1;
        }

        //1.得到url
        String shoppingUrl = URLUtils.rootUrl+URLUtils.SHOPURL+type+URLUtils.USERID+userId+URLUtils.PAGECOUNT+pageCount;

        //2.获得一个请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        //3.请求数据
        StringRequest request = new StringRequest(Request.Method.GET, shoppingUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                 Log.e("TAG", "请求成功..............： s=" + s);
                //隐藏下拉控件
                xlv_cat_exclus.onRefreshComplete();
                //请求成功，解析数据
                analysisData(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                 Log.e("TAG", "请求失败： volleyError=" + volleyError);
                //隐藏下拉控件
               xlv_cat_exclus.onRefreshComplete();
                Toast.makeText(CatExcluActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

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
        //添加到请求队列
        queue.add(request);
    }

    /**
     * 解析请求到的json数据，封装成对于bean类
     * @param s
     */
    private void analysisData(String s) {
        ShopBean shopBean = new Gson().fromJson(s, ShopBean.class);
        mCartCount = shopBean.getData().getCartCount();
        Log.e("TAG","mCartCount == "+mCartCount);
        int size = mCommoditylist.size();
        if (mCartCount != 0){
            mTextPoint.setVisibility(View.VISIBLE);
        }else {
            mTextPoint.setVisibility(View.GONE);
        }
        //得到集合数据
        if (pageCount == 1){
            mCommoditylist = shopBean.getData().getCommodityList();
        }else {
            mCommoditylist.addAll(shopBean.getData().getCommodityList());
        }
        //设置adapter
        xlv_cat_exclus.setAdapter(new ShoppingAdapter(this, mCommoditylist, mTextPoint, mCartCount, tv_red_point, mCart));

        if (pageCount != 1){
            ListView listView = xlv_cat_exclus.getRefreshableView();
            //设置焦点，聚焦到某一条
            listView.setSelection(size);

        }
    }

    @Override
    protected void onResume() {
        Log.e("TAG", "onResume..................");
        super.onResume();
       if (ShareWare.getInstance().getData().size() != 0){
          mCartCount = ShareWare.getInstance().getData().size();
           mTextPoint.setVisibility(View.VISIBLE);
       }else {
           mTextPoint.setVisibility(View.GONE);
       }

    }

    /**
     * 设置view的点击监听
     */
    private void setListener() {
        mBack.setOnClickListener(this);
        mCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else if (v == mCart){
            int login = CacheUtils.getIntFromCache(this, Constant.LOGIN_FLAG);
            if (login != -1){
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                intent.putExtra(Constant.LOGIN_FLAG, login);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }else {
                startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_CODE_LOGIN);
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
