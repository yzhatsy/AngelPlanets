package com.angelplanets.app.utils.common;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用 PagerAdapter 抽象类
 * Created by 123 on 2016/3/2.
 */
public abstract class CommonPagerAdapter<T> extends PagerAdapter {

    private List<T> mData;

    public CommonPagerAdapter(List<T> mData) {
        this.mData = mData;
    }

    /**
     * 获取元素(子视图)数量
     */
    @Override
    public int getCount() {
        return mData.size();
    }

    /**
     * 根据索引取出元素
     *
     * @param position 索引位置
     * @return 索引对应的元素
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * @param container 容器, 就是相应的 ViewPager
     * @param position  页面的下标位置
     * @return 指定位置的子视图
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(position);
        container.addView(view);
        return view;
    }

    /**
     * 获取需要显示的页面
     *
     * @param position 需要显示的页面的下标位置
     * @return 需要显示的页面
     */
    protected abstract View getView(int position);

    /**
     * @param view   当前显示的子视图
     * @param object 从 instantiateItem() 方法返回的对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 移除指定位置的子视图
     *
     * @param container 容器, 就是相应的 ViewPager
     * @param position  页面的下标位置
     * @param object    待移除的视图对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
