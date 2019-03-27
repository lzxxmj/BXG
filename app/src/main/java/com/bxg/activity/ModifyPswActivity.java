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
import com.bxg.utils.AnalysisUtils;
import com.bxg.utils.MD5Utils;

public class ModifyPswActivity extends AppCompatActivity {
    //声明变量
    private TextView tv_main_title;//标题
    private TextView tv_back;//返回按钮
    private RelativeLayout rl_title_bar;//标题栏

    private Button btn_save;//保存按钮
    private EditText et_orginal_psw,et_psw,et_psw_again;//原始密码，密码，确认
    private String orignalPsw,psw,pswAgain;//获取值

   private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        init();
        userName= AnalysisUtils.readLoginUserName(this);
    }

    private void init() {
        //初始化
        tv_main_title=(TextView)this.findViewById(R.id.tv_main_title);
        tv_back=(TextView)this.findViewById(R.id.tv_back);
        btn_save=(Button)this.findViewById(R.id.btn_save);
        et_orginal_psw=(EditText)this.findViewById(R.id.et_orignal_psw);
        et_psw=(EditText)this.findViewById(R.id.et_psw);
        et_psw_again=(EditText)this.findViewById(R.id.et_psw_again);
        rl_title_bar=(RelativeLayout)this.findViewById(R.id.title_bar);

        //标题栏背景改为透明的
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //文字改为注册
        tv_main_title.setText("修改密码");
        //返回按钮
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPswActivity.this.finish();
            }
        });

        //按钮
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if(TextUtils.isEmpty(orignalPsw)){
                    Toast.makeText(ModifyPswActivity.this,"原始密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!MD5Utils.md5(orignalPsw).equals(readPsw())){
                    Toast.makeText(ModifyPswActivity.this,"原始密码不正确",Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(ModifyPswActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(ModifyPswActivity.this,"密码确认不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)) {
                    Toast.makeText(ModifyPswActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //允许用户修改
                    Toast.makeText(ModifyPswActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    //保存用户名和密码
                    saveModifyPswInfo(psw);
                    //登录界面
                    Intent intent =new Intent(ModifyPswActivity.this,LoginActivity.class);
                   startActivity(intent);
                    ModifyPswActivity.this.finish();
                    SettingActivity.instance.finish();
                }

            }
        });

    }

    private String readPsw() {
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(userName,"");
    }

    private void saveModifyPswInfo(String psw) {
        //sp
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        String mdtpsw= MD5Utils.md5(psw);
        //存储加密后的用户名和密码
        //保存的key是用户名的值，键是密码
        editor.putString(userName,mdtpsw);

        editor.commit();
    }


    private void getEditString() {
        orignalPsw=et_orginal_psw.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();

    }
}
