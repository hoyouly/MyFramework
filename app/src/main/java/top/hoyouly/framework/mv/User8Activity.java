package top.hoyouly.framework.mv;

import android.util.Log;
import android.view.View;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.ViewModel;
import top.hoyouly.framework.databinding.ActivityUser8Binding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User8Activity extends BaseBindingActivity<ActivityUser8Binding> {
	@Override
	protected void initView() {
		initData();
	}

	private void initData() {

		final ViewModel model=new ViewModel();
		mBinding.setModel(model);

		model.isDisplay.set(false);
		Log.d("hoyouly", getClass().getSimpleName() + " -> initData:  数值变化 "+model.isDisplay.get());


		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mBinding.philview.setVisibility(View.VISIBLE);
						//View 变化结果，值将写入到ViewModel的isDisplay中
						Log.d("hoyouly", getClass().getSimpleName() + " -> run: "+model.isDisplay.get());
					}
				});

			}
		}).start();

	}

	@Override
	protected int getLayouId() {
		return R.layout.activity_user8;
	}

}
