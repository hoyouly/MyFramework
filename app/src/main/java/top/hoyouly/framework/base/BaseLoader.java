package top.hoyouly.framework.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hoyouly on 18-3-27.
 * 将一些重复的操作，抽取到父类中，以免每个Loader 里面每个接口都有重复代码
 */

public class BaseLoader {

	protected <T>Observable<T> observe(Observable<T> observable){
		return observable.subscribeOn(Schedulers.io())//
				.unsubscribeOn(Schedulers.io())//
				.observeOn(AndroidSchedulers.mainThread());

	}

}
