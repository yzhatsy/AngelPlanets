package com.angelplanets.app.utils.bases;

import android.app.Activity;
import android.view.View;

/**
 * 各个页面的基类
 * Created by 123 on 2016/3/2.
 */
public abstract class BasePager {

    public boolean mInit; //页面是否初始化
    public View mRootView;//根视图
    public Activity mActivity; //activity的引用

    public BasePager(){

    }
    public BasePager(Activity activity){
        mActivity = activity;
        mRootView = getView();
    }

    /**
     * 获取当前页面的视图，强制子类实现
     * @return
     */
    protected abstract View getView();

    /**
     * 初始化子view特有方法，由子类实现，页面加载时调用
     */
    public void initData(){}
}
