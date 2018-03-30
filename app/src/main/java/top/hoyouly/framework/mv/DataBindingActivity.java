package top.hoyouly.framework.mv;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.hoyouly.framework.R;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.bean.Translation;
import top.hoyouly.framework.databinding.ActivityDataBinding;
import top.hoyouly.framework.inter.MovieService;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;

/**
 * Created by hoyouly on 18-3-29.
 */

public class DataBindingActivity extends BaseActivity {
	private Translation contributor;
	private ActivityDataBinding dataBinding;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBinding = DataBindingUtil.setContentView(DataBindingActivity.this, R.layout.activity_data);
		dataBinding.viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
			@Override
			public void onInflate(ViewStub stub, View inflated) {
				ViewDataBinding viewDataBinding = DataBindingUtil.bind(inflated);
				
			}
		});

	}


	public void get(View view) {
		getTopContributor();
	}

	public void change(View view) {
		contributor.getTranslateResult().get(0).get(0).setTgt("hello world ");
		dataBinding.topContributor.setText(contributor.getTranslateResult().get(0).get(0).getTgt());
//		ToolbarDefaultBinding defaultBinding= DataBindingUtil.setContentView(DataBindingActivity.this,R.layout.toolbar_default);

//		defaultBinding.titleTv.setText("1111");

	}

	private void getTopContributor() {
		Retrofit retrofit = new Retrofit.Builder()//
				.baseUrl("http://fanyi.youdao.com/")//
				.addConverterFactory(GsonConverterFactory.create())//
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

		MovieService service = retrofit.create(MovieService.class);
		service.getCallByRxJava("I love you ")//
				.subscribeOn(Schedulers.io())//
				.observeOn(AndroidSchedulers.mainThread())//
				.subscribe(new ProgressDialogSubscriber<Translation>(new SubscriberOnNextListener<Translation>() {
							@Override
							public void onNext(Translation translation) {
								contributor = translation;
								String tgt = contributor.getTranslateResult().get(0).get(0).getTgt();
								dataBinding.topContributor.setText(tgt);
							}
						}, DataBindingActivity.this)

				);
	}

}
