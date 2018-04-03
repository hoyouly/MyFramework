package top.hoyouly.framework.mv;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.ViewModel;
import top.hoyouly.framework.databinding.ActivityUser10Binding;
import top.hoyouly.framework.databinding.ActivityUser9Binding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User10Activity extends BaseBindingActivity<ActivityUser10Binding> {
	private GestureDetector gestureDetector;
	private ViewModel model;

	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		gestureDetector = creatGestureDetector();
		model = new ViewModel();
		mBinding.setModel(model);
		mBinding.philscrollview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				gestureDetector.onTouchEvent(motionEvent);
				return false;
			}
		});
	}

	private GestureDetector creatGestureDetector() {
		return new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				if((distanceY<0)&&(mBinding.philscrollview.getVisibility()==0)){
					if(model.isDisplay.get()){
						Log.d("hoyouly", getClass().getSimpleName() + " -> onScroll: 加载中，请勿重复加载");
					}else {
						Log.d("hoyouly", getClass().getSimpleName() + " -> onScroll: 开始下拉刷新");
						loadMore();
					}
				}
				return super.onScroll(e1, e2, distanceX, distanceY);
			}
		});


	}

	private void loadMore() {
		new Thread(new Runnable() {
			@Override
			public void run() {

				model.isDisplay.set(true);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				model.isDisplay.set(false);
			}
		}).start();

	}

	@Override
	protected int getLayouId() {
		return R.layout.activity_user10;
	}

}
