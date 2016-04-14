package com.angelplanets.app.social.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angelplanets.app.R;
import com.angelplanets.app.social.adapter.CommentAdapter;
import com.angelplanets.app.social.bean.CommentBean;
import com.angelplanets.app.social.bean.SocialDetailBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.CacheUtils;
import com.angelplanets.app.utils.Constant;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社交详情页面
 */
public class SocialDetailActivity extends Activity implements View.OnClickListener, CommentAdapter.OnItemCommnet {

    private int mSocialId;
    private int mCustomerId;
    private int mUserId;
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
    private TextView mLabel1;             //标签1
    private TextView mLabel2;             //标签2
    private TextView mLabel3;             //标签3
    private TextView mNoComment;
    private DisplayImageOptions options; //处理图片的属性
    private RelativeLayout mBottomLike;        //底部赞
    private TextView mBottomComment;         //底部评论
    private ImageView iv_like;
    private ImageView iv_toplike;
    private SocialDetailBean.DataEntity mData; //标签类
    private int likeCount;
    private RequestQueue queue;
    ImageLoader imageLoader = ImageLoader.getInstance();//获取imgeLoader实例
    private PopupWindow popWindow;
    private CommentAdapter mAdapter;
    private EditText editText;
    private String commentUrl;
    private LinearLayout ll_like;
    private LinearLayout ll_bottom_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_detail);
        initView();

        mUserId = CacheUtils.getIntFromCache(this, Constant.LOGIN_FLAG);
        String detailUrl = URLUtils.SOCIAL_DETAIL_URL + mSocialId;
        Log.e("TAG", "detailUrl = " + detailUrl);
        commentUrl = URLUtils.COMMENT_URL + mUserId + URLUtils.CUSTOMER_ID + mCustomerId + URLUtils.SOCIAL_ID + mSocialId + URLUtils.PAGECOUNT + 1;
        String collectsUrl = URLUtils.LIKE_STATE_URL + mSocialId + "/" + mUserId;

        Log.e("TAG", "commentUrl = " + commentUrl);
        getDataFromNet(collectsUrl, "collects");
        getDataFromNet(detailUrl, "detail");
        setListener();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        queue = Volley.newRequestQueue(this);
        mSocialId = getIntent().getIntExtra("SOCIAL_ID", -1);
        mCustomerId = getIntent().getIntExtra(Constant.CUSTOMER_ID, -1);
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
        mBottomLike = (RelativeLayout) findViewById(R.id.rl_like);
        mBottomComment = (TextView) findViewById(R.id.tv_comment);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        iv_toplike = (ImageView) findViewById(R.id.iv_toplike);
        mListView = (MyListView) findViewById(R.id.lv_user_describe);
        mLabel1 = (TextView) findViewById(R.id.tv_label1);
        mLabel2 = (TextView) findViewById(R.id.tv_label1);
        mLabel3 = (TextView) findViewById(R.id.tv_label1);
        mNoComment = (TextView) findViewById(R.id.tv_no_comment);
        ll_like = (LinearLayout) findViewById(R.id.ll_like);
        ll_bottom_like = (LinearLayout) findViewById(R.id.ll_bottom_like);
    }

    /**
     * 监听事件
     */
    private void setListener() {
        mBack.setOnClickListener(this);
        mBottomLike.setOnClickListener(this);
        iv_toplike.setOnClickListener(this);
        mBottomComment.setOnClickListener(this);
        mUserIcon.setOnClickListener(this);
    }

    /**
     * item 的评论监听
     * @param itemPosition
     */
    @Override
    public void onComment(int itemPosition) {
        CommentBean.DataEntity.SocialCommentListEntity comment = mAdapter.getItem(itemPosition);
        int toSocialId = comment.getSocialCommentId();
        toComment(mSocialId, mUserId, toSocialId + ""); //评论
    }

    /**
     * 网络请求数据
     */
    private void getDataFromNet(String url, final String type) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                Log.e("TAG", "请求成功..............： s=" + s);
                setData(s, type);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                Log.e("TAG", "请求失败： volleyError=" + volleyError);
                if ("detail".equals(type)){
                    Toast.makeText(SocialDetailActivity.this, "亲~请检查您的网络", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String data = new String(response.data, "UTF-8");
                    return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    /**
     * 设置页面数据
     */
    private void setData(String jsonStr, String type) {
        if ("detail".equals(type)) {         //详情
            setDetailData(jsonStr);
            //加载评论列表
            getDataFromNet(commentUrl, "comment");

        } else if ("comment".equals(type)) {  //评论
            setCommentListData(jsonStr);
        } else if ("collects".equals(type)) { //点赞
            setCollectListData(jsonStr);
        }

    }

    private int collectState = -1;

    /**
     * 对该用户是否已经点赞
     *
     * @param jsonStr
     */
    private void setCollectListData(String jsonStr) {
        try {
            JSONObject collectStr = new JSONObject(jsonStr);
            int statusCode = collectStr.optInt("statusCode");
            if (statusCode == 200) {
                collectState = collectStr.optInt("data");
                switch (collectState) {
                    case 0:
                        iv_like.setImageResource(R.drawable.icon_good);
                        iv_toplike.setImageResource(R.drawable.icon_good);
                        break;
                    case 1:
                        iv_like.setImageResource(R.drawable.icon_good_selected);
                        iv_toplike.setImageResource(R.drawable.icon_good_selected);
                        break;
                }
            }
            ll_like.setVisibility(View.VISIBLE);
            ll_bottom_like.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置用户评论列表的数据
     *
     * @param jsonStr
     */
    private void setCommentListData(String jsonStr) {
        CommentBean commentBean = CUtils.getGson().fromJson(jsonStr, CommentBean.class);
        List<CommentBean.DataEntity.SocialCommentListEntity> commentList = commentBean.getData().getSocialCommentList();
        mAdapter = new CommentAdapter(this, commentList);
        if (commentList.isEmpty()) {
            mNoComment.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mNoComment.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mListView.setFocusable(false);
            mListView.setAdapter(mAdapter);
            mListView.setFocusable(false);
            mAdapter.setOnItemCommnet(this);
        }
    }

    /**
     * 设置用户详情数据
     *
     * @param jsonStr
     */
    private void setDetailData(String jsonStr) {
        final SocialDetailBean detailBean = CUtils.getGson().fromJson(jsonStr, SocialDetailBean.class);
        mData = detailBean.getData();
        mUserName.setText(detailBean.getData().getCustomer().getNickname());
        mPublishTime.setText("" + CUtils.getStandardDate(detailBean.getData().getCreateTime()));
        mPublishDetail.setText("" + detailBean.getData().getDetail());
        likeCount = detailBean.getData().getCollectCount();
        mTopLikeCount.setText("" + likeCount);
        if (mData.getTag1() != null) {
            mLabel1.setVisibility(View.VISIBLE);
            mLabel1.setText(mData.getTag1());
        }
        if (mData.getTag2() != null) {
            mLabel2.setVisibility(View.VISIBLE);
            mLabel2.setText(mData.getTag2());
        }
        if (mData.getTag3() != null) {
            mLabel3.setVisibility(View.VISIBLE);
            mLabel3.setText(mData.getTag3());
        }
        /*Drawable drawable= getResources().getDrawable(R.drawable.drawable);
        //这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        myTextview.setCompoundDrawables(drawable, null, null, null);*/
        x.image().bind(mUserIcon, URLUtils.rootUrl + detailBean.getData().getCustomer().getAvatarUrl());
        imageLoader.displayImage(URLUtils.rootUrl + detailBean.getData().getPhotoList().get(1), mPublishPhoto, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        float width = loadedImage.getWidth();
                        float height = loadedImage.getHeight();
                        float ratio = width / height;
                        Log.e("TAG", "width= " + width + "height = " + height + "ratio = " + ratio);

                        mPublishPhoto.setRatio(ratio);
                        mPhotoCount.setVisibility(View.VISIBLE);
                        mPhotoCount.setText("" + (detailBean.getData().getPhotoList().size()-1));
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    }
                }
        );
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_common_back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            case R.id.rl_like:
            case R.id.iv_toplike:
                changeLikeState();
                break;
            case R.id.tv_comment:
                toComment(mSocialId, mUserId, "");
                break;
            case R.id.iv_detail_item_icon:
                Intent intent = new Intent(this, UserInfoActivity.class);
                intent.putExtra(Constant.CUSTOMER_ID,mCustomerId);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }
    }

    /**
     * 评论
     */
    private void toComment(final int socialId, final int customerId, final String toSocialId) {

        View view = View.inflate(this, R.layout.text_comment, null);
        editText = (EditText) view.findViewById(R.id.et_comment);

        final InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        popWindow = new PopupWindow();
        //初始化弹出菜单
        popWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, false);
        //设置可以获取焦点，否则弹出菜单中的EditText是无法获取输入的
        popWindow.setFocusable(true);
        //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //在底部显示
        popWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        if (!popWindow.isShowing()) {  //pop消失后隐藏键盘

            if (inputManager.isActive()) {
                inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
        //按回车键发送
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    if (inputManager.isActive()) {
                        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        comment(socialId, customerId, toSocialId);  //评论

                        popWindow.dismiss();
                    }
                }
                return false;
            }
        });

    }

    /**
     * 添加评论
     */
    private void comment(final int socialId, final int customerId, final String toSocialId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUtils.ADD_COMMENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "comment -> " + response);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            int code = jsonObject.optInt("statusCode");
                            if (code == 200) {
                                getDataFromNet(commentUrl, "comment");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap();
                map.put("socialId", socialId + "");
                map.put("customerId", customerId + "");
                String content = editText.getText().toString();
                map.put("content", content);
                map.put("toSocialId", toSocialId);
                return map;
            }
        };
        queue.add(stringRequest);
    }

    private boolean flag = true;

    /**
     * 点赞的操作
     */
    private void changeLikeState() {

        if (collectState == 0) {
            collectState = 1;
            iv_like.setImageResource(R.drawable.icon_good_selected);
            iv_toplike.setImageResource(R.drawable.icon_good_selected);
            likeCount++;
            mTopLikeCount.setText("" + likeCount);
        } else if (collectState == 1) {
            collectState = 0;
            iv_like.setImageResource(R.drawable.icon_good);
            iv_toplike.setImageResource(R.drawable.icon_good);
            likeCount--;
            mTopLikeCount.setText("" + likeCount);
        } else {//没网状态为保证用户体验正常，设置的模拟点赞
            int collects = likeCount;
            if (flag) {
                iv_like.setImageResource(R.drawable.icon_good_selected);
                iv_toplike.setImageResource(R.drawable.icon_good_selected);
                collects++;
                mTopLikeCount.setText("" + collects);
                flag = false;
            } else {
                iv_like.setImageResource(R.drawable.icon_good);
                iv_toplike.setImageResource(R.drawable.icon_good);
                collects--;
                mTopLikeCount.setText("" + collects);
                flag = true;
            }
        }

    }

    @Override
    protected void onDestroy() {
        View view_null = View.inflate(this, R.layout.view_null, null);
        setContentView(view_null);
        super.onDestroy();
    }

    /**
     * back键平滑退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return false;
        }
        return false;

    }

}
