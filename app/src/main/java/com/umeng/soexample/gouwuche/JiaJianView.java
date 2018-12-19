package com.umeng.soexample.gouwuche;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JiaJianView extends LinearLayout implements View.OnClickListener {
    private TextView mAdd;
    private TextView mDelete;
    private TextView mNumber;
    //商品数量
    private int mCount;

    public JiaJianView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.add_remove_view_layout, this);
        initViews();
    }

    private void initViews() {
        mAdd = findViewById(R.id.add_tv);
        mDelete = findViewById(R.id.delete_tv);
        mNumber = findViewById(R.id.product_number_tv);
        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
    }

    //先给初始值赋值
    public void setNumber(int number) {
        this.mCount = number;
        mNumber.setText(number + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_tv:
                if (mCount > 0) {
                    mCount--;
                    mNumber.setText(mCount + "");
                    if (mCountChange != null) {
                        mCountChange.setCount(mCount);
                    }
                }
                break;
            case R.id.add_tv:
                mCount++;
                mNumber.setText(mCount + "");
                if (mCountChange != null) {
                    mCountChange.setCount(mCount);
                }
                break;
        }
    }

    public interface OnCountChange {
        void setCount(int count);
    }

    private OnCountChange mCountChange;

    public void setOnChange(OnCountChange countChange) {
        this.mCountChange = countChange;
    }
}
