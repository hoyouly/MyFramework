package top.hoyouly.framework.persenter;

import android.content.Context;

import top.hoyouly.framework.bean.GankBean;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.loder.GankLoader;
import top.hoyouly.framework.mvpview.BenefitView;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;

/**
 * Created by hoyouly on 18-4-4.
 */

public class BenefitPerSenterImpl implements BenefitPersenter {

	private BenefitView mBenefitView;
	private GankLoader mGankLoader;
	private int currentPage;
	public static final int NUM=20;
	private Context mContext;

	public BenefitPerSenterImpl(Context context,BenefitView benefitView) {
		mContext=context;
		mBenefitView = benefitView;
		mGankLoader= new GankLoader();
	}

	@Override
	public void onViewCreat() {
		mBenefitView.setPageState(true);
		currentPage=1;
		getListData(0);
	}

	@Override
	public void onRefresh() {
		currentPage=1;
		getListData(1);

	}

	@Override
	public void onLoadMore() {
		currentPage++;
		getListData(2);

	}

	private void getListData(final int requestDataType) {
		SubscriberOnNextListener<GankBean> onNextListener = new SubscriberOnNextListener<GankBean>() {
			@Override
			public void onNext(GankBean gankBean) {
				switch (requestDataType) {
					case 0: // init
						mBenefitView.setPageState(false);
						break;
					case 1: // refresh
						mBenefitView.onRefreshComplete();
                        break;
					case 2: // load more
						mBenefitView.onLoadMoreComplete();
						break;
				}
					    mBenefitView.setListData(gankBean.results,requestDataType);

			}
		};
		mGankLoader.getGankBenefitList(NUM, currentPage).subscribe(new ProgressDialogSubscriber<GankBean>(onNextListener, mContext));
	}

}
