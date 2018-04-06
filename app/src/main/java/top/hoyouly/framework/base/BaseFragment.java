package top.hoyouly.framework.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hoyouly on 18-2-9.
 */

public abstract class BaseFragment<VB extends ViewDataBinding, P extends BasePersenter> extends Fragment implements BaseMVPView {
    protected VB mBinding;
    protected P mPresenter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = getPersenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater, getLayouId(), container, false);
        initData(savedInstanceState);
        //这地方必须得返回的是mBinding.getRoot()不然后果很严重。。
        return mBinding.getRoot();
    }

    public void initData(Bundle savedInstanceState) { }


    protected abstract int getLayouId();

    protected abstract P getPersenter();


    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
        mPresenter.detachView();
    }

}
