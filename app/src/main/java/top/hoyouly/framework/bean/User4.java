package top.hoyouly.framework.bean;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by hoyouly on 18-4-3.
 */

public class User4  extends BaseObservable{
	public final ObservableInt id=new ObservableInt();
	public final ObservableField<String> name=new ObservableField<>();
	public final ObservableField<String> url=new ObservableField<>();

}
