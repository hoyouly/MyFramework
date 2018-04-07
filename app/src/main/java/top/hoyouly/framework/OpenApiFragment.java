package top.hoyouly.framework;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import top.hoyouly.framework.adapter.AdapterWrapper;
import top.hoyouly.framework.adapter.CommonRecyclerAdapter;
import top.hoyouly.framework.adapter.SwipeToLoadHelper;
import top.hoyouly.framework.base.BaseFragment;
import top.hoyouly.framework.bean.MeituBean;
import top.hoyouly.framework.bean.SatinBean;
import top.hoyouly.framework.databinding.ActivityBenefitBinding;
import top.hoyouly.framework.databinding.ImageItemBinding;
import top.hoyouly.framework.databinding.SatinItemBinding;
import top.hoyouly.framework.mvpview.BenefitView;
import top.hoyouly.framework.persenter.OpenApiPersenter;

public class OpenApiFragment extends BaseFragment<ActivityBenefitBinding, OpenApiPersenter> implements BenefitView, SwipeRefreshLayout.OnRefreshListener, SwipeToLoadHelper.LoaderMoreListener {
    private static final String DATA_KEY = "data_key";
    private String fragmentType;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterWrapper adapterWrapper;
    private CommonRecyclerAdapter mAdapter;
    private SwipeToLoadHelper swipeToLoadHelper;

    public static OpenApiFragment getInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(DATA_KEY, type);
        OpenApiFragment fragment = new OpenApiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentType = getArguments().getString(DATA_KEY);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        int adapterType = AdapterWrapper.ADAPTER_TYPE_LINEAR;
        if ("美图".equals(fragmentType)) {
            mAdapter = new CommonRecyclerAdapter<MeituBean, ImageItemBinding>(getActivity(), R.layout.image_item, BR.bean);
            layoutManager = new GridLayoutManager(getActivity(), 3);
            adapterType = AdapterWrapper.ADAPTER_TYPE_GRID;
        } else {
            mAdapter = new CommonRecyclerAdapter<SatinBean, SatinItemBinding>(getActivity(), R.layout.satin_item, BR.bean);
            layoutManager = new LinearLayoutManager(getActivity());
        }
        adapterWrapper = new AdapterWrapper(mAdapter);
        adapterWrapper.setAdapterType(adapterType);
        mBinding.recycleView.setLayoutManager(layoutManager);
        mBinding.recycleView.setAdapter(adapterWrapper);
        //创建 swipeToLoadHelper过程必须在RecycleView设置LayoutManager之后，
        swipeToLoadHelper = new SwipeToLoadHelper(mBinding.recycleView, adapterWrapper);
        swipeToLoadHelper.setLoadMoreListener(this);

        mBinding.srf.setOnRefreshListener(this);
        mPresenter.onCreatView();
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_benefit;
    }

    @Override
    protected OpenApiPersenter getPersenter() {
        return new OpenApiPersenter(fragmentType);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
        swipeToLoadHelper.setSwipeToLoadEnabled(false);
    }

    @Override
    public void onLoad() {
        mPresenter.onLoadMore();
        mBinding.srf.setEnabled(false);
    }

    @Override
    public void setPageState(boolean isLoading) {
        mBinding.srf.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        mBinding.gankLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setListData(List benefitBeans, int type) {
        if (type != 2) {
            mAdapter.getItems().clear();
        }
        mAdapter.getItems().addAll(benefitBeans);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshComplete() {
        mBinding.srf.setRefreshing(false);
        //开启加载更多功能
        swipeToLoadHelper.setSwipeToLoadEnabled(true);
        adapterWrapper.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
        adapterWrapper.notifyDataSetChanged();

    }

    @Override
    public void onLoadMoreComplete() {
        mBinding.srf.setEnabled(true);
        swipeToLoadHelper.setLoadMoreFinish();
        mAdapter.notifyDataSetChanged();
        adapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onInitFailed() {
        mBinding.srf.setVisibility(View.GONE);
        mBinding.gankLoading.setVisibility(View.GONE);
        mBinding.gankLoadFailedTv.setVisibility(View.VISIBLE);
    }
}
