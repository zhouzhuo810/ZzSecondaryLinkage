package me.zhouzhuo.zzsecondarylinkage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import me.zhouzhuo.zzsecondarylinkage.bean.BaseMenuBean;
import me.zhouzhuo.zzsecondarylinkage.viewholder.BaseListViewHolder;

/**
 * Created by zz on 2016/8/19.
 */
public abstract class LeftMenuBaseListAdapter<T extends BaseListViewHolder, K extends BaseMenuBean> extends BaseAdapter {

    private LayoutInflater inflater;
    protected List<K> list;

    public LeftMenuBaseListAdapter(Context ctx, List<K> list) {
        inflater = LayoutInflater.from(ctx);
        this.list = list;
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
    public Object getItem(int i) {
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
            bindView((T) holder, view);
            view.setTag(holder);
        } else {
            holder = (T) view.getTag();
        }
        if (list.get(i).isSelected()) {
            holder.rootView.setBackgroundResource(getIndicatorResId());
        } else {
            holder.rootView.setBackgroundResource(android.R.color.transparent);
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

    /**
     * return drawable ResId of left menu indicator.
     *
     * @return drawable ResId
     */
    public abstract int getIndicatorResId();


    public void setSelection(int i) {
        for (K data : list) {
            data.setSelected(false);
        }
        list.get(i).setSelected(true);
        notifyDataSetChanged();
    }
}
