package com.angelplanets.app.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.mine.setting.SettingActivity;
import com.angelplanets.app.utils.bases.BasePager;
import com.angelplanets.app.view.CircleImageView;
import com.angelplanets.app.view.PullScrollView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的页面
 * Created by 123 on 2016/3/2.
 */
public class MinePager extends BasePager {
    private static final int REQUESTCODE = 0;

    @ViewInject(R.id.tv_mine_star)
    private TextView mStar; //关注

    @ViewInject(R.id.tv_mine_follower)
    private TextView mFollower; //粉丝

    @ViewInject(R.id.tv_mine_memory)
    private TextView mMemory;  //回忆

    @ViewInject(R.id.pullScrollview)
    private PullScrollView pullScrollview;

    @ViewInject(R.id.iv_mine_icon)
    private CircleImageView iv_mine_icon; //个人头像

    @ViewInject(R.id.background_img)
    private ImageView mHeadImg;  //背景图

    @ViewInject(R.id.iv_mine_add)
    private ImageView iv_mine_add;  //添加宠物

    @ViewInject(R.id.ll_mine_addplanet)
    private LinearLayout ll_mine_addplanet;

    @ViewInject(R.id.ib_mine_setting)
    private ImageButton mSetting;
    public MinePager(Activity activity) {
        super(activity);
    }

    @Override
    protected View getView() {
       View view = View.inflate(mActivity, R.layout.pager_mine,null);
        View photoWall = view.inflate(mActivity,R.layout.pager_mine_photo_wall,null);
        x.view().inject(this, view);
        return view;
    }


    @Override
    public void initData() {
        pullScrollview.setHeader(mHeadImg);
        setListener();

    }

    /**
     * 设置view的监听事件
     */
    private void setListener() {
        mStar.setOnClickListener(new MineOnclickListener());
        mFollower.setOnClickListener(new MineOnclickListener());
        iv_mine_icon.setOnClickListener(new MineOnclickListener());
        iv_mine_add.setOnClickListener(new MineOnclickListener());
        mSetting.setOnClickListener(new MineOnclickListener());
    }

    /**
     * 自定义监听事件
     */
    class MineOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v == mStar){                 //关注
                mActivity.startActivity(new Intent(mActivity,StarActivity.class));
            }else if (v == mFollower){       //粉丝
                mActivity.startActivity(new Intent(mActivity,FollowerActivity.class));
            }else if (v == iv_mine_icon){    //个人信息
                mActivity.startActivity(new Intent(mActivity,OwnerInformationActivity.class));
            }else if (v == iv_mine_add){    //添加宠物
                //addAnimal();
                Intent intent = new Intent(mActivity,AnimalInformationActivity.class);
                mActivity.startActivityForResult(intent,REQUESTCODE);
            }else if (v == mSetting){       //设置
                mActivity.startActivity(new Intent(mActivity,SettingActivity.class));
            }
        }
    }

    /**
     * 添加宠物
     */
    private void addAnimal() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.rightMargin = 20;
        layoutParams.topMargin = 20;


    }
}
