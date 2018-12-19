package com.umeng.soexample.gouwuche.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * 文件描述：
 * 作者：鲁华丰
 * 创建时间：2018/12/19
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        if (getLayout() != 0) {
            setContentView(getLayout());
            initViews();
            onClicks();
            progress();
        }
    }

    protected abstract int getLayout();

    protected abstract void initViews();

    protected abstract void onClicks();

    protected abstract void progress();

}