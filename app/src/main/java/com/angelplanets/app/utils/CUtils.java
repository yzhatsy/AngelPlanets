package com.angelplanets.app.utils;

import android.app.Application;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 工具类
 * Created by 123 on 2016/3/11.
 */
public class CUtils {

    private static Application mApplication;
    private static Gson mGson;
    public static final String CHARSET_NAME = "UTF-8";
    /**
     * 私有化构造器
     */
    private CUtils() {}

    /**
     * 初始化一个 Application 供全局使用
     *
     * @param application Application 对象
     */
    public static void init(Application application) {
        mApplication = application;
        mGson = new Gson();
    }

    /**
     * @return 返回 Application 对象
     */
    public static Application getApplication() {
        return mApplication;
    }

    /**
     * @return 返回 Gson 对象
     */
    public static Gson getGson() {
        return mGson;
    }

    /**
     * 返回当前时间
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * @param timeStr   时间戳
     * @return
     */
    public static String getStandardDate(long timeStr) {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = null;
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            format = new SimpleDateFormat("MM月dd日", Locale.CHINA);
        } else{
          format = new SimpleDateFormat("HH:mm", Locale.CHINA);
        }
        return format.format(timeStr);

    }

    /**
     * md5加密
     * @param string
     * @return
     * @throws Exception
     */
    public static String md5(String string) {
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


}
