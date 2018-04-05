package top.hoyouly.framework.persenter;

import top.hoyouly.framework.bean.GankBean;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.loder.GankLoader;
import top.hoyouly.framework.base.BasePersenter;
import top.hoyouly.framework.mvpview.BenefitView;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;

/**
 * Created by hoyouly on 18-4-4.
 */

public class BenefitPersenter extends BasePersenter<BenefitView> {
    private GankLoader mGankLoader;
    private int currentPage;
    public static final int NUM = 20;
    public BenefitPersenter() {
        mGankLoader = new GankLoader();
    }

    public void onViewCreat() {
        if(!isViewAttached()) return;
        mvpView.setPageState(true);
        currentPage = 1;
        getListData(0);
    }

    public void onRefresh() {
        currentPage = 1;
        getListData(1);

    }

    public void onLoadMore() {
        currentPage++;
        getListData(2);

    }

    private void getListData(final int requestDataType) {
        SubscriberOnNextListener<GankBean> onNextListener = new SubscriberOnNextListener<GankBean>() {
            @Override
            public void onNext(GankBean gankBean) {
                if(!isViewAttached()) return;
                switch (requestDataType) {
                    case 0: // init
                        mvpView.setPageState(false);
                        break;
                    case 1: // refresh
                        mvpView.onRefreshComplete();
                        break;
                    case 2: // load more
                        mvpView.onLoadMoreComplete();
                        break;
                }
                mvpView.setListData(gankBean.results, requestDataType);

            }
        };
        mGankLoader.getGankBenefitList(NUM, currentPage).subscribe(new ProgressDialogSubscriber<GankBean>(onNextListener, mvpView.getContext()));
    }
}
