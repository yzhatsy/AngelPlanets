package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.angelplanets.app.store.bean.AddressBean;
import com.angelplanets.app.store.bean.ShoppingCartBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.URLUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * 确认订单支付页面
 */
public class PaymentActivity extends Activity implements View.OnClickListener {

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;
    private ListView mListView;
    private TextView tv_pay;        //总价格
    private RelativeLayout Rl_pay;  //结算
    private TextView tv_total_pay;  //总数
    private LinearLayout ll_address; //收货地址
    private TextView tv_custom_name;  //收件人
    private TextView tv_phone_number;  //收件电话
    private TextView tv_pay_address;  //收件地址
    private ImageOptions mImageOptions;
    private double priceCount; //总价格
    private int userId;
    private int mAddressId = -1;
    private TreeSet<ShoppingCartBean.DataEntity> checkShops;
    private List<ShoppingCartBean.DataEntity> mCartData; //购物车数据集合
    private  AddressBean.AddressMessage addressMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mImageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(240), DensityUtil.dip2px(240))
                .build();

        initView();
        setListener();
        userId = getIntent().getIntExtra(Constant.LOGIN_FLAG,-1);
        Log.e("TAG", "PaymentActivity..."+userId);
        checkShops = (TreeSet<ShoppingCartBean.DataEntity>) getIntent().getSerializableExtra("CHECK_SHOPS");
        Iterator iterator=checkShops.iterator();  //迭代器
        while(iterator.hasNext()){
            mCartData.add((ShoppingCartBean.DataEntity) iterator.next());
        }
        getdataFromNet();
        setData();
    }

    /**
     * 请求地址数据
     */
    private void getdataFromNet() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URLUtils.ADDRESS_URL + userId, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                parseJson(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String data = new String(response.data,"UTF-8");
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
     * 解析数据
     * @param result
     */
    private void parseJson(String result) {
        AddressBean addressBean = CUtils.getGson().fromJson(result, AddressBean.class);
        if (addressBean!= null){
            if (addressBean.getData().size() != 0) {
                addressMessage = addressBean.getData().get(0);
                mAddressId = addressMessage.getDeliveryAddressId();
                tv_custom_name.setText(addressMessage.getName());
                tv_phone_number.setText(addressMessage.getPhonenumber());
                tv_pay_address.setText(addressMessage.getDetailAddress());
            }
        }
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

        View header = View.inflate(this,R.layout.payment_listview_head,null);
        ll_address = (LinearLayout) header.findViewById(R.id.ll_address);
        tv_custom_name = (TextView) header.findViewById(R.id.tv_custom_name);
        tv_phone_number = (TextView) header.findViewById(R.id.tv_phone_number);
        tv_pay_address = (TextView) header.findViewById(R.id.tv_pay_address);
        mListView.addHeaderView(header);

    }


    /**
     * 设置数据
     */
    private void setData(){

        mListView.setAdapter(new PaymentAdapter());
        int count = 0;
        priceCount= 0.0;

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
                Intent intent = new Intent(this,AddressActivity.class);
                intent.putExtra(Constant.LOGIN_FLAG,userId);
                intent.putExtra("ADDRESS_ID",mAddressId);
                startActivityForResult(intent, Constant.ADDRESS_REQUEST_CODE);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.Rl_pay:   //支付按钮
                if (addressMessage == null){
                    Toast.makeText(this,"请填写收货地址",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent1 = new Intent(this,EnsurePayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ADDRESS",addressMessage);
                bundle.putSerializable("SHOP_LIST", (Serializable) mCartData);
                intent1.putExtras(bundle);
                intent1.putExtra(Constant.LOGIN_FLAG, userId);
                intent1.putExtra("PRICE_COUNT",priceCount);
                startActivity(intent1);
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

    /**
     * 带回掉返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constant.ADDRESS_REQUEST_CODE){
            Bundle bundle = data.getExtras();
           addressMessage = (AddressBean.AddressMessage) bundle.getSerializable("ADDRESS_BACK");
            if (addressMessage != null){
                mAddressId = addressMessage.getDeliveryAddressId();
                tv_custom_name.setText(addressMessage.getName());
                tv_phone_number.setText(addressMessage.getPhonenumber());
                tv_pay_address.setText(addressMessage.getDetailAddress());
                Log.e("TAG","--------------------------------");
            }

        }
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
