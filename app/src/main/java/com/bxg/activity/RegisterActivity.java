package com.bxg.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bxg.R;
import com.bxg.utils.MD5Utils;


public class RegisterActivity extends AppCompatActivity {
    //声明变量
    private TextView tv_main_title;//标题
    private TextView tv_back;//返回按钮
    private Button btn_register;//注册按钮
    private EditText et_user_name,et_psw,et_psw_again;//用户名，密码，确认
    private String username,psw,pswAgain;//获取值
    private RelativeLayout rl_title_bar;//标题栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        //初始化
        tv_main_title=(TextView)this.findViewById(R.id.tv_main_title);
        tv_back=(TextView)this.findViewById(R.id.tv_back);
        btn_register=(Button)this.findViewById(R.id.btn_register);
        et_user_name=(EditText)this.findViewById(R.id.et_user_name);
        et_psw=(EditText)this.findViewById(R.id.et_psw);

        et_psw_again=(EditText)this.findViewById(R.id.et_psw_again);
        rl_title_bar=(RelativeLayout)this.findViewById(R.id.title_bar);

        //标题栏背景改为透明的
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //文字改为注册
        tv_main_title.setText("注册");
        //返回按钮
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });

        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this,"密码确认不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExistUserName(username)){
                    Toast.makeText(RegisterActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //允许用户注册
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //保存用户名和密码
                    saveRegisterInfo(username,psw);
                    //返回到登录界面
                    Intent intent =new Intent();
                    intent.putExtra("username",username);
                    setResult(RESULT_OK,intent);
                    RegisterActivity.this.finish();
                }

            }
        });

    }

    private void saveRegisterInfo(String username, String psw) {
        //sp
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        String mdtpsw= MD5Utils.md5(psw);
        //存储加密后的用户名和密码
        //保存的key是用户名的值，键是密码
        editor.putString(username,mdtpsw);

        editor.commit();
    }

    private boolean isExistUserName(String username) {
        //判断用户名是不是已经存在
        boolean has_username=false;
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw=sp.getString(username,"");
        if(!TextUtils.isEmpty(spPsw)){
            has_username=true;
        }
        return has_username;
    }

    private void getEditString() {
        username=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();

    }
}
