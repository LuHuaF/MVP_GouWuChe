package com.umeng.soexample.gouwuche.presenter;

import com.umeng.soexample.gouwuche.callback.MyCallBack;
import com.umeng.soexample.gouwuche.model.ModelImpl;
import com.umeng.soexample.gouwuche.view.IView;

/**
 * 文件描述：
 * 作者：鲁华丰
 * 创建时间：2018/12/19
 */
public class IPresenterImpl implements IPresenter {
    private IView iView;
    private ModelImpl model;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        model = new ModelImpl();
    }

    @Override
    public void getString(String urls) {
        model.setStringStr(urls, new MyCallBack() {
            @Override
            public void setSuccess(Object success) {
                iView.getData(success);
            }

            @Override
            public void setError(Object error) {
                iView.getData(error);
            }
        });

    }
}
