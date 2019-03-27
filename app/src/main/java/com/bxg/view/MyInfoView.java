package com.bxg.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bxg.R;
import com.bxg.activity.LoginActivity;
import com.bxg.activity.SettingActivity;
import com.bxg.utils.AnalysisUtils;

public class MyInfoView {
    //public static MyInfoView myInfoViewInstance;
    //获取视图并显示出来
    public ImageView iv_head_icon;
    private LinearLayout ll_head;
    private RelativeLayout rl_course_history,rl_setting;
    private TextView tv_user_name;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;


    //初始化成员变量
    public MyInfoView(Activity context){
        mContext=context;
        mInflater=LayoutInflater.from(mContext);//布局解析器
    }

    public void createView(){

        initView();


    }

    private void initView() {

        Log.i("Main","initView");
        //创建视图
        mCurrentView=mInflater.inflate(R.layout.main_view_myinfo,null);
        ll_head=mCurrentView.findViewById(R.id.ll_head);
        iv_head_icon=mCurrentView.findViewById(R.id.iv_head_icon);
        rl_course_history=(RelativeLayout)mCurrentView.findViewById(R.id.r1_course_history);
        rl_setting=(RelativeLayout)mCurrentView.findViewById(R.id.r1_setting);
        tv_user_name=mCurrentView.findViewById(R.id.tv_user_name);
        mCurrentView.setVisibility(View.VISIBLE);

        setLoginParams(readLoginStatus());
        //历史记录
        rl_course_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(readLoginStatus()){//跳转到历史记录

                   }else{
                       Toast.makeText(mContext,"您还没有登录，请先登录",Toast.LENGTH_SHORT).show();
                   }
            }
        });
        //设置
        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){//跳转到设置
                     Intent intent=new Intent(mContext, SettingActivity.class);
                     mContext.startActivityForResult(intent,1);
                }else{
                    Toast.makeText(mContext,"您还没有登录，请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){
                    //跳转到详情
                }else{
                   Intent intent=new Intent(mContext, LoginActivity.class);
                   mContext.startActivityForResult(intent,1);
                }
            }
        });


    }

    public void setLoginParams(boolean b) {
        if(b){//显示用户名
           tv_user_name.setText(AnalysisUtils.readLoginUserName(mContext));
           Log.i("Main"," xianshiyonghuming");
        }else{//显示立即登录
           tv_user_name.setText("请登录");
            Log.i("Main"," denlgu");
        }
    }

    private boolean readLoginStatus() {
        SharedPreferences sp=mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin",false);

    }


    public View getView(){//返回一个视图
        if(mCurrentView==null){
            createView();
        }
        return mCurrentView;

    }

    public void showView(){//显示视图
        if(mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);


    }


}
