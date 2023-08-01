package com.example.smartcity.activity.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcity.R;
import com.example.smartcity.adapter.weather.FutureWeatherHourAdapter;
import com.example.smartcity.entry.WeatherBean;
import com.example.smartcity.Help.weather.NetUtil;
import com.example.smartcity.Help.weather.WeaIcon;
import com.example.smartcity.main.MainActivity;
import com.google.gson.Gson;

import java.util.List;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgTitleLocation, imgTitleDay, imgBack;
    public static WeatherBean beanData;

    private TextView tvArea, tvUpdateTime, tvTemp, tvWeaDateWeek, tvTemp2Temp1, tvAirQuality, tvWinLevel, tvHumi, tvAirTips, tvSunrise, tvSunset;
    private ImageView weatherIcon;
    private RecyclerView rlvHour;


    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String data = (String) msg.obj;  // 输入地区返回的是json
            if (TextUtils.isEmpty(data)) {
                return;
            }
            Gson gson = new Gson();
            beanData = gson.fromJson(data, WeatherBean.class);
            UpdateUiOfWeather(beanData);  // 获取数据之后更新UI
        }
    };

    /**
     * 更新UI
     *
     * @param beanData
     */
    String country = ""; // 存储地区给7Days
    String city = "";

    private void UpdateUiOfWeather(WeatherBean beanData) {
        if (beanData == null) {
            return;
        }

        city = beanData.getCity();
        country = beanData.getCountry();
        tvArea.setText(country + "/" + city);   // 设置地区

        String update_time = beanData.getUpdate_time();
        tvUpdateTime.setText("更新时间:" + update_time);  // 更新时间

        WeatherBean.DataDTO datum = beanData.getData().get(0);// 进入data集合选取第一项
        String tem = datum.getTem();  // 获取温度
        Log.i("tem===>", tem);
        if (TextUtils.isEmpty(tem)) {
            tem = "Null";
        }
        tvTemp.setText(tem); // 设置温度

        String wea_img = datum.getWea_img();  // 获取天气图标
        int icon = WeaIcon.getImgResOfWeather(wea_img);
        weatherIcon.setImageResource(icon); // 设置天气图标

        String wea = datum.getWea();  // 获得天气 多云
        String week = datum.getWeek();  // 获得星期几
        String date = datum.getDate(); // 获得日期 2021-10-27
        tvWeaDateWeek.setText(wea + "(" + date + week + ")"); // 设置天气情况日期周

        String tem2 = datum.getTem2(); // 获取低温
        String tem1 = datum.getTem1(); // 获取高温
        tvTemp2Temp1.setText(tem2 + "~" + tem1);

        String air = datum.getAir(); // 获取空气
        String air_level = datum.getAir_level(); // 获取空气等级
        tvAirQuality.setText("空气:" + air + air_level); // 设置空气

        String win = datum.getWin().get(0); // 获取风向
        String win_speed = datum.getWin_speed(); // 获取风向等级
        tvWinLevel.setText(win + win_speed); // 设置风及风的等级

        String humidity = datum.getHumidity(); // 获取湿度
        tvHumi.setText("湿度:" + humidity);

        String air_tips = datum.getAir_tips(); // 获取空气的信息
        tvAirTips.setText(air_tips); //设置空气信息

        String sunrise = datum.getSunrise();  // 获取日出数据
        tvSunrise.setText("日出: " + sunrise);

        String sunset = datum.getSunset();  // 获取日落数据
        tvSunset.setText("日落: " + sunset);

//        List<WeatherBean.DataDTO> data = beanData.getData();  // 进入data集合
//        for (WeatherBean.DataDTO datum : data) {
//
//            String tem = datum.getTem();  // 获取温度
//            Log.i("tem===>",tem);
//            if(TextUtils.isEmpty(tem))
//            {
//                tem = "Null";
//            }
//            tvTemp.setText(tem); // 设置温度
//
//            String wea_img = datum.getWea_img();  // 获取天气图标
//            int icon = WeaIcon.getImgResOfWeather(wea_img);
//            weatherIcon.setImageResource(icon); // 设置天气图标
//
//            String wea = datum.getWea();  // 获得天气 多云
//            String week = datum.getWeek();  // 获得星期几
//            String date = datum.getDate(); // 获得日期 2021-10-27
//            tvWeaDateWeek.setText(wea+"("+date+week+")"); // 设置天气情况日期周
//
//            String tem2 = datum.getTem2(); // 获取低温
//            String tem1 = datum.getTem1(); // 获取高温
//            tvTemp2Temp1.setText(tem2+"~"+tem1);
//
//            String air = datum.getAir(); // 获取空气
//            String air_level = datum.getAir_level(); // 获取空气等级
//            tvAirQuality.setText("空气:"+air+air_level); // 设置空气
//
//            String win = datum.getWin().get(0); // 获取风向
//            String win_speed = datum.getWin_speed(); // 获取风向等级
//            tvWinLevel.setText(win+win_speed); // 设置风及风的等级
//
//            String humidity = datum.getHumidity(); // 获取湿度
//            tvHumi.setText("湿度:"+humidity);
//
//            String air_tips = datum.getAir_tips(); // 获取空气的信息
//            tvAirTips.setText(air_tips); //设置空气信息
//
//            String sunrise = datum.getSunrise();  // 获取日出数据
//            tvSunrise.setText("日出: "+sunrise);
//
//            String sunset = datum.getSunset();  // 获取日落数据
//            tvSunrise.setText("日落: "+sunset);
//
//        }

        WeatherBean.DataDTO dataDTO = beanData.getData().get(0);
        List<WeatherBean.DataDTO.HoursDTO> hours = dataDTO.getHours();
        FutureWeatherHourAdapter futureWeatherHourAdapter = new FutureWeatherHourAdapter(WeatherActivity.this, hours);
        rlvHour.setAdapter(futureWeatherHourAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rlvHour.setLayoutManager(layoutManager);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().hide();
        /**
         * 初始化控件
         */
        InitView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = NetUtil.GetWeatherOfCity("长沙"); // 默认启动长沙
                Message msg = Message.obtain();
                msg.obj = data;
                handler.sendMessage(msg);
            }
        }).start();

    }

    private void InitView() {
        imgTitleLocation = findViewById(R.id.img_title_location);
        imgTitleDay = findViewById(R.id.img_title_day);
        imgBack = findViewById(R.id.img_back);
        imgTitleLocation.setOnClickListener(this);
        imgTitleDay.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        tvArea = findViewById(R.id.area);
        tvUpdateTime = findViewById(R.id.update);
        tvTemp = findViewById(R.id.temp);
        tvWeaDateWeek = findViewById(R.id.wea_date_week);
        tvTemp2Temp1 = findViewById(R.id.tem2_tem1);
        tvAirQuality = findViewById(R.id.air_level);
        tvWinLevel = findViewById(R.id.win_level);
        tvHumi = findViewById(R.id.humi);
        tvAirTips = findViewById(R.id.air_tips);
        tvAirTips.setOnClickListener(this);
        tvSunrise = findViewById(R.id.sunrise);
        tvSunset = findViewById(R.id.sunset);

        weatherIcon = findViewById(R.id.weather_icon);

        rlvHour = findViewById(R.id.rlvHour);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_title_location:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请输入你的地区");
                builder.setMessage("Please select a region within China!");
                EditText et = new EditText(this);
                builder.setView(et);
                builder.setCancelable(false);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String location = et.getText().toString(); // 获取输入框中的地区
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                String data = NetUtil.GetWeatherOfCity(location);
                                Message msg = Message.obtain();
                                msg.obj = data;
                                handler.sendMessage(msg);

                            }
                        }).start();

                    }
                });
                builder.show();


                break;
            case R.id.img_title_day:
                Intent intent = new Intent(this, SevenDaysActivity.class);
                intent.putExtra("area", country);
                intent.putExtra("city", city);
                startActivity(intent);
                break;

            case R.id.air_tips:
                Intent intent_life = new Intent(this, LifeTipsActivity.class);
                intent_life.putExtra("area", country);
                intent_life.putExtra("city", city);
                startActivity(intent_life);
                break;
            case R.id.img_back:
                Intent intent_back = new Intent(this, MainActivity.class);
                startActivity(intent_back);
                break;
        }
    }
}










