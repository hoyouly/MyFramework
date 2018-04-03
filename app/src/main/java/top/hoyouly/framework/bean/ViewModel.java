package top.hoyouly.framework.bean;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

/**
 * Created by hoyouly on 18-4-3.
 */

public class ViewModel extends BaseObservable {
	public ObservableBoolean isDisplay = new ObservableBoolean();
}
