package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.store.bean.ShoppingCartBean;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class PaymentActivity extends Activity implements View.OnClickListener {

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;
    private ListView mListView;
    private String shoppingUrl;     //购物车商品url
    private TextView tv_pay;        //总价格
    private RelativeLayout Rl_pay;  //结算
    private TextView tv_total_pay;  //总数
    private LinearLayout ll_address; //收货地址
    private List<ShoppingCartBean.DataEntity> mCartData; //购物车数据集合
    private String mjsonStr;
    private ImageOptions mImageOptions;
    private TreeSet<ShoppingCartBean.DataEntity> checkShops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mImageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(240), DensityUtil.dip2px(240))
                .build();

        initView();

        View header = View.inflate(this,R.layout.payment_listview_head,null);
        ll_address = (LinearLayout) header.findViewById(R.id.ll_address);
        mListView.addHeaderView(header);
        setListener();
        int userId = getIntent().getIntExtra(Constant.LOGIN_FLAG,-1);
        checkShops = (TreeSet<ShoppingCartBean.DataEntity>) getIntent().getSerializableExtra("CHECK_SHOPS");
        Iterator iterator=checkShops.iterator();  //迭代器
        while(iterator.hasNext()){
            mCartData.add((ShoppingCartBean.DataEntity) iterator.next());
        }

        setData();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        ib_common_back.setOnClickListener(this);
        Rl_pay.setOnClickListener(this);
        ll_address.setOnClickListener(this);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);
        mListView = (ListView) findViewById(R.id.lv_shopping_pay);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        Rl_pay = (RelativeLayout) findViewById(R.id.Rl_pay);
        tv_total_pay = (TextView) findViewById(R.id.tv_total_pay);
        ib_common_back.setVisibility(View.VISIBLE);
        tv_total_pay.setVisibility(View.VISIBLE);
        tv_common_title.setText("确认订单");
        mCartData = new ArrayList<>();

    }


    /**
     * 设置数据
     */
    private void setData(){

        mListView.setAdapter(new PaymentAdapter());
        int count = 0;
        double priceCount = 0.0;

          for (int i=0; i<mCartData.size(); i++){
              count += mCartData.get(i).getCount();
              priceCount += mCartData.get(i).getCount()*mCartData.get(i).getPrice();
          }
        tv_pay.setText("￥"+priceCount);
        tv_total_pay.setText("("+count+")");
    }

    /**
     * view的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:   //返回
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            case R.id.ll_address:   //地址管理
                startActivity(new Intent(this,AddressActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.Rl_pay:   //支付按钮
                startActivity(new Intent(this,EnsurePayActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }
    }



    class PaymentAdapter extends BaseAdapter{
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder ;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(PaymentActivity.this, R.layout.item_payment_shopping,null);
                holder.iv_item_shoppingicon = (ImageView) convertView.findViewById(R.id.iv_item_shoppingicon);
                holder.tv_cart_item_name = (TextView) convertView.findViewById(R.id.tv_cart_item_name);
                holder.tv_item_shopcolor = (TextView) convertView.findViewById(R.id.tv_item_shopcolor);
                holder.tv_item_shopsize = (TextView) convertView.findViewById(R.id.tv_item_shopsize);
                holder.tv_shopping_item_price = (TextView) convertView.findViewById(R.id.tv_shopping_item_price);
                holder.tv_shop_item_count = (TextView) convertView.findViewById(R.id.tv_shop_item_count);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            ShoppingCartBean.DataEntity data = mCartData.get(position);
            holder.tv_cart_item_name.setText(data.getName());
            holder.tv_shopping_item_price.setText("￥"+data.getPrice());
            holder.tv_shop_item_count.setText("" + data.getCount());
            x.image().bind(holder.iv_item_shoppingicon, URLUtils.rootUrl + data.getPictures(), mImageOptions);

            Iterator i = data.getMap().entrySet().iterator();
            ArrayList<String> keyStr = new ArrayList<>();
            ArrayList<String> valueStr = new ArrayList<>();

            while (i.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) i.next();
                keyStr.add(entry.getKey());
                valueStr.add(entry.getValue());
            }
            holder.tv_item_shopcolor.setText(keyStr.get(0) + ": " + valueStr.get(0));
            holder.tv_item_shopsize.setText(keyStr.get(1) + ": " + valueStr.get(1));

            return convertView;
        }
    }

    class ViewHolder{
        ImageView iv_item_shoppingicon;     //商品图标
        TextView tv_cart_item_name;
        TextView tv_item_shopcolor;         //颜色
        TextView tv_item_shopsize;          //尺码
        TextView tv_shopping_item_price;    //价格
        TextView tv_shop_item_count;        //商品数量
    }

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
