package top.hoyouly.framework.bean;

import android.databinding.BaseObservable;
import android.databinding.ObservableInt;

/**
 * Created by hoyouly on 18-4-3.
 */

public class Progress extends BaseObservable {
	public final ObservableInt porgress = new ObservableInt();
}