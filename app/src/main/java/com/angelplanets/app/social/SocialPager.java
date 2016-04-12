package com.angelplanets.app.social;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.angelplanets.app.R;
import com.angelplanets.app.social.activity.NotifyActivity;
import com.angelplanets.app.social.bean.SocialBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;
import com.angelplanets.app.utils.bases.BasePager;
import com.angelplanets.app.view.XListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 社交内容页面
 * Created by 123 on 2016/3/2.
 */
public class SocialPager extends BasePager implements View.OnClickListener {

    private static final int PAGE_COUNTS = 1;
    private XListView mXListView;
    private LinearLayout mTopPart;
    private ImageButton mNotify;//消息通知
    private int mPosition;
    private int mUserId;
    private int mPage =  URLUtils.getPageCount();

    /**
     * 社交页面接口数据集合
     */
    private List<SocialBean.SocialData> socialDatas;

    public SocialPager(Activity activity) {
        super(activity);

    }

    @Override
    protected View getView() {
        View view = View.inflate(mActivity, R.layout.pager_social, null);
        mNotify = (ImageButton) view.findViewById(R.id.ib_social_list);
        mXListView = (XListView) view.findViewById(R.id.xListView_social);
        mTopPart = (LinearLayout) View.inflate(mActivity, R.layout.pager_social_header, null);
        return view;
    }

    @Override
    public void initData() {
        socialDatas = new ArrayList<>();
        setListener();
        mXListView.setPullRefreshEnable(true); //设置可下拉刷新
        mXListView.setPullLoadEnable(true, "null");//设置可上拉加载更多
        //获取id

        mXListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                getDataFromNet(PAGE_COUNTS);
            }

            @Override
            public void onLoadMore() {
                mPage++;
                getDataFromNet(mPage);
            }
        });
        mXListView.setOnScrollListener(new XOnScrollListener());

        //从本地读取缓存数据并解析
        String s = CacheUtils.readTextFile(URLUtils.socialUrl);
        Log.e("TAG","------------------"+s);
        if (TextUtils.isEmpty(s)) {
            getDataFromNet(PAGE_COUNTS);
            return;
        }
        parseData(s);
        //getDataFromNet();

    }

    /**
     * 设置view的监听
     */
    private void setListener() {
        mNotify.setOnClickListener(this);
    }

    /**
     * 从网络中获取数据
     */
    private void getDataFromNet(int page) {
        if (page == 1){
            mPage = 1;
        }
        mUserId = CacheUtils.getIntFromCache(mActivity, Constant.LOGIN_FLAG);
        String url = URLUtils.socialUrl+mUserId+URLUtils.PAGECOUNT+page+URLUtils.SOCIALTYPE;
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "社交 网络请求成功......" + result);
                //缓存数据到本地
                CacheUtils.saveTextFile(URLUtils.socialUrl, result);
                //解析数据
                parseData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "网络请求失败......" + ex);
                mXListView.stopRefresh();
                mXListView.stopLoadMore();
                Toast.makeText(mActivity, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                mXListView.stopRefresh();
                mXListView.stopLoadMore();
                mXListView.setRefreshTime(CUtils.getCurrentTime());
            }
        });
    }

    /**
     * 根据json字符串解析数据并封装
     *
     * @param result
     */
    private void parseData(String result) {
        SocialBean socialBean = new Gson().fromJson(result, SocialBean.class);

        int size = socialDatas.size();
        //得到集合数据
        if (mPage == 1){
            socialDatas = socialBean.getData();
        }else {
            socialDatas.addAll(socialBean.getData());
        }
        mXListView.addHeaderView(mTopPart);
        mXListView.setAdapter(new SocialAdapter(mActivity, socialDatas));
        if (mPage != 1){
            mXListView.getListView().setSelection(mPosition);
        }
    }

    /**
     * button的点击监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v == mNotify){
            mActivity.startActivity(new Intent(mActivity,NotifyActivity.class));
        }
    }

    /**
     * 自定义XListView 滚动监听
     */
    class XOnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            mPosition = firstVisibleItem;
        }
    }
}
