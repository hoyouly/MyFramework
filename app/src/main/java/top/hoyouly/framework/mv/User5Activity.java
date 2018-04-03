package top.hoyouly.framework.mv;

import android.view.View;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.User5;
import top.hoyouly.framework.databinding.ActivityUser5Binding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User5Activity extends BaseBindingActivity<ActivityUser5Binding> {
	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		final User5 user = new User5();
		mBinding.setUser(user);

		user.id.set(0);
		user.name.set("zhangphil");

		user.quality.put("normal", "N url");
		user.quality.put("high", "H url");
		user.quality.put("super", "S url");

		user.current.set("normal");

		mBinding.button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				user.id.set(1);
				user.quality.put("normal", "33333333333");
//				user.current.set("high");
			}
		});
	}

	@Override
	protected int getLayouId() {
		return R.layout.activity_user5;
	}

}
