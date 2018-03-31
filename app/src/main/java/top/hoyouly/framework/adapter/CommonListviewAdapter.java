package top.hoyouly.framework.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by hoyouly on 18-3-30.
 */

public class CommonListviewAdapter<T> extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private int layoutId; //layoutId这个表示item布局的资源id
	private int variableId;//variableId是系统自动生成的，根据我们的实体类，直接从外部传入即可
	private List<T> list;

	public CommonListviewAdapter(Context context, int layoutId, List<T> list, int resId) {
		this.context = context;
		this.layoutId = layoutId;
		this.list = list;
		this.variableId = resId;
		inflater = LayoutInflater.from(context);
	}

	public void setList(List<T> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewDataBinding dataBinding;
		if (convertView == null) {
			dataBinding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
		} else {
			dataBinding = DataBindingUtil.getBinding(convertView);
		}
		dataBinding.setVariable(variableId, list.get(position));

		return dataBinding.getRoot();
	}
}
