package top.hoyouly.framework.mv;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.User6;
import top.hoyouly.framework.databinding.ActivityUser7Binding;
import top.hoyouly.framework.utils.TextUtil;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User7Activity extends BaseBindingActivity<ActivityUser7Binding> {
	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		final User6 user = new User6();
		mBinding.setUser(user);

		TextUtil textUtil=new TextUtil();
		mBinding.setUtil(textUtil);

	}

	@Override
	protected int getLayouId() {
		return R.layout.activity_user7;
	}

}
