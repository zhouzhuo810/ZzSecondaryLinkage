package me.zhouzhuo.zzsecondarylinkage;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import me.zhouzhuo.zzsecondarylinkage.adapter.LeftMenuBaseListAdapter;
import me.zhouzhuo.zzsecondarylinkage.adapter.RightMenuBaseListAdapter;
import me.zhouzhuo.zzsecondarylinkage.bean.BaseMenuBean;
import me.zhouzhuo.zzsecondarylinkage.model.ILinkage;
import me.zhouzhuo.zzsecondarylinkage.widget.ZzRefreshLayout;

/**
 * Created by zz on 2016/8/19.
 */
public class ZzSecondaryLinkage<T extends BaseMenuBean> extends LinearLayout implements ILinkage<T> {

    private View rootView;
    private RelativeLayout customNoDataView;
    private ListView lvLeft;
    private ListView lvRight;
    private LeftMenuBaseListAdapter leftAdapter;
    private RightMenuBaseListAdapter rightAdapter;

    private List<T> list;
    private OnItemClickListener mItemClickListener;
    private OnRefreshListener mRefreshListener;
    private OnLoadMoreListener mLoadMoreListener;

    private ZzRefreshLayout refreshLayout;

    public ZzSecondaryLinkage(Context context) {
        super(context);
        init(context, null);
    }

    public ZzSecondaryLinkage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZzSecondaryLinkage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.zz_secondary_linkage, null);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(rootView);

        customNoDataView = (RelativeLayout) rootView.findViewById(R.id.custom_no_data_view);

        refreshLayout = (ZzRefreshLayout) rootView.findViewById(R.id.refresh_layout);

        lvLeft = (ListView) rootView.findViewById(R.id.left_lv);
        lvRight = (ListView) rootView.findViewById(R.id.right_lv);

        setItemClickListener();
        setRefreshListener();

        if (attrs == null) {

        } else {

        }
    }

    private void setRefreshListener() {
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.footer_load_more, null);
        refreshLayout.setListViewAndFooter(lvRight, footer);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });

        refreshLayout.setOnLoadListener(new ZzRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                if (mLoadMoreListener != null && !refreshLayout.isRefreshing()) {
                    refreshLayout.setEnabled(false);
                    mLoadMoreListener.onLoad();
                }
            }
        });

    }

    @Override
    public void stopLoadMore() {
        refreshLayout.setEnabled(true);
        refreshLayout.setLoading(false);
    }

    @Override
    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setLeftMenuAdapter(LeftMenuBaseListAdapter adapter) {
        leftAdapter = adapter;
        if (leftAdapter != null)
            lvLeft.setAdapter(leftAdapter);
    }

    @Override
    public void setRightContentAdapter(RightMenuBaseListAdapter adapter) {
        rightAdapter = adapter;
        if (rightAdapter != null)
            lvRight.setAdapter(rightAdapter);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void updateData(List<T> list) {
        this.list = list;
        if (leftAdapter != null) {
            leftAdapter.setList(this.list);
            if (this.list != null && this.list.size() > 0) {
                leftAdapter.setSelection(0);
                if (mItemClickListener != null) {
                    mItemClickListener.onLeftClick(null, 0);
                }
            }
        }
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void setCustomNoDataView(View view) {
        if (view != null) {
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));
            this.customNoDataView.removeAllViews();
            this.customNoDataView.addView(view);
        }
    }

    @Override
    public void setCustomNoDataViewWithLayoutId(int layoutId) {
        if (layoutId > 0) {
            View view = LayoutInflater.from(getContext()).inflate(layoutId, null);
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));
            this.customNoDataView.removeAllViews();
            this.customNoDataView.addView(view);
        }
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    private void setItemClickListener() {
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (leftAdapter != null) {
                    leftAdapter.setSelection(i);
                }
                if (mItemClickListener != null) {
                    mItemClickListener.onLeftClick(view, i);
                }
            }
        });
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mItemClickListener != null && i < rightAdapter.getCount()) {
                    mItemClickListener.onRightClick(view, i);
                }
            }
        });
    }


}
