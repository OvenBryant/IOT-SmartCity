package com.example.smartcity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_REGISTER = 1;
    private Button btnLogin;
    private EditText etAccount, etPassword;
    private CheckBox cbRemember, cbAutoLogin;
    private String user = "admin";
    private String pwd = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initData();


        /**
         *  勾选自动登录的时候,一定勾选记住密码
         *  取消记住密码的时候,一定会取消自动登录
         */
        cbAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbRemember.setChecked(true);
                }
            }
        });

        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbAutoLogin.setChecked(false);
                }
            }
        });
    }

    private void init() {

        btnLogin = findViewById(R.id.btn_login);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        cbRemember = findViewById(R.id.cb_remember);
        cbAutoLogin = findViewById(R.id.cb_auto_login);

        btnLogin.setOnClickListener(this);
    }


    private void initData() {
        SharedPreferences spf = getSharedPreferences("spfRecord", MODE_PRIVATE);
        String account = spf.getString("account", "");
        String password = spf.getString("password", "");
        boolean isRemember = spf.getBoolean("isRemember", false);
        boolean isLogin = spf.getBoolean("isLogin", false);

        user = account;
        pwd = password;

        if(isLogin)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("account", user);
            startActivity(intent);
            LoginActivity.this.finish(); // 结束自己
        }
        if (isRemember) {
            etAccount.setText(account);
            etPassword.setText(password);
            cbRemember.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入用户名或者密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(account, user)) {
            if (TextUtils.equals(pwd, password)) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                if (cbRemember.isChecked()) // 勾选了记住密码
                {
                    SharedPreferences spf = getSharedPreferences("spfRecord", MODE_PRIVATE);
                    SharedPreferences.Editor edit = spf.edit();
                    edit.putString("account", user);
                    edit.putString("password", pwd);
                    edit.putBoolean("isRemember", true);
                    if (cbAutoLogin.isChecked())  // 自动登录被勾选
                    {
                        edit.putBoolean("isLogin", true);
                    } else {
                        edit.putBoolean("isLogin", false);
                    }
                    edit.apply(); // 确定,提交
                } else // 没有勾选记住密码
                {
                    SharedPreferences spf = getSharedPreferences("spfRecord", MODE_PRIVATE);
                    SharedPreferences.Editor edit = spf.edit();
                    edit.putBoolean("isRemember", false);
                    edit.apply(); // 确定,提交
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("account", user);
                startActivity(intent);
                LoginActivity.this.finish(); // 结束自己

            } else {
                Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "账号错误", Toast.LENGTH_SHORT).show();
        }
    }


    /*
    点击 没有账号？ 去注册界面,并且把注册好的账号和密码传回来,自动填充登录时所需要的账号和密码框
     */
    public void to_register(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    /**
     * 注册成功,返回的账号和密码,下次登录的时候就用注册后的账号和密码
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    /**
     * 回传函数
     * @param requestCode
     * @param resultCode
     * @param data
     */

    // 回传函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER && resultCode ==RegisterActivity.RESULT_CODE_REGISTER && data != null) {
//            Intent intent = getIntent();
//            String account = intent.getStringExtra("account");
//            String password = intent.getStringExtra("password");

            // 获取回传数据
            String account = data.getStringExtra("account");
            String password = data.getStringExtra("password");

            etAccount.setText(account);
            etPassword.setText(password);

            user = account;
            pwd = password;

        }
    }
}