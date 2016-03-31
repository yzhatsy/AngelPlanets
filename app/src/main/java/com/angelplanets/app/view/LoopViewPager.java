package com.angelplanets.app.view;

import android.content.Context;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.angelplanets.app.utils.common.CommonHandler;


/**
 * 自定义无限循环的 ViewPager, 且支持自动轮播
 * Created by Jian Chang on 2016-01-14.
 */
public class LoopViewPager extends ViewPager {

    private static final int WHAT_LOOP = 0x11;

    private int itemCount;
    private boolean mIsLoop;
    private long mLoopDelayTimeMillis = 5000;
    private LoopViewPagerHandler handler;

    public LoopViewPager(Context context) {
        super(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置轮播的延迟时间, 默认 5000ms
     */
    public void setLoopDelayTimeMillis(long loopDelayTimeMillis) {
        this.mLoopDelayTimeMillis = loopDelayTimeMillis;
    }

    /**
     * 设置是否启用循环播放
     */
    public void setLooped(boolean isLoop) {
        if (mIsLoop != isLoop) {
            mIsLoop = isLoop;
            if (null == handler)
                handler = new LoopViewPagerHandler(this);
            else
                handler.removeMessages(WHAT_LOOP);
            if (mIsLoop)
                handler.sendEmptyMessageDelayed(WHAT_LOOP, mLoopDelayTimeMillis);
        }
    }

    /**
     * 当鼠标拖动时停止自动轮播
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null != handler)
                    handler.removeMessages(WHAT_LOOP);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (null != handler)
                    handler.sendEmptyMessageDelayed(WHAT_LOOP, mLoopDelayTimeMillis);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter.getCount() > 2000)
            itemCount = adapter.getCount() / 2000;
        else
            itemCount = adapter.getCount();
    }

    @Override
    public void setCurrentItem(int item) {
        int newIten = item % itemCount;
        if (newIten != item)
            super.setCurrentItem(item);
    }

    /**
     * 自定义 Handler, 用于图片轮播
     */
    static class LoopViewPagerHandler extends CommonHandler<LoopViewPager> {

        public LoopViewPagerHandler(LoopViewPager v) {
            super(v);
        }

        @Override
        protected void handle(LoopViewPager v, Message msg) {
            switch (msg.what) {
                case WHAT_LOOP:
                    // 判断是否可以进行图片轮播
                    v.setCurrentItem(v.getCurrentItem() + 1);
                    removeMessages(WHAT_LOOP);
                    sendEmptyMessageDelayed(WHAT_LOOP, v.mLoopDelayTimeMillis);
                    break;
            }
        }
    }

}
