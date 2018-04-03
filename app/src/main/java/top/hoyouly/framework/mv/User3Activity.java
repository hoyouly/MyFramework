package top.hoyouly.framework.mv;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import top.hoyouly.framework.BR;
import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.User3;
import top.hoyouly.framework.databinding.ActivityUserBinding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class User3Activity extends BaseBindingActivity<ActivityUserBinding> {
	private int index = 0;

	private ItemAdapter mItemAdapter;
	private ArrayList<User3> mItems;
	@Override
	protected void initView() {
		initData();
	}

	private void initData() {
		mItems = new ArrayList();
		for (int i = 0; i < 1; i++) {
			User3 u = new User3();
			u.setId(index + "");
			u.setName("zhangphil @" + index);
			u.setBlog("blog.csdn.net/zhangphil @" + index);
			mItems.add(u);
			index++;
		}

		RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

		mItemAdapter = new ItemAdapter();
		mRecyclerView.setAdapter(mItemAdapter);

		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				User3 u = new User3();
				u.setId(index + "");
				u.setName("zhangphil @" + index);
				u.setBlog("blog.csdn.net/zhangphil @" + index);

				mItems.add(u);
				mItemAdapter.notifyDataSetChanged();

				index++;
			}
		});
	}
	@Override
	protected int getLayouId() {
		return R.layout.recycler_view_layout;
	}

	private class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

		@Override
		public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item, viewGroup, false);
			ItemViewHolder holder = new ItemViewHolder(binding);
			return holder;
		}

		@Override
		public void onBindViewHolder(ItemViewHolder viewHolder, int i) {
			viewHolder.getBinding().setVariable(BR.user3, mItems.get(i));
			viewHolder.getBinding().executePendingBindings();
		}

		@Override
		public int getItemCount() {
			return mItems.size();
		}
	}

	private class ItemViewHolder extends RecyclerView.ViewHolder {
		private ViewDataBinding binding;

		public ItemViewHolder(ViewDataBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		public void setBinding(ViewDataBinding binding) {
			this.binding = binding;
		}

		public ViewDataBinding getBinding() {
			return this.binding;
		}
	}

}
