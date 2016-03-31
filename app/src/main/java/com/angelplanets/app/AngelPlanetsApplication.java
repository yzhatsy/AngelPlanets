package com.angelplanets.app;

import android.app.Application;

import org.xutils.x;

import com.angelplanets.app.utils.CUtils;

/**
 * 应用的全局仓库，数据供整个应用使用
 * Created by 123 on 2016/3/8.
 */
public class AngelPlanetsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CUtils.init(this);

        //xUtil的全局申明
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
