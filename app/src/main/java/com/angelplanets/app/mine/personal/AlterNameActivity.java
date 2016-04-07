package com.angelplanets.app.mine.personal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;

/**
 * 修改姓名的页面
 * Created by 123 on 2016/4/7.
 */
public class AlterNameActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mBack;
    private TextView mTitle;
    private TextView mSave;
    private LinearLayout ll_group;
    private int mType;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_name);

        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        ll_group = (LinearLayout) findViewById(R.id.ll_group);
        mSave = (TextView) findViewById(R.id.tv_common_confirm);
        mSave.setVisibility(View.VISIBLE);
        mSave.setText("保存");
        mTitle.setText("个人信息");
        mBack.setOnClickListener(this);
        mSave.setOnClickListener(this);

        mType = getIntent().getIntExtra("TYPE",-1);

        if (mType == 1){
            view = View.inflate(this,R.layout.item_edittext,null);
            EditText name = (EditText) view.findViewById(R.id.et_alter_name);
            ll_group.addView(view);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == mBack){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else if(v == mSave){

           saveData();

        }
    }

    /**
     * 保存数据
     */
    private void saveData() {

        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
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
