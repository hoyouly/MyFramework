package top.hoyouly.framework.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import top.hoyouly.framework.inter.ProgressCancelListener;

/**
 * Created by hoyouly on 18-3-28.
 */

public class ProgressDialogHandler extends Handler {

	public static final int SHOW_PROGRESS_DIALOG = 1;
	public static final int DISMISS_PROGRESS_DIALOG = 2;

	private ProgressDialog dialog;
	private Context context;
	private boolean isCancel;
	private ProgressCancelListener cancelListener;

	public ProgressDialogHandler(Context context, ProgressCancelListener cancelListener, boolean isCancel) {
		this.context = context;
		this.cancelListener = cancelListener;
		this.isCancel = isCancel;
	}


	private void initProgressDialog() {
		if (dialog == null) {
			dialog = new ProgressDialog(context);
			dialog.setTitle("标题");
			dialog.setMessage("加载中。。。。。");
		}
		dialog.setCancelable(isCancel);
		if (isCancel) {
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancelListener.onCancelProgress();
				}
			});
		}

		if (!dialog.isShowing()) {
			dialog.show();
		}
	}


	private void dismissProgressDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
			case SHOW_PROGRESS_DIALOG:
				initProgressDialog();
				break;
			case DISMISS_PROGRESS_DIALOG:
				dismissProgressDialog();
				break;
		}
	}
}
