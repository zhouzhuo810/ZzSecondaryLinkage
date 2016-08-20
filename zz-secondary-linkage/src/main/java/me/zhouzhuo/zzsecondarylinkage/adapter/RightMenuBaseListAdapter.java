package me.zhouzhuo.zzsecondarylinkage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import me.zhouzhuo.zzsecondarylinkage.viewholder.BaseListViewHolder;

/**
 * Created by zz on 2016/8/19.
 */
public abstract class RightMenuBaseListAdapter<T extends BaseListViewHolder, K> extends BaseAdapter {
    private LayoutInflater inflater;
    protected List<K> list;
    protected Context context;

    public RightMenuBaseListAdapter(Context ctx, List<K> list) {
        inflater = LayoutInflater.from(ctx);
        this.list = list;
        this.context = ctx;
    }

    public void setList(List<K> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public K getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        T holder = null;
        if (view == null) {
            holder = getViewHolder();
            view = inflater.inflate(getLayoutId(), viewGroup, false);
            holder.rootView = view;
            bindView(holder, view);
            view.setTag(holder);
        } else {
            holder = (T) view.getTag();
        }
        bindData(holder, i);
        return view;
    }

    public abstract T getViewHolder();

    /**
     * do findViewById.
     *
     * @param t        viewHolder
     * @param itemView itemView
     */
    public abstract void bindView(T t, View itemView);

    /**
     * do setData.
     *
     * @param t        viewHolder
     * @param position position
     */
    public abstract void bindData(T t, int position);

    /**
     * return the layoutId of ListView item.
     *
     * @return layoutId
     */
    public abstract int getLayoutId();

}
