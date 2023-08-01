package com.example.smartcity.adapter.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.entry.WeatherBean;
import com.example.smartcity.Help.weather.WeaIcon;

import java.util.List;

public class FutureWeatherHourAdapter extends RecyclerView.Adapter<FutureWeatherHourAdapter.WeatherViewHolder> {

    private Context mContext;
    List<WeatherBean.DataDTO.HoursDTO> hours;

    public FutureWeatherHourAdapter(Context context, List<WeatherBean.DataDTO.HoursDTO> _bean) {
        this.mContext = context;
        this.hours=_bean;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.weather_item_hour_layout, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherBean.DataDTO.HoursDTO hour = hours.get(position);

        String h = hour.getHours(); // 获取小时
            holder.tvHourHour.setText(h); // 设置时间

            String tem = hour.getTem(); // 获取实际温度
            holder.tvHourTemp.setText(tem+"℃"); // 设置温度

            String wea_img = hour.getWea_img(); // 获取天气图标
            holder.imgHourIcon.setImageResource(WeaIcon.getImgResOfWeather(wea_img)); // 设置图标

            String wea = hour.getWea(); // 获取天气状况
            holder.tvHourWeather.setText(wea); // 设置天气的状况

            String win = hour.getWin(); // 获取风向
            String win_speed = hour.getWin_speed(); // 获取风的等级
            holder.tvHourWinLevel.setText(win + win_speed); // 设置风向及等级

    }


    @Override
    public int getItemCount() {
        return (hours == null) ? 0 : hours.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tvHourHour, tvHourTemp, tvHourWeather, tvHourWinLevel;
        ImageView imgHourIcon;


        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHourHour = itemView.findViewById(R.id.tv_hour_hour);
            tvHourTemp = itemView.findViewById(R.id.tv_hour_temp);
            tvHourWeather = itemView.findViewById(R.id.tv_hour_weather);
            tvHourWinLevel = itemView.findViewById(R.id.tv_hour_win_level);
            imgHourIcon = itemView.findViewById(R.id.img_hour_icon);

        }
    }

}































