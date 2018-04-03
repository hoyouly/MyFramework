package top.hoyouly.framework.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hoyouly on 18-4-3.
 */

public class PhilScrollView extends NestedScrollView {

	private static boolean isRefreshing=false;
	private static InverseBindingListener mInverseBindingListener;

	public PhilScrollView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@BindingAdapter(value="refreshing",requireAll = false)
	public static void setDisplay(PhilScrollView view, boolean refreshing){
		Log.d("hoyouly", " -> setDisplay: "+refreshing);
		if(isRefreshing == refreshing){
			Log.d("hoyouly", " -> setDisplay: 重复设置 ");
		}else {
			isRefreshing = refreshing;
		}
	}

	//用InverseBindingAdapter定义getter函数。
	//attribute是xml中的属性名，event是设置属性监听的属性名，类型是InverseBindingListener。
	@InverseBindingAdapter(attribute = "refreshing",event = "refreshingAttrChanged")
	public static boolean getDisplay(PhilScrollView view){
		Log.d("hoyouly", " -> getDisplay: "+view.getVisibility());
		return  isRefreshing;
	}


	//使用BindingAdapter注解设置与第三步中event值相同的回调函数的setter函数。
	@BindingAdapter(value = "refreshingAttrChanged",requireAll = false)  //	value 中可以用{}包括，也可以省略 @BindingAdapter(value = {"displayAttrChanged"},requireAll = false)
	public static  void setRefreshingAttrChanged(PhilScrollView view, final InverseBindingListener inverseBindingListener){
		Log.d("hoyouly", " -> setDiaplayAttrChanged: ");
		if(inverseBindingListener==null){
			Log.d("hoyouly", " -> setDiaplayAttrChanged: inverseBindingListener 为null");
			view.setRefreshingListener(null);
		}else {

			mInverseBindingListener=inverseBindingListener;
			view.setRefreshingListener(mOnRefreshingListener);
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if((t<oldt)&&t==0){
			if(isRefreshing){
				Log.d("hoyouly", getClass().getSimpleName() + " -> onScrollChanged: 正在刷新，请勿重复加载");
			}else {
				longTimeTask();
			}
		}

	}


	private void longTimeTask() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				mOnRefreshingListener.startRefreshing();

				try {
					//假设这里做了一个长时间的耗时操作
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				mOnRefreshingListener.stopRefreshing();
			}
		}).start();
	}

	public void setRefreshingListener(OnRefreshingListener listener) {
		this.mOnRefreshingListener = listener;
	}
	public static abstract class OnRefreshingListener {
		public void startRefreshing() {
			isRefreshing = true;
			mInverseBindingListener.onChange();
		}

		public void stopRefreshing() {
			isRefreshing = false;
			mInverseBindingListener.onChange();
		}
	}

	private static OnRefreshingListener mOnRefreshingListener = new OnRefreshingListener() {
		@Override
		public void startRefreshing() {
			super.startRefreshing();
		}

		@Override
		public void stopRefreshing() {
			super.stopRefreshing();
		}
	};

}
