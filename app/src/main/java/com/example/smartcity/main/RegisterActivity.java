package com.example.smartcity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.R;

public class RegisterActivity extends AppCompatActivity {

    public static final int RESULT_CODE_REGISTER = 0;
    private EditText etAccount,etPassword,etSurePassword;
    private Button btnRegister;
    private CheckBox cbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().setTitle("注册");

        etAccount = findViewById(R.id.et_account);
        etPassword =findViewById(R.id.et_password);
        etSurePassword = findViewById(R.id.et_sure_password);
        btnRegister = findViewById(R.id.btn_register);
        cbAgree = findViewById(R.id.cb_agree);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                String sure_password = etSurePassword.getText().toString();

                if(TextUtils.isEmpty(username))
                {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(RegisterActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.equals(password,sure_password))
                {
                    Toast.makeText(RegisterActivity.this, "密码不一致!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!cbAgree.isChecked())
                {
                    Toast.makeText(RegisterActivity.this, "请勾选用户协议!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 存储注册的用户名和密码
                SharedPreferences spf = getSharedPreferences("spfRecord", MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                edit.putString("account",username);  // 获取注册成功的用户名
                edit.putString("password",password); // 获取注册成功的密码
                edit.apply();

                // 数据回传
                Intent intent = new Intent();
                intent.putExtra("account",username);
                intent.putExtra("password",password);
                setResult(RESULT_CODE_REGISTER,intent);

                Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                // 结束当前页面
                RegisterActivity.this.finish();  // 因为我们是从登陆界面跳转过来的,所以我们只需要结束当前页面，就相当于回到登录页面了
            }
        });
    }
}