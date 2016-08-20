package me.zhouzhuo.zzsecondarylinkagedemo.utils;

import android.util.Log;

import me.zhouzhuo.zzsecondarylinkagedemo.constants.Constants;

/**
 * Created by Joe on 2016/3/11.
 */
public class LogUtils {

    public static void e(String s){
        if(Constants.IS_DEBUG){
            Log.e("ZzSecondaryLinkage",s);
        }
    }
    public static void d(String s){
        if(Constants.IS_DEBUG){
            Log.d("ZzSecondaryLinkage",s);
        }
    }

    public static void api(String s){
        if(Constants.IS_DEBUG){
            Log.d("ZzSecondaryLinkage",s);
        }
    }
}
