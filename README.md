# ZzSecondaryLinkage
A Secondary Linkage ListView with refreshing and loading more.

```
compile 'me.zhouzhuo.zzsecondarylinkage:zz-secondary-linkage:1.0.0'
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

***开始使用***

⑤最后

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

