package com.angelplanets.app.utils.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用 Adapter 抽象类
 * Created by 123 on 2016/3/2
 */
@SuppressWarnings("unused")
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData;
    protected int[] mLayoutIds;

    /**
     * @param context   Context 对象
     * @param data      List数据集合
     * @param layoutIds 布局ID, 至少设置一个
     */
    public CommonAdapter(Context context, List<T> data, int... layoutIds) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutIds = layoutIds;
    }

    /**
     * 获取 mData 元素的数量, ListView 会根据其生成对应数量的 Item
     *
     * @return Item 的数量
     */
    @Override
    public int getCount() {
        return mData.size();
    }

    /**
     * 布局类型(数据类型)的数量, 会根布局类型生成相对应的 ViewHolder
     *
     * @return 布局类型(数据类型)
     */
    @Override
    public int getViewTypeCount() {
        return mLayoutIds.length;
    }

    /**
     * 获取对应位置的数据
     *
     * @param position ListView 中 Item 在所在位置
     * @return 对应位置 Item 所需的数据
     */
    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * 使用分类型 ListView 时需要重写此方法, 返回对应的 Type,
     * Adapter 才可以根据此 Type 使用相应的 Item布局和 ViewHolder.
     *
     * @param position 对应位置 Item 所需的数据
     * @return 默认 0, 数据的 Type
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 返回 Item 的 ID, 不常用
     *
     * @param position ListView 中 Item 在所在位置
     * @return Item 的 ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据指定的位置获取一个视图, 用于显示到 ListView 中
     *
     * @param position    ListView 中 Item 在所在位置
     * @param convertView 被重用的指定类型的 View, 可能为 null
     * @param parent      返回的 View 将要被绑定到的父视图
     * @return 根据数据生成的视图对象
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int loadLayoutId = mLayoutIds[getItemViewType(position)];
        ViewHolder holder = ViewHolder.getHolder(mContext, convertView, loadLayoutId);
        convert(holder, position);
        return holder.getConvertView();
    }

    /**
     * 设置视图数据的抽象方法
     * 如果使用了分类型 ListView, 需要重写 getItemViewType(position) 返回对应的Type值,
     * 且 getItemViewType(position) 返回的 Type 值需要是在 0 ~ getViewTypeCount() 之间.
     *
     * @param holder   与布局类型对应的 ViewHolder 对象
     * @param position 当前要处理视图(数据)的位置
     */
    protected abstract void convert(ViewHolder holder, int position);

}
