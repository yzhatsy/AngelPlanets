package com.angelplanets.app.store.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angelplanets.app.R;
import com.angelplanets.app.store.activity.ShopDetailActivity;
import com.angelplanets.app.store.bean.ShopBean;
import com.angelplanets.app.utils.URLUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 商品信息数据的adapter
 * Created by 123 on 2016/3/21.
 */
public class ShoppingAdapter extends BaseAdapter{

    private Context context;
    private TextView mTextPoint;
    private int mCartCount;
    private ImageView mCart;
    private TextView tv_red_point;
    private List<ShopBean.DataEntity.CommodityListEntity> commodityList;
    private DisplayImageOptions options; //处理图片的属性
    ImageLoader imageLoader = ImageLoader.getInstance();//获取imgeLoader实例

    public ShoppingAdapter(Context context,List<ShopBean.DataEntity.CommodityListEntity> commodityList,TextView mTextPoint,int mCartCount,TextView tv_red_point,ImageView mCart){
        this.context = context;
        this.commodityList = commodityList;
        this.mTextPoint = mTextPoint;
        this.mCartCount = mCartCount;
        this.tv_red_point = tv_red_point;
        this.mCart = mCart;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return commodityList.size();
    }

    @Override
    public Object getItem(int position) {
        return commodityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shopping_list,null);

            holder.iv_shopping = (ImageView) convertView.findViewById(R.id.iv_shopping);
            holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
            holder.tv_sale = (TextView) convertView.findViewById(R.id.tv_sale);
            holder.tv_original_cost = (TextView) convertView.findViewById(R.id.tv_original_cost);
            holder.iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        final ShopBean.DataEntity.CommodityListEntity commodityData = commodityList.get(position);
        holder.tv_detail.setText(commodityData.getName());
        holder.tv_sale.setText("￥"+commodityData.getPrice());

        //设置原价为过期价(用横杠注释掉)
        Spannable spanStrikethrough = new SpannableString("￥"+commodityData.getOldPrice());
        StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
        spanStrikethrough.setSpan(stSpan, 0, ("￥" + commodityData.getOldPrice()).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tv_original_cost.setText(spanStrikethrough);

        //加载图片
        imageLoader.displayImage(URLUtils.rootUrl + commodityData.getPhotoUrl(), holder.iv_shopping, options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.e("TAG", "商品图片加载失败.....");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
                Log.e("TAG", "商品图片加载完成.....");
                if (loadedImage != null) {
                    ImageView imageView = (ImageView) view;
                    boolean firstDisplay = !displayedImages.contains(imageUri);
                    if (firstDisplay) {
                        FadeInBitmapDisplayer.animate(imageView, 500);
                        displayedImages.add(imageUri);
                    }
                }
            }
        });
        //添加购物车的点击监听
        holder.iv_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (mCartCount>0){
                    mTextPoint.setVisibility(View.VISIBLE);
                }

                starAnimation();
                final int shopId = commodityData.getCommodityId();

                String httpurl = URLUtils.UPDATE_CART;
                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,httpurl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("TAG", "response -> " + response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }) {
                    @Override
                    protected Map<String,String> getParams() {
                        //在这里设置需要post的参数
                        Map<String,String> map = new HashMap();
                        map.put("customerId","167");
                        map.put("commodityId", shopId+"");
                        map.put("status",1+"");
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        holder.iv_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到商品的id
                int commodityId = commodityList.get(position).getCommodityId();
                //跳转商品详情页面
                Intent intent = new Intent(context,ShopDetailActivity.class);
                intent.putExtra("COMMODITY_ID", commodityId);
                intent.putExtra("CART_COUNT",mCartCount);
                context.startActivity(intent);

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


        return convertView;
    }

    /**
     * 动画
     */
    private void starAnimation() {
        tv_red_point.setVisibility(View.VISIBLE);

        RotateAnimation rotateAnimation = new RotateAnimation(0,-70,60,130);

        rotateAnimation.setDuration(100);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(false);
        tv_red_point.startAnimation(rotateAnimation);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1,0.8f,1,0.8f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(200);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        scaleAnimation.setFillAfter(false);
        mCart.startAnimation(scaleAnimation);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {
               tv_red_point.setVisibility(View.GONE);
               tv_red_point.clearAnimation();
           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });

    }

    class ViewHolder{
        ImageView iv_shopping; //商品图片
        TextView tv_detail;   //商品描述
        TextView tv_sale;     //特价
        TextView tv_original_cost; //原价
        ImageView iv_add;    //加入购物车
    }
}
