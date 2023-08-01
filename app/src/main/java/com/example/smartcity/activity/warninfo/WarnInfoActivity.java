package com.example.smartcity.activity.warninfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcity.R;
import com.example.smartcity.main.MainActivity;

import java.sql.SQLOutput;
import java.util.List;

import cn.com.newland.nle_sdk.requestEntity.DeviceElement;
import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.SensorInfo;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class WarnInfoActivity extends AppCompatActivity {

    private TextView tvLedDisplay, tvFire, tvSmoke, tvPerson;
    private ImageView imgIcon, imgAlarmBack, ImgAlarmConfigure;
    private final static String HTTPAPI = "http://api.nlecloud.com:80";
    NetWorkBusiness netWork;
    public static int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_info);
        getSupportActionBar().hide();
        if (count == 1) {
            Toast.makeText(this, "如想要实现预警信息效果,还请右上角配置!", Toast.LENGTH_SHORT).show();
        }
        /**
         * 控件初始化
         */
        InitView();

    }

    private void InitView() {
        tvLedDisplay = findViewById(R.id.tv_led_display);
        tvFire = findViewById(R.id.tv_fire);
        tvSmoke = findViewById(R.id.tv_smoke);
        tvPerson = findViewById(R.id.tv_person);
        imgIcon = findViewById(R.id.img_icon);
        imgAlarmBack = findViewById(R.id.img_alarm_back);
        imgAlarmBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarnInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ImgAlarmConfigure = findViewById(R.id.img_alarm_configure);
        ImgAlarmConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarnInfoActivity.this, WarnInfoConfigureActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        boolean flag = intent.getBooleanExtra("flag", false);
        /**
         * 保证每次进入都不执行,而是点击配置按钮的时候才执行操作
         */
        if (flag) {
            SharedPreferences spf = getSharedPreferences("WarnInfoDate", MODE_PRIVATE);
            String user = spf.getString("user", "");
            String pwd = spf.getString("pwd", "");
            String deviceId = spf.getString("deviceId", "");
            String fire_apiTag = spf.getString("fire_apiTag", "");
            String smoke_apiTag = spf.getString("smoke_apiTag", "");
            String person_apiTag = spf.getString("person_apiTag", "");

            Login(user, pwd, deviceId, fire_apiTag, smoke_apiTag, person_apiTag);  // 登录

        }
    }
    boolean fire_flag = false;
    boolean smoke_flag = false;
    boolean person_flag = false;

    private void Login(String _user, String _pwd, String _deviceId, String _fireApiTag, String _smokeApiTag, String _personApiTag) {
        NetWorkBusiness net = new NetWorkBusiness("", HTTPAPI);
        net.signIn(new SignIn(_user, _pwd), new NCallBack<BaseResponseEntity<User>>(getApplicationContext()) {
            @Override
            protected void onResponse(BaseResponseEntity<User> response) {
                if (response.getStatus() == 0) {
                    System.out.println("-----------------账号: "+_user);
                    System.out.println("-----------------密码: "+_pwd);
                    String accessToken = response.getResultObj().getAccessToken();
                    if (accessToken != null) {
                        netWork = new NetWorkBusiness(accessToken, HTTPAPI);
                        Toast.makeText(WarnInfoActivity.this, "正在执行中,请操作硬件!", Toast.LENGTH_SHORT).show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    netWork.getSensors(_deviceId, _fireApiTag + "," + _smokeApiTag + "," + _personApiTag, new NCallBack<BaseResponseEntity<List<SensorInfo>>>(getApplicationContext()) {
                                        @Override
                                        protected void onResponse(BaseResponseEntity<List<SensorInfo>> response) {
                                            List<SensorInfo> resultObj = response.getResultObj();
                                            for (SensorInfo info : resultObj) {
                                                if (TextUtils.equals(info.getApiTag(), _fireApiTag)) {
                                                    if(TextUtils.equals(info.getValue(),"1")) {
                                                        imgIcon.setImageResource(R.drawable.bigfire);
                                                        tvFire.setText("有火");
                                                        fire_flag = true;
                                                        if(smoke_flag&&person_flag&&fire_flag) {
                                                            tvLedDisplay.setText("有火焰-有烟雾-有人入侵!");
                                                        }
                                                        if(smoke_flag&&person_flag==false&&fire_flag)
                                                        {
                                                            tvLedDisplay.setText("有火焰-有烟雾");
                                                        }
                                                        if(smoke_flag==false&&person_flag==true&&fire_flag)
                                                        {
                                                            tvLedDisplay.setText("有火焰-有人入侵!");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        imgIcon.setBackgroundResource(0);

                                                        tvFire.setText("正常");
                                                        fire_flag = false;
                                                        if(smoke_flag==true&&person_flag==true&&fire_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有烟雾-有人入侵");
                                                        }
                                                        if(smoke_flag==false&&person_flag==true&&fire_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有人入侵!");
                                                        }
                                                        if(smoke_flag==true&&person_flag==false&&fire_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有烟雾!");
                                                        }
                                                        if(fire_flag==false&&smoke_flag==false&&person_flag==false)
                                                        {
                                                            tvLedDisplay.setText("");
                                                        }
                                                    }
                                                }
                                                if (TextUtils.equals(info.getApiTag(), _smokeApiTag)) {
                                                   if(TextUtils.equals(info.getValue(),"1")) {
                                                        imgIcon.setBackgroundResource(R.drawable.alarm_smoke);
                                                        tvSmoke.setText("有烟");
                                                        smoke_flag = true;
                                                        if(fire_flag&&person_flag&&smoke_flag) {
                                                            tvLedDisplay.setText("有烟雾-有火焰-有人入侵!");
                                                        }
                                                        if(fire_flag==true&&person_flag==false&&smoke_flag) {
                                                            tvLedDisplay.setText("有烟雾-有火焰");
                                                        }
                                                        if(fire_flag==false&&person_flag==true&&smoke_flag) {
                                                            tvLedDisplay.setText("有烟雾-有人入侵!");
                                                        }
                                                    }
                                                    else {
                                                        imgIcon.setBackgroundResource(0);
                                                        tvSmoke.setText("正常");
                                                        smoke_flag = false;
                                                        if(fire_flag&&person_flag&&smoke_flag==false)  // 有火有人
                                                        {
                                                            tvLedDisplay.setText("有火焰-有人入侵!");
                                                        }
                                                        if(fire_flag==true&&person_flag==false&&smoke_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有火焰");
                                                        }
                                                        if(fire_flag==false&&person_flag==true&&smoke_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有人入侵!");
                                                        }
                                                        if(fire_flag==false&&smoke_flag==false&&person_flag==false)  // 一切正常
                                                        {
                                                            tvLedDisplay.setText("");
                                                        }
                                                    }
                                                }
                                                if (TextUtils.equals(info.getApiTag(), _personApiTag))
                                                {
                                                    if(TextUtils.equals(info.getValue(),"0")) {
                                                        tvPerson.setText("有人入侵");
                                                        person_flag = true;
                                                        if(fire_flag&&smoke_flag&&person_flag==true)
                                                        {
                                                            tvLedDisplay.setText("有人入侵-有火焰-有烟雾");
                                                        }
                                                        if(fire_flag==true&&person_flag==true&&smoke_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有人入侵-有火焰");
                                                        }
                                                        if(fire_flag==false&&person_flag==true&&smoke_flag==true)
                                                        {
                                                            tvLedDisplay.setText("有人入侵-有烟雾");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        tvPerson.setText("正常");
                                                        person_flag = false;
                                                        if(fire_flag&&smoke_flag&&person_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有火焰-有烟雾");
                                                        }
                                                        if(fire_flag==true&&smoke_flag==false&&person_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有火焰");
                                                        }
                                                        if(fire_flag==false&&smoke_flag==true&&person_flag==false)
                                                        {
                                                            tvLedDisplay.setText("有烟雾");
                                                        }
                                                        if(fire_flag==false&&smoke_flag==false&&person_flag==false)
                                                        {
                                                            tvLedDisplay.setText("");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    try {
                                        Thread.sleep(800);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }
                }
                else
                {
                    Toast.makeText(WarnInfoActivity.this, "请检查你的配置参数!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}











