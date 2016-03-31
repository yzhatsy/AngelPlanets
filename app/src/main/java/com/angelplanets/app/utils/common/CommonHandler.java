package com.angelplanets.app.utils.common;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 通用 Handler 抽象类
 * Created by Jian Chang on 2015-12-09.
 */
public abstract class CommonHandler<T> extends Handler {

    private final WeakReference<T> reference;

    public CommonHandler(T t) {
        reference = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        T t = reference.get();
        if (null == t)
            return;
        handle(t, msg);
    }

    protected abstract void handle(T t, Message msg);

}
