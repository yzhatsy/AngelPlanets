package com.angelplanets.app.social;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.social.bean.SocialBean;
import com.angelplanets.app.social.activity.SocialDetailActivity;
import com.angelplanets.app.social.activity.UserInfoActivity;
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

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 社交页面的adapter
 * Created by 123 on 2016/3/11.
 */
class SocialAdapter extends BaseAdapter {

    private Context context;
    private List<SocialBean.SocialData> socialDatas;
    private DisplayImageOptions options; //处理图片的属性
    ImageLoader imageLoader = ImageLoader.getInstance();//获取imgeLoader实例
    private ImageOptions mImageOptions;

    public SocialAdapter(Context context, List<SocialBean.SocialData> socialDatas) {
        this.context = context;
        this.socialDatas = socialDatas;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                        // .imageScaleType(ImageScaleType.NONE)
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SocialBean.SocialData socialData = socialDatas.get(position);
        holder.name.setText(socialData.getNickname());
        holder.time.setText(""+CUtils.getStandardDate(socialData.getUpdateTime()));
        holder.detail.setText(""+socialData.getDetail());
        holder.praise.setText(""+socialData.getCollectCount());

        x.image().bind(holder.imageView, URLUtils.rootUrl + socialData.getAvatarUrl(),mImageOptions);
        //使用imageLoader 加载图片
        final ViewHolder finalHolder = holder;
        imageLoader.displayImage(URLUtils.rootUrl + socialData.getPictures().get(0),holder.photo, options, new SimpleImageLoadingListener() {
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
                        Log.e("TAG", "width= " + width + "height = " + height+"ratio = "+ratio);


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

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SocialDetailActivity.class);
                intent.putExtra("SOCIAL_ID",socialData.getSocialId());
                intent.putExtra(Constant.CUSTOMER_ID,socialData.getCustomerId());
                Log.e("TAG","social_id = "+socialData.getSocialId());
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder {
        CircleImageView imageView;  //头像
        TextView name;
        TextView time;
        TextView detail;
        TextView praise;         //点赞
        TextView comment;        //评论
        SocialImageView photo;   //图片

    }

}

