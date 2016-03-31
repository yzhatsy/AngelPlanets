package com.angelplanets.app.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angelplanets.app.R;
import com.angelplanets.app.mine.AddressBook.StickyListHeadersListView;

import java.util.ArrayList;

/**
 * 我的关注页面activity
 * Created by 123 on 2016/3/15.
 */
public class StarActivity extends EditTextActivity implements View.OnClickListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener {

    private RelativeLayout ib_common_back; //返回键
    private TextView tv_common_title;   //标题
   // private IconCenterEditText mSearh; //搜索

    private static final String KEY_LIST_POSITION = "KEY_LIST_POSITION";
    private int firstVisible;
    private StarListAdapter adapter;
    private ArrayList<String> text = new ArrayList<String>();


    @Override
    public void iniView() {
        setContentView(R.layout.activity_star);
        ib_common_back = (RelativeLayout) findViewById(R.id.ib_common_back);
        tv_common_title = (TextView)findViewById(R.id.tv_common_title);
        tv_common_title.setText("我的关注");
        //    mSearh = (IconCenterEditText) findViewById(R.id.et_follower_search);
        View view = View.inflate(this,R.layout.follower_top_part,null);
        view.setFocusable(true);
        StickyListHeadersListView stickyList = (StickyListHeadersListView) findViewById(R.id.lv_star_list);
        stickyList.addHeaderView(view);
        stickyList.setOnScrollListener(this);
        stickyList.setOnItemClickListener(this);
        stickyList.setOnHeaderClickListener(this);
        stickyList.setFocusable(false);

       stickyList.setEmptyView(findViewById(R.id.empty));
        for (int i = 0; i < 26; i++) {
            char ch = (char) ('a' + i);
            for (int j = 0; j < 5; j++) {
                text.add(ch + "测试联系人姓名" + j);
            }
        }
        adapter = new StarListAdapter(this, text);
        stickyList.setAdapter(adapter);
        stickyList.setSelection(firstVisible);

        stickyList.setDrawingListUnderStickyHeader(true);

        setListener();
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        ib_common_back.setOnClickListener(this);
        // 实现TextWatcher监听即可
       /* mSearh.setOnSearchClickListener(new IconCenterEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                Toast.makeText(StarActivity.this, "i'm going to seach", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        if (v == ib_common_back){
            finish();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_LIST_POSITION, firstVisible);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisible = firstVisibleItem;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Item " + position + " 被点击!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
        Toast.makeText(this, "header   " + (char) headerId + " 被点击!", Toast.LENGTH_SHORT).show();
    }
}
