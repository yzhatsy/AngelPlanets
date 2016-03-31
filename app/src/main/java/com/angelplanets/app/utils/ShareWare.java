package com.angelplanets.app.utils;

import com.angelplanets.app.store.bean.ShoppingCartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局共享数据
 * Created by 123 on 2016/3/28.
 */
public class ShareWare {
    private ShareWare(){}
    private static ShareWare instance = new ShareWare();

    public static ShareWare getInstance(){
        return instance;
    }

    //购物车数据的集合
    private List<ShoppingCartBean.DataEntity> data = new ArrayList<ShoppingCartBean.DataEntity>();

    public List<ShoppingCartBean.DataEntity> getData() {
        return data;
    }

    public void setData(List<ShoppingCartBean.DataEntity> data) {
        this.data = data;
    }
}
