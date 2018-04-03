package top.hoyouly.framework.mv;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.User6;
import top.hoyouly.framework.databinding.ActivityUser6Binding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User6Activity extends BaseBindingActivity<ActivityUser6Binding> {
	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		final User6 user = new User6();
		mBinding.setUser(user);
	}

	@Override
	protected int getLayouId() {
		return R.layout.activity_user6;
	}

}
