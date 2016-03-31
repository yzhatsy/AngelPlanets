package com.angelplanets.app.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
/**
 * 我的粉丝页面
 * Created by 123 on 2016/3/16.
 */
public class FollowerActivity extends Activity implements View.OnClickListener {

    private RelativeLayout ib_common_back; //返回键
    private TextView tv_common_title;   //标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView)findViewById(R.id.tv_common_title);
        tv_common_title.setText("我的粉丝");

        setListener();
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        ib_common_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ib_common_back){
            finish();
        }
    }
}
