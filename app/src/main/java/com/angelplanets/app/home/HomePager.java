package com.angelplanets.app.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.utils.URLUtils;
import com.angelplanets.app.utils.bases.BasePager;
import com.angelplanets.app.utils.common.CommonPagerAdapter;
import com.angelplanets.app.view.CircleImageView;
import com.angelplanets.app.view.LoopViewPager;
import com.angelplanets.app.view.MyGridView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页页面
 * Created by 123 on 2016/3/2.
 */
public class HomePager extends BasePager {

    @ViewInject(R.id.vp_home_banner)
    private LoopViewPager mBannerViewPager;

    @ViewInject(R.id.ll_point_group)
    private LinearLayout mPointGroup;

    @ViewInject(R.id.gv_home_star)
    private MyGridView mGridView;

    @ViewInject(R.id.rl_find)
    private RelativeLayout rl_find;
    private List<BannerBean.BannerEntity> bannerData;  //装载banner数据的集合

    private ImageOptions mImageOptions;//图片处理属性
    private int preSelectPosition = 0; // 圆点高亮显示的位置


    private int[] images = new int[8]; //测试数据


    public HomePager(Activity activity) {
        super(activity);
        mImageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(240), DensityUtil.dip2px(240))
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
    }

    /**
     * 加载视图
     * @return
     */
    @Override
    protected View getView() {
        View view = View.inflate(mActivity, R.layout.pager_home,null);
        x.view().inject(this, view);
        TextView textView = (TextView) view.findViewById(R.id.tv_common_title);
        textView.setText("奢宠计划");
        return view;
    }

    /**
     * 页面加载后调用的方法, 在此处请求数据并显示
     */
    @Override
    public void initData() {
        bannerData = new ArrayList<>();
        //联网请求数据
        getDataFromNet(URLUtils.bannerUrl);

        for (int i=0; i<8; i++){
            images[i] = R.drawable.oval_6_copy_4_2x;
        }

        mGridView.setAdapter(new GridAdapter());
        rl_find.setOnClickListener(new HomeOnclickListener());

    }

    /**
     * 点击监听
     */
    class HomeOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == rl_find){
                mActivity.startActivity(new Intent(mActivity,FoundActivity.class));
            }
        }
    }
    /**
     * 自定义GridView 的适配器
     */
    class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v  = View.inflate(mActivity,R.layout.item_home_page_bottom_part,null);
            CircleImageView imageView = (CircleImageView) v.findViewById(R.id.iv_item_icon);
            imageView.setImageResource(images[position]);
            return v;
        }
    }

    /**
     *  联网请求数据
     * @param url 路径
     */
    private void getDataFromNet(String url) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "网络请求成功......" + result);
                //解析数据
                parseData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "网络请求失败......" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 解析json格式数据
     * @param result 服务器返回的数据结果
     */
    private void parseData(String result) {
        BannerBean bannerBean = new Gson().fromJson(result, BannerBean.class);
        //获得数据
        bannerData = bannerBean.getData();

        //设置ViewPager适配器
        BannerViewPagerAdapter pagerAdapter = new BannerViewPagerAdapter(bannerData);
        mBannerViewPager.setAdapter(pagerAdapter);
        mBannerViewPager.setCurrentItem(bannerData.size() * 1000);
        mBannerViewPager.setLooped(true); //设置自动轮播
        mPointGroup.removeAllViews();
        //添加圆点
        addPoint();

        //设置ViewPager页面改变的监听
        mBannerViewPager.addOnPageChangeListener(new MyAddOnPageChangeListener());
        //设置显示第一个圆点高亮
        mPointGroup.getChildAt(preSelectPosition).setEnabled(true);

    }

    /**
     * 自定义ViewPager的页面滚动监听
     */
    class MyAddOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            int count = mPointGroup.getChildCount();
            position %= count;
            for (int i = 0; i < count; i++) {
                ImageView view = (ImageView) mPointGroup.getChildAt(i);
                view.setBackgroundResource(position == i ? R.drawable.banner_point_select
                        : R.drawable.banner_point_default);
            }
            mBannerViewPager.setCurrentItem(position);
        }
    }

    /**
     * 在banner中添加指针圆点
     */
    private void addPoint() {
        //设置高亮点
        for (int i=0; i<bannerData.size(); i++){
            ImageView point = new ImageView(mActivity);
            //设置背景
            //point.setBackgroundResource(R.drawable.point_tabdetailpager_selector);
            point.setBackgroundResource(i == 0 ? R.drawable.banner_point_select : R.drawable.banner_point_default);
            //设置圆点的宽和高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(5),DensityUtil.dip2px(5));

            if (i != 0){
                params.leftMargin = DensityUtil.dip2px(8);
            }

            //添加圆点到视图中
            mPointGroup.addView(point,params);
        }

    }

    /**
     * 自定义Viewpager适配器
     */
    public class BannerViewPagerAdapter extends CommonPagerAdapter<BannerBean.BannerEntity>{
        public BannerViewPagerAdapter(List<BannerBean.BannerEntity> mData) {
            super(mData);
        }

       @Override
        public int getCount() {
            return super.getCount()*2000;
        }

        @Override
        protected View getView(int position) {
            BannerBean.BannerEntity bannerEntity = getItem(position % super.getCount());
            ImageView imageView = new ImageView(mActivity);
            imageView.setImageResource(R.drawable.index_banner_2x);
            //使用xUtil联网请求图片
            x.image().bind(imageView, URLUtils.rootUrl + bannerEntity.getPhotoUrl(), mImageOptions);
            switch (position){
                case 0:
                    imageView.setOnClickListener(new BannnerViewPageOnClickListener(position));
                    break;
                case 1:
                    imageView.setOnClickListener(new BannnerViewPageOnClickListener(position));
                    break;
                case 2:
                    imageView.setOnClickListener(new BannnerViewPageOnClickListener(position));
                    break;
                case 3:
                    imageView.setOnClickListener(new BannnerViewPageOnClickListener(position));
                    break;
            }
            return imageView;
        }
    }


    /**
     * 自定义ViewPage的点击监听类
     */
    class BannnerViewPageOnClickListener implements View.OnClickListener{

        int position = 0;
        public BannnerViewPageOnClickListener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            switch (position){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    }


}
