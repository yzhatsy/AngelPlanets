package com.angelplanets.app.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angelplanets.app.R;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页发现的页面
 */
public class FoundActivity extends Activity implements View.OnClickListener {

    private CardStack container;
    private CardAdapter adapter;
    private ImageView iv_next;
    private ImageView iv_like;
    List data = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);

        container = (CardStack) findViewById(R.id.container);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        //container.setContentResource(R.layout.item_cardview);

        for (int i=0; i<10; i++){
            data.add(new CardData("大鼻涕唐唐","我爱奢宠计划",R.drawable.zhifuchenggong));
        }
        adapter = new CardAdapter(this);
        container.setAdapter(adapter);

        iv_like.setOnClickListener(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v== iv_like){
            Toast.makeText(this,"喜欢",Toast.LENGTH_SHORT).show();
        }
    }


    class CardAdapter extends BaseAdapter{

        private Context context;
        public CardAdapter(Context context) {
            //super(context, R.layout.item_cardview);
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(context,R.layout.item_cardview,null);
                holder. name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.detail = (TextView) convertView.findViewById(R.id.tv_detail);
                holder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            CardData cardData = (CardData) data.get(position);
            holder.name.setText(cardData.getName());
            holder.detail.setText(cardData.getDetail());
            holder.iv_photo.setImageResource(cardData.getResource());
            return convertView;
        }
    }

    class ViewHolder{
        TextView name;
        TextView detail;
        ImageView iv_photo;
    }

    class CardData{
        String name;
        String detail;
        int resource;

        public CardData() {
        }

        public CardData(String name, String detail,int resource) {
            this.name = name;
            this.detail = detail;
            this.resource = resource;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getResource() {
            return resource;
        }

        public void setResource(int resource) {
            this.resource = resource;
        }
    }
}
