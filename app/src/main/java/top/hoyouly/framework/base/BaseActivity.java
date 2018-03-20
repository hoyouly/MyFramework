package top.hoyouly.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by hoyouly on 18-2-9.
 */

public class BaseActivity extends Activity {
	protected  UIHandler handler;
	protected static class UIHandler extends Handler {
		WeakReference<BaseActivity> softReference;
		UIHandler(BaseActivity activity){
			softReference=new WeakReference<BaseActivity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			BaseActivity activity=softReference.get();
			if(activity!=null){
				activity.handleMessage(msg);
			}else {
				Log.d("hoyouly", getClass().getSimpleName() + " -> handleMessage: activity null");
			}
		}

		Activity getActivity(){
			return softReference.get();
		}
	}

	public Handler getHandler() {
		return handler;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler=new UIHandler(this);
	}

	public void handleMessage(Message message){

	}

}
