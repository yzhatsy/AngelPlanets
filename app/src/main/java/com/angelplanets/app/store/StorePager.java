package com.angelplanets.app.store;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.store.activity.CatExcluActivity;
import com.angelplanets.app.store.activity.ShopDetailActivity;
import com.angelplanets.app.utils.bases.BasePager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 商城内容页面
 * Created by 123 on 2016/3/2.
 */
public class StorePager extends BasePager implements View.OnClickListener {

    @ViewInject(R.id.tv_common_search)
    private ImageButton mSearch;  //查找

    @ViewInject(R.id.tv_common_title)
    private TextView mTitle;  //标题

    @ViewInject(R.id.tv_common_order)
    private TextView mOrder; //订单

    @ViewInject(R.id.rl_cat_exclusive)
    private RelativeLayout rl_cat_exclusive; //猫咪专用

    @ViewInject(R.id.tv_store_number01)
    private TextView tv_store_number01;  //猫咪专用的款数

    @ViewInject(R.id.rl_cat_recommend)
    private RelativeLayout rl_cat_recommend;//精品推荐

    @ViewInject(R.id.iv_cat_recommend)
    private ImageView iv_cat_recommend; //精品推荐猫咪类图

    @ViewInject(R.id.rl_cat_hot)
    private RelativeLayout rl_cat_hot;  //猫咪热卖

    @ViewInject(R.id.iv_cat_hot)
    private ImageView iv_cat_hot; //猫咪热卖图

    @ViewInject(R.id.rl_cat_abroad)
    private RelativeLayout rl_cat_abroad; //猫咪海外

    @ViewInject(R.id.iv_cat_abroad)
    private ImageView iv_cat_abroad;  //猫咪海外图

    @ViewInject(R.id.rl_dog_exclusive)
    private RelativeLayout rl_dog_exclusive; //汪汪专用

    @ViewInject(R.id.rl_dog_recommend)
    private RelativeLayout rl_dog_recommend;  //汪汪精品推荐

    public StorePager(Activity activity) {
        super(activity);
        mOrder.setVisibility(View.VISIBLE);
        mTitle.setText("奢宠商城");
        mSearch.setVisibility(View.VISIBLE);
    }


    /**
     * 初始化View
     * @return
     */
    @Override
    protected View getView() {
        View view = View.inflate(mActivity, R.layout.pager_store,null);
        //当前view与xUtil关联
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {

        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        rl_cat_exclusive.setOnClickListener(this);
        rl_cat_recommend.setOnClickListener(this);
        rl_cat_hot.setOnClickListener(this);
        rl_cat_abroad.setOnClickListener(this);
        rl_dog_exclusive.setOnClickListener(this);
        rl_dog_recommend.setOnClickListener(this);
    }

    /**
     * view 的点击监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == rl_cat_exclusive){        //猫咪专用
            Intent intent = new Intent(mActivity, CatExcluActivity.class);
            intent.putExtra("TYPE",1);
            mActivity.startActivity(intent);
            mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }else if (v == rl_cat_recommend){  //精品推荐
            Intent intent = new Intent(mActivity, ShopDetailActivity.class);
            intent.putExtra("COMMODITY_ID", 24);
            mActivity.startActivityForResult(intent, 200);
            mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }else if (v == rl_cat_hot){      //猫咪热卖


        }else if (v == rl_cat_abroad){      //猫咪海外热卖

        }else if (v == rl_dog_exclusive){      //汪汪专用
            Intent intent = new Intent(mActivity, CatExcluActivity.class);
            intent.putExtra("TYPE",2);
            mActivity.startActivity(intent);
            mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }else if (v == rl_dog_recommend){     //汪汪推荐
            Intent intent = new Intent(mActivity, ShopDetailActivity.class);
            intent.putExtra("COMMODITY_ID", 24);
            mActivity.startActivityForResult(intent, 200);
            mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }

    
}
