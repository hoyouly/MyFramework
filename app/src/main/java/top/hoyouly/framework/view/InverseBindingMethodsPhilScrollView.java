package top.hoyouly.framework.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by hoyouly on 18-4-3.
 */

//（3）InverseBindingMethod中定义的attribute值，即为开发者想要和xml布局里面的app:xxx绑定的值。
//如果基于InverseBindingMethod，在绑定注解类里面get和set方法，后面的方法名即为attribute的值，举例，如果，attribute=”xxx”，那么set和get方法即为setXxx()和getXxx()。
@InverseBindingMethods({@InverseBindingMethod(type = InverseBindingMethodsPhilScrollView.class ,attribute = "refreshing",event = "refreshingAttrChanged")})
public class InverseBindingMethodsPhilScrollView extends NestedScrollView {

	private static boolean isRefreshing = false;

	public InverseBindingMethodsPhilScrollView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public  void setRefreshing( boolean refreshing) {
		Log.d("hoyouly", " -> setDisplay: " + refreshing);
		if (isRefreshing == refreshing) {
			Log.d("hoyouly", " -> setDisplay: 重复设置 ");
		} else {
			isRefreshing = refreshing;
		}
	}

	@SuppressLint("WrongConstant")
	public  boolean getRefreshing() {
		return isRefreshing;
	}


	public  void setRefreshingAttrChanged(final InverseBindingListener inverseBindingListener) {
		Log.d("hoyouly", " -> setDiaplayAttrChanged: ");
		if (inverseBindingListener == null) {
			Log.d("hoyouly", " -> setDiaplayAttrChanged: inverseBindingListener 为null");
		} else {
			inverseBindingListener.onChange();
		}
	}
}
