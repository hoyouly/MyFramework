package top.hoyouly.framework;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import top.hoyouly.framework.adapter.CommonListviewAdapter;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.bean.GankBean;
import top.hoyouly.framework.bean.GankDataBean;
import top.hoyouly.framework.databinding.ActivityGankBinding;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.loder.GankLoader;
import top.hoyouly.framework.persenter.BenefitPersenter;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;

/**
 * Created by hoyouly on 18-3-29.
 */

public class GankActivity extends BaseActivity<ActivityGankBinding,BenefitPersenter> {
    private List<GankDataBean> benefitBeans=new ArrayList<GankDataBean>();
    private GankLoader loader;
    private CommonListviewAdapter<GankDataBean> adapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        loader=new GankLoader();
        adapter = new CommonListviewAdapter<>(GankActivity.this, R.layout.listview_item, benefitBeans, BR.benefit);//是BR中的，这个BR和我们项目中的R文件类似，都是系统自动生成的。
        mBinding.lv.setAdapter(adapter);
        getListData();
    }

    private void getListData() {
        SubscriberOnNextListener<GankBean> onNextListener = new SubscriberOnNextListener<GankBean>() {
            @Override
            public void onNext(GankBean gankBean) {
                adapter.setList(gankBean.results);
            }
        };
        loader.getGankBenefitList(10,1).subscribe(new ProgressDialogSubscriber<GankBean>(onNextListener,GankActivity.this));
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_gank;
    }

    @Override
    protected BenefitPersenter getPersenter() {
        return new BenefitPersenter();
    }


}
