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

public class MulitThreadHanderActivity extends BaseActivity {
	private Handler handlerA, handlerB;
	private boolean flagA, flagB;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new ThreadA().start();
		new ThreadB().start();
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
							handlerB.sendEmptyMessage(1);
							break;
						default:
							Log.e("hoyouly", "handleMessage: 2222222222");
					}
				}
			};
			flagA = true;
			while (flagA) {
				//				Log.e("hoyouly", getClass().getSimpleName() + " -> run: "+flagB );
				if (flagB) {
					handlerB.sendEmptyMessage(1);
					flagA = false;
				}

			}
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
			flagB = true;
			Log.e("hoyouly",  "flagB -> run: "+flagB );
			Looper.loop();
		}
	}
}
