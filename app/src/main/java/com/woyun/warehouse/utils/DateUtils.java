package com.woyun.warehouse.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kd on 2018/6/16.
 */

public class DateUtils {
    private static final String TAG = "DateUtils";
    public static final String YMD = "yyyyMMdd";
    public static final String YMD_YEAR = "yyyy";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String YMDHMS = "yyyyMMddHHmmss";
    public static final String YEAR_MONTH_DAY_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHMS_BREAK_HALF = "yyyy-MM-dd HH:mm";
    public static final String MD = "MM-dd";
    public static final String MD_HM = "MM-dd HH:mm";

    /**
     * 计算时间差
     */
    public static final int CAL_MINUTES = 1000 * 60;
    public static final int CAL_HOURS = 1000 * 60 * 60;
    public static final int CAL_DAYS = 1000 * 60 * 60 * 24;

    /**
     * 获取当前时间格式化后的值
     *
     * @param pattern
     * @return
     */
    public static String getNowDateText(String pattern) {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 获取日期格式化后的值
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateText(Date date, String pattern) {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字符串时间转换成Date格式
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date getDate(String date, String pattern) throws ParseException {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 获取时间戳
     *
     * @param date
     * @return
     */
    public static Long getTime(Date date) {
        return date.getTime();
    }

    /**
     * 计算时间差
     *
     * @param startDate
     * @param endDate
     * @param calType   计算类型,按分钟、小时、天数计算
     * @return
     */
    public static int calDiffs(Date startDate, Date endDate, int calType) {
        Long start = DateUtils.getTime(startDate);
        Long end = DateUtils.getTime(endDate);
        int diff = (int) ((end - start) / calType);
        return diff;
    }

    /**
     * 计算时间差值以某种约定形式显示
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String timeDiffText(Date startDate, Date endDate) {
        int calDiffs = DateUtils.calDiffs(startDate, endDate, DateUtils.CAL_MINUTES);
        if (calDiffs == 0) {
            return "刚刚";
        }
        if (calDiffs < 60) {
            return calDiffs + "分钟前";
        }
        calDiffs = DateUtils.calDiffs(startDate, endDate, DateUtils.CAL_HOURS);
        if (calDiffs < 24) {
            return calDiffs + "小时前";
        }
        if (calDiffs < 48) {
            return "昨天";
        }

        return DateUtils.getDateText(startDate, DateUtils.YMDHMS_BREAK_HALF);
    }

    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 显示某种约定后的时间值,类似微信朋友圈发布说说显示的时间那种
     *
     * @param date
     * @return
     */
    public static String showTimeText(Date date) {
        return DateUtils.timeDiffText(date, new Date());
    }

    public static Date dateTimeString2Date(String date_str) {
        try {
            Calendar cal = Calendar.getInstance();//日期类
            java.sql.Timestamp timestampnow = new java.sql.Timestamp(cal.getTimeInMillis());//转换成正常的日期格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date current = formatter.parse(date_str, pos);
            timestampnow = new java.sql.Timestamp(current.getTime());
            return timestampnow;
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * @param type     1 天数   2 时
     * @return 返回时间差
     */
    public static String getTimeDifference(String starTime, String endTime, int type) {
        String timeString = "";
        String dateString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            dateString = day + "天" + hour + "小时" + min + "分";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
//            timeString = hour1 + "小时" + min1 + "分";
            timeString = String.valueOf(hour1);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (type == 1) {
            return dateString;
        } else {
            return timeString;
        }
    }

    /**
     * 时间差
     * @param starTime
     * @param endTime
     * @param type
     * @return
     */
    public static String timeSub(String starTime, String endTime, int type) {
        String timeString = "";
        String dateString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            dateString = day + "天" + hour + "小时" + min + "分"+s+"秒";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
//            timeString = hour1 + "小时" + min1 + "分";
            timeString = String.valueOf(hour1);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (type == 1) {
            return dateString;
        } else {
            return timeString;
        }
    }

    /**
     * 获取当前时间
     **/
    public static String getCurentTime() {
        String currentTime = "";
        Date d = new Date();//获取时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        currentTime = dateFormat.format(d);
        return currentTime;
    }

    /**
     * long--to String
     * format
     **/
    public static String longToStringTime(long time,String formmat ) {
        String result = "";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formmat);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        result = simpleDateFormat.format(time);
        return result;
    }



    public static String longToStringTime2(long time) {
        String result = "";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        result = simpleDateFormat.format(time);
        return result;
    }

    public static String longToStringTimed(long time) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        result = simpleDateFormat.format(time);
        return result;
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean isSameDay(Date date, Date sameDate) {
        if (null == date || null == sameDate) {
            return false;
        }
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(sameDate);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)
                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)
                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {
            return true;
        }
        // if (date.getYear() == sameDate.getYear() && date.getMonth() == sameDate.getMonth()
        // && date.getDate() == sameDate.getDate()) {
// return true;
        // }
        return false;

    }

    /**
     * String ---date
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    /**
     *
     * @return
     */
    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * second to HH:MM:ss
     * @param seconds
     * @return
     */
    public static String convertSecondsToTime(long seconds) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (seconds <= 0)
            return "00:00";
        else {
            minute = (int)seconds / 60;
            if (minute < 60) {
                second = (int)seconds % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = (int)(seconds - hour * 3600 - minute * 60);
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断时间是否在时间段内
     */
    public static  boolean isBelong(String start,String end){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(start);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean flag = belongCalendar(now, beginTime, endTime);
        LogUtils.e(TAG, "isBelong:是否在时间内"+flag);
        return flag;
    }

}
