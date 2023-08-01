//package com.example.smartcity.activity.smartsupermarket;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.smartcity.R;
//
//public class SmartVideoConfigureActivity extends AppCompatActivity {
//
//    private EditText etUser,etPwd,etId,etChannel;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smart_video_configure);
//
//        InitView();
//    }
//
//    private void InitView() {
//        etUser = findViewById(R.id.et_user);
//        etPwd = findViewById(R.id.et_pwd);
//        etId = findViewById(R.id.et_id);
//        etChannel = findViewById(R.id.et_channel);
//
//        SharedPreferences camera = getSharedPreferences("Camera", MODE_PRIVATE);
//        etUser.setText(camera.getString("user",""));
//        etPwd.setText(camera.getString("pwd",""));
//        etId.setText(camera.getString("id",""));
//        etChannel.setText(camera.getString("channel",""));
//
//    }
//
//    public void btn_sure(View view) {
//        String user = etUser.getText().toString().trim();
//        String pwd = etPwd.getText().toString().trim();
//        String id = etId.getText().toString().trim();
//        String channel = etChannel.getText().toString().trim();
//
//        if(TextUtils.isEmpty(user)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(id)||TextUtils.isEmpty(channel))
//        {
//            Toast.makeText(this, "请认真填写配置参数!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        SharedPreferences camera = getSharedPreferences("Camera", MODE_PRIVATE);
//        SharedPreferences.Editor edit = camera.edit();
//        edit.putString("user",user);
//        edit.putString("pwd",pwd);
//        edit.putString("id",id);
//        edit.putString("channel",channel);
//        edit.putBoolean("flag",true);
//        edit.apply();
//
//        Intent intent = new Intent(this,SmartVideoActivity.class);
//        startActivity(intent);
//        this.finish();
//    }
//}