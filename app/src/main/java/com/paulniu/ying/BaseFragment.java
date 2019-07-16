package com.paulniu.ying;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 16:59
 * Desc: fragment基类
 * Version:
 */
public abstract class BaseFragment extends Fragment {

    public View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(getLayoutId(), container, false);
        lazyLoad();
        return mRoot;
    }

    public abstract int getLayoutId();

    public abstract void initDateExtra();

    public abstract void initViewFindViewById(View view);

    public abstract void initListener();

    public abstract void initData();

    private void lazyLoad() {
        initDateExtra();
        initViewFindViewById(mRoot);
        initListener();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
