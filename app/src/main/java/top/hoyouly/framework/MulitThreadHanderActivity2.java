package top.hoyouly.framework;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import top.hoyouly.framework.base.BaseActivity;

/**
 * Created by hoyouly on 18/3/22.
 */

public class MulitThreadHanderActivity2 extends BaseActivity {
    private Handler handlerA, handlerB;
    private boolean flagA, flagB;
    private Object object;
    private ThreadA threadA;
    private ThreadB threadB;
    private boolean isStop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        object = new Object();

        threadA = new ThreadA(object);
        threadA.start();
        threadB = new ThreadB(object);
        threadB.start();
    }


    public class ThreadA extends Thread {
        private Object object;

        public ThreadA(Object object) {
            this.object = object;
        }

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
                            if(isStop) return;
                            Log.e("hoyouly", "handleMessage: " + Looper.myLooper() + "  " + msg.what);
                            handlerB.sendEmptyMessageDelayed(1,1000);
                            break;
                        default:
                            Log.e("hoyouly", "handleMessage: 2222222222");
                    }
                }
            };

            if (handlerB==null) {
                Log.e("hoyouly", "run: handlerB null");
                synchronized (object) {
                    try {
                        Log.e("hoyouly", "run: 开始等待");
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.e("hoyouly", "run: 发送消息");
            handlerB.sendEmptyMessage(1);
            Looper.loop();
        }
    }

    public class ThreadB extends Thread {
        private Object object;

        public ThreadB(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Log.e("hoyouly", "run: 创建 Hanlder对象" );
            handlerB = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:
                            if(isStop) return;
                            Log.e("hoyouly", "handleMessage: " + Looper.myLooper() + "  收到HanderA 消息");
                            handlerA.sendEmptyMessageDelayed(2,1000);
                            break;
                        default:
                            Log.e("hoyouly", "handleMessage: 111111");
                    }
                }
            };
            Log.e("hoyouly", "run: handlerB 不为null");
            synchronized (object) {
                Log.e("hoyouly", "run: notify");
                object.notify();
            }
            Looper.loop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStop=true;
    }
}
