package com.angelplanets.app.store.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.store.bean.ShoppingCartBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.URLUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yzh on 2016/3/26.
 */
public class ShoppingCatAdapter extends BaseAdapter{

    private ImageOptions mImageOptions;//图片处理属性
    private String mJsonStr;
    private List<ShoppingCartBean.DataEntity> mCartData;
    private Context context;
    private OnItemChecked mOnItemChecked;
    private OnItemCountModify mOnCountModify;


    public void setmOnItemChecked(OnItemChecked mOnItemChecked) {
        this.mOnItemChecked = mOnItemChecked;
    }

    public void setmOnCountModify(OnItemCountModify mOnCountModify) {
        this.mOnCountModify = mOnCountModify;
    }

    public ShoppingCatAdapter(Context context,List<ShoppingCartBean.DataEntity> mCartData,String mJsonStr){
        this.context = context;
        this.mCartData = mCartData;
        this.mJsonStr = mJsonStr;
        mImageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(240), DensityUtil.dip2px(240))
                .build();

    }

    @Override
    public int getCount() {
        return mCartData.size();
    }

    @Override
    public ShoppingCartBean.DataEntity getItem(int position) {
        return mCartData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder ;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context.getApplicationContext(), R.layout.item_shoppingcart,null);
            holder.cb_item_check = (CheckBox) convertView.findViewById(R.id.cb_item_check);
            holder.iv_item_shoppingicon = (ImageView) convertView.findViewById(R.id.iv_item_shoppingicon);
            holder.tv_cart_item_name = (TextView) convertView.findViewById(R.id.tv_cart_item_name);
            holder.tv_item_shopcolor = (TextView) convertView.findViewById(R.id.tv_item_shopcolor);
            holder.tv_item_shopsize = (TextView) convertView.findViewById(R.id.tv_item_shopsize);
            holder.tv_shopping_item_price = (TextView) convertView.findViewById(R.id.tv_shopping_item_price);
            holder.tv_shop_item_count = (TextView) convertView.findViewById(R.id.tv_shop_item_count);
            holder.ib_minus = (RelativeLayout) convertView.findViewById(R.id.ib_minus);
            holder.ib_add = (RelativeLayout) convertView.findViewById(R.id.ib_add);
            holder.et_leave_word = (EditText) convertView.findViewById(R.id.et_follower_search);
            holder.rl_item_check = (RelativeLayout) convertView.findViewById(R.id.rl_item_check);
            holder.cb_item_check.setId(position);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        ShoppingCartBean.DataEntity data = mCartData.get(position);
        holder.tv_cart_item_name.setText(data.getName());
        holder.tv_shopping_item_price.setText("￥" + data.getPrice());
        holder.tv_shop_item_count.setText("" + data.getCount());
        holder.cb_item_check.setChecked(data.isCheckable());

        x.image().bind(holder.iv_item_shoppingicon, URLUtils.rootUrl + data.getPictures(), mImageOptions);

        if (!TextUtils.isEmpty(mJsonStr)) {
            try {
                JSONObject jsonObject = new JSONObject(mJsonStr);
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                JSONObject object = jsonArray.getJSONObject(position);
                String str = object.optString("specification");
                if (!TextUtils.isEmpty(str)) {
                    Type type = new TypeToken<HashMap<String, String>>() {}.getType();
                    HashMap map = CUtils.getGson().fromJson(str, type);

                    Iterator iterator = map.entrySet().iterator();
                    ArrayList<String> keyStr = new ArrayList<>();
                    ArrayList<String> valueStr = new ArrayList<>();

                    while (iterator.hasNext()) {
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                        keyStr.add(entry.getKey());
                        valueStr.add(entry.getValue());
                    }
                    holder.tv_item_shopcolor.setText(keyStr.get(0)+": "+valueStr.get(0));
                    holder.tv_item_shopsize.setText(keyStr.get(1)+": "+valueStr.get(1));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * item 的checkBox
         */
        holder.rl_item_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cb_item_check.setChecked(!holder.cb_item_check.isChecked());
                mCartData.get(position).setIsCheckable(holder.cb_item_check.isChecked());
                mOnItemChecked.onItemChecked(position,holder.cb_item_check.isChecked());
            }
       });

        /**
         * 商品减按钮
         */
        holder.ib_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCountModify.doDecrease(position, holder.tv_shop_item_count,holder.cb_item_check.isChecked());
            }
        });

        /**
         * 商品增加按钮
         */
        holder.ib_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mOnCountModify.doIncrease(position,holder.tv_shop_item_count,holder.cb_item_check.isChecked());
            }
        });

        return convertView;
    }

    class ViewHolder{
        CheckBox cb_item_check;             //选择商品
        ImageView iv_item_shoppingicon;     //商品图标
        TextView tv_cart_item_name;
        TextView tv_item_shopcolor;         //颜色
        TextView tv_item_shopsize;          //尺码
        TextView tv_shopping_item_price;    //价格
        RelativeLayout ib_minus;               //减
        TextView tv_shop_item_count;        //商品数量
        RelativeLayout ib_add;                 //加
        EditText et_leave_word;             //留言
        RelativeLayout rl_item_check;       //代替checkbox的点击
    }

    /**
     * 自定义监听器
     */
    public interface OnItemChecked{
        /**
         * item的CheckBox状态改变的监听
         *
         * @param itemPosition
         *              item的位置
         * @param isChecked
         *              checkBox是否选中
         */
        public void onItemChecked(int itemPosition, boolean isChecked);
    }

    /**
     * 数量改变的监听
     */
    public interface OnItemCountModify{
        /**
         * 增加操作
         * @param itemPosition
         *          item位置
         * @param showCountView
         *          item的商品的数量
         * @param isChecked
         *          checkBox是否选中
         */
        public void doIncrease(int itemPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         * @param itemPosition
         *          item位置
         * @param showCountView
         *          item的商品的数量
         * @param isChecked
         *          checkBox是否选中
         */
        public void doDecrease(int itemPosition, View showCountView, boolean isChecked);
    }

}