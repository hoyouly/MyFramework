package top.hoyouly.framework.utils;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by hoyouly on 18-4-9.
 */

public class MoveGesutureDetector extends BaseGestureDetector {
	private PointF mCurrentPointer;
	private PointF mPrePointer;

	//仅仅减少内存
	private PointF mDeltaPointer = new PointF();

	//用于记录最终数据，并返回
	private PointF mExtanalPointer = new PointF();

	private OnMoveGestureListener mListener;

	public MoveGesutureDetector(Context context, OnMoveGestureListener listener) {
		super(context);
		mListener = listener;
	}

	@Override
	public void handleInProgressEvent(MotionEvent event) {
		int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
		switch (actionCode) {
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				mListener.onMoveEnd(this);
				resetState();
				break;
			case MotionEvent.ACTION_MOVE:
				updateStateByEvent(event);
				boolean isScuess = mListener.onMove(this);
				if (isScuess) {
					mPreMotionEvent.recycle();
					mPreMotionEvent = MotionEvent.obtain(event);
				}
				break;
		}

	}

	@Override
	public void handleStartProgressEvent(MotionEvent event) {
		int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
		switch (actionCode) {
			case MotionEvent.ACTION_DOWN:
				resetState();//防止么有接收到CANCEL，UP，保险起见
				mPreMotionEvent = MotionEvent.obtain(event);
				updateStateByEvent(event);
				break;
			case MotionEvent.ACTION_MOVE:
				mGestureInPorgress = mListener.onMoveBegin(this);
				break;
		}

	}

	@Override
	public void updateStateByEvent(MotionEvent event) {
		final MotionEvent prev = mPreMotionEvent;
		mPrePointer = caculateFocalPointer(prev);
		mCurrentPointer = caculateFocalPointer(event);

		boolean isSkipThisMoveEvent = prev.getPointerCount() != event.getPointerCount();
		mExtanalPointer.x = isSkipThisMoveEvent ? 0 : mCurrentPointer.x - mPrePointer.x;
		mExtanalPointer.y = isSkipThisMoveEvent ? 0 : mCurrentPointer.y - mPrePointer.y;
	}

	/**
	 * 根据event计算多指中心点
	 *
	 * @param event
	 * @return
	 */
	private PointF caculateFocalPointer(MotionEvent event) {
		final int conut = event.getPointerCount();
		float x = 0, y = 0;
		for (int i = 0; i < conut; i++) {
			x += event.getX();
			y += event.getY();
		}
		x /= conut;
		y /= conut;

		return new PointF(x, y);
	}

	public float getMoveY() {
		return mExtanalPointer.y;
	}

	public float getMoveX() {
		return mExtanalPointer.x;
	}


	public interface OnMoveGestureListener {

		boolean onMoveBegin(MoveGesutureDetector detector);

		boolean onMove(MoveGesutureDetector detector);

		void onMoveEnd(MoveGesutureDetector detector);

	}

	public static class SimpleMoveGestuerDetector implements OnMoveGestureListener {

		@Override
		public boolean onMoveBegin(MoveGesutureDetector detector) {
			return true;
		}

		@Override
		public boolean onMove(MoveGesutureDetector detector) {
			return false;
		}

		@Override
		public void onMoveEnd(MoveGesutureDetector detector) {
		}
	}

}
