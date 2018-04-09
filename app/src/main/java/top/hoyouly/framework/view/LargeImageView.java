package top.hoyouly.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.InputStream;

import top.hoyouly.framework.utils.MoveGesutureDetector;

/**
 * Created by hoyouly on 18-4-9.
 */

public class LargeImageView extends View {
	private BitmapRegionDecoder mDecoder;
	/**
	 * 图片的宽度和高度
	 */
	private int mImageWidth, mImageHeight;
	/**
	 * 绘制区域
	 */
	private volatile Rect mRect = new Rect();

	private MoveGesutureDetector mDetector;

	private static final BitmapFactory.Options options = new BitmapFactory.Options();

	static {
		options.inPreferredConfig = Bitmap.Config.RGB_565;
	}

	public LargeImageView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}


	@Override//监听move的手势，在监听的回调里面去改变rect的参数，以及做边界检查，最后invalidate
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return true;
	}

	@Override//里面就是根据rect拿到bitmap，然后draw了
	protected void onDraw(Canvas canvas) {
		if (mDecoder != null) {
			Bitmap bitmap = mDecoder.decodeRegion(mRect, options);
			canvas.drawBitmap(bitmap, 0, 0, null);
		}else {
			super.onDraw(canvas);
		}
	}

	@Override//里面为我们的显示区域的rect赋值，大小为view的尺寸
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int imageWidth = mImageWidth;
		int imageHeight = mImageHeight;
		mRect.left = imageWidth / 2 - width / 2;
		mRect.top = imageHeight / 2 - height / 2;

		mRect.right = mRect.left + width;
		mRect.bottom = mRect.top + height;
	}

	//setInputStream里面去获得图片的真实的宽度和高度，以及初始化我们的mDecoder
	public void setInputStream(InputStream in) {
		try {
			mDecoder = BitmapRegionDecoder.newInstance(in, false);
			BitmapFactory.Options tmpOption = new BitmapFactory.Options();
			tmpOption.inJustDecodeBounds = false;
			BitmapFactory.decodeStream(in, null, tmpOption);
			mImageWidth = tmpOption.outWidth;
			mImageHeight = tmpOption.outHeight;
			requestLayout();
			invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void init() {
		mDetector = new MoveGesutureDetector(getContext(), new MoveGesutureDetector.SimpleMoveGestuerDetector() {
			@Override
			public boolean onMove(MoveGesutureDetector detector) {
				int moveX = (int) detector.getMoveX();
				int moveY = (int) detector.getMoveY();
				if (mImageWidth > getWidth()) {
					mRect.offset(-moveX, 0);
					checkWidth();
					invalidate();
				}
				if (mImageHeight > getHeight()) {
					mRect.offset(0, -moveY);
					checkHeight();
					invalidate();
				}
				return true;
			}
		});
	}

	private void checkHeight() {
		Rect rect = mRect;
		int imageHeight = mImageHeight;

		if (rect.bottom > imageHeight) {
			rect.bottom = imageHeight;
			rect.top = imageHeight - getHeight();
		}

		if (rect.top < 0) {
			rect.top = 0;
			rect.bottom = getHeight();
		}

	}

	private void checkWidth() {

		Rect rect = mRect;
		int imageWidth = mImageWidth;

		if (rect.right > imageWidth) {
			rect.right = imageWidth;
			rect.left = imageWidth - getWidth();
		}
		if (rect.left < 0) {
			rect.left = 0;
			rect.right = getWidth();
		}


	}

}
