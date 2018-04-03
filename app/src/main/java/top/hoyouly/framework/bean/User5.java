package top.hoyouly.framework.bean;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableMap;

/**
 * Created by hoyouly on 18-4-3.
 */

public class User5 extends BaseObservable{
	public final ObservableInt id=new ObservableInt();
	public final ObservableField<String> name=new ObservableField<>();
	public final ObservableField<String> current=new ObservableField<>();
	public final ObservableMap<String,Object> quality=new ObservableArrayMap<>() ;


}
