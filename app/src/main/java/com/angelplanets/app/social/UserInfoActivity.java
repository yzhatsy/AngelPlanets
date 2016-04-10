package com.angelplanets.app.social;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.angelplanets.app.mine.bean.BaseInfoBean;
import com.angelplanets.app.mine.bean.PetsListBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;
import com.angelplanets.app.view.CircleImageView;
import com.angelplanets.app.view.MyListView;
import com.angelplanets.app.view.PullScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 好友个人信息
 */
public class UserInfoActivity extends Activity implements View.OnClickListener {

    private ImageButton mBack;
    private int mCustomerId;
    private ImageView background_img;
    private PullScrollView user_pullScrollview;
    private CircleImageView iv_mine_icon;
    private TextView tv_mine_name;
    private TextView tv_mine_signature;
    private TextView tv_mine_star;
    private TextView tv_mine_follower;
    private LinearLayout ll_mine_addplanet;
    private MyListView ll_user_describe;
    private RequestQueue requestQueue ;
    private DisplayImageOptions options; //处理图片的属性
    ImageLoader imageLoader = ImageLoader.getInstance();//获取imgeLoader实例
    private  List<PetsListBean.DataEntity> petList;
    private List<MemoryBean.DataEntity.SocialsEntity> socials; //回忆状态列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mCustomerId = getIntent().getIntExtra(Constant.CUSTOMER_ID, -1);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        requestQueue = Volley.newRequestQueue(this);
        mBack = (ImageButton) findViewById(R.id.back);
        background_img = (ImageView) findViewById(R.id.background_img);
        user_pullScrollview = (PullScrollView) findViewById(R.id.user_pullScrollview);
        iv_mine_icon = (CircleImageView) findViewById(R.id.iv_mine_icon);
        tv_mine_name = (TextView) findViewById(R.id.tv_mine_name);
        tv_mine_signature = (TextView) findViewById(R.id.tv_mine_signature);
        tv_mine_star = (TextView) findViewById(R.id.tv_mine_star);
        tv_mine_follower = (TextView) findViewById(R.id.tv_mine_follower);
        ll_mine_addplanet = (LinearLayout) findViewById(R.id.ll_mine_addplanet);
        ll_user_describe = (MyListView) findViewById(R.id.ll_user_describe);
        user_pullScrollview.setHeader(background_img);

        mBack.setOnClickListener(this);
        tv_mine_star.setOnClickListener(this);
        getDataFromNet(URLUtils.USER_INFO_URL + mCustomerId + URLUtils.BASE, "base");
        getDataFromNet(URLUtils.USER_INFO_URL + mCustomerId + URLUtils.PETS, "pet");
        int userId = CacheUtils.getIntFromCache(this,Constant.LOGIN_FLAG);

        String code = CUtils.md5("*{"+userId+")AP("+mCustomerId+"}*");
        String memoryUrl = URLUtils.MEMORY_URL+userId+URLUtils.CUSTOMER_ID+mCustomerId+URLUtils.CODE+code+URLUtils.PAGECOUNT+1;
        Log.e("TAG", "MEMORY..."+memoryUrl);
        getDataFromNet(memoryUrl, "memory");


    }

    /**
     * 网络请求
     */
    private void getDataFromNet(String url,final String type) {
        StringRequest request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                Log.e("TAG", "请求成功..............： s=" + s);
                analysisData(s,type);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                Log.e("TAG", "请求失败： volleyError=" + volleyError);
                Toast.makeText(UserInfoActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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
     * @param json
     * @param type
     */
    private void analysisData(String json,String type){
        if ("base".equals(type) ){
            parseBaseData(json);
        }else if ("pet".equals(type)){
            parsePetData(json);
        }else if ("memory".equals(type)){
            parseMemoryData(json);
        }
    }

    /**
     * 回忆列表
     * @param json
     */
    private void parseMemoryData(String json) {
        MemoryBean memoryBean = CUtils.getGson().fromJson(json, MemoryBean.class);
        socials = memoryBean.getData().getSocials();
        ll_user_describe.setFocusable(false);
        ll_user_describe.setAdapter(new MemoryAdapter());
        ll_user_describe.setFocusable(false);
    }

    /**
     * 宠物信息
     * @param json
     */
    private void parsePetData(String json) {
        PetsListBean bean = CUtils.getGson().fromJson(json, PetsListBean.class);
        petList = bean.getData();

        for (int i=0; i<petList.size(); i++){
            CircleImageView imageView = new CircleImageView(this);
            imageView.setBorderWidth(2);
            imageView.setBorderColor(Color.parseColor("#f1f1f1"));
            x.image().bind(imageView, URLUtils.rootUrl+petList.get(i).getAvatarUrl());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(58), DensityUtil.dip2px(58));
            params.rightMargin = DensityUtil.dip2px(20);
            params.gravity = Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(params);
            ll_mine_addplanet.addView(imageView);

        }
    }

    /**
     * 解析用户基本信息
     * @param s
     */
    private void parseBaseData(String s) {
        BaseInfoBean bean = CUtils.getGson().fromJson(s, BaseInfoBean.class);
        BaseInfoBean.DataEntity data = bean.getData();
        tv_mine_star.setText("关注"+data.getFollowningCount());
        tv_mine_follower.setText("粉丝"+data.getFollowerCount());
        tv_mine_name.setText(data.getNickname());
        if (!"".equals(data.getSignature())){
            tv_mine_signature.setText(data.getSignature());
        }
        imageLoader.displayImage(URLUtils.rootUrl + data.getAvatarUrl(), iv_mine_icon, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.e("TAG", "图片加载失败.....");
            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Bitmap bluBitmap = CUtils.fastblur(UserInfoActivity.this, loadedImage, 20);
                background_img.setImageBitmap(bluBitmap);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == mBack){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else if (v == tv_mine_star){
            Intent intent = new Intent(this,StarListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 适配器
     */
    class MemoryAdapter extends BaseAdapter{

        String times;
        @Override
        public int getCount() {
            return socials.size();
        }
        @Override
        public Object getItem(int position) {
            return socials.get(position);
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(UserInfoActivity.this,R.layout.item_user_info_list,null);
                holder.ll_time = (LinearLayout) convertView.findViewById(R.id.ll_time);
                holder.tv_day = (TextView) convertView.findViewById(R.id.tv_day);
                holder.tv_hour = (TextView) convertView.findViewById(R.id.tv_hour);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tv_day_hour = (TextView) convertView.findViewById(R.id.tv_day_hour);
                holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
                holder.tv_descirbe = (TextView) convertView.findViewById(R.id.tv_descirbe);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            MemoryBean.DataEntity.SocialsEntity social = socials.get(position);
            holder.tv_descirbe.setText(social.getSocialDetial());
            holder.tv_count.setText(social.getSocialPhoto().size()+"");
            long dataTime = social.getSocialCreateTime();

            long time = System.currentTimeMillis();
            SimpleDateFormat format = null;
            long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

            if (day - 1 > 0) {
                format = new SimpleDateFormat("dd/", Locale.CHINA);
                String temp_time = format.format(dataTime);
                if (!temp_time.equals(times)){
                    holder.ll_time.setVisibility(View.VISIBLE);
                    holder.tv_day.setText(temp_time+"");
                    format = new SimpleDateFormat("MM月", Locale.CHINA);
                    times = temp_time;
                    holder.tv_day_hour.setText(format.format(dataTime));
                }else {
                    holder.ll_time.setVisibility(View.GONE);
                }

            } else{
                holder.tv_hour.setVisibility(View.VISIBLE);
                format = new SimpleDateFormat("HH:mm", Locale.CHINA);
                holder.tv_hour.setText(format.format(dataTime));
            }
            x.image().bind(holder.iv_icon, URLUtils.rootUrl+social.getSocialPhoto().get(0));
            return convertView;
        }
    }

   class ViewHolder{
       LinearLayout ll_time;
       TextView tv_day;
       TextView tv_day_hour;
       TextView tv_hour;
       ImageView iv_icon;
       TextView tv_count;
       TextView tv_descirbe;
   }

}
