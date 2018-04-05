package top.hoyouly.framework;

import android.content.Intent;
import android.view.View;

import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.databinding.ActivityMainBinding;
import top.hoyouly.framework.base.BasePersenter;


public class MainActivity extends BaseActivity<ActivityMainBinding,BasePersenter> {
    Intent intent = new Intent();

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePersenter getPersenter() {
        return new BasePersenter();
    }

    public void benefit(View view) {
        intent.setClass(MainActivity.this, BenefitActivity.class);
        startActivity(intent);
    }

    public void android(View view){
        intent.setClass(MainActivity.this, GankActivity.class);
        startActivity(intent);
    }


}
