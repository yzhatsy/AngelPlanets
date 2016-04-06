package com.angelplanets.app.mine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.angelplanets.app.R;
import com.angelplanets.app.view.PullScrollView;

/**
 * 宠物详情页面
 * Created by 123 on 2016/4/6.
 */
public class AnimalInformationActivity extends Activity {

    private PullScrollView mPullScrollview;
    private ImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_information);

        mPullScrollview = (PullScrollView) findViewById(R.id.animal_pullScrollview);
        mImage = (ImageView) findViewById(R.id.background_img);
        mPullScrollview.setHeader(mImage);
    }
}
