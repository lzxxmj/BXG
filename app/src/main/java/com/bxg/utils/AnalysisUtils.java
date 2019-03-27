package com.bxg.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AnalysisUtils {

    public static String readLoginUserName(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        return sharedPreferences.getString("loginUserName","");
    }
}
