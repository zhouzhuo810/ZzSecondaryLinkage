package me.zhouzhuo.zzsecondarylinkage.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.zhouzhuo.zzsecondarylinkage.R;


/**
 * Created by zz on 2016/6/23.
 */
public class ZzRefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;

    private OnLoadListener mListener;

    private ListView mListView;

    private View mListViewFooter;

    private int mYDown;

    private int mLastY;

    private boolean isLoading = false;

    public ZzRefreshLayout(Context context) {
        super(context);
    }

    public ZzRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setListViewAndFooter(ListView lv, View footer) {
        this.mListView = lv;
        this.mListViewFooter = footer;
        mListView.addFooterView(mListViewFooter);
    }

    public void setOnLoadListener(OnLoadListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                float disY = mLastY - event.getRawY();
                if (disY > mTouchSlop) {
                    if (canLoad()) {
                        loadData();
                    }
                }
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoad() {
        return isBottom() && !isLoading && !isRefreshing();
    }

    private boolean isBottom() {

        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }

        return false;
    }

    public void setFooterVisible(int visible) {
        mListViewFooter.setVisibility(visible);
    }

    public void removeFooter() {
        if (mListViewFooter != null) {
            mListViewFooter.setVisibility(GONE);
        }
    }

    public void addFooter() {
        if (mListViewFooter != null) {
            mListViewFooter.setVisibility(VISIBLE);
        }
    }

    private boolean isPullUp() {
        Log.i("distance", (mYDown - mLastY) + "");
        return (mYDown - mLastY) >= mTouchSlop;
    }

    private void loadData() {
        if (mListener != null) {
            setLoading(true);
            mListener.onLoad();
        }
    }

    public void setLoadEnable(boolean enable) {
        isLoading = enable;
    }


    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        if (!refreshing) {
            stopLoad();
        }
    }

    /**
     * just like setRefreshing, toggle loading animation
     *
     * @param loading true/false
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            startLoad();
        } else {
            stopLoad();
            mYDown = 0;
            mLastY = 0;
        }
    }

    private void stopLoad() {
        if (mListViewFooter != null) {
            ProgressBar pb = (ProgressBar) mListViewFooter.findViewById(R.id.list_item_load_more_pb);
            TextView tv = (TextView) mListViewFooter.findViewById(R.id.list_item_load_more_tv);
            pb.setVisibility(GONE);
            tv.setText("上拉加载更多");
        }
    }

    private void startLoad() {
        if (mListViewFooter != null) {
            ProgressBar pb = (ProgressBar) mListViewFooter.findViewById(R.id.list_item_load_more_pb);
            TextView tv = (TextView) mListViewFooter.findViewById(R.id.list_item_load_more_tv);
            pb.setVisibility(VISIBLE);
            tv.setText("玩命加载中...");
        }
    }

    public void noNeedLoad() {
        isLoading = false;
        if (mListViewFooter != null) {
            ProgressBar pb = (ProgressBar) mListViewFooter.findViewById(R.id.list_item_load_more_pb);
            TextView tv = (TextView) mListViewFooter.findViewById(R.id.list_item_load_more_tv);
            pb.setVisibility(GONE);
            tv.setText("已经是全部数据了");
        }

    }

    public interface OnLoadListener {
        void onLoad();
    }
}
