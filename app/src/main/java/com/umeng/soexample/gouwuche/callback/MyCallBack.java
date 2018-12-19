package com.umeng.soexample.gouwuche.callback;

/**
 * 文件描述：
 * 作者：鲁华丰
 * 创建时间：2018/12/19
 */
public interface MyCallBack<T> {
    void setSuccess(T success);
    void setError(T error);
}
