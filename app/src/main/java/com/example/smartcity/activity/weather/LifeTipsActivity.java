package com.example.smartcity.activity.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartcity.R;
import com.example.smartcity.adapter.weather.TipsAdapter;
import com.example.smartcity.entry.WeatherBean;

import java.util.List;

public class LifeTipsActivity extends AppCompatActivity {

    private RecyclerView rlvTips;
    private TipsAdapter mTipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_tips);

        rlvTips = findViewById(R.id.rlv_tips);

        Intent intent = getIntent();
        String area = intent.getStringExtra("area"); // 获取地区
        String city = intent.getStringExtra("city"); // 获取地区
        getSupportActionBar().setTitle(area+"/"+city);

        WeatherBean.DataDTO dataDTO = new WeatherActivity().beanData.getData().get(0);
        List<WeatherBean.DataDTO.IndexDTO> index = dataDTO.getIndex();
        if (index.size() > 0) {
            mTipsAdapter = new TipsAdapter(this, index);
            rlvTips.setAdapter(mTipsAdapter);
            rlvTips.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}