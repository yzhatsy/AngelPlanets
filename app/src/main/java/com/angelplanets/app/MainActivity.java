package com.angelplanets.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.angelplanets.app.home.HomePager;
import com.angelplanets.app.mine.MinePager;
import com.angelplanets.app.social.SocialPager;
import com.angelplanets.app.store.StorePager;
import com.angelplanets.app.utils.Constant;
import com.angelplanets.app.utils.bases.BasePager;

import java.util.ArrayList;

/**
 * 主Activity，是其他页面的载体
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private FragmentManager mFragmentManager;
    private RadioGroup mRadioGroup;
    private int mPosition;                //RadioGroup的下标
    private ArrayList<BasePager> mPagers; //装载pager的集合
    private ImageView ib_message; //发布消息
    Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.angelplanets.app.R.layout.activity_main);

        initData();
        //获得Fragment管理器
        mFragmentManager = getSupportFragmentManager();

        //设置从别的页面跳转首页后选择需要的pager页
        int index = getIntent().getIntExtra("PAY_SUCCESS",-1);
        if (index == 1){
            mPosition =1;
            RadioButton button = (RadioButton) mRadioGroup.getChildAt(mPosition);
            button.setChecked(true);
        }

        //首次进入选中首页内容
        setFragment();


    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRadioGroup = (RadioGroup) findViewById(com.angelplanets.app.R.id.radiogroup_main);

        ib_message = (ImageView) findViewById(com.angelplanets.app.R.id.ib_message);
        mPagers = new ArrayList<>();
        mPagers.add(new HomePager(this));
        mPagers.add(new StorePager(this));
        mPagers.add(new SocialPager(this));
        mPagers.add(new MinePager(this));
        mRadioGroup.setOnCheckedChangeListener(new MianOnCheckedChangeListener());
        ib_message.setOnClickListener(this);
    }

    /**
     * button的点击监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == ib_message){
           // startActivity(new Intent(MainActivity.this,MessageActivity.class));
        }
    }


    /**
     * 自定义 OnCheckedChangeListener
     * 点击按钮切换到相应的页面
     */
    class MianOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_home:  //首页
                    mPosition = 0;
                    break;
                case R.id.rb_store:  //商城
                    mPosition = 1;
                    break;
                case R.id.rb_social: //社交
                    mPosition = 2;
                    break;
                case R.id.rb_mine:   //我的
                    mPosition = 3;
                    break;
            }
            setFragment();
        }
    }

    /**
     * 设置Fragment到Activity中
     */
    private void setFragment() {
        //开启事务
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.fl_content_main,new Fragment(){
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState){
                BasePager pager = getBasePager();
                if (null != pager)
                    return pager.mRootView;
                return null;

            }
        }).commit();
    }

    /**
     * 根据RadioGroup的索引位置获取指定的Pager页面
     * @return
     */
    private BasePager getBasePager() {
        BasePager pager = mPagers.get(mPosition);
        if (null != pager && !pager.mInit){
            pager.mInit = true;
            pager.initData();
        }
        return pager;
    }

    /**
     * 在按一次先回到首页
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (0 != mPosition) {
                mRadioGroup.check(R.id.rb_home);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode == RESULT_OK && requestCode == Constant.REQUESTCODE){
           mPosition =3;
           RadioButton button = (RadioButton) mRadioGroup.getChildAt(mPosition);
           button.setChecked(true);
       }
    }
}
