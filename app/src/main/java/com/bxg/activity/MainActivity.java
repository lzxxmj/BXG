package com.bxg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bxg.R;
import com.bxg.view.MyInfoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //非公有的非静态成员变量m
    //公有的非静态
    //静态的变量s
    //SSSS
private TextView tv_back;
private TextView tv_main_title;
private RelativeLayout rl_title_bar;
private FrameLayout mBodyLayout;
private LinearLayout mBottomLayout;
private View mCourseBtn,mExercisesBtn,mMyInfoBtn;
private TextView tv_course,tv_exercises,tv_myInfo;
private ImageView iv_course,iv_exercises,iv_myInfo;
    MyInfoView myInfoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Main","main ---------onCreate");

        init();//初始化标题栏和中间视图
        initBottomBar();//初始化底部导航栏
        setListener();//设计底部导航栏点击事件
        setInitStaturs();//设计初始化状态
    }

    private void init() {
        tv_main_title = (TextView) this.findViewById(R.id.tv_main_title);
        tv_back = (TextView) this.findViewById(R.id.tv_back);
        rl_title_bar = (RelativeLayout) this.findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4ff"));
        //文字改为
        tv_main_title.setText("博学谷课程");
        tv_back.setVisibility(View.GONE);//返回按钮消失
        mBodyLayout=this.findViewById(R.id.main_body);
    }


    private void initBottomBar() { mBottomLayout=this.findViewById(R.id.main_bottom_bar);
       mCourseBtn=this.findViewById(R.id.bottom_bar_course_btn);
       mExercisesBtn=this.findViewById(R.id.bottom_bar_exercises_btn);
       mMyInfoBtn=this.findViewById(R.id.bottom_bar_myinfo_btn);
       tv_course=this.findViewById(R.id.bottom_bar_text_course);
       tv_exercises=this.findViewById(R.id.bottom_bar_text_exercises);
       tv_myInfo=this.findViewById(R.id.bottom_bar_text_myinfo);
       iv_course=this.findViewById(R.id.bottom_bar_image_course);
       iv_exercises=this.findViewById(R.id.bottom_bar_image_exercises);
       iv_myInfo=this.findViewById(R.id.bottom_bar_image_myinfo);

    }

    private void setListener() {
        for(int i=0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setOnClickListener(MainActivity.this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_bar_course_btn:
                //清除底部导航栏所有状态
                clearBottomImageState();
                //设置底部导航栏状态
                setSelectedStatus(0);
                //清除中间视图
                removeAllViews();
                //设置中间视图
                createView(0);
                break;
            case R.id.bottom_bar_exercises_btn:
                clearBottomImageState();
                //设置底部导航栏状态
                setSelectedStatus(1);
                removeAllViews();
                //设置中间视图
                createView(1);
                break;
            case R.id.bottom_bar_myinfo_btn:
                clearBottomImageState();
                //设置底部导航栏状态
                setSelectedStatus(2);
                removeAllViews();
                //设置中间视图
                createView(2);
                break;
        }

    }



    private void clearBottomImageState() { //清除底部导航栏所有状态
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercises.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
        iv_myInfo.setImageResource(R.drawable.main_my_icon);
    }

    private void setSelectedStatus(int index) {//设置导航栏状态
        switch (index){
            case 0:
                iv_course.setImageResource(R.drawable.main_course_icon_selected);
                tv_course.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷课程");
                break;
            case 1:
                iv_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                tv_exercises.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷习题");
                break;
            case 2:
                iv_myInfo.setImageResource(R.drawable.main_my_icon_selected);
                tv_myInfo.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.GONE);


                break;
        }
    }

    //清除中间视图
    private void removeAllViews() {
        for(int i=0;i<mBodyLayout.getChildCount();i++){
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }
    //创建中间视图
    private void createView(int i) {
        switch (i){
            case 0:
                // TODO: 2019/3/18 创建课程视图
                break;
            case 1:
                // TODO: 2019/3/18 创建习题视图
                break;
            case 2:
                Log.i("Main","main"+"myInfoView");
                if(myInfoView==null){
                    myInfoView=new MyInfoView(this);
                    View view=myInfoView.getView();
                    mBodyLayout.addView(view);
                }else{
                   myInfoView.getView();
                }
                myInfoView.showView();
                break;

        }
    }

    //设置初始状态
    private void setInitStaturs() {
        clearBottomImageState();
        //设置底部导航栏状态
        setSelectedStatus(0);
        //设置中间视图
        createView(0);
    }


    //两次点击返回键则退出应用程序
   protected long exitTime;//第一次点击的时间



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("boxuegu",exitTime+"");
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-exitTime>2000){
                Log.i("boxuegu",System.currentTimeMillis()+"");
                Toast.makeText(MainActivity.this,"再按一次返回键则退出",Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();
            }else{
                MainActivity.this.finish();
                //把用户的登录信息清除掉
                if(readLoginStatus()){
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    //判断用户是否登录
    private boolean readLoginStatus() {
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }
    //清除用户登录信息
    private void clearLoginStatus() {
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isLogin = false;
        //从设置界面或者登陆界面回来的数据
        if(data!=null){
            isLogin=data.getBooleanExtra("isLogin",false);
            Log.i("Main","aaaa"+isLogin);
            if(isLogin){
                //跳转到课程界面
                //清除底部导航栏所有状态
                clearBottomImageState();
                //设置底部导航栏状态
                setSelectedStatus(0);
                //清除中间视图
                removeAllViews();
                //设置中间视图
                createView(0);
                Log.i("Main","bbbbb");
            }
            if(myInfoView!=null){
                myInfoView.setLoginParams(isLogin);
                Log.i("Main","cccccc");
            }
        }


    }
}
