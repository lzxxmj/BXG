package com.bxg.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bxg.R;
import com.bxg.utils.AnalysisUtils;
import com.bxg.utils.MD5Utils;

public class FindPswActivity extends AppCompatActivity {
    private EditText et_validate_name,et_user_name;
    private TextView tv_main_title,tv_back;
    private TextView tv_reset_psw,tv_user_name;
    private Button btn_validate;
    private String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        from=getIntent().getStringExtra("from");
        init();
    }

    private void init() {
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_back=findViewById(R.id.tv_back);
        et_user_name=findViewById(R.id.et_user_name);
        et_validate_name=findViewById(R.id.et_validate_name);
        tv_reset_psw=findViewById(R.id.tv_reset_psw);
        tv_user_name=findViewById(R.id.tv_user_name);
        btn_validate=findViewById(R.id.btn_validate);

        if("security".equals(from)){
             tv_main_title.setText("设置密保");
        }else{
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }

       tv_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FindPswActivity.this.finish();
           }
       });

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validateName=et_validate_name.getText().toString().trim();
                if("security".equals(from)){
                    if(TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Toast.makeText(FindPswActivity.this,"密保设置成功",Toast.LENGTH_SHORT).show();
                        saveSecurity(validateName);
                        FindPswActivity.this.finish();
                        return;
                    }

                }else{
                    String userName=et_user_name.getText().toString().trim();
                    String sp_security=readSecurity(userName);
                    if(TextUtils.isEmpty(userName)){
                        Toast.makeText(FindPswActivity.this,"请输入您的用户名",Toast.LENGTH_SHORT).show(); return;
                    }else if(!isExistUserName(userName)){
                        Toast.makeText(FindPswActivity.this,"您输入的用户名不存在",Toast.LENGTH_SHORT).show(); return;
                    }else  if(TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_SHORT).show(); return;
                    }else if(!validateName.equals(sp_security)){
                        Toast.makeText(FindPswActivity.this,"输入的密保不正确",Toast.LENGTH_SHORT).show(); return;
                    }else {
                        tv_reset_psw.setVisibility(View.VISIBLE);
                        tv_reset_psw.setText("初始密码123456");
                        savePsw(userName);
                        return;
                    }


                }
            }
        });
    }

    private void savePsw(String userName) {
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String key= AnalysisUtils.readLoginUserName(this);
        editor.putString(userName, MD5Utils.md5("123456"));
        editor.commit();
    }

    private String readSecurity(String userName) {
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sharedPreferences.getString(userName+"_security","");

    }

    private void saveSecurity(String validateName) {//保存用户的密保
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String key= AnalysisUtils.readLoginUserName(this);
        editor.putString(key+"_security",validateName);
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
}
