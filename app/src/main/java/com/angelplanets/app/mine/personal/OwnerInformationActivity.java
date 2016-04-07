package com.angelplanets.app.mine.personal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;

/**
 * 个人信息页面
 * Created by 123 on 2016/4/5.
 */
public class OwnerInformationActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_CODE_NAME = 100;
    private RelativeLayout mBack;
    private TextView mTitle;
    private RelativeLayout mAlterIcon; //修改头像
    private RelativeLayout mAlterName;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_information);

        initView();

        mBack.setOnClickListener(this);
        mAlterIcon.setOnClickListener(this);
        mAlterName.setOnClickListener(this);
    }

    private void initView() {
        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        mBack.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.tv_common_title);
        mTitle.setText("个人信息");
        mAlterIcon = (RelativeLayout) findViewById(R.id.rl_alter_icon);
        mAlterName = (RelativeLayout) findViewById(R.id.rl_alter_name);
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_common_back:
                Bundle bundle = new Bundle();
               bundle.putParcelable("ICON",bitmap);
                Intent intent = getIntent().putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;

            case R.id.rl_alter_icon:      //修改头像
                break;

            case R.id.rl_alter_name:      //修改名字
                Intent nameIntent = new Intent(this,AlterNameActivity.class);
                nameIntent.putExtra("TYPE",1);
                startActivityForResult(nameIntent,REQUEST_CODE_NAME);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
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
