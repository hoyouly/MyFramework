package top.hoyouly.framework.mv;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.Progress;
import top.hoyouly.framework.databinding.ActivityUser11Binding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User11Activity extends BaseBindingActivity<ActivityUser11Binding> {
    private Progress model;
    private Handler handlerA, handlerB, mainHander;

    @Override
    protected void initView() {
        initData();
    }

    private void initData() {
        model = new Progress();
        mBinding.setModel(model);
        model.porgress.set(20);
        new ThreadA().start();
        new ThreadB().start();

    }


    @Override
    protected int getLayouId() {
        return R.layout.activity_user11;
    }

    public class ThreadA extends Thread {
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            handlerA = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 2:
                            Log.e("hoyouly", "handleMessage: " + Looper.myLooper() + "  " + msg.what);
                            break;
                        default:
                            Log.e("hoyouly", "handleMessage: 2222222222");
                    }
                }
            };
            handlerB.sendEmptyMessage(1);
            Looper.loop();
        }
    }

    public class ThreadB extends Thread {
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            handlerB = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:
                            Log.e("hoyouly", "handleMessage: " + Looper.myLooper() + "  收到HanderA 消息");
                            handlerA.sendEmptyMessage(2);
                            break;

                        default:
                            Log.e("hoyouly", "handleMessage: 111111");
                    }
                }
            };
            Looper.loop();
        }
    }


}
