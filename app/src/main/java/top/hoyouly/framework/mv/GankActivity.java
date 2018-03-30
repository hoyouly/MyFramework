package top.hoyouly.framework.mv;

import java.util.ArrayList;
import java.util.List;

import top.hoyouly.framework.BR;
import top.hoyouly.framework.R;
import top.hoyouly.framework.adapter.MyBaseAdapter;
import top.hoyouly.framework.bean.BenefitBean;
import top.hoyouly.framework.bean.GankBean;
import top.hoyouly.framework.databinding.ActivityGankBinding;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.loder.GankLoader;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;

/**
 * Created by hoyouly on 18-3-29.
 */

public class GankActivity extends BaseBindingActivity<ActivityGankBinding> {
    private List<BenefitBean> benefitBeans=new ArrayList<BenefitBean>();
    private GankLoader loader;
    private MyBaseAdapter<BenefitBean> adapter;

    @Override
    protected void initView() {
        initData();
        getListData();
    }

    private void initData() {
        loader=new GankLoader();
        adapter = new MyBaseAdapter<>(GankActivity.this, R.layout.listview_item, benefitBeans, BR.benefit);//是BR中的，这个BR和我们项目中的R文件类似，都是系统自动生成的。
        mBinding.lv.setAdapter(adapter);
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
}
