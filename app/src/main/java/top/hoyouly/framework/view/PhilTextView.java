package top.hoyouly.framework.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hoyouly on 18-4-3.
 */


/**
 * BindingMethods包含若干BindingMethod，BindingMethod是BindingMethods的子集。
 * BindingMethods与BindingMethod用于类的注解，简单的可以理解为，定义xml中定义的属性与某个medthod（方法）绑定。
 */
@SuppressLint("AppCompatCustomView")
//BindingMethods与BindingMethod定义了一个自己声明的属性：zhangphiltoast 该属性与PhilTextView中的showZhangPhilToast绑定。

@BindingMethods({@BindingMethod(type = TextView.class, attribute = "zhangphiltoast", method = "showZhangPhilToast")})
public class PhilTextView extends TextView {
	public PhilTextView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public void showZhangPhilToast(String s) {
		if (TextUtils.isEmpty(s)) {
			return;
		}

		Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
	}
}