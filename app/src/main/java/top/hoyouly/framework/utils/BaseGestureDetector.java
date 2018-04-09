package top.hoyouly.framework.utils;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by hoyouly on 18-4-9.
 */

public abstract class BaseGestureDetector {
	protected boolean mGestureInPorgress;
	protected MotionEvent mPreMotionEvent;
	protected MotionEvent mCurrentMotionEvent;
	protected Context mContext;

	public BaseGestureDetector(Context context) {
		mContext = context;
	}


	public boolean onTouchEvent(MotionEvent event) {
		if (!mGestureInPorgress) {
			handleStartProgressEvent(event);
		} else {
			handleInProgressEvent(event);
		}
		return true;
	}

	public abstract void handleInProgressEvent(MotionEvent event);

	public abstract void handleStartProgressEvent(MotionEvent event);

	public abstract void updateStateByEvent(MotionEvent event);

	protected void resetState() {
		if (mPreMotionEvent != null) {
			mPreMotionEvent.recycle();
			mPreMotionEvent = null;
		}

		if (mCurrentMotionEvent != null) {
			mCurrentMotionEvent.recycle();
			mCurrentMotionEvent = null;
		}

		mGestureInPorgress = false;

	}

}
