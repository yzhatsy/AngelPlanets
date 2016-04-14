package com.angelplanets.app.utils.bases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 提供一个基类Fragment
 * Created by 123 on 2016/4/13.
 */
public class BaseFragment extends Fragment{

    private int position;
    private ArrayList<BasePager> mPagers; //装载pager的集合
    public BaseFragment(){}

    public static  BaseFragment getInstance(int position, ArrayList<BasePager> mPagers){
        BaseFragment fragment = new BaseFragment();
        fragment.position = position;
        fragment.mPagers = mPagers;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BasePager pager = mPagers.get(position);
        if (null != pager && !pager.mInit){
            pager.mInit = true;
            pager.initData();
        }
        if (null != pager)
            return pager.mRootView;
        return null;


    }
}
