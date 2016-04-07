package com.angelplanets.app.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.angelplanets.app.R;
import com.angelplanets.app.view.PullScrollView;

/**
 * 宠物详情页面
 * Created by 123 on 2016/4/6.
 */
public class AnimalInformationActivity extends Activity implements View.OnClickListener {

    private PullScrollView mPullScrollview;
    private ImageView mImage;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_information);

        mPullScrollview = (PullScrollView) findViewById(R.id.animal_pullScrollview);
        mImage = (ImageView) findViewById(R.id.background_img);
        mPullScrollview.setHeader(mImage);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
        }
    }
}
