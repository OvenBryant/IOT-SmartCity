package com.example.smartcity.activity.gardening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcity.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.newland.nle_sdk.responseEntity.SensorInfo;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class GardeningActivity extends AppCompatActivity {

    String user, pwd, tempApiTag, humiApiTag, LightApiTag, PmApiTag, KeFireApiTag, FireApiTag;
    String deviceId, m_lamp_apiTag, m_fan_apiTag, m_alarmlight_apiTag;
    private int tempProgress = 30;        //默认值
    private int lightProgress = 3000;        //默认值
    private double Main_temp, Main_light;

    TextView tvCurrrentDate;
    TextView tvGardeningMainThresholdTemp, tvGardeningMainThresholdTempCmds;
    TextView tvGardeningMainThresholdLight, tvGardeningMainThresholdLightCmds;

    TextView tvDisplayTemp, tvDisplayHumi, tvDisplayLight, tvDisplayPM, tvDisplayKeFire, tvDisplayFire;

    String temp_apiTag, humi_apiTag, light_apiTag, pm_apiTag, ke_fire_apiTag, fire_apiTag;

    ImageView imgsFan, imgsLamp, imgsAlarm;
    private String httpApi = "http://api.nlecloud.com:80";
    NetWorkBusiness net;

    AnimationDrawable animation;
    int m = 0, n = 0; // 风扇标志
    int x = 0, y = 0; // 灯泡标志
    public static int count = 1;

    /**
     * 更新UI
     */
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<SensorInfo> list = (List<SensorInfo>) msg.obj;
            for (SensorInfo info : list) {
                if (TextUtils.equals(info.getApiTag(), temp_apiTag))  // 温度
                {
                    double temp = Double.valueOf(info.getValue());
                    n++;
                    m = 0;
                    if (temp >= Main_temp) {   // 这里有个小bug就是一直会执行这里
                        // 开启风扇
                        if (n < 2) {
                            tvGardeningMainThresholdTempCmds.setText("开启风扇");
                            net.control(deviceId, m_fan_apiTag, 1, new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                @Override
                                protected void onResponse(BaseResponseEntity response) {
                                    animation.run();
                                }
                            });
                        }

                    } else {
                        n = 0;
                        m++;
                        if (m < 2) {
                            tvGardeningMainThresholdTempCmds.setText("关闭风扇");
                            net.control(deviceId, m_fan_apiTag, 0, new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                @Override
                                protected void onResponse(BaseResponseEntity response) {
                                    animation.stop();
                                }
                            });
                            // 关闭风扇
                        }

                    }
                    tvDisplayTemp.setText(info.getValue() + "℃");
                } else if (TextUtils.equals(info.getApiTag(), humi_apiTag))  // 湿度
                {
                    tvDisplayHumi.setText(info.getValue() + "%RH");
                } else if (TextUtils.equals(info.getApiTag(), light_apiTag))  //光照
                {
                    double light = Double.valueOf(info.getValue());
                    x++;
                    y = 0;
                    if (light >= Main_light) {
                        if (x < 2) {
                            tvGardeningMainThresholdLightCmds.setText("开启灯泡");
                            net.control(deviceId, light_apiTag, 1, new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                @Override
                                protected void onResponse(BaseResponseEntity response) {

                                }
                            });
                        }

                    } else {
                        y++;
                        x = 0;
                        if (y < 2) {
                            tvGardeningMainThresholdLightCmds.setText("关闭灯泡");
                            net.control(deviceId, light_apiTag, 0, new NCallBack<BaseResponseEntity>(getApplicationContext()) {
                                @Override
                                protected void onResponse(BaseResponseEntity response) {

                                }
                            });
                        }

                    }
                    tvDisplayLight.setText(info.getValue() + "Lx");
                } else if (TextUtils.equals(info.getApiTag(), pm_apiTag))  //空气质量
                {
                    tvDisplayPM.setText(info.getValue() + "ug/m3");

                } else if (TextUtils.equals(info.getApiTag(), ke_fire_apiTag))  //可燃气体
                {
                    tvDisplayKeFire.setText(info.getValue() + "ppm");

                } else if (TextUtils.equals(info.getApiTag(), fire_apiTag))  //火焰
                {
                    String fire = Double.valueOf(info.getValue()) >= 1 ? "有火" : "无火";
                    tvDisplayFire.setText(fire);
                    if (TextUtils.equals(fire, "有火")) {
                        imgsAlarm.setImageResource(R.drawable.lamp_alarm_on);
                    } else {
                        imgsAlarm.setImageResource(R.drawable.lamp_alarm_off);
                    }
                }
            }
        }
    };


    /**
     * 没有执行
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SharedPreferences hint = getSharedPreferences("GardeningInfoData", MODE_PRIVATE);
//        SharedPreferences.Editor edit = hint.edit();
//        edit.putBoolean("_hint", true);
//        edit.apply();
        count = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardening);

        if (count == 1) {
            Toast.makeText(this, "如想要实现园艺效果,还请右上角配置!", Toast.LENGTH_SHORT).show();
        }


//        SharedPreferences hint = getSharedPreferences("GardeningInfoData", MODE_PRIVATE);
//        if (hint.getBoolean("_hint", true)) {
//            Toast.makeText(this, "如想要实现园艺效果,还请右上角配置!", Toast.LENGTH_SHORT).show();
//        }

        InitView();

        /**
         * 获取当前显示在界面上的温湿度值
         */
        Main_temp = Double.valueOf(tvGardeningMainThresholdTemp.getText().toString());
        Main_light = Double.valueOf(tvGardeningMainThresholdLight.getText().toString());

        Intent intent = getIntent();
        boolean NetSuccess = intent.getBooleanExtra("NetSuccess", false);
        String accessToken = intent.getStringExtra("accessToken");
        if (NetSuccess == true) // 登录成功
        {
            SharedPreferences spf = getSharedPreferences("GardeningInfoData", MODE_PRIVATE);
            String user = spf.getString("user", "");
            String pwd = spf.getString("pwd", "");
            deviceId = spf.getString("deviceId", "");
            temp_apiTag = spf.getString("temp_apiTag", "");
            humi_apiTag = spf.getString("humi_apiTag", "");
            light_apiTag = spf.getString("light_apiTag", "");
            pm_apiTag = spf.getString("pm_apiTag", "");
            ke_fire_apiTag = spf.getString("ke_fire_apiTag", "");
            fire_apiTag = spf.getString("fire_apiTag", "");
            m_lamp_apiTag = spf.getString("m_lamp_apiTag", "");
            m_fan_apiTag = spf.getString("m_fan_apiTag", "");
            m_alarmlight_apiTag = spf.getString("m_alarmlight_apiTag", "");

            net = new NetWorkBusiness(accessToken, httpApi);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        net.getSensors(deviceId, temp_apiTag + "," + humi_apiTag + "," + light_apiTag + "," + pm_apiTag + "," + ke_fire_apiTag + "," + fire_apiTag, new NCallBack<BaseResponseEntity<List<SensorInfo>>>(getApplicationContext()) {
                            @Override
                            protected void onResponse(BaseResponseEntity<List<SensorInfo>> response) {
                                List<SensorInfo> resultObj = response.getResultObj();
                                Message msg = Message.obtain();
                                msg.obj = resultObj;
                                handler.sendMessage(msg);
                            }
                        });


                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }


    private void InitView() {
        //tvCurrrentDate = findViewById(R.id.tv_current_date);
        tvGardeningMainThresholdTemp = findViewById(R.id.tv_gardening_main_threshold_temp);
        tvGardeningMainThresholdTempCmds = findViewById(R.id.tv_gardening_main_threshold_temp_cmds);
        tvGardeningMainThresholdLight = findViewById(R.id.tv_gardening_main_threshold_light);
        tvGardeningMainThresholdLightCmds = findViewById(R.id.tv_gardening_main_threshold_light_cmds);

        tvDisplayTemp = findViewById(R.id.tv_display_temp);
        tvDisplayHumi = findViewById(R.id.tv_display_humi);
        tvDisplayLight = findViewById(R.id.tv_display_light);
        tvDisplayPM = findViewById(R.id.tv_display_pm);
        tvDisplayKeFire = findViewById(R.id.tv_display_ke_fire);
        tvDisplayFire = findViewById(R.id.tv_display_fire);

        imgsFan = findViewById(R.id.imgs_fan);
        imgsLamp = findViewById(R.id.imgs_lamp);
        imgsAlarm = findViewById(R.id.imgs_alarm);

        /**
         * 加载动画
         */
        imgsFan.setBackgroundResource(R.drawable.fans_change);
        animation = (AnimationDrawable) imgsFan.getBackground();
        animation.setOneShot(false);

        SharedPreferences spf = getSharedPreferences("GardeningInfoData", MODE_PRIVATE);
        if (spf.getBoolean("flag", false)) {
            user = spf.getString("user", "admin");
            pwd = spf.getString("pwd", "admin");
            deviceId = spf.getString("deviceId", "id");
            tempApiTag = spf.getString("temp_apiTag", "1");
            humiApiTag = spf.getString("humi_apiTag", "1");
            LightApiTag = spf.getString("light_apiTag", "1");
            PmApiTag = spf.getString("pm_apiTag", "1");
            KeFireApiTag = spf.getString("ke_fire_apiTag", "1");
            FireApiTag = spf.getString("fire_apiTag", "1");
        }
        SharedPreferences gardeningThreshold = getSharedPreferences("GardeningThreshold", MODE_PRIVATE);
        if (gardeningThreshold.getBoolean("Threshold_flag", false)) {
            int _tempProgress = gardeningThreshold.getInt("tempProgress", 30);
            int _lightProgress = gardeningThreshold.getInt("lightProgress", 3000);
            tvGardeningMainThresholdTemp.setText(_tempProgress + "");
            tvGardeningMainThresholdLight.setText(_lightProgress + "");
        }


        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


//        /**
//         * 获取当前系统时间
//         */
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Date date = new Date(System.currentTimeMillis());
//                String currentDate = sdf.format(date);
//                tvCurrrentDate.setText(currentDate);
//            }
//        }, 0, 1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gardening_configure, menu);
        return true;
    }

    /**
     * 响应上下文菜单项
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.GardeningConfigure:
                Intent intent = new Intent(this, GardeningConfigureActivity.class);
                startActivity(intent);

                break;
            case R.id.GardeningThresholdSettings:
                Dialog dialog = new Dialog(GardeningActivity.this, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View dialogView = LayoutInflater.from(GardeningActivity.this).inflate(R.layout.dialog_setting_threshold, null);

                /**
                 * 阈值设置
                 */
                Button btnConfirm = dialogView.findViewById(R.id.btn_confirm);
                Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
                SeekBar sbTemp = dialogView.findViewById(R.id.sb_temp);
                SeekBar sbLight = dialogView.findViewById(R.id.sb_light);
                TextView tvTempValue = dialogView.findViewById(R.id.tv_tempValue);
                TextView tvLightValue = dialogView.findViewById(R.id.tv_lightValue);

                SharedPreferences gardeningThreshold = getSharedPreferences("GardeningThreshold", MODE_PRIVATE);
                int _tempProgress = gardeningThreshold.getInt("tempProgress", 30);
                int _lightProgress = gardeningThreshold.getInt("lightProgress", 3000);
                sbTemp.setProgress(_tempProgress);
                sbLight.setProgress(_lightProgress);
                tvTempValue.setText(_tempProgress + "");
                tvLightValue.setText(_lightProgress + "");


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //对话框消失
                        dialog.dismiss();
                    }
                });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //对话框消失
                        tvGardeningMainThresholdTemp.setText(String.valueOf(tempProgress));
                        tvGardeningMainThresholdLight.setText(String.valueOf(lightProgress));

                        SharedPreferences gardeningThreshold = getSharedPreferences("GardeningThreshold", MODE_PRIVATE);
                        SharedPreferences.Editor edit = gardeningThreshold.edit();
                        edit.putInt("tempProgress", tempProgress);
                        edit.putInt("lightProgress", lightProgress);
                        edit.putBoolean("Threshold_flag", true);
                        edit.apply();

                        new GardeningActivity().count = 2;
                        /**
                         * 程序启动的时候获取界面上的温湿度,点击设置阈值的时候再次获取一次
                         */
                        Main_temp = Double.valueOf(tvGardeningMainThresholdTemp.getText().toString());
                        Main_light = Double.valueOf(tvGardeningMainThresholdLight.getText().toString());

                        Toast.makeText(GardeningActivity.this, "设置成功!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                sbTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //SeekBar进度显示到TextView上
                        tvTempValue.setText(String.valueOf(progress));
                        tempProgress = progress;
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
                sbLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //SeekBar进度显示到TextView上
                        tvLightValue.setText(String.valueOf(progress));
                        lightProgress = progress;
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });


                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(true);//设置区域外不可取消
                dialog.show();
                break;
        }
        return true;
    }
}