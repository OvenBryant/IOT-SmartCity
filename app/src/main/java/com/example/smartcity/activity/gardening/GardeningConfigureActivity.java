package com.example.smartcity.activity.gardening;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.R;
import com.example.smartcity.activity.warninfo.WarnInfoActivity;

import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class GardeningConfigureActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llGardeningUser, llGardeningPwd, llGardeningDeviceId, llGardeningTemp,
            llGardeningHumi, llGardeningLight, llGardeningPm, llGardeningKeFire, llGardeningFire,
            llGardeningM_Lamp, llGardeningM_Fan, llGardeningM_AlarmLight;
    TextView tvYunGardeningUser, tvYunGardeningPwd, tvYunGardeningDeviceId, tvYunGardeningTempApiTag,
            tvYunGardeningHumiApiTag, tvYunGardeningLightApiTag, tvYunGardeningPmApiTag, tvYunGardeningKeFireApiTag,
            tvYunGardeningFireApiTag, tvYunGardeningM_LampApiTag, tvYunGardeningM_FanApiTag, tvYunGardeningM_AlarmLightApiTag;
    SharedPreferences spf;

    String user, pwd;

    private String accessToken = "";
    private String httpApi = "http://api.nlecloud.com:80";
    NetWorkBusiness netWork;
    boolean NetSuccess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardening_configure);

        InitView();
    }

    private void InitView() {
        llGardeningUser = findViewById(R.id.ll_gardening_user);
        llGardeningPwd = findViewById(R.id.ll_gardening_pwd);
        llGardeningDeviceId = findViewById(R.id.ll_gardening_deviceId);
        llGardeningTemp = findViewById(R.id.ll_gardening_temp_apiTag);
        llGardeningHumi = findViewById(R.id.ll_gardening_humi_apiTag);
        llGardeningLight = findViewById(R.id.ll_gardening_light_apiTag);
        llGardeningPm = findViewById(R.id.ll_gardening_pm_apiTag);
        llGardeningKeFire = findViewById(R.id.ll_gardening_ke_fire_apiTag);
        llGardeningFire = findViewById(R.id.ll_gardening_fire_apiTag);
        llGardeningM_Lamp = findViewById(R.id.ll_gardening_m_lamp_apiTag);
        llGardeningM_Fan = findViewById(R.id.ll_gardening_m_fan_apiTag);
        llGardeningM_AlarmLight = findViewById(R.id.ll_gardening_m_alarmlight_apiTag);

        llGardeningUser.setOnClickListener(this);
        llGardeningPwd.setOnClickListener(this);
        llGardeningDeviceId.setOnClickListener(this);
        llGardeningTemp.setOnClickListener(this);
        llGardeningHumi.setOnClickListener(this);
        llGardeningLight.setOnClickListener(this);
        llGardeningPm.setOnClickListener(this);
        llGardeningKeFire.setOnClickListener(this);
        llGardeningFire.setOnClickListener(this);
        llGardeningM_Lamp.setOnClickListener(this);
        llGardeningM_Fan.setOnClickListener(this);
        llGardeningM_AlarmLight.setOnClickListener(this);

        tvYunGardeningUser = findViewById(R.id.tv_yun_gardening_user);
        tvYunGardeningPwd = findViewById(R.id.tv_yun_gardening_pwd);
        tvYunGardeningDeviceId = findViewById(R.id.tv_yun_gardening_deviceId);
        tvYunGardeningTempApiTag = findViewById(R.id.tv_yun_gardening_temp_apiTag);
        tvYunGardeningHumiApiTag = findViewById(R.id.tv_yun_gardening_humi_apiTag);
        tvYunGardeningLightApiTag = findViewById(R.id.tv_yun_gardening_light_apiTag);
        tvYunGardeningPmApiTag = findViewById(R.id.tv_yun_gardening_pm_apiTag);
        tvYunGardeningKeFireApiTag = findViewById(R.id.tv_yun_gardening_ke_fire_apiTag);
        tvYunGardeningFireApiTag = findViewById(R.id.tv_yun_gardening_fire_apiTag);
        tvYunGardeningM_LampApiTag = findViewById(R.id.tv_yun_gardening_m_lamp_apiTag);
        tvYunGardeningM_FanApiTag = findViewById(R.id.tv_yun_gardening_m_fan_apiTag);
        tvYunGardeningM_AlarmLightApiTag = findViewById(R.id.tv_yun_gardening_alarmlight_apiTag);

        spf = getSharedPreferences("GardeningInfoData", MODE_PRIVATE);
        user = spf.getString("user", "");
        pwd = spf.getString("pwd", "");
        String deviceId = spf.getString("deviceId", "");
        String temp_apiTag = spf.getString("temp_apiTag", "");
        String humi_apiTag = spf.getString("humi_apiTag", "");
        String light_apiTag = spf.getString("light_apiTag", "");
        String pm_apiTag = spf.getString("pm_apiTag", "");
        String ke_fire_apiTag = spf.getString("ke_fire_apiTag", "");
        String fire_apiTag = spf.getString("fire_apiTag", "");
        String m_lamp_apiTag = spf.getString("m_lamp_apiTag", "");
        String m_fan_apiTag = spf.getString("m_fan_apiTag", "");
        String m_alarmlight_apiTag = spf.getString("m_alarmlight_apiTag", "");
        // flag

        tvYunGardeningUser.setText(user);
        tvYunGardeningPwd.setText(pwd);
        tvYunGardeningDeviceId.setText(deviceId);
        tvYunGardeningTempApiTag.setText(temp_apiTag);
        tvYunGardeningHumiApiTag.setText(humi_apiTag);
        tvYunGardeningLightApiTag.setText(light_apiTag);
        tvYunGardeningPmApiTag.setText(pm_apiTag);
        tvYunGardeningKeFireApiTag.setText(ke_fire_apiTag);
        tvYunGardeningFireApiTag.setText(fire_apiTag);
        tvYunGardeningM_LampApiTag.setText(m_lamp_apiTag);
        tvYunGardeningM_FanApiTag.setText(m_fan_apiTag);
        tvYunGardeningM_AlarmLightApiTag.setText(m_alarmlight_apiTag);
    }


    @Override
    protected void onPause() {
        super.onPause();
        spf = getSharedPreferences("GardeningInfoData", MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putBoolean("_hint", false);
        edit.apply();
        Toast.makeText(this, "执行了false", Toast.LENGTH_SHORT).show();
    }

    /**
     * 确定按钮
     * @param view
     */
    public void btn_gardening_configure(View view) {
        /**
         * 登录云平台获取对应的数据
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetNetworkSensorData();
            }
        }).start();
    }

    /**
     * 登录云平台获取对应的数据
     */
    private void GetNetworkSensorData() {

        spf = getSharedPreferences("GardeningInfoData", MODE_PRIVATE);
        user = spf.getString("user", "");
        pwd = spf.getString("pwd", "");

        NetWorkBusiness net = new NetWorkBusiness(accessToken, httpApi);
        net.signIn(new SignIn(user, pwd), new NCallBack<BaseResponseEntity<User>>(getApplicationContext()) {
            @Override
            protected void onResponse(BaseResponseEntity<User> response) {
                if (response.getStatus() == 0) {
                    accessToken = response.getResultObj().getAccessToken();
                    if (!TextUtils.isEmpty(accessToken)) // 登录成功
                    {
                        netWork = new NetWorkBusiness(accessToken, httpApi);
                        NetSuccess = true;

                        String deviceId = tvYunGardeningDeviceId.getText().toString().trim(); // 设备ID不能为空,否则报【服务器异常】
                        if (!TextUtils.isEmpty(deviceId)) {
                            Intent intent = new Intent(GardeningConfigureActivity.this, GardeningActivity.class);
                            intent.putExtra("NetSuccess", NetSuccess);
                            intent.putExtra("accessToken", accessToken);
                            startActivity(intent);
                            finish();
                            new GardeningActivity().count=2;
                            overridePendingTransition(0, 0);
                        } else {
                            Toast.makeText(GardeningConfigureActivity.this, "设备ID不能为空", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(GardeningConfigureActivity.this, "请核对您云平台的账号和密码!不会弄,请联系开发者!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_gardening_user:
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

                        tvYunGardeningUser.setText(edit_user.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("user", edit_user.getText().toString().trim());
                        spf_edit.putBoolean("flag", true);
                        spf_edit.apply();
                        //Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_user.show();
                break;
            case R.id.ll_gardening_pwd:
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
                        tvYunGardeningPwd.setText(edit_pwd.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("pwd", edit_pwd.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_pwd.show();
                break;
            case R.id.ll_gardening_deviceId:
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
                        tvYunGardeningDeviceId.setText(edit_deviceID.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("deviceId", edit_deviceID.getText().toString().trim());
                        spf_edit.apply();
                        //Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_deviceID.show();
                break;
            case R.id.ll_gardening_temp_apiTag:
                AlertDialog.Builder builder_temp_apiTag = new AlertDialog.Builder(this);
                builder_temp_apiTag.setTitle("请设置的你的温度标识名: ");
                builder_temp_apiTag.setCancelable(false);
                EditText edit_temp_apiTag = new EditText(this);
                builder_temp_apiTag.setView(edit_temp_apiTag);
                builder_temp_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_temp_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunGardeningTempApiTag.setText(edit_temp_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("temp_apiTag", edit_temp_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_temp_apiTag.show();
                break;
            case R.id.ll_gardening_humi_apiTag:
                AlertDialog.Builder builder_humi_apiTag = new AlertDialog.Builder(this);
                builder_humi_apiTag.setTitle("请设置的你的湿度标识名: ");
                builder_humi_apiTag.setCancelable(false);
                EditText edit_humi_apiTag = new EditText(this);
                builder_humi_apiTag.setView(edit_humi_apiTag);
                builder_humi_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_humi_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunGardeningHumiApiTag.setText(edit_humi_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("humi_apiTag", edit_humi_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_humi_apiTag.show();
                break;
            case R.id.ll_gardening_light_apiTag:
                AlertDialog.Builder builder_light_apiTag = new AlertDialog.Builder(this);
                builder_light_apiTag.setTitle("请设置的你的光照标识名: ");
                builder_light_apiTag.setCancelable(false);
                EditText edit_light_apiTag = new EditText(this);
                builder_light_apiTag.setView(edit_light_apiTag);
                builder_light_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_light_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunGardeningLightApiTag.setText(edit_light_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("light_apiTag", edit_light_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        //Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_light_apiTag.show();
                break;
            case R.id.ll_gardening_pm_apiTag:
                AlertDialog.Builder builder_pm_apiTag = new AlertDialog.Builder(this);
                builder_pm_apiTag.setTitle("请设置的你的空气质量标识名: ");
                builder_pm_apiTag.setCancelable(false);
                EditText edit_pm_apiTag = new EditText(this);
                builder_pm_apiTag.setView(edit_pm_apiTag);
                builder_pm_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_pm_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunGardeningPmApiTag.setText(edit_pm_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("pm_apiTag", edit_pm_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_pm_apiTag.show();
                break;
            case R.id.ll_gardening_ke_fire_apiTag:
                AlertDialog.Builder builder_ke_fire_apiTag = new AlertDialog.Builder(this);
                builder_ke_fire_apiTag.setTitle("请设置的你的可燃气体标识名: ");
                builder_ke_fire_apiTag.setCancelable(false);
                EditText edit_ke_fire_apiTag = new EditText(this);
                builder_ke_fire_apiTag.setView(edit_ke_fire_apiTag);
                builder_ke_fire_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_ke_fire_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunGardeningKeFireApiTag.setText(edit_ke_fire_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("ke_fire_apiTag", edit_ke_fire_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_ke_fire_apiTag.show();
                break;
            case R.id.ll_gardening_fire_apiTag:
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
                        tvYunGardeningFireApiTag.setText(edit_fire_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("fire_apiTag", edit_fire_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_fire_apiTag.show();
                break;
            case R.id.ll_gardening_m_lamp_apiTag:
                AlertDialog.Builder builder_m_lamp_apiTag = new AlertDialog.Builder(this);
                builder_m_lamp_apiTag.setTitle("请设置的你的灯光标识名: ");
                builder_m_lamp_apiTag.setCancelable(false);
                EditText edit_m_lamp_apiTag = new EditText(this);
                builder_m_lamp_apiTag.setView(edit_m_lamp_apiTag);
                builder_m_lamp_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_m_lamp_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunGardeningM_LampApiTag.setText(edit_m_lamp_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("m_lamp_apiTag", edit_m_lamp_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_m_lamp_apiTag.show();
                break;
            case R.id.ll_gardening_m_fan_apiTag:
                AlertDialog.Builder builder_m_fan_apiTag = new AlertDialog.Builder(this);
                builder_m_fan_apiTag.setTitle("请设置的你的风扇标识名: ");
                builder_m_fan_apiTag.setCancelable(false);
                EditText edit_m_fan_apiTag = new EditText(this);
                builder_m_fan_apiTag.setView(edit_m_fan_apiTag);
                builder_m_fan_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_m_fan_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunGardeningM_FanApiTag.setText(edit_m_fan_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("m_fan_apiTag", edit_m_fan_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_m_fan_apiTag.show();
                break;
            case R.id.ll_gardening_m_alarmlight_apiTag:
                AlertDialog.Builder builder_m_alarmlight_apiTag = new AlertDialog.Builder(this);
                builder_m_alarmlight_apiTag.setTitle("请设置的你的报警灯标识名: ");
                builder_m_alarmlight_apiTag.setCancelable(false);
                EditText edit_m_alarmlight_apiTag = new EditText(this);
                builder_m_alarmlight_apiTag.setView(edit_m_alarmlight_apiTag);
                builder_m_alarmlight_apiTag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder_m_alarmlight_apiTag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvYunGardeningM_AlarmLightApiTag.setText(edit_m_alarmlight_apiTag.getText().toString());
                        SharedPreferences.Editor spf_edit = spf.edit();
                        spf_edit.putString("m_alarmlight_apiTag", edit_m_alarmlight_apiTag.getText().toString().trim());
                        spf_edit.apply();
                        // Toast.makeText(GardeningConfigureActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                builder_m_alarmlight_apiTag.show();
                break;

        }
    }
}

















