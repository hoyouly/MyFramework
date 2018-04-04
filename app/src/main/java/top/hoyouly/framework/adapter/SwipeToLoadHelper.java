package top.hoyouly.framework.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hoyouly on 18-4-4.
 */

public class SwipeToLoadHelper extends RecyclerView.OnScrollListener {
	private RecyclerView mRecyclerView;
	private RecyclerView.LayoutManager mLayouManager;
	private AdapterWrapper mAdapterWrapper;
	private LoaderMoreListener mListener;
	/**
	 * 是否正在加载中
	 */
	private boolean isLoading = false;
	/**
	 * 上拉刷新功能是否开启
	 */
	private boolean mIsSwipeToLoadEnabled = true;

	public SwipeToLoadHelper(RecyclerView recyclerView, AdapterWrapper adapterWrapper) {
		mLayouManager = recyclerView.getLayoutManager();
		mAdapterWrapper = adapterWrapper;

		if (mLayouManager instanceof GridLayoutManager) {
			mAdapterWrapper.setAdapterType(AdapterWrapper.ADAPTER_TYPE_GRID);
			mAdapterWrapper.setSpanCount(((GridLayoutManager) mLayouManager).getSpanCount());

		} else {
			mAdapterWrapper.setAdapterType(AdapterWrapper.ADAPTER_TYPE_LINEAR);
		}
		//将onScrollListener 设置RecyclerView
		recyclerView.addOnScrollListener(this);
	}


	@Override
	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		if (mIsSwipeToLoadEnabled && RecyclerView.SCROLL_STATE_IDLE == newState) {
			if (mLayouManager instanceof GridLayoutManager) {
				final GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayouManager;
				gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
					@Override
					public int getSpanSize(int position) {
						if (mIsSwipeToLoadEnabled) {
							//功能开启，会根据位置判断，最后一个item时返回整个宽度，其他位置返回1
							// AdapterWrapper会保证最后一个item 从新的一行开始。
							if (position == mLayouManager.getItemCount() - 1) {
								return gridLayoutManager.getSpanCount();
							} else {
								return 1;
							}
						} else {
							return 1;
						}
					}
				});
			}
			if (mLayouManager instanceof LinearLayoutManager) {
				//当最后一个item显示不全的时候，也就是倒数第二item完全显示
				LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mLayouManager;
				int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
				if (lastVisibleItemPosition == mLayouManager.getItemCount() - 2) {
					int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
					View view = linearLayoutManager.findViewByPosition(lastVisibleItemPosition);

					if (view == null) return;

					int deltay = recyclerView.getBottom() - recyclerView.getPaddingBottom() - view.getBottom();
					if (deltay > 0 && firstCompletelyVisibleItemPosition != 0) {
						recyclerView.smoothScrollBy(0, -deltay);
					}
				} else if (lastVisibleItemPosition == mLayouManager.getItemCount() - 1) {
					//最后一个完全显示，触发操作，执行加载更多，禁用回弹判断
					isLoading = true;
					mAdapterWrapper.setLoadItemState(true);
					if (mListener != null) {
						mListener.onLoad();
					}
				}

			}
		}
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
	}

	/**
	 * 设置下来刷新功能是否开启
	 * @param isSwipeToLoadEnabled
	 */
	public void setSwipeToLoadEnabled(boolean isSwipeToLoadEnabled){
		if(this.mIsSwipeToLoadEnabled !=isSwipeToLoadEnabled){
			this.mIsSwipeToLoadEnabled =isSwipeToLoadEnabled;
			mAdapterWrapper.setLoadItemVisibility(isSwipeToLoadEnabled);
		}
	}

	/**
	 * 设置LoadMore Item 加载完成的状态，上拉加载更多完成时调用
	 */
	public void setLoadMoreFinish(){
		isLoading=false;
		mAdapterWrapper.setLoadItemState(false);
	}

	/**
	 * 上拉操作触发是调用的接口
	 * @param listener
	 */
	public void setLoadMoreListener(LoaderMoreListener listener) {
		mListener = listener;
	}

	public interface LoaderMoreListener {
		void onLoad();
	}


}
