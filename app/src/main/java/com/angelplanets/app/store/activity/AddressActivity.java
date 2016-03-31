package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
import com.angelplanets.app.utils.CUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 地址管理的页面
 * Created by 123 on 2016/3/30.
 */
public class AddressActivity extends Activity implements View.OnClickListener {

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;
    private ListView lv_address;
    private RelativeLayout rl_add_address;
    private List<AddressBean.AddressMessage> addressData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);
        lv_address = (ListView) findViewById(R.id.lv_address);
        rl_add_address = (RelativeLayout) findViewById(R.id.rl_add_address);
        ib_common_back.setVisibility(View.VISIBLE);
        tv_common_title.setText("我的地址");

        getDataFromNet();

        ib_common_back.setOnClickListener(this);
        rl_add_address.setOnClickListener(this);
    }

    /**
     * 网络请求获得数据
     */
    private void getDataFromNet() {

        String url = "http://123.57.55.74:8394/rest/api/deliveryAddress/listAll?userId=167";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) { //请求成功
                Log.e("TAG", "商品详情请求成功..............： s=" + s);
                analysisData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { //请求失败
                Log.e("TAG", "商品详情请求失败： volleyError=" + volleyError);
                Toast.makeText(AddressActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

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
     * @param s
     */
    private void analysisData(String s) {
        AddressBean addressBean = CUtils.getGson().fromJson(s, AddressBean.class);
        Log.e("TAG", "address is null == " + (addressBean == null));
        addressData = addressBean.getData();
        lv_address.setAdapter(new AddressAdapter());
    }


    @Override
    public void onClick(View v) {
        if(v == ib_common_back){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else if(v == rl_add_address){
            startActivity(new Intent(this,AddAddressActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }


    class AddressAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return addressData.size();
        }

        @Override
        public AddressBean.AddressMessage getItem(int position) {
            return addressData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(AddressActivity.this,R.layout.item_address,null);
                holder.tv_custom_name = (TextView) convertView.findViewById(R.id.tv_custom_name);
                holder.tv_phone_number = (TextView) convertView.findViewById(R.id.tv_phone_number);
                holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
                holder.cb_item_check = (CheckBox) convertView.findViewById(R.id.cb_item_check);
                holder.rl_return = (RelativeLayout) convertView.findViewById(R.id.rl_return);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            AddressBean.AddressMessage addressMessage = addressData.get(position);
            holder.tv_custom_name.setText(addressMessage.getName());
            holder.tv_phone_number.setText(addressMessage.getPhonenumber());
            holder.tv_address.setText(addressMessage.getDetailAddress());



            return convertView;
        }
    }


    class ViewHolder{
        TextView tv_custom_name;
        TextView tv_phone_number;
        TextView tv_address;
        CheckBox cb_item_check;
        RelativeLayout rl_return;
    }
}
