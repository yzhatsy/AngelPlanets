package com.angelplanets.app.utils.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 通用 ViewHolder 类
 * Created by Jian Chang on 2015-12-08.
 */
@SuppressWarnings("unused")
public final class ViewHolder {

    private View findView;
    private View convertView;
    private SparseArray<Object> tags;

    /**
     * 私有构造器, 不被外部调用, 只允许通过 getHolder() 方法获取 ViewHolder 对象
     */
    private ViewHolder(Context context, int layoutId) {
        tags = new SparseArray<>();
        findView = convertView = View.inflate(context, layoutId, null);
        convertView.setTag(this);
    }

    /**
     * 获取一个 ViewHolder 对象
     * 如果 convertView 为 null 就调用构造方法创建一个 ViewHolder 对象
     * 如果 convertView 不为 null 表示是复用的 Item, 直接去 Tag 中取 ViewHolder 对象
     */
    public synchronized static ViewHolder getHolder(@NonNull Context context,
                                                    @Nullable View convertView,
                                                    @LayoutRes int layoutId) {
        if (null == convertView)
            return new ViewHolder(context, layoutId);
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 返回当前 convertView: 就是 ListView 的 Item 视图
     */
    public View getConvertView() {
        return convertView;
    }

    /**
     * 返回当前 findView: 表示 getView() 方法从哪个视图上取一个视图对象
     * 默认和 convertView 是同一个对象, 当使用 setFindViewAt() 方法后就是 convertView 的某一个子视图
     */
    public View getFindView() {
        return findView;
    }

    /**
     * 根据 viewId 获取一个 View 对象
     */
    public View getView(@IdRes int viewId) {
        View view;
        Object viewObj = findView.getTag(viewId);
        if (null != viewObj) {
            view = (View) viewObj;
        } else {
            view = findView.findViewById(viewId);
            findView.setTag(viewId, view);
        }
        return view;
    }

    /**
     * 设置从 convertView 指定位置的子视图上 getView()
     * getView() 方法将从该子视图上 findViewById()
     * 设置 index 为负数时, 将直接从 convertView 上 findViewById()
     */
    public ViewHolder setFindViewAt(int index) {
        if (0 <= index && (convertView instanceof ViewGroup)) {
            ViewGroup viewGroup = (ViewGroup) this.convertView;
            if (index < viewGroup.getChildCount())
                findView = viewGroup.getChildAt(index);
            else
                throw new RuntimeException("invalid index");
        } else
            findView = convertView;
        return this;
    }

    /**
     * 根据 viewId 获取一个 ImageView 对象
     */
    public ImageView getImageView(@IdRes int viewId) {
        return (ImageView) getView(viewId);
    }

    /**
     * 根据 viewId 获取一个 TextView 对象
     */
    public TextView getTextView(@IdRes int viewId) {
        return (TextView) getView(viewId);
    }

    /**
     * 根据 viewId 获取一个 CheckBox 对象
     */
    public CheckBox getCheckBox(@IdRes int viewId) {
        return (CheckBox) getView(viewId);
    }

    /**
     * 为指定 viewId 的 ImageView 对象设置图片
     */
    public ViewHolder setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable) {
        getImageView(viewId).setImageDrawable(drawable);
        return this;
    }

    /**
     * 为指定 viewId 的 ImageView 对象设置图片
     */
    public ViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        getImageView(viewId).setImageResource(resId);
        return this;
    }

    /**
     * 使用 xUtils 为指定 viewId 的 ImageView 对象设置图片
     */
    public ViewHolder bindImage(@IdRes int viewId, @NonNull String url) {
        bindImage(viewId, url, null);
        return this;
    }

    /**
     * 使用 xUtils 为指定 viewId 的 ImageView 对象设置图片
     */
    public ViewHolder bindImage(@IdRes int viewId, @NonNull String url, ImageOptions options) {
        x.image().bind(getImageView(viewId), url, options);
        return this;
    }

    /**
     * 为指定 viewId 的 TextView 对象设置文字
     */
    public ViewHolder setText(@IdRes int viewId, @StringRes int resid) {
        getTextView(viewId).setText(resid);
        return this;
    }

    /**
     * 为指定 viewId 的 TextView 对象设置文字
     */
    public ViewHolder setText(@IdRes int viewId, CharSequence text) {
        getTextView(viewId).setText(text);
        return this;
    }

    /**
     * 为指定 viewId 的 TextView 对象设置文字颜色
     */
    public ViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        getTextView(viewId).setTextColor(color);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置 TAG
     */
    public ViewHolder setTag(@IdRes int viewId, final Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置背景图片
     */
    public ViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int resid) {
        getView(viewId).setBackgroundResource(resid);
        return this;
    }

    /**
     * 使用当前 ViewHolder 记录一个 TAG
     */
    public ViewHolder putTag(int key, final Object tag) {
        tags.put(key, tag);
        return this;
    }

    /**
     * 从当前 ViewHolder 中取出一个TAG
     */
    public Object getTag(int key) {
        return tags.get(key);
    }

    /**
     * 为指定 viewId 的 CheckBox 对象设置选中状态
     */
    public ViewHolder setChecked(@IdRes int viewId, boolean checked) {
        getCheckBox(viewId).setChecked(checked);
        return this;
    }

    /**
     * 切换指定 viewId 的 CheckBox 的选中状态
     */
    public ViewHolder toggle(@IdRes int viewId) {
        getCheckBox(viewId).toggle();
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置点击监听
     */
    public ViewHolder setOnClickListener(@IdRes int viewId, @Nullable OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置是否可见
     */
    public ViewHolder setVisibile(@IdRes int viewId, boolean visible) {
        getView(viewId).setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置是否可见
     */
    public ViewHolder setVisibility(@IdRes int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置是否可见
     */
    public ViewHolder setLayoutParams(@IdRes int viewId, LayoutParams params) {
        getView(viewId).setLayoutParams(params);
        return this;
    }

}
