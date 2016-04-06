package com.angelplanets.app.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;

/**
 * 个人信息页面
 * Created by 123 on 2016/4/5.
 */
public class OwnerInformationActivity extends Activity implements View.OnClickListener {

    private RelativeLayout mBack;
    private TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_information);

        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mTitle.setText("个人信息");

        mBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
        }
    }
}
