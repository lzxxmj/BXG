package com.bxg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bxg.R;
import com.bxg.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    //声明变量
    private TextView tv_main_title;//标题
    private TextView tv_back;//返回按钮
    private Button btn_login;//登录按钮
    private EditText et_user_name, et_psw;//用户名，密码

    private RelativeLayout rl_title_bar;//标题栏
    private TextView tv_register,tv_find_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        //初始化
        tv_main_title = (TextView) this.findViewById(R.id.tv_main_title);
        tv_back = (TextView) this.findViewById(R.id.tv_back);
        tv_find_psw = (TextView) this.findViewById(R.id.tv_find_psw);
        tv_register = (TextView) this.findViewById(R.id.tv_register);
        btn_login = (Button) this.findViewById(R.id.btn_login);
        et_user_name = (EditText) this.findViewById(R.id.et_user_name);
        et_psw = (EditText) this.findViewById(R.id.et_psw);

        rl_title_bar = (RelativeLayout) this.findViewById(R.id.title_bar);

        //标题栏背景改为透明的
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //文字改为注册
        tv_main_title.setText("登录");
        //返回按钮
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });

        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent =new Intent(LoginActivity.this,FindPswActivity.class);
                startActivity(intent);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=et_user_name.getText().toString().trim();
                String psw=et_psw.getText().toString().trim();
                String sppsw=readPsw(username);//sp里边的加密后的密码
                String md5psw= MD5Utils.md5(psw);//用户输入的密码进行加密

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(sppsw.equals(md5psw)){//登录成功
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    saveLoginStatus(true,username);//保存登录的用户名
                    Intent intent =new Intent();
                    intent.putExtra("isLogin",true);
                    setResult(RESULT_OK,intent);
                    LoginActivity.this.finish();
                    return;
                }else if(!TextUtils.isEmpty(sppsw)&&!sppsw.equals(md5psw)){//密码不正确
                    Toast.makeText(LoginActivity.this,"密码不正确",Toast.LENGTH_SHORT).show();
                    return;
                }else {//用户名不存在
                    Toast.makeText(LoginActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    private void saveLoginStatus(boolean status,String username) {
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",status);
        editor.putString("loginUserName",username);
        editor.commit();
    }

    private String readPsw(String username) {
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(username,"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String username=data.getStringExtra("username");
        if(!TextUtils.isEmpty(username)){
            et_user_name.setText(username);
            et_user_name.setSelection(username.length());
        }
    }
}
