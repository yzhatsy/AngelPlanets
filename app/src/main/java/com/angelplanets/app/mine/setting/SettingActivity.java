package com.angelplanets.app.mine.setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.MainActivity;
import com.angelplanets.app.R;
import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;

/**
 * 设置页面
 * Created by 123 on 2016/4/6.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    private View parentView;
    private RelativeLayout mBack;
    private TextView mTitle;
    private TextView mCache;//缓存
    private RelativeLayout mResponse;//反馈
    private RelativeLayout mClean;//清除缓存
    private PopupWindow mPop;
    private LinearLayout ll_popup;
    private TextView mExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(R.layout.activity_setting, null);
        setContentView(parentView);

        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mTitle.setText("设置");
        mResponse = (RelativeLayout) findViewById(R.id.rl_help_response);
        mClean = (RelativeLayout) findViewById(R.id.rl_clean);
        mCache = (TextView) findViewById(R.id.tv_clean);
        mExit = (TextView) findViewById(R.id.tv_exit);

        try {
            //设置缓存
            String size = CacheUtils.getTotalCacheSize(this);
            mCache.setText(size);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mBack.setOnClickListener(this);
        mResponse.setOnClickListener(this);
        mClean.setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:     //返回
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            case R.id.rl_help_response:   //反馈
                startActivity(new Intent(this,ResponseActivity.class));
                break;
            case R.id.rl_clean:          //清理缓存
                cleanCache();
                break;
            case R.id.tv_exit:
                CacheUtils.setIntToCache(this, Constant.LOGIN_FLAG,-1 );
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    /**
     * 清理缓存
     */
    private void cleanCache() {
        mPop = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_popwindow, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        mPop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        mPop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        TextView tv1 = (TextView) view
                .findViewById(R.id.tv_clean);
        TextView tv2 = (TextView) view
                .findViewById(R.id.tv_cancel);
        mPop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CacheUtils.clearAllCache(SettingActivity.this);
                mCache.setText("0.00KB");
                mPop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mPop.dismiss();
                ll_popup.clearAnimation();
            }
        });

    }
}
