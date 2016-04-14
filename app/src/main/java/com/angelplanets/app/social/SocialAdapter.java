package com.angelplanets.app.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angelplanets.app.R;
import com.angelplanets.app.social.activity.SocialDetailActivity;
import com.angelplanets.app.social.activity.UserInfoActivity;
import com.angelplanets.app.social.bean.SocialBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;
import com.angelplanets.app.view.CircleImageView;
import com.angelplanets.app.view.SocialImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社交页面的adapter
 * Created by 123 on 2016/3/11.
 */
public class SocialAdapter extends BaseAdapter {

    private Context context;
    private int mUserId;
    private List<SocialBean.SocialData> socialDatas;
    private DisplayImageOptions options; //处理图片的属性
    ImageLoader imageLoader = ImageLoader.getInstance();//获取imgeLoader实例
    private ImageOptions mImageOptions;

    public SocialAdapter(Context context, List<SocialBean.SocialData> socialDatas,int UserId) {
        this.context = context;
        this.socialDatas = socialDatas;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        mUserId = UserId;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        mImageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(240), DensityUtil.dip2px(240))
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
    }

    @Override
    public int getCount() {
        return socialDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return socialDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_social_xlistview, null);
            holder.imageView = (CircleImageView) convertView.findViewById(R.id.iv_social_item_icon);
            holder.name = (TextView) convertView.findViewById(R.id.tv_social_item_name);
            holder.time = (TextView) convertView.findViewById(R.id.tv_social_item_time);
            holder.detail = (TextView) convertView.findViewById(R.id.tv_social_item_detail);
            holder.praise = (TextView) convertView.findViewById(R.id.tv_social_item_praise);
            holder.comment = (TextView) convertView.findViewById(R.id.tv_social_item_comment);
            holder.photo = (SocialImageView) convertView.findViewById(R.id.iv_social_item_photo);
            holder.iv_itm_like = (ImageView) convertView.findViewById(R.id.iv_itm_like);
            holder.rl_item_like = (LinearLayout) convertView.findViewById(R.id.rl_item_like);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SocialBean.SocialData socialData = socialDatas.get(position);
        holder.name.setText(socialData.getNickname());
        holder.time.setText(""+CUtils.getStandardDate(socialData.getUpdateTime()));
        holder.detail.setText(""+socialData.getDetail());
        holder.praise.setText(""+socialData.getCollectCount());
        setLikeState(holder, socialData,position);

        x.image().bind(holder.imageView, URLUtils.rootUrl + socialData.getAvatarUrl(), mImageOptions);
        //使用imageLoader 加载图片
        final ViewHolder finalHolder = holder;
        imageLoader.displayImage(URLUtils.rootUrl + socialData.getPictures().get(1),holder.photo, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }
                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                    }
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        float width = loadedImage.getWidth();
                        float height = loadedImage.getHeight();
                        float  ratio = width /height;
                        Log.e("TAG", "width= " + width + "height = " + height + "ratio = " + ratio);
                        finalHolder.photo.setRatio(ratio);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    }
                }
        );

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra(Constant.CUSTOMER_ID, socialData.getCustomerId());
                context.startActivity(intent);
            }
        });
        //进详情
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SocialDetailActivity.class);
                intent.putExtra("SOCIAL_ID",socialData.getSocialId());
                intent.putExtra(Constant.CUSTOMER_ID, socialData.getCustomerId());
                intent.putExtra("POSITION",position);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        //点赞
        final ViewHolder finalHolder1 = holder;
        holder.rl_item_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLikeState(finalHolder1,socialData,position);
            }
        });
        //评论
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SocialDetailActivity.class);
                intent.putExtra("SOCIAL_ID",socialData.getSocialId());
                intent.putExtra(Constant.CUSTOMER_ID, socialData.getCustomerId());
                intent.putExtra("COMMENT_FLAG","COMMENT");
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        return convertView;
    }

    /**
     * 点赞
     */
    private void changeLikeState(ViewHolder holder, SocialBean.SocialData socialData,int position) {
        int collectState = map.get(position);
        Log.e("TAG","changeLikeState position="+position+"collectState = "+collectState);
        if (collectState == 0) {
            collectState = 1;
            map.put(position,collectState);
            holder.iv_itm_like.setImageResource(R.drawable.icon_good_selected);
            toLike(socialData,collectState);
            socialData.setCollectCount(socialData.getCollectCount()+1);
            holder.praise.setText("" + (socialData.getCollectCount()));

        } else if (collectState == 1) {
            collectState = 0;
            map.put(position,collectState);
            holder.iv_itm_like.setImageResource(R.drawable.icon_good_2x);
            toLike(socialData, collectState);
            if (socialData.getCollectCount()<0){
                socialData.setCollectCount(0);
            }else {
                socialData.setCollectCount(socialData.getCollectCount() - 1);
            }
            holder.praise.setText(""+(socialData.getCollectCount()));

        }
    }

    /**
     * 点赞操作
     */
    private void toLike(SocialBean.SocialData socialData, final int status) {
        String collectsUrl = URLUtils.LIKE_STATE_URL + socialData.getSocialId() + "/" + mUserId;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, collectsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "socialAdapter ---- " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String,String> getParams() {
                //在这里设置需要post的参数
                Map<String,String> map = new HashMap();
                map.put("status",status+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 设置点赞状态
     * @param holder
     * @param socialData
     */
    private void setLikeState(final ViewHolder holder, SocialBean.SocialData socialData, final int position) {
        String collectsUrl = URLUtils.LIKE_STATE_URL + socialData.getSocialId() + "/" + mUserId;
        RequestParams params = new RequestParams(collectsUrl);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result, holder, position);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }
    private Map<Integer,Integer> map = new HashMap<>();
    /**
     * 解析数据
     * @param result
     * @param holder
     * @param position
     */
    private void parseData(String result, ViewHolder holder,int position) {
        try {
            JSONObject collectStr = new JSONObject(result);
            int statusCode = collectStr.optInt("statusCode");
            Log.e("TAG","stateCode"+statusCode);
            if (statusCode == 200) {
                int collectState = collectStr.optInt("data");
                Log.e("TAG","parseData position="+position+"collectState = "+collectState);
                map.put(position,collectState);
                Log.e("TAG", "collectState"+collectState);
                switch (collectState) {
                    case 0:
                        holder.iv_itm_like.setImageResource(R.drawable.icon_good_2x);
                        break;
                    case 1:
                        holder.iv_itm_like.setImageResource(R.drawable.icon_good_selected);
                        break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    class ViewHolder {
        CircleImageView imageView;  //头像
        TextView name;
        TextView time;
        TextView detail;
        TextView praise;         //点赞
        TextView comment;        //评论
        SocialImageView photo;   //图片
        ImageView iv_itm_like;
        LinearLayout rl_item_like;
    }


}

