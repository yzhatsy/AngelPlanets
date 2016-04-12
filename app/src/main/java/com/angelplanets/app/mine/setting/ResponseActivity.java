package com.angelplanets.app.mine.setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.angelplanets.app.R;

/**
 * 帮助与反馈页面
 * Created by 123 on 2016/4/6.
 */
public class ResponseActivity extends Activity implements View.OnClickListener {

    private RelativeLayout mBack;
    private EditText et_idea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        mBack = (RelativeLayout) findViewById(R.id.ib_common_back);
        et_idea = (EditText) findViewById(R.id.et_idea);
        mBack.setVisibility(View.VISIBLE);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }

    /**
     * 提交意见
     * @param view
     */
    public void submit(View view){
        String text  = et_idea.getText().toString();
        final ProgressDialog dialog = ProgressDialog.show(this,null,"正在提交...");
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
                finish();
            }
        }.start();

    }
}
