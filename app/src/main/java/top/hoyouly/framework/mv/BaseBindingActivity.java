package top.hoyouly.framework.mv;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by hoyouly on 18-3-29.
 */

public abstract class BaseBindingActivity<VB extends ViewDataBinding> extends Activity {
	protected  VB mBinding;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView=getLayoutInflater().inflate(this.getLayouId(),null,false);
		mBinding= DataBindingUtil.bind(rootView);

		initView();
	}

	protected abstract void initView();

	protected abstract int getLayouId();

}
