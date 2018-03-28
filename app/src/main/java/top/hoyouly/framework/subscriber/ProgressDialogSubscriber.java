package top.hoyouly.framework.subscriber;

import android.content.Context;
import android.util.Log;

import rx.Subscriber;
import top.hoyouly.framework.inter.ProgressCancelListener;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.view.ProgressDialogHandler;

/**
 * Created by hoyouly on 18-3-28.
 * 在每一次发送请求的时候，弹出ProgressDialog的Subscribe，
 */

public class ProgressDialogSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

	private SubscriberOnNextListener onNextListener;
	private Context context;
	private ProgressDialogHandler progressDialogHandler;

	public ProgressDialogSubscriber(SubscriberOnNextListener onNextListener, Context context) {
		this.onNextListener = onNextListener;
		this.context = context;
		progressDialogHandler = new ProgressDialogHandler(context, this, true);
	}

	@Override
	public void onStart() {
		super.onStart();
		//启动一个PressgressDialog
		if (progressDialogHandler != null) {
			progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
		}
	}

	@Override
	public void onCompleted() {
		//停止ProgressDialog
		Log.d("hoyouly", getClass().getSimpleName() + " -> onCompleted: ");
		dismissDialog();
	}

	private void dismissDialog() {
		if (progressDialogHandler != null) {
			progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
			progressDialogHandler = null;
		}
	}

	@Override
	public void onError(Throwable e) {
		//处理错误，同时停止ProgressDialog
		Log.d("hoyouly", getClass().getSimpleName() + " -> onError: ");
		dismissDialog();
	}

	@Override
	public void onNext(T t) {
		//处理相关的数据逻辑
		if (onNextListener != null) {
			onNextListener.onNext(t);
		}
	}

	//实现cancel掉Progress的时候，取消订阅
	@Override
	public void onCancelProgress() {
		if (!isUnsubscribed()) {
			unsubscribe();
		}
	}
}
