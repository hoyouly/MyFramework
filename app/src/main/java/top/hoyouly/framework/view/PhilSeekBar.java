package top.hoyouly.framework.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

/**
 * Created by hoyouly on 18-4-3.
 */

@SuppressLint("AppCompatCustomView")
public class PhilSeekBar extends SeekBar {
	private static InverseBindingListener inverseBindingListener;

	public PhilSeekBar(Context context, AttributeSet attrs) {
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

	@BindingAdapter(value = "phliprogress", requireAll = false)
	public static void setPhilProgress(PhilSeekBar seekBar, int progress) {
		if (getPhilProgress(seekBar) != progress) {
			seekBar.setProgress(progress);
		}
	}


	@InverseBindingAdapter(attribute = "phliprogress", event = "phliprogressAttrChanged")
	public static int getPhilProgress(PhilSeekBar seekBar) {
		return seekBar.getProgress();
	}

	@BindingAdapter(value = "phliprogressAttrChanged" ,requireAll = false)
	public static void setPhliprogressAttrChanged(PhilSeekBar seekBar, InverseBindingListener listener) {
		if (listener == null) {
			Log.d("hoyouly", " -> setPhliprogressAttrChanged: 为null ");
		} else {
			inverseBindingListener = listener;
		}
	}


}
