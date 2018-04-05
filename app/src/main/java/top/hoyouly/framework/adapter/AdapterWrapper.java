package top.hoyouly.framework.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import top.hoyouly.framework.R;

/**
 * Created by hoyouly on 18-4-4.
 */

public class AdapterWrapper extends RecyclerView.Adapter {
	/**
	 * 线性类型
	 */
	public static final int ADAPTER_TYPE_LINEAR = 0x01;
	/**
	 * 网格
	 */
	public static final int ADAPTER_TYPE_GRID = 0x02;

	/**
	 * view type 上拉加载更多
	 */
	private static final int ITTM_TYPE_LOAD = Integer.MAX_VALUE / 2;


	private RecyclerView.Adapter mAdapter;
	private boolean mShowLoadMore = true;

	private WrapperHolder mWrapperHolder;

	private int mAdapterType = ADAPTER_TYPE_LINEAR;

	private int mSpanCount;

	public AdapterWrapper(RecyclerView.Adapter adapter) {
		mAdapter = adapter;
	}

	/**
	 * 设置wrapper 类型，默认是线性的
	 *
	 * @param type
	 */
	public void setAdapterType(int type) {
		if (mAdapterType != type) {
			mAdapterType = type;
		}
	}

	/**
	 * 网格布局的网格数量
	 *
	 * @param count
	 */
	public void setSpanCount(int count) {
		if (mSpanCount != count) {
			mSpanCount = count;
		}
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == ITTM_TYPE_LOAD) {
			if (mWrapperHolder == null) {
				mWrapperHolder = new WrapperHolder(View.inflate(parent.getContext(), R.layout.item_load_more, null));
			}
			return mWrapperHolder;
		}
		return mAdapter.onCreateViewHolder(parent, viewType);
	}

	//允许显示加载更多item,并且position为末尾的时候，拦截
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (mShowLoadMore && position == getItemCount() - 1) {
			//最后一项，不需要做额外的事情
		} else if (position < mAdapter.getItemCount()) {//正常显示
            // 正常情况 首先需要把item设置为VISIBLE，不然item复用的时候，会显示不出来。
            holder.itemView.setVisibility(View.VISIBLE);
			mAdapter.onBindViewHolder(holder, position);
		} else {
			//网格补空的情况
			holder.itemView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public int getItemCount() {
		if (mAdapterType == ADAPTER_TYPE_LINEAR) {
			return mShowLoadMore ? mAdapter.getItemCount() + 1 : mAdapter.getItemCount();
		} else {
			//网格布局
			if (!mShowLoadMore) {//不显示load more时候直接返回真是数量
				return mAdapter.getItemCount();
			}
			int remain = mAdapter.getItemCount() % mSpanCount;//求余
			if (remain == 0) {//余数为零，就直接返回总条目加上一个  加载更多布局
				return mAdapter.getItemCount() + 1;
			}
			//余数不为零的情况，先凑满再加一
			return mAdapter.getItemCount() + 1 + (mSpanCount - remain);
		}
	}

	@Override
	public int getItemViewType(int position) {
		//当显示加载更多条目，并且位置是最后一个的时候，wrapper进行拦截。
		if (mShowLoadMore && position == getItemCount()-1) {
			return ITTM_TYPE_LOAD;
		}
		//其他情况交给原来的Adapter处理
		return mAdapter.getItemViewType(position);
	}


	public void setLoadItemVisibility(boolean isShow) {
		if (mShowLoadMore != isShow) {
			mShowLoadMore = isShow;
			notifyDataSetChanged();
		}
	}

	public void setLoadItemState(boolean isLoading) {
		if (isLoading) {
			mWrapperHolder.mLoadTv.setText("正在加载...");
			mWrapperHolder.mLoadPb.setVisibility(View.VISIBLE);
		} else {
			mWrapperHolder.mLoadTv.setText("上拉加载更多");
			mWrapperHolder.mLoadPb.setVisibility(View.GONE);
		}
	}


	class WrapperHolder extends RecyclerView.ViewHolder {

		public TextView mLoadTv;
		public ProgressBar mLoadPb;

		public WrapperHolder(View itemView) {
			super(itemView);
			mLoadPb = (ProgressBar) itemView.findViewById(R.id.item_load_pb);
			mLoadTv = (TextView) itemView.findViewById(R.id.item_load_tv);
		}
	}
}
