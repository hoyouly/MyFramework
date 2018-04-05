package top.hoyouly.framework;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import top.hoyouly.framework.adapter.AdapterWrapper;
import top.hoyouly.framework.adapter.CommonRecyclerAdapter;
import top.hoyouly.framework.adapter.SwipeToLoadHelper;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.bean.GankDataBean;
import top.hoyouly.framework.databinding.ActivityBenefitBinding;
import top.hoyouly.framework.persenter.BenefitPersenter;
import top.hoyouly.framework.mvpview.BenefitView;

/**
 * Created by hoyouly on 18-3-29.
 * 福利数据对应的Activity
 */

public class BenefitActivity extends BaseActivity<ActivityBenefitBinding,BenefitPersenter> implements SwipeRefreshLayout.OnRefreshListener, SwipeToLoadHelper
        .LoaderMoreListener,BenefitView {
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private AdapterWrapper mAdapterWrapper;
	private SwipeToLoadHelper mLoadMoreHelper;
	private ProgressBar mProgressBar;
	private CommonRecyclerAdapter mAdapter;
    private RecyclerView recycleView;


    @Override
	protected void initView() {
		mSwipeRefreshLayout = mBinding.srf;
		mProgressBar=mBinding.gankLoading;
        recycleView = mBinding.recycleView;
		initData();
	}

	private void initData() {
        mAdapter = new CommonRecyclerAdapter(BenefitActivity.this, R.layout.recycler_item, BR.gankData);
        mAdapterWrapper = new AdapterWrapper(mAdapter);
		recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        mLoadMoreHelper = new SwipeToLoadHelper(recycleView, mAdapterWrapper);
        recycleView.setAdapter(mAdapterWrapper);

		mSwipeRefreshLayout.setOnRefreshListener(this);
        mLoadMoreHelper.setLoadMoreListener(this);

		//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		//        mBinding.rvPeople.setLayoutManager(layoutManager);
		mPresenter.onViewCreat();
	}


	@Override
	protected int getLayouId() {
		return R.layout.activity_benefit;
	}

    @Override
    protected BenefitPersenter getPersenter() {
        return new BenefitPersenter();
    }


    @Override
	public void onRefresh() {
		mPresenter.onRefresh();
		// 刷新时禁用上拉加载更多
		mLoadMoreHelper.setSwipeToLoadEnabled(false);
	}

	@Override
	public void onLoad() {
		mSwipeRefreshLayout.setEnabled(false);
		mPresenter.onLoadMore();
	}

	@Override
	public void setPageState(boolean isLoading) {
		mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
		mSwipeRefreshLayout.setVisibility(isLoading ? View.GONE : View.VISIBLE);
	}

	@Override
	public void setListData(List<GankDataBean> benefitBeans,int type) {
        if(type!=2){
            mAdapter.getItems().clear();
        }
		mAdapter.getItems().addAll(benefitBeans);
	}

	@Override
	public void onRefreshComplete() {
		mSwipeRefreshLayout.setRefreshing(false);
		// 刷新完成是解禁上拉加载更多
		mLoadMoreHelper.setSwipeToLoadEnabled(true);
		mAdapterWrapper.notifyDataSetChanged();
	}

	@Override
	public void onLoadMoreComplete() {
		mSwipeRefreshLayout.setEnabled(true);
		mLoadMoreHelper.setLoadMoreFinish();
		mAdapterWrapper.notifyDataSetChanged();
	}

	@Override
	public void onInitFailed() {
		mSwipeRefreshLayout.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		mBinding.gankLoadFailedTv.setVisibility(View.VISIBLE);

	}

}
