package com.angelplanets.app.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.utils.bases.BasePager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的页面
 * Created by 123 on 2016/3/2.
 */
public class MinePager extends BasePager {

    @ViewInject(R.id.tv_mine_star)
    private TextView mStar; //关注

    @ViewInject(R.id.tv_mine_follower)
    private TextView mFollower; //粉丝

    @ViewInject(R.id.tv_mine_memory)
    private TextView mMemory;  //回忆

    public MinePager(Activity activity) {
        super(activity);
    }

    @Override
    protected View getView() {
       View view = View.inflate(mActivity, R.layout.pager_mine,null);
        x.view().inject(this, view);
        return view;
    }


    @Override
    public void initData() {

        setListener();

    }

    /**
     * 设置view的监听事件
     */
    private void setListener() {
        mStar.setOnClickListener(new MineOnclickListener());
        mFollower.setOnClickListener(new MineOnclickListener());
    }


    class MineOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == mStar){
                mActivity.startActivity(new Intent(mActivity,StarActivity.class));
            }else if (v == mFollower){
                mActivity.startActivity(new Intent(mActivity,FollowerActivity.class));
            }
        }
    }
}
