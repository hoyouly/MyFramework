package top.hoyouly.framework.mv;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.ViewModel;
import top.hoyouly.framework.databinding.ActivityUser9Binding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User9Activity extends BaseBindingActivity<ActivityUser9Binding> {
	private ViewModel model;

	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		model = new ViewModel();
		mBinding.setModel(model);
	}


	@Override
	protected int getLayouId() {
		return R.layout.activity_user9;
	}

}
