package me.zhouzhuo.zzsecondarylinkage.model;

import android.view.View;

import java.util.List;

import me.zhouzhuo.zzsecondarylinkage.adapter.LeftMenuBaseListAdapter;
import me.zhouzhuo.zzsecondarylinkage.adapter.RightMenuBaseListAdapter;
import me.zhouzhuo.zzsecondarylinkage.bean.BaseMenuBean;

/**
 * Created by zz on 2016/8/19.
 */
public interface ILinkage<T extends BaseMenuBean> {

    /**
     * Adapter for left ListView
     *
     * @param adapter #
     */
    void setLeftMenuAdapter(LeftMenuBaseListAdapter adapter);

    /**
     * Adapter for right ListView
     *
     * @param adapter #
     */
    void setRightContentAdapter(RightMenuBaseListAdapter adapter);

    /**
     * set data for left listView
     *
     * @param list #
     */
    void updateData(List<T> list);

    /**
     * set item click listenr for left and right ListView
     *
     * @param listener #
     */
    void setOnItemClickListener(OnItemClickListener listener);

    /**
     * the view showing when there's no data.
     *
     * @param view #
     */
    void setCustomNoDataView(View view);

    /**
     * the view layout id showing when there's no data.
     *
     * @param layoutId #
     */
    void setCustomNoDataViewWithLayoutId(int layoutId);

    /**
     * set refresh listener for right ListView
     *
     * @param listener #
     */
    void setOnRefreshListener(OnRefreshListener listener);

    /**
     * set load listener for right ListView
     *
     * @param listener #
     */
    void setOnLoadMoreListener(OnLoadMoreListener listener);

    /**
     * stop load more
     */
    void stopLoadMore();

    /**
     * stop refresh
     */
    void stopRefresh();

    interface OnRefreshListener {
        void onRefresh();
    }

    interface OnLoadMoreListener {
        void onLoad();
    }

    interface OnItemClickListener {
        void onLeftClick(View itemView, int position);

        void onRightClick(View itemView, int position);
    }

}
