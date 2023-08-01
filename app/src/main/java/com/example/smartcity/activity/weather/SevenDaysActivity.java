package com.example.smartcity.activity.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartcity.R;
import com.example.smartcity.adapter.weather.FutureWeatherDayAdapter;
import com.example.smartcity.entry.WeatherBean;

import java.util.List;

public class SevenDaysActivity extends AppCompatActivity {

    RecyclerView rlvDay;
    FutureWeatherDayAdapter futureWeatherDayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_days);

        Intent intent = getIntent();
        String area = intent.getStringExtra("area"); // 获取地区
        String city = intent.getStringExtra("city"); // 获取地区
        getSupportActionBar().setTitle(area+"/"+city);

        rlvDay = findViewById(R.id.rlvDay);


        List<WeatherBean.DataDTO> data = new WeatherActivity().beanData.getData();
        if(data.size()>0)
        {
            // 7天展示
            futureWeatherDayAdapter = new FutureWeatherDayAdapter(this,data);
            rlvDay.setAdapter(futureWeatherDayAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rlvDay.setLayoutManager(layoutManager);
        }
    }
}