package top.hoyouly.framework.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by hoyouly on 18-2-9.
 */

public abstract class BaseActivity<VB extends ViewDataBinding> extends Activity {
    protected  VB mBinding;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,getLayouId());
        handler=new UIHandler(this);
        initView();
    }

    protected abstract void initView();

    protected abstract int getLayouId();


	public Handler getHandler() {
		return handler;
	}

	public void handleMessage(Message message){

	}

}
