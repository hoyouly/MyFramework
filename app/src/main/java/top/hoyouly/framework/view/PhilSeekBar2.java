package top.hoyouly.framework.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

/**
 * Created by hoyouly on 18-4-3.
 */

@SuppressLint("AppCompatCustomView")
@InverseBindingMethods(@InverseBindingMethod(type = PhilSeekBar2.class, attribute = "phliprogress", event = "phliprogressAttrChanged"))
public class PhilSeekBar2 extends SeekBar {
	private int progress;
	private InverseBindingListener inverseBindingListener;

	public PhilSeekBar2(Context context, AttributeSet attrs) {
		super(context, attrs);

		setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				if (inverseBindingListener != null) {
					inverseBindingListener.onChange();
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}

	public void setPhilProgress(int progress) {
		if (this.progress != progress) {
			this.progress = progress;
			//这一句代码可以解决初始化阶段进度条显示的值正确，但没有及时更新UI的问题。
			//super.setProgress(progress);
		}
	}


	public int getPhilProgress() {
		return progress;
	}

	public void setPhliprogressAttrChanged(PhilSeekBar2 seekBar, InverseBindingListener listener) {
		if (listener == null) {
			Log.d("hoyouly", " -> setPhliprogressAttrChanged: 为null ");
		} else {
			inverseBindingListener = listener;
		}
	}


}
