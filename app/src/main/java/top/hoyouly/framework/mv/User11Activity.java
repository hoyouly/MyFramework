package top.hoyouly.framework.mv;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.Progress;
import top.hoyouly.framework.databinding.ActivityUser11Binding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User11Activity extends BaseBindingActivity<ActivityUser11Binding> {
	private Progress model;

	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		model = new Progress();
		mBinding.setModel(model);
		model.porgress.set(20);
	}


	@Override
	protected int getLayouId() {
		return R.layout.activity_user11;
	}

}
