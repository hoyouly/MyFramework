package top.hoyouly.framework.persenter;

import java.util.Random;

import top.hoyouly.framework.base.BasePersenter;
import top.hoyouly.framework.bean.MeituBean;
import top.hoyouly.framework.bean.OpenApiResultBean;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.loder.OpenApiLoader;
import top.hoyouly.framework.mvpview.BenefitView;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;

public class MeituPersenter extends BasePersenter<BenefitView<OpenApiResultBean>> {
    private OpenApiLoader openApiLoader;

    private int currentPage=0;

    public MeituPersenter() {
        openApiLoader=new OpenApiLoader();
    }

    public void onCreatView(){
        if(!isViewAttached()) return;
        mvpView.setPageState(true);
        currentPage=1;
        getListData(0);
    }


    public void onRefresh() {
        Random rand = new Random();
//        int i = rand.nextInt(); //int范围类的随机数 i = rand.nextInt(100)
        currentPage =rand.nextInt(100) ;
        getListData(1);

    }

    public void onLoadMore() {
        currentPage++;
        getListData(2);

    }


    private void getListData(final int type) {
        SubscriberOnNextListener<OpenApiResultBean<MeituBean>> subscriberOnNextListener=new SubscriberOnNextListener<OpenApiResultBean<MeituBean>>() {
            @Override
            public void onNext(OpenApiResultBean meituBean) {
                if(!isViewAttached()) return;
                switch (type) {
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
                mvpView.setListData(meituBean.getData(), type);
            }
        } ;

        openApiLoader.getMeituList(currentPage).subscribe(new ProgressDialogSubscriber<OpenApiResultBean>(subscriberOnNextListener,mvpView.getContext()));

    }
}
