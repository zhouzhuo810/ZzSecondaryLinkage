package me.zhouzhuo.zzsecondarylinkagedemo.api;


import me.zhouzhuo.zzsecondarylinkagedemo.bean.TaskListEntity;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Joe on 16/5/18.
 */
public interface ApiService {

    @GET("/files/zz_secondary_linkage.php")
    Observable<TaskListEntity> getTaskListData();

}
