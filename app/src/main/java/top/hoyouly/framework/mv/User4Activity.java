package top.hoyouly.framework.mv;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.User4;
import top.hoyouly.framework.databinding.ActivityUser4Binding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User4Activity extends BaseBindingActivity<ActivityUser4Binding> {
	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		User4 user = new User4();
		mBinding.setUser(user);

		user.id.set(1);
		user.name.set("zhangphil");
		user.url.set("https://www.baidu.com/img/bd_logo1.png");

	}

	@Override
	protected int getLayouId() {
		return R.layout.activity_user4;
	}

}
