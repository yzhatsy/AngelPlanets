package com.angelplanets.app.mine;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.angelplanets.app.mine.setting.SettingActivity;
import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;
import com.angelplanets.app.utils.bases.BasePager;
import com.angelplanets.app.view.CircleImageView;
import com.angelplanets.app.view.PullScrollView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;

/**
 * 我的页面
 * Created by 123 on 2016/3/2.
 */
public class MinePager extends BasePager {

    @ViewInject(R.id.tv_mine_star)
    private TextView mStar; //关注

    @ViewInject(R.id.tv_mine_follower)
    private TextView mFollower; //粉丝

    @ViewInject(R.id.tv_mine_memory)
    private TextView mMemory;  //回忆

    @ViewInject(R.id.pullScrollview)
    private PullScrollView pullScrollview;

    @ViewInject(R.id.iv_mine_icon)
    private CircleImageView iv_mine_icon; //个人头像

    @ViewInject(R.id.background_img)
    private ImageView mHeadImg;  //背景图

    @ViewInject(R.id.iv_mine_add)
    private ImageView iv_mine_add;  //添加宠物

    @ViewInject(R.id.ll_mine_addplanet)
    private LinearLayout ll_mine_addplanet;

    @ViewInject(R.id.ib_mine_setting)
    private ImageButton mSetting;
    RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
    private int userId;
    public MinePager(Activity activity) {
        super(activity);
    }

    @Override
    protected View getView() {
       View view = View.inflate(mActivity, R.layout.pager_mine,null);
        x.view().inject(this, view);
        return view;
    }


    @Override
    public void initData() {
        userId = CacheUtils.getIntFromCache(mActivity,Constant.LOGIN_FLAG);
        pullScrollview.setHeader(mHeadImg);
        setListener();
        getDataFromNet(URLUtils.USER_INFO_URL + userId + URLUtils.BASE,"base");
    }

    /**
     * 获取数据
     */
    private void getDataFromNet(String url, final String type) {

        StringRequest request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                Log.e("TAG", "mine请求成功..............： s=" + s);
                analysisData(s,type);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                Log.e("TAG", "商品详情请求失败： volleyError=" + volleyError);
                Toast.makeText(mActivity, "网络连接失败", Toast.LENGTH_SHORT).show();

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
     * 解析数据
     * @param s
     */
    private void analysisData(String s,String type) {

    }

    /**
     * 设置view的监听事件
     */
    private void setListener() {
        mStar.setOnClickListener(new MineOnclickListener());
        mFollower.setOnClickListener(new MineOnclickListener());
        iv_mine_icon.setOnClickListener(new MineOnclickListener());
        iv_mine_add.setOnClickListener(new MineOnclickListener());
        mSetting.setOnClickListener(new MineOnclickListener());


    }

    /**
     * 自定义监听事件
     */
    class MineOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v == mStar){                 //关注
                mActivity.startActivity(new Intent(mActivity,StarActivity.class));
            }else if (v == mFollower){       //粉丝
                mActivity.startActivity(new Intent(mActivity,FollowerActivity.class));
            }else if (v == iv_mine_icon){    //个人信息
                mActivity.startActivity(new Intent(mActivity,OwnerInformationActivity.class));
            }else if (v == iv_mine_add){    //添加宠物
                //addAnimal();
                Intent intent = new Intent(mActivity,AnimalInformationActivity.class);
                mActivity.startActivityForResult(intent, Constant.REQUESTCODE);
            }else if (v == mSetting){       //设置
                mActivity.startActivity(new Intent(mActivity,SettingActivity.class));
            }
        }
    }

    /**
     * 添加宠物
     */
    private void addAnimal() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.rightMargin = 20;
        layoutParams.topMargin = 20;


    }

}
