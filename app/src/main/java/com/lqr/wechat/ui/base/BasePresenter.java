package com.lqr.wechat.ui.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class BasePresenter<V> {

    /*================== 以下是网络请求接口 ==================*/

    public BaseActivity mContext;
    //初始化
    public BasePresenter(BaseActivity context) {
        mContext = context;
    }

    protected Reference<V> mViewRef;

    //绑定view，这个方法将会在activity中调用
    public void attachView(V view) {
        //使用弱引用对象
        mViewRef = new WeakReference<V>(view);
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }
    //解绑
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public V getView() {
        return mViewRef != null ? mViewRef.get() : null;
    }

}
