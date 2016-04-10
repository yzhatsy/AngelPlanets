package com.angelplanets.app.social;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.URLUtils;
import com.angelplanets.app.view.CircleImageView;
import com.angelplanets.app.view.MyListView;
import com.angelplanets.app.view.SocialImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.xutils.x;

/**
 * 社交详情页面
 */
public class SocialDetailActivity extends Activity implements View.OnClickListener {

    private SocialBean.SocialData socialData;
    private RelativeLayout mBack;               //返回
    private TextView mTitle;                     //标题
    private CircleImageView mUserIcon;         //用户头像
    private TextView mUserName;                //用户姓名
    private TextView mPublishTime;             //发布时间
    private TextView mTopLikeCount;            //顶部显示赞的数量
    private SocialImageView mPublishPhoto;      //发布的图片
    private TextView mPublishDetail;           //发布详情内容
    private MyListView mListView;             //用户评论的lsitView
    private TextView mPhotoCount;             //图片数
    private DisplayImageOptions options; //处理图片的属性
    private TextView mBottomLikeCount;        //底部赞的数目
    ImageLoader imageLoader = ImageLoader.getInstance();//获取imgeLoader实例
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_detail);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        socialData = (SocialBean.SocialData) getIntent().getSerializableExtra("SOCIAL_DATA");
        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("详情");
        mUserIcon = (CircleImageView) findViewById(R.id.iv_detail_item_icon);
        mUserName = (TextView) findViewById(R.id.tv_detail_item_name);
        mPublishTime = (TextView) findViewById(R.id.tv_detail_item_time);
        mTopLikeCount = (TextView) findViewById(R.id.tv_detail_like_count);
        mPublishPhoto = (SocialImageView) findViewById(R.id.iv_social_item_photo);
        mPublishDetail = (TextView) findViewById(R.id.tv_social_item_detail);
        mPhotoCount = (TextView) findViewById(R.id.tv_detail_count);
        mBottomLikeCount = (TextView) findViewById(R.id.tv_like_count);
        mListView = (MyListView) findViewById(R.id.lv_user_describe);
        mBack.setOnClickListener(this);
        setData();

    }

    /**
     * 设置页面数据
     */
    private void setData() {
        mUserName.setText(socialData.getNickname());
        mPublishTime.setText(""+ CUtils.getStandardDate(socialData.getUpdateTime()));
        mPublishDetail.setText(""+socialData.getDetail());
        mTopLikeCount.setText(""+socialData.getCollectCount());
        mBottomLikeCount.setText("" + socialData.getCollectCount());
        mPhotoCount.setText("" + socialData.getPictures().size());

        /*Drawable drawable= getResources().getDrawable(R.drawable.drawable);
        //这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        myTextview.setCompoundDrawables(drawable, null, null, null);*/
        x.image().bind(mUserIcon, URLUtils.rootUrl + socialData.getAvatarUrl());
        imageLoader.displayImage(URLUtils.rootUrl + socialData.getPictures().get(0), mPublishPhoto, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {}

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {}

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        float width = loadedImage.getWidth();
                        float height = loadedImage.getHeight();
                        float ratio = width / height;
                        Log.e("TAG", "width= " + width + "height = " + height + "ratio = " + ratio);

                        mPublishPhoto.setRatio(ratio);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {}
                }
        );
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        View view_null = View.inflate(this,R.layout.view_null,null);
        setContentView(view_null);
        super.onDestroy();
    }

    /**
     * back键平滑退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK ){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return false;
        }
        return false;

    }
}
