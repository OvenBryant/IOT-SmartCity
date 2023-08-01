package com.example.smartcity.activity.warninfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcity.R;
import com.example.smartcity.activity.gardening.GardeningActivity;

public class WarnInfoConfigureActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llUser, llPwd, llDeviceId, llFireApiTag, llSmokeApiTag, llPersonApiTag;
    TextView tvYunUser, tvYunPwd, tvYunDeviceId, tvYunFireApiTag, tvYunSmokeApiTag, tvYunPersonApiTag;
    SharedPreferences spf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_info_configure);

        InitView();
    }

    private void InitView() {
        llUser = findViewById(R.id.ll_user);
        llPwd = findViewById(R.id.ll_pwd);
        llDeviceId = findViewById(R.id.ll_deviceId);
        llFireApiTag = findViewById(R.id.ll_fire_apiTag);
        llSmokeApiTag = findViewById(R.id.ll_smoke_apiTag);
        llPersonApiTag = findViewById(R.id.ll_person_apiTag);
        llUser.setOnClickListener(this);
        llPwd.setOnClickListener(this);
        llDeviceId.setOnClickListener(this);
        llFireApiTag.setOnClickListener(this);
        llSmokeApiTag.setOnClickListener(this);
        llPersonApiTag.setOnClickListener(this);

        tvYunUser = findViewById(R.id.tv_yun_user);
        tvYunPwd = findViewById(R.id.tv_yun_pwd);
        tvYunDeviceId = findViewById(R.id.tv_yun_deviceId);
        tvYunFireApiTag = findViewById(R.id.tv_yun_fire_apiTag);
        tvYunSmokeApiTag = findViewById(R.id.tv_yun_smoke_apiTag);
        tvYunPersonApiTag = findViewById(R.id.tv_yun_person_apiTag);

        spf = getSharedPreferences("WarnInfoDate", MODE_PRIVATE);
        String user = spf.getString("user", "");
        String pwd = spf.getString("pwd", "");
        String deviceId = spf.getString("deviceId", "");
        String fire_apiTag = spf.getString("fire_apiTag", "");
        String smoke_apiTag = spf.getString("smoke_apiTag", "");
        String person_apiTag = spf.getString("person_apiTag", "");

        tvYunUser.setText(user);
        tvYunPwd.setText(pwd);
        tvYunDeviceId.setText(deviceId);
        tvYunFireApiTag.setText(fire_apiTag);
        tvYunSmokeApiTag.setText(smoke_apiTag);
        tvYunPersonApiTag.setText(person_apiTag);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//            builder.setTitle("请点击确定按钮完成配置!");
//            builder.setMessage("请点击确定按钮,不明白吗?");
//            builder.show();
//
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    public void btn_configure(View view) {
        Intent intent = new Intent(WarnInfoConfigureActivity.this, WarnInfoActivity.class);
        intent.putExtra("flag",true);
        startActivity(intent);
        new WarnInfoActivity().count=2;
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_user:
                AlertDialog.Builder builder_user = new AlertDialog.Builder(this);
                builder_user.setTitle("请设置的你的账号: ");
                builder_user.setCancelable(false);
                EditText edit_user = new EditText(this);
                builder_user.setView(edit_user);
                builder_user.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_user.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        tvYunUser.setText(edit_user.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("user",edit_user.getText().toString().trim());
                        spf_edit.apply();
                        //Toast.makeText(WarnInfoConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_user.show();
                break;
            case R.id.ll_pwd:
                AlertDialog.Builder builder_pwd = new AlertDialog.Builder(this);
                builder_pwd.setTitle("请设置的你的密码: ");
                builder_pwd.setCancelable(false);
                EditText edit_pwd = new EditText(this);
                builder_pwd.setView(edit_pwd);
                builder_pwd.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_pwd.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunPwd.setText(edit_pwd.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("pwd",edit_pwd.getText().toString().trim());
                        spf_edit.apply();
                        //Toast.makeText(WarnInfoConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_pwd.show();
                break;
            case R.id.ll_deviceId:
                AlertDialog.Builder builder_deviceID = new AlertDialog.Builder(this);
                builder_deviceID.setTitle("请设置的你的设备ID: ");
                builder_deviceID.setCancelable(false);
                EditText edit_deviceID = new EditText(this);
                builder_deviceID.setView(edit_deviceID);
                builder_deviceID.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_deviceID.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunDeviceId.setText(edit_deviceID.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("deviceId",edit_deviceID.getText().toString().trim());
                        spf_edit.apply();
                        //Toast.makeText(WarnInfoConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_deviceID.show();
                break;
            case R.id.ll_fire_apiTag:
                AlertDialog.Builder builder_fire_apiTag = new AlertDialog.Builder(this);
                builder_fire_apiTag.setTitle("请设置的你的火焰标识名: ");
                builder_fire_apiTag.setCancelable(false);
                EditText edit_fire_apiTag = new EditText(this);
                builder_fire_apiTag.setView(edit_fire_apiTag);
                builder_fire_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_fire_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunFireApiTag.setText(edit_fire_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("fire_apiTag",edit_fire_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        //Toast.makeText(WarnInfoConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_fire_apiTag.show();
                break;
            case R.id.ll_smoke_apiTag:
                AlertDialog.Builder builder_smoke_apiTag = new AlertDialog.Builder(this);
                builder_smoke_apiTag.setTitle("请设置的你的烟雾标识名: ");
                builder_smoke_apiTag.setCancelable(false);
                EditText edit_smoke_apiTag = new EditText(this);
                builder_smoke_apiTag.setView(edit_smoke_apiTag);
                builder_smoke_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_smoke_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunSmokeApiTag.setText(edit_smoke_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("smoke_apiTag",edit_smoke_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        //Toast.makeText(WarnInfoConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_smoke_apiTag.show();
                break;
            case R.id.ll_person_apiTag:
                AlertDialog.Builder builder_person_apiTag = new AlertDialog.Builder(this);
                builder_person_apiTag.setTitle("请设置的你的人体标识名: ");
                builder_person_apiTag.setCancelable(false);
                EditText edit_person_apiTag = new EditText(this);
                builder_person_apiTag.setView(edit_person_apiTag);
                builder_person_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_person_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunPersonApiTag.setText(edit_person_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("person_apiTag",edit_person_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        //Toast.makeText(WarnInfoConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_person_apiTag.show();
                break;
        }
    }
}

















