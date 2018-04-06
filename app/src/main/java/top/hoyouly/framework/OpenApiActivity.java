package top.hoyouly.framework;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import top.hoyouly.framework.adapter.ViewPageAdapter;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.base.BasePersenter;
import top.hoyouly.framework.databinding.ActivityOpenapiBinding;


public class OpenApiActivity extends BaseActivity<ActivityOpenapiBinding,BasePersenter> {

    private List<String> tabTitleList=new ArrayList<String>();


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        tabTitleList.add("美图");
        tabTitleList.add("段子");
        mBinding.viewpager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(),tabTitleList));
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_openapi;
    }

    @Override
    protected BasePersenter getPersenter() {
        return new BasePersenter();
    }




}
