package com.example.smartcity.Help.weather;

import com.example.smartcity.R;

/**
 * @author Amy
 * @date -
 **/
public class WeaIcon {

    /**
     * 更换图片
     *
     * @param weaIcon
     * @return
     */
    public static int getImgResOfWeather(String weaIcon) {
        int result = 0;
        switch (weaIcon) {
            case "xue":
                result = R.drawable.xue;
                break;
            case "lei":
                result = R.drawable.lei;
                break;
            case "shachen":
                result = R.drawable.shachen;
                break;
            case "wu":
                result = R.drawable.wu;
                break;
            case "bingbaao":
                result = R.drawable.bingbao;
                break;
            case "yun":
                result = R.drawable.yun;
                break;
            case "yu":
                result = R.drawable.yu;
                break;
            case "yin":
                result = R.drawable.yin;
                break;
            case "qing":
                result = R.drawable.qing;
                break;
            default:
                result = R.drawable.yun;
                break;
        }
        return result;
    }
}


