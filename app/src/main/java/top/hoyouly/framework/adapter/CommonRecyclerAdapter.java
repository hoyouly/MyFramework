package top.hoyouly.framework.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hoyouly on 18/3/31.
 */

public  class CommonRecyclerAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ObservableArrayList<M> items;
    private ListChangedCallback itemsChangedCallback;
    private int layoutResId;
    private int variableId;

    public interface  OnRecyclerViewItemClickListener<M>{
        void OnItemClick(View view, M m);
    }

    private OnRecyclerViewItemClickListener listener;

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public CommonRecyclerAdapter(Context context, int layoutResId, int variableId) {
        this.context = context;
        items = new ObservableArrayList<M>(); // extends ArrayList<T> implements ObservableList<T>
        itemsChangedCallback = new ListChangedCallback();
        this.layoutResId=layoutResId;
        this.variableId=variableId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), getLayoutResId(viewType), parent, false);
        binding.getRoot().setOnClickListener(this);
        return new CommonRecyclerViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(variableId,items.get(position));
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ObservableArrayList<M> getItems() {
        return items;
    }


    protected  int getLayoutResId(int viewType){
        return layoutResId;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        items.addOnListChangedCallback(itemsChangedCallback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        items.removeOnListChangedCallback(itemsChangedCallback);
    }

    protected void onChanged(ObservableArrayList<M> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeChanged(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onItemRangeInserted(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onItemRangeMoved(ObservableArrayList<M> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeRemoved(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    private void resetItems(ObservableArrayList<M> newItems) {
        this.items = newItems;
    }

    class ListChangedCallback extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<M>> {

        @Override
        public void onChanged(ObservableArrayList<M> ms) {
            CommonRecyclerAdapter.this.onChanged(items);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<M> ms, int i, int i1) {
            CommonRecyclerAdapter.this.onItemRangeChanged(ms,i,i1);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<M> ms, int i, int i1) {
            CommonRecyclerAdapter.this.onItemRangeInserted(ms, i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<M> ms, int i, int i1, int i2) {
            CommonRecyclerAdapter.this.onItemRangeMoved(ms);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<M> ms, int i, int i1) {
            CommonRecyclerAdapter.this.onItemRangeRemoved(ms,i,i1);
        }
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.OnItemClick(v, (M) v.getTag());
        }
    }

}
