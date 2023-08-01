package com.example.smartcity.Help.weather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {

    private static final String URL_WEATHER_WITH_FUTURE = "https://tianqiapi.com/api?unescape=1&version=v1&appid=61687842&appsecret=Vj8DEvH2";

    /**
     * 网络请求
     *
     * @param urlStr
     * @return
     */
    private static String DoGet(String urlStr) {
        String result = "";
        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        // 连接网络
        try {
            URL urL = new URL(urlStr);
            connection = (HttpURLConnection) urL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            // 从连接中读取数据(二进制)
            InputStream inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            // 二进制流送入缓冲区
            bufferedReader = new BufferedReader(inputStreamReader);

            // 从缓存区中一行行读取字符串
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 根据城市查询
     * @param city
     * @return
     */
    public static String GetWeatherOfCity(String city) {
        String weatherUrl = URL_WEATHER_WITH_FUTURE + "&city=" + city;
        Log.i("---url---",weatherUrl);
        return DoGet(weatherUrl);
    }
}
