package com.woyun.warehouse.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author :leilei on 2016/11/11 2326.
 */
public class TimeTools {

    //毫秒换成00:00:00
    public static String getCountTimeByLong(long finishTime) {
        int day;
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            if(hour>24){//显示天数
                day= hour/24;
                hour=hour %24;

                if(hour<10){
                    sb.append(day+"天").append("0").append(hour).append(":");
                }else{
                    sb.append(day+"天").append(hour).append(":");
                }
            }else{
                sb.append(hour).append(":");
            }
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();

    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
//        res = String.valueOf(ts);
        return ts;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换成描述性时间（昨天、今天、明天）
     *
     * @param timestamp 时间戳
     * @return 描述性日期
     */
    public static String descriptiveData(long timestamp) {
        String descriptiveText = null;
        String format = "yyyy-MM-dd HH:mm:ss";
        //当前时间
        Calendar currentTime = Calendar.getInstance();
        //要转换的时间
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timestamp);
        //年相同
        if (currentTime.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
            //获取一年中的第几天并相减，取差值
            switch (currentTime.get(Calendar.DAY_OF_YEAR) - time.get(Calendar.DAY_OF_YEAR)) {
                case 1://当前比目标多一天，那么目标就是昨天
                    descriptiveText = "昨日";
                    format = "HH:mm:ss";
                    break;
                case 0://当前和目标是同一天，就是今天
                    descriptiveText = "今日";
                    format = "HH:mm:ss";
                    break;
                case -1://当前比目标少一天，就是明天
                    descriptiveText = "明日";
                    format = "HH:mm:ss";
                    break;
                default:
                    descriptiveText = null;
                    format = "yyyy-MM-dd HH:mm:ss";
                    break;
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        String formatDate = simpleDateFormat.format(time.getTime());
        if (!TextUtils.isEmpty(descriptiveText)) {
//            return descriptiveText + " " + formatDate;
            return descriptiveText;
        }
//        return formatDate;
        return "";
    }
}
