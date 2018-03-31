package top.hoyouly.framework;

import android.content.Intent;
import android.view.View;

import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<ActivityMainBinding> {
    Intent intent = new Intent();

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_main;
    }

    public void benefit(View view) {
        intent.setClass(MainActivity.this, GankActivity.class);
        startActivity(intent);

    }


}
