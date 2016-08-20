# ZzSecondaryLinkage
A Secondary Linkage ListView with refreshing and loading more.

Gradle

```
compile 'me.zhouzhuo.zzsecondarylinkage:zz-secondary-linkage:1.0.0'
```

Maven

```
<dependency>
  <groupId>me.zhouzhuo.zzsecondarylinkage</groupId>
  <artifactId>zz-secondary-linkage</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

**What does it look like ?**

![效果图](https://github.com/zhouzhuo810/ZzSecondaryLinkage/blob/master/zzsecondarylinkage.gif)

**How to use it ?**

***准备工作***

①第一步，添加布局

```xml
    <me.zhouzhuo.zzsecondarylinkage.ZzSecondaryLinkage
        android:id="@+id/zz_linkage"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

②定义没有数据时，显示的布局(可选)

例如：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <TextView
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:gravity="center"
        android:text="当前没有数据哟！" />
</LinearLayout>
```

③给左侧菜单的Java Bean继承BaseMenuBean

```java
    //for left listView, java bean must extends BaseMenuBean
    public static class LeftMenuEntity extends BaseMenuBean {}
```

④分别给左右两别的ListView添加ViewHolder, 且继承BaseListViewHolder。

```java
public class LeftListViewHolder extends BaseListViewHolder {
    public TextView tvMacName;
    public TextView tvMacId;
}
```

```java
public class RightListViewHolder extends BaseListViewHolder {
    public ImageView ivPic;
    public TextView tvProductName;
    public TextView tvMacName;
    public TextView tvTaskNum;
    public TextView tvStartTime;
    public TextView tvTaskId;
}

```


⑤定义左右两个ListView的Adapter，必须继承`LeftMenuBaseListAdapter<T extends BaseListViewHolder, K extends BaseMenuBean>`

```java
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
```

```java
public class RightContentListAdapter extends RightMenuBaseListAdapter<RightListViewHolder, TaskListEntity.LeftMenuEntity.TaskListDataEntity> {

    public RightContentListAdapter(Context ctx, List<TaskListEntity.LeftMenuEntity.TaskListDataEntity> list) {
        super(ctx, list);
    }

    @Override
    public RightListViewHolder getViewHolder() {
        return new RightListViewHolder();
    }

    @Override
    public void bindView(RightListViewHolder rightListViewHolder, View itemView) {
        ViewUtil.scaleContentView((ViewGroup) itemView.findViewById(R.id.root));
        rightListViewHolder.ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
        rightListViewHolder.tvProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
        rightListViewHolder.tvMacName = (TextView) itemView.findViewById(R.id.mac_name);
        rightListViewHolder.tvTaskNum = (TextView) itemView.findViewById(R.id.tv_task_number);
        rightListViewHolder.tvTaskId = (TextView) itemView.findViewById(R.id.tv_task_id);
        rightListViewHolder.tvStartTime = (TextView) itemView.findViewById(R.id.tv_start_time);
    }

    @Override
    public void bindData(RightListViewHolder rightListViewHolder, int position) {
        Glide.with(context).load(Constants.BASE_URL + getItem(position).getPicUrl()).into(rightListViewHolder.ivPic);
        rightListViewHolder.tvProductName.setText(getItem(position).getProductName());
        rightListViewHolder.tvMacName.setText(getItem(position).getMachineName());
        rightListViewHolder.tvTaskNum.setText(getItem(position).getTaskNo());
        rightListViewHolder.tvTaskId.setText(getItem(position).getTaskId());
        rightListViewHolder.tvStartTime.setText(getItem(position).getStartTime());
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_content;
    }
}

```

***开始使用***

⑥最后

```java
	//定义全局变量
    private List<TaskListEntity.LeftMenuEntity> entities;
    private ZzSecondaryLinkage zzSecondaryLinkage;
		
		//findView
        zzSecondaryLinkage = (ZzSecondaryLinkage) findViewById(R.id.zz_linkage);

		//初始化左边ListView的Adapter
        entities = new ArrayList<>();
        LeftMenuListAdapter leftAdapter = new LeftMenuListAdapter(this, entities);
        zzSecondaryLinkage.setLeftMenuAdapter(leftAdapter);

		//初始化右边ListView的Adapter
        List<TaskListEntity.LeftMenuEntity.TaskListDataEntity> rightEntity = new ArrayList<>();
        final RightContentListAdapter rightAdapter = new RightContentListAdapter(this, rightEntity);
        zzSecondaryLinkage.setRightContentAdapter(rightAdapter);
		
		//设置左右两个ListView的点击监听
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

		//设置右边ListView的下拉刷新监听
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

		//设置右边ListView的上拉加载监听
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

        //从网络获取数据
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
	                    //填充并刷新数据
                        entities = taskListEntity.getData();
                        zzSecondaryLinkage.updateData(entities);
                    }
                });
```
