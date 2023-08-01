package com.example.smartcity.adapter.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.entry.WeatherBean;
import com.example.smartcity.Help.weather.WeaIcon;

import java.util.List;

public class FutureWeatherDayAdapter extends RecyclerView.Adapter<FutureWeatherDayAdapter.WeatherViewHolder> {

    private Context mContext;
    private List<WeatherBean.DataDTO> Days;
    int timezone_offset;
    boolean flag;

    public FutureWeatherDayAdapter(Context context, List<WeatherBean.DataDTO> _daily) {
        mContext = context;
        this.Days = _daily;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.weather_item_day_layout, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        return weatherViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherBean.DataDTO day = Days.get(position);

        String date = day.getDate(); // 获得日期
        String week = day.getWeek(); // 获得星期几
        holder.tvDayDate.setText(week+","+date);

        String wea_img = day.getWea_img(); // 获得天气图标
        holder.imgDayWeatheIcon.setImageResource(WeaIcon.getImgResOfWeather(wea_img));

        String tem = day.getTem(); // 获取温度
        holder.tvDayTemp.setText(tem);

        String visibility = day.getVisibility(); // 能见度
        if(TextUtils.isEmpty(visibility))
        {
            visibility="Null";
        }
        holder.tvDayVisibility.setText("能见度:"+visibility);

        String pressure = day.getPressure(); // 压力
        if(TextUtils.isEmpty(pressure))
        {
            pressure="Null";
        }
        holder.tvDayPressure.setText("压力:"+pressure);

    }

    @Override
    public int getItemCount() {
        return (Days == null) ? 0 : Days.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayDate, tvDayTemp,tvDayVisibility,tvDayPressure;
        ImageView imgDayWeatheIcon;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayDate = itemView.findViewById(R.id.tv_day_date);
            imgDayWeatheIcon = itemView.findViewById(R.id.img_day_weather_icon);
            tvDayTemp = itemView.findViewById(R.id.tv_day_temp);
            tvDayVisibility = itemView.findViewById(R.id.tv_day_visibility);
            tvDayPressure = itemView.findViewById(R.id.tv_day_pressure);

        }
    }
}
