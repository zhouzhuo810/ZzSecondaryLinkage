package me.zhouzhuo.zzsecondarylinkagedemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo.zzsecondarylinkage.ZzSecondaryLinkage;
import me.zhouzhuo.zzsecondarylinkage.model.ILinkage;
import me.zhouzhuo.zzsecondarylinkagedemo.adapter.LeftMenuListAdapter;
import me.zhouzhuo.zzsecondarylinkagedemo.adapter.RightContentListAdapter;
import me.zhouzhuo.zzsecondarylinkagedemo.api.Api;
import me.zhouzhuo.zzsecondarylinkagedemo.bean.TaskListEntity;
import me.zhouzhuo.zzsecondarylinkagedemo.utils.LogUtils;
import me.zhouzhuo.zzsecondarylinkagedemo.utils.ViewUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private List<TaskListEntity.LeftMenuEntity> entities;
    private ZzSecondaryLinkage zzSecondaryLinkage;

    @SuppressWarnings({"unchecked"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtil.scaleContentView((ViewGroup) findViewById(R.id.act_main_root_layout));

        zzSecondaryLinkage = (ZzSecondaryLinkage) findViewById(R.id.zz_linkage);

        entities = new ArrayList<>();
        LeftMenuListAdapter leftAdapter = new LeftMenuListAdapter(this, entities);
        zzSecondaryLinkage.setLeftMenuAdapter(leftAdapter);

        List<TaskListEntity.LeftMenuEntity.TaskListDataEntity> rightEntity = new ArrayList<>();
        final RightContentListAdapter rightAdapter = new RightContentListAdapter(this, rightEntity);
        zzSecondaryLinkage.setRightContentAdapter(rightAdapter);

        zzSecondaryLinkage.setOnItemClickListener(new ILinkage.OnItemClickListener() {
            @Override
            public void onLeftClick(View itemView, int position) {
                Toast.makeText(MainActivity.this, "left," + position, Toast.LENGTH_SHORT).show();
                rightAdapter.setList(entities.get(position).getTaskList());
            }

            @Override
            public void onRightClick(View itemView, int position) {
                Toast.makeText(MainActivity.this, "right," + position, Toast.LENGTH_SHORT).show();
            }
        });

        zzSecondaryLinkage.setOnRefreshListener(new ILinkage.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        zzSecondaryLinkage.stopRefresh();

                    }
                }.execute();
            }
        });

        zzSecondaryLinkage.setOnLoadMoreListener(new ILinkage.OnLoadMoreListener() {
            @Override
            public void onLoad() {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        zzSecondaryLinkage.stopLoadMore();

                    }
                }.execute();
            }
        });

        //get data from network
        Api.getInstance().mApiService.getTaskListData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<TaskListEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(TaskListEntity taskListEntity) {
                        entities = taskListEntity.getData();
                        zzSecondaryLinkage.updateData(entities);
                    }
                });


    }

}
