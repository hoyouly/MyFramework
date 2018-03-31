package top.hoyouly.framework;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import top.hoyouly.framework.adapter.CommonRecyclerAdapter;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.bean.GankBean;
import top.hoyouly.framework.bean.GankDataBean;
import top.hoyouly.framework.databinding.ActivityBenefitBinding;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.loder.GankLoader;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;

/**
 * Created by hoyouly on 18-3-29.
 * 福利数据对应的Activity
 */

public class BenefitActivity extends BaseActivity<ActivityBenefitBinding> {
    private GankLoader loader;
    private CommonRecyclerAdapter adapter;

    @Override
    protected void initView() {
        initData();
        getListData();
    }

    private void initData() {
        loader=new GankLoader();
        adapter=new CommonRecyclerAdapter(BenefitActivity.this,R.layout.recycler_item,BR.gankData) ;
        mBinding.recycleView.setAdapter(adapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        mBinding.rvPeople.setLayoutManager(layoutManager);

        mBinding.recycleView.setLayoutManager(new GridLayoutManager(this,3));
        adapter.setListener(new CommonRecyclerAdapter.OnRecyclerViewItemClickListener<GankDataBean>() {
            @Override
            public void OnItemClick(View view, GankDataBean gankDataBean) {
//                Toast.makeText(BenefitActivity.this, "你点击的是：" + gankDataBean.getDesc(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getListData() {
        SubscriberOnNextListener<GankBean> onNextListener = new SubscriberOnNextListener<GankBean>() {
            @Override
            public void onNext(GankBean gankBean) {
                adapter.getItems().addAll(gankBean.results);
            }
        };
        loader.getGankBenefitList(100,1).subscribe(new ProgressDialogSubscriber<GankBean>(onNextListener,BenefitActivity.this));
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_benefit;
    }
}
