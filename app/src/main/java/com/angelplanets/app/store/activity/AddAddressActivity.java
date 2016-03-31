package com.angelplanets.app.store.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;

public class AddAddressActivity extends Activity implements View.OnClickListener {

    private RelativeLayout ib_common_back;
    private TextView tv_common_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView) findViewById(R.id.tv_common_title);

        ib_common_back.setVisibility(View.VISIBLE);
        tv_common_title.setText("新建地址");
        ib_common_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == ib_common_back){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }
}
