package top.hoyouly.framework.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hoyouly on 18-4-3.
 */

public class PhilView extends View {

	private interface onViewChangeListener{
		void onChange();
	}

	private onViewChangeListener listener;

	public void setListener(onViewChangeListener listener) {
		this.listener = listener;
	}

	public PhilView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	//重写该方法，该方法在View的visiblety时候回调
	@Override
	protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		Log.d("hoyouly", getClass().getSimpleName() + " -> onVisibilityChanged: ");
		if(listener!=null){
			listener.onChange();
		}
	}

	@BindingAdapter(value="display",requireAll = false)
	public static void setDisplay(PhilView view,boolean isDisplay){
		Log.d("hoyouly", " -> setDisplay: "+isDisplay);
		if(getDisplay(view)==isDisplay){
			Log.d("hoyouly", " -> setDisplay: 重复设置 ");
		}else {
			view.setVisibility(isDisplay?View.VISIBLE:View.INVISIBLE);
			Log.d("hoyouly", " -> setDisplay: "+view.getVisibility());
		}
	}

	//用InverseBindingAdapter定义getter函数。
	//attribute是xml中的属性名，event是设置属性监听的属性名，类型是InverseBindingListener。
	@InverseBindingAdapter(attribute = "display",event = "displayAttrChanged")
	public static boolean getDisplay(PhilView view){
		Log.d("hoyouly", " -> getDisplay: "+view.getVisibility());
		return  view.getVisibility()==View.VISIBLE;
	}


	//使用BindingAdapter注解设置与第三步中event值相同的回调函数的setter函数。
	@BindingAdapter(value = "displayAttrChanged",requireAll = false)  //	value 中可以用{}包括，也可以省略 @BindingAdapter(value = {"displayAttrChanged"},requireAll = false)
	public static  void setDiaplayAttrChanged(PhilView view,final InverseBindingListener inverseBindingListener){
		Log.d("hoyouly", " -> setDiaplayAttrChanged: ");
		if(inverseBindingListener==null){
			view.setListener(null);
			Log.d("hoyouly", " -> setDiaplayAttrChanged: inverseBindingListener 为null");
		}else {
			view.setListener(new onViewChangeListener() {
				@Override
				public void onChange() {
					Log.d("hoyouly", "setDiaplayAttrChanged   -> onChange: ");
					inverseBindingListener.onChange();
				}
			});
		}
	}

}
