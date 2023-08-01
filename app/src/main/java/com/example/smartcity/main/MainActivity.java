package com.example.smartcity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.smartcity.R;
import com.example.smartcity.activity.gardening.GardeningActivity;
import com.example.smartcity.activity.smartsupermarket.SmartSupermarketActivity;
import com.example.smartcity.activity.warninfo.WarnInfoActivity;
import com.example.smartcity.activity.weather.WeatherActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnWeather, btnSmartShop, btnAlarm,btnGardening;
    private Intent intent;
    private TextView tvCurrrentDate,tvDisplayUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("智能校园");

        /**
         * 初始化控件
         */
        InitView();
    }

    private void InitView() {
        tvCurrrentDate = findViewById(R.id.tv_current_date);
        btnWeather = findViewById(R.id.btn_weather);
        btnWeather.setOnClickListener(this);
        btnSmartShop = findViewById(R.id.btn_smart_shop);
        btnSmartShop.setOnClickListener(this);
        btnAlarm = findViewById(R.id.btn_alart);
        btnAlarm.setOnClickListener(this);
        btnGardening = findViewById(R.id.btn_gardening);
        btnGardening.setOnClickListener(this);
        tvDisplayUser = findViewById(R.id.tv_display_user);

        Intent intent = getIntent();
        String account = intent.getStringExtra("account");
        tvDisplayUser.setText(" 欢迎"+"["+account+"]"+"使用该系统! ");

        /**
         * 获取当前系统时间
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date(System.currentTimeMillis());
                String currentDate = sdf.format(date);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCurrrentDate.setText(currentDate);
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public void onClick(View v) {
        intent = null;
        switch (v.getId()) {
            case R.id.btn_weather:
                intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_smart_shop:
                intent = new Intent(this, SmartSupermarketActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_alart:
                intent = new Intent(this, WarnInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_gardening:
                intent = new Intent(this, GardeningActivity.class);
                startActivity(intent);
                break;
        }
    }
}