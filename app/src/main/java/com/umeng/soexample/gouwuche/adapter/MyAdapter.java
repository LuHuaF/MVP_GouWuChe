package com.umeng.soexample.gouwuche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.soexample.gouwuche.JiaJianView;
import com.umeng.soexample.gouwuche.R;
import com.umeng.soexample.gouwuche.bean.MyData;

import java.util.List;

public class MyAdapter extends BaseExpandableListAdapter {
    private List<MyData.DataBean> mList;
    private Context mContext;

    public MyAdapter(List<MyData.DataBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mList.get(i).getSpus().size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null){
            groupHolder = new GroupHolder();
            convertView = View.inflate(mContext, R.layout.group_list,null);
            groupHolder.mGroupCheck = convertView.findViewById(R.id.Group_CB);
            groupHolder.mGroupTv = convertView.findViewById(R.id.Group_Name);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        MyData.DataBean dataBean = mList.get(groupPosition);
        groupHolder.mGroupTv.setText(dataBean.getName() + "");
        boolean childAllCheck = isChildAllCheck(groupPosition);
        groupHolder.mGroupCheck.setChecked(childAllCheck);
        groupHolder.mGroupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterCallback != null){
                    adapterCallback.setGroupCheck(groupPosition);
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = View.inflate(mContext, R.layout.child_item, null);
            childHolder.mChildCheck = convertView.findViewById(R.id.Child_Check_CB);
            childHolder.mChildPrice = convertView.findViewById(R.id.Child_price);
            childHolder.mChildTitle = convertView.findViewById(R.id.Child_title);
            childHolder.mImage = convertView.findViewById(R.id.Child_Icon);
            childHolder.jiaJianView = convertView.findViewById(R.id.Jia_Jian_View);
            convertView.setTag(childHolder);
        }else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        MyData.DataBean.SpusBean spusBean = mList.get(groupPosition).getSpus().get(childPosition);
        Glide.with(mContext).load(spusBean.getPic_url()).into(childHolder.mImage);
        childHolder.mChildTitle.setText(spusBean.getName() + "");
        childHolder.mChildPrice.setText(spusBean.getSkus().get(0).getPrice() + "");
        childHolder.mChildCheck.setChecked(spusBean.isChildChecked());
        childHolder.jiaJianView.setNumber(spusBean.getPraise_num());
        childHolder.mChildCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterCallback != null){
                    adapterCallback.setChildCheck(groupPosition,childPosition);
                }
            }
        });
        childHolder.jiaJianView.setOnChange(new JiaJianView.OnCountChange() {
            @Override
            public void setCount(int count) {
                if (adapterCallback != null){
                    adapterCallback.setNumber(groupPosition,childPosition,count);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class GroupHolder {
        private CheckBox mGroupCheck;
        private TextView mGroupTv;
    }
    class ChildHolder{
        private CheckBox mChildCheck;
        private TextView mChildTitle;
        private ImageView mImage;
        private TextView mChildPrice;
        private JiaJianView jiaJianView;
    }
    //点击group的CheckBox让所有child选中
    public void childAllCheck(int groupPosition , boolean isCheck){
        MyData.DataBean dataBean = mList.get(groupPosition);
        List<MyData.DataBean.SpusBean> spus = dataBean.getSpus();
        for (int i = 0; i < spus.size() ; i++) {
            MyData.DataBean.SpusBean bean = spus.get(i);
            bean.setChildChecked(isCheck);
        }
    }
    //用来判断小组是否全被选中
    public boolean isChildAllCheck(int groupPosition) {
        boolean boo = true;
        MyData.DataBean dataBean = mList.get(groupPosition);
        List<MyData.DataBean.SpusBean> spus = dataBean.getSpus();
        for (int i = 0; i < spus.size(); i++) {
            MyData.DataBean.SpusBean bean = spus.get(i);
            //只要有一个没有被选中 点这个CheckBox就是全选的功能
            if (!bean.isChildChecked()) {
                return false;
            }
        }
        return boo;
    }
    //点击child给他进行赋值
    public void setChildChecked(int groupPosition, int childPosition , boolean isCheckBox){
        MyData.DataBean.SpusBean spusBean = mList.get(groupPosition).getSpus().get(childPosition);
        spusBean.setChildChecked(isCheckBox);
    }

    //查看当前这个商品有没有被选中
    public boolean isChildChecked(int groupPositon, int childPositon) {

        MyData.DataBean.SpusBean spusBean = mList.get(groupPositon).getSpus().get(childPositon);
        if (spusBean.isChildChecked()) {
            return true;
        }
        return false;
    }

    //因为点击Group和Child第CheckBox在主页面都需要刷新值所以做成接口回调
    public interface AdapterCallback {
        void setGroupCheck(int groupPosition);

        void setChildCheck(int groupPosition, int childPosition);

        //点击加减按钮刷新
        void setNumber(int groupPosition, int childPosition, int number);

    }
    //给商品数量进行赋值

    public void setShangPingNumber(int groupPosition, int childPosition, int number) {
        MyData.DataBean.SpusBean spusBean = mList.get(groupPosition).getSpus().get(childPosition);
        spusBean.setPraise_num(number);
    }
    //因为咱们底部视图有个一全选和反选
    public boolean isAllGoods() {
        boolean boo = true;
        for (int i = 0; i < mList.size(); i++) {
            MyData.DataBean dataBean = mList.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                MyData.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                if (!spusBean.isChildChecked()) {
                    boo = false;
                }
            }
        }
        return boo;
    }
    //全选反选功能
    public void setAllGoodsIsChecked(boolean isAllCheck) {
        for (int i = 0; i < mList.size(); i++) {
            MyData.DataBean dataBean = mList.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                MyData.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                spusBean.setChildChecked(isAllCheck);
            }
        }
    }
    //计算所有商品的价格
    public float getAllGoodsPrice() {
        float allPrice = 0;
        for (int i = 0; i < mList.size(); i++) {
            MyData.DataBean dataBean = mList.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                MyData.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                if (spusBean.isChildChecked()){
                    allPrice = allPrice + spusBean.getPraise_num() * Float.parseFloat(spusBean.getSkus().get(0).getPrice());
                }
            }
        }
        return allPrice;
    }
    //计算所有商品的数量
    public int getAllGoodsNumber() {
        int allNumber = 0;
        for (int i = 0; i < mList.size(); i++) {
            MyData.DataBean dataBean = mList.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                MyData.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                if (spusBean.isChildChecked()){
                    allNumber = allNumber + spusBean.getPraise_num();
                }
            }
        }
        return allNumber;
    }
    private AdapterCallback adapterCallback;

    public void setCallback(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }
}

