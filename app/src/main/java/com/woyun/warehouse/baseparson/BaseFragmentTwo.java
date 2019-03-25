package com.woyun.warehouse.baseparson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 *  懒加载第一次可见 并且加载一次
 */
public abstract  class BaseFragmentTwo extends Fragment {
    private static final String TAG = "BaseFragmentTwo";

    public View rootView;
    public Activity mContext;
    /**
     * 是否初始过布局
     */
    protected boolean isViewInit;
    /**
     * 当前页面是否可见
     */
    protected  boolean isVisible;
    /**
     * 是否加载过数据
     */
    protected  boolean isLoadData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        rootView=inflater(getLayoutId(),container,false);
        ButterKnife.bind(this,rootView);
        mContext = getActivity();

        this.initData();
        return  rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInit=true;
        //加载数据
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        this.isVisible=isVisibleToUser;
        if(isVisible){
            prepareFetchData();
        }
    }


    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     *
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */

    public void prepareFetchData(boolean forceUpdate){
        if(isVisible && isViewInit && (!isLoadData || forceUpdate)){
            loadData();
            isLoadData=true;
        }
    }
    /**
     * 懒加载
     */
    protected abstract void loadData();
    /**
     * 获取布局中id
     */
    protected abstract int getLayoutId();
    /**
     * 数据初始化操作
     */
    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.rootView = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(getActivity()).unbind();
    }








    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

}
