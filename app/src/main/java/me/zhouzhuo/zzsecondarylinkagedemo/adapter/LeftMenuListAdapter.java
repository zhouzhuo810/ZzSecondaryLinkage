package me.zhouzhuo.zzsecondarylinkagedemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.zhouzhuo.zzsecondarylinkage.adapter.LeftMenuBaseListAdapter;
import me.zhouzhuo.zzsecondarylinkagedemo.R;
import me.zhouzhuo.zzsecondarylinkagedemo.bean.TaskListEntity;
import me.zhouzhuo.zzsecondarylinkagedemo.utils.ViewUtil;
import me.zhouzhuo.zzsecondarylinkagedemo.viewholder.LeftListViewHolder;

/**
 * Created by zz on 2016/8/19.
 */
public class LeftMenuListAdapter extends LeftMenuBaseListAdapter<LeftListViewHolder, TaskListEntity.LeftMenuEntity> {

    public LeftMenuListAdapter(Context ctx, List<TaskListEntity.LeftMenuEntity> list) {
        super(ctx, list);
    }

    @Override
    public LeftListViewHolder getViewHolder() {
        return new LeftListViewHolder();
    }

    @Override
    public void bindView(LeftListViewHolder leftListViewHolder, View itemView) {
        ViewUtil.scaleContentView((ViewGroup) itemView.findViewById(R.id.root));
        leftListViewHolder.tvMacName = (TextView) itemView.findViewById(R.id.tv_menu);
        leftListViewHolder.tvMacId = (TextView) itemView.findViewById(R.id.tv_id);

    }

    @Override
    public void bindData(LeftListViewHolder leftListViewHolder, int position) {
        leftListViewHolder.tvMacName.setText(list.get(position).getMacName());
        leftListViewHolder.tvMacId.setText(list.get(position).getMacId());
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_menu;
    }

    //9-patch drawable
    @Override
    public int getIndicatorResId() {
        return R.drawable.list_select;
    }
}
