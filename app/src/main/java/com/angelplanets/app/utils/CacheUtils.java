package com.angelplanets.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.MD5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求缓存工具类
 * Created by 123 on 2016/3/11.
 */
public class CacheUtils {

    private static File mCacheDir;

    static {
        mCacheDir = CUtils.getApplication().getExternalCacheDir();
        if (null == mCacheDir)
            mCacheDir = CUtils.getApplication().getCacheDir();
    }

    /**
     * 将网络返回的文本数据保存为文本文件
     *
     * @param content 文本数据内容
     * @param url     请求所用得到 URL
     * @return 保存是否成功
     */
    public static boolean saveTextFile(String url, String content) {
        if (null == url) return false;
        File cacheFile = new File(mCacheDir, MD5.md5(url));
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(cacheFile);
            out.write(content.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 根据请求的 URL 返回缓存的文本内容
     *
     * @param url 请求所用得到 URL
     * @return 缓存的文本内容
     */
    public static String readTextFile(String url) {
        if (null == url) return null;
        File cacheFile = new File(mCacheDir, MD5.md5(url));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (cacheFile.exists()) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(cacheFile);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return out.toString();
    }

    /**
     * 将一个对象以 JSON 字符串的形式保存到文本文件
     *
     * @param url 请求所用得到 URL
     * @param obj 要保存的对象
     * @return 保存是否成功
     */
    public static <T> boolean saveObjectToTextFile(String url, T obj) {
        return null != obj && saveTextFile(url, CUtils.getGson().toJson(obj));
    }

    /**
     * 从保存的文件中读取 JSON 字符串并返回对应的对象
     *
     * @param url      请求所用得到 URL
     * @param classOfT 要返回的对象的类类型
     * @param <T>      泛型
     * @return 如果成功返回对应的对象, 否则返回 null
     */
    public static <T> T readObjectFromTextFile(String url, Class<T> classOfT) {
        String json = readTextFile(url);
        if (!TextUtils.isEmpty(json)) {
            return CUtils.getGson().fromJson(json, classOfT);
        }
        return null;
    }

    /**
     * 保存字符串到sp
     * @param context
     * @param key
     * @param value
     */
    public static void setStringToCache(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    /**
     * 从sp中读取字符串
     * @param context
     * @param key
     * @return
     */
    public static String getStringFromCache(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String string = sp.getString(key, "");
        return string;
    }

    public static void setBooleanToCache(Context context,String key,boolean isGuide){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, isGuide).commit();
    }

    public static boolean getBooleanFromCache(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean isGuide = sp.getBoolean(key, false);
        return isGuide;
    }

    public static void setIntToCache(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getIntFromCache(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    /**
     *保存 int数据集合
     * @param context
     * @param key
     * @param value int型集合
     */
    public static void saveArray(Context context,String key, List<Integer> value){
        JSONArray mJsonArray = new JSONArray();
       for (int i=0; i<value.size(); i++){
           JSONObject object = new JSONObject();
           try {
               object.put("commodityId",value.get(i));
           } catch (JSONException e) {
               e.printStackTrace();
           }
           mJsonArray.put(object);
       }

        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, mJsonArray.toString()).commit();

    }


    /**
     * 获取集合
     * @param context
     * @param key
     * @return
     */
    public static List<Integer> getArray(Context context, String key) {
        List<Integer> datas = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String result = sp.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                int commodityId = itemObject.getInt("commodityId");
                datas.add(commodityId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0.00KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
