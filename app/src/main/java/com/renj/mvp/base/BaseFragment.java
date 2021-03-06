package com.renj.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renj.mvp.app.MyApplication;
import com.renj.mvp.base.dagger.BaseFragmentComponent;
import com.renj.mvp.base.dagger.BaseFragmentModule;
import com.renj.mvp.base.dagger.DaggerBaseFragmentComponent;
import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   18:39
 * <p>
 * 描述：Fragment的基类<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果一个新的Fragment不需要访问网络，那么就直接继承{@link BaseFragment}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果一个新的Fragment需要访问防落，那么可以继承{@link BasePresenterFragment}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseFragment extends RxFragment implements BaseControl.IView, View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(getLayoutId(),null);
        ButterKnife.inject(this, view);
        inject(initBaseComponent());
        initPresenter();
        initData();
        return view;
    }

    /**
     * 初始化Presenter，在{@link BasePresenterFragment}中重写
     */
    void initPresenter() {

    }

    /**
     * 初始化{@link BaseFragmentComponent}
     *
     * @return
     */
    private BaseFragmentComponent initBaseComponent() {
        return DaggerBaseFragmentComponent.builder()
                .applicationComponent(MyApplication.applicationComponent)
                .baseFragmentModule(new BaseFragmentModule())
                .build();
    }

    /**
     * 当有对象是通过注入的方式初始化的，那么需要重写该方法<br/>
     * <b>提示：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该方法的一般写法：
     * <code>fragmentComponent.inject(this);</code>
     * </b><br/>
     * <b>注意：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如上重写该方法，需要在{@link BaseFragmentComponent}中添加代码
     * <code>void inject(CustomFragment customFragment);</code>
     * </b>
     *
     * @param fragmentComponent
     */
    protected abstract void inject(BaseFragmentComponent fragmentComponent);

    @Override
    public void onClick(View v) {
        handlerClick(v,v.getId());
    }

    /**
     * 处理点击事件时，只需要重写该方法即可，不需要再实现{@link android.view.View.OnClickListener}接口
     *
     * @param v   点击的控件
     * @param vId 点击的控件id
     */
    protected void handlerClick(View v, int vId) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
