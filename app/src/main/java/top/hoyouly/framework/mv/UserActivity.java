package top.hoyouly.framework.mv;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.User;
import top.hoyouly.framework.databinding.ActivityUserBinding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class UserActivity extends BaseBindingActivity<ActivityUserBinding> {
	private Intent intent = new Intent();
	private String name = "zhangphil";
	private int id = 1;
	private String blog = "http://blog.csdn.net/zhangphil";

	private User user = new User();

	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		mBinding.setUser(user);

		final TextView idView = mBinding.id;
		final TextView nameView = mBinding.name;
		final TextView blogView = mBinding.blog;

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (id == 5) {
								idView.setText("zhang");
								nameView.setText("phil");
								blogView.setText("blog");
								return;
							} else {
								user.setId(String.valueOf(id++) + " " + System.currentTimeMillis());
								user.setName(name + " " + System.currentTimeMillis());
								user.setBlog(blog + " " + System.currentTimeMillis());
							}
						}
					});

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	protected int getLayouId() {
		return R.layout.activity_user;
	}

	public void click3(View view) {
		skipActivity(User3Activity.class);
	}

	private void skipActivity(Class clz) {
		intent.setClass(UserActivity.this, clz);
		startActivity(intent);
	}

	public void click4(View view) {
		skipActivity(User4Activity.class);
	}

	public void click5(View view) {
		skipActivity(User5Activity.class);
	}

	public void click6(View view) {
		skipActivity(User6Activity.class);
	}

	public void click7(View view) {
		skipActivity(User7Activity.class);
	}

	public void click8(View view) {
		skipActivity(User8Activity.class);
	}

	public void click9(View view) {
		skipActivity(User9Activity.class);
	}

	public void click10(View view) {
		skipActivity(User10Activity.class);
	}

	public void click11(View view) {
		skipActivity(User11Activity.class);
	}public void click12(View view) {
		skipActivity(User12Activity.class);
	}


}
