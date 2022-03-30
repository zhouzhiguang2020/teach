package com.futureeducation.commonmodule.utill;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 */
public class TimeUtil {

    public static final int MIN_IN_MS = 60 * 1000;

    public static boolean isEarly(int days, long time) {
        return (currentTimeMillis() - time) > (days * 24 * 3600 * 1000);
    }

    public static long getNow_millisecond() {
        return (new Date()).getTime();
    }

    public static int currentTimeSecond() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long[] getTsTimes() {
        long[] times = new long[2];

        Calendar calendar = Calendar.getInstance();

        times[0] = calendar.getTimeInMillis() / 1000;

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        times[1] = calendar.getTimeInMillis() / 1000;

        return times;
    }

    public static String getFormatDatetime(int year, int month, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new GregorianCalendar(year, month, day).getTime());
    }

    public static Date getDateFromFormatString(String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getNowDatetime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return (formatter.format(new Date()));
    }

    public static String getNowDatetime1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return (formatter.format(new Date()));
    }

    public static String getNowDatetime2() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String getNowDatetimeMillisecond() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());
        return (formatter.format(new Date()));
    }

    //这里返回妙
    public static int getNow() {
        return (int) ((new Date()).getTime() / 1000);
    }

    public static String getNowDateTime(String format) {
        Date date = new Date();

        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.format(date);
    }

    public static String getDateString(long milliseconds) {
        return getDateTimeString(milliseconds, "yyyyMMdd");
    }

    public static String getDateString2(long milliseconds) {
        return getDateTimeString(milliseconds, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getTimeString(long milliseconds) {
        return getDateTimeString(milliseconds, "HHmmss");
    }

    public static String getTimeString2(long milliseconds) {
        return getDateTimeString(milliseconds, "HH:mm");
    }

    public static String getBeijingNowTimeString(String format) {
        TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");

        Date date = new Date(currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        formatter.setTimeZone(timezone);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeZone(timezone);
        String prefix = gregorianCalendar.get(Calendar.AM_PM) == Calendar.AM ? "上午" : "下午";

        return prefix + formatter.format(date);
    }

    public static String getBeijingNowTime(String format) {
        TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");

        Date date = new Date(currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        formatter.setTimeZone(timezone);

        return formatter.format(date);
    }

    public static String getDateTimeString(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }


    public static String getFavoriteCollectTime(long milliseconds) {
        String showDataString = "";
        Date today = new Date();
        Date date = new Date(milliseconds);
        Date firstDateThisYear = new Date(today.getYear(), 0, 0);
        if (!date.before(firstDateThisYear)) {
            SimpleDateFormat dateformatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
            showDataString = dateformatter.format(date);
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            showDataString = dateformatter.format(date);
        }
        return showDataString;
    }

    public static String getTimeShowString(long milliseconds, boolean abbreviate) {
        String dataString;
        String timeStringBy24;

        Date currentTime = new Date(milliseconds);
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = "今天";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else if (!currentTime.before(preyesterday)) {
            dataString = "前天";
        } else if (isSameWeekDates(currentTime, today)) {
            dataString = getWeekOfDate(currentTime);
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }

        SimpleDateFormat timeformatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeStringBy24 = timeformatter24.format(currentTime);

        if (abbreviate) {
            if (!currentTime.before(todaybegin)) {
                return getTodayTimeBucket(currentTime);
            } else {
                return dataString;
            }
        } else {
            return dataString + " " + timeStringBy24;
        }
    }

    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date
     * @return
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨 " + timeformatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "上午 " + timeformatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "下午 " + timeformatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "晚上 " + timeformatter1to12.format(date);
        }
        return "";
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "7";
        } else if ("2".equals(mWay)) {
            mWay = "1";
        } else if ("3".equals(mWay)) {
            mWay = "2";
        } else if ("4".equals(mWay)) {
            mWay = "3";
        } else if ("5".equals(mWay)) {
            mWay = "4";
        } else if ("6".equals(mWay)) {
            mWay = "5";
        } else if ("7".equals(mWay)) {
            mWay = "6";
        }
        return mWay;
    }


    public static boolean isSameDay(long time1, long time2) {
        return isSameDay(new Date(time1), new Date(time2));
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }

    /**
     * 判断两个日期是否在同一周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }

    public static long getSecondsByMilliseconds(long milliseconds) {
        long seconds = new BigDecimal((float) ((float) milliseconds / (float) 1000)).setScale(0,
                BigDecimal.ROUND_HALF_UP).intValue();
        // if (seconds == 0) {
        // seconds = 1;
        // }
        return seconds;
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    public static String getElapseTimeForShow(int milliseconds) {
        StringBuilder sb = new StringBuilder();
        int seconds = milliseconds / 1000;
        if (seconds < 1) {
            seconds = 1;
        }
        int hour = seconds / (60 * 60);
        if (hour != 0) {
            sb.append(hour).append("小时");
        }
        int minute = (seconds - 60 * 60 * hour) / 60;
        if (minute != 0) {
            sb.append(minute).append("分");
        }
        int second = (seconds - 60 * 60 * hour - 60 * minute);
        if (second != 0) {
            sb.append(second).append("秒");
        }
        return sb.toString();
    }

    /**
     * one day millisecond count
     */
    public static final long ONE_DAY_MILLISECONDS = 1000 * 3600 * 24;

    public static final long ONE_HOUR_MILLISECONDS = 1000 * 3600;

    public static final long ONE_MIN_MILLISECONDS = 1000 * 60;

    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormatYMDHMS1 = "yyyy/MM/dd HH:mm:ss";
    public static String dateFormatYMDHMS_f = "yyyyMMddHHmmss";
    public static String dateFormatMDHM = "MM-dd HH:mm";
    public static String dateFormat = "yyyy-MM-dd HH:mm";
    /**
     * 时间日期格式化到年月日.
     */
    public static String dateFormatYMD = "yyyy-MM-dd";

    /**
     * 时间日期格式化到年月日时分.中文显示
     */
    public static String dateFormatYMDHMofChinese = "yyyy年MM月dd日 HH:mm";

    /**
     * 时间日期格式化到年月日.中文显示
     */
    public static String dateFormatYMDofChinese = "yyyy年MM月dd日";
    /**
     * 时间日期格式化到月日.中文显示
     */
    public static String dateFormatMDofChinese = "MM月dd日";
    /**
     * 时间日期格式化到月.中文显示
     */
    public static String dateFormatMofChinese = "MM月";
    /**
     * 时间日期格式化到年月.
     */
    public static String dateFormatYM = "yyyy-MM";

    /**
     * 时间日期格式化到年月日时分.
     */
    public static String dateFormatYMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 时间日期格式化到月日.
     */
    public static String dateFormatMD = "MM/dd";
    public static String dateFormatM_D = "MM-dd";

    public static String dateFormatM = "MM月";
    public static String dateFormatD = "dd";
    public static String dateFormatM2 = "MM";

    public static String dateFormatMDHMofChinese = "MM月dd日HH时mm分";
    public static String dateFormatHMofChinese = "HH时mm分";

    /**
     * 时分秒.
     */
    public static String dateFormatHMS = "HH:mm:ss";

    /**
     * 时分.
     */
    public static String dateFormatHM = "HH:mm";

    /**
     * 上午/下午时分
     */
    public static String dateFormatAHM = "aHH:mm";

    public static String dateFormatYMDE = "yyyy/MM/dd E";
    public static String dateFormatYMD2 = "yyyy/MM/dd";

    /**
     * yyyy-MM-dd HH:mm
     */
    private static SimpleDateFormat sdf7 = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    @SuppressLint("SimpleDateFormat")
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将时间戳转换成yyyy-MM-dd HH:mm字符串
     *
     * @param time 时间戳
     * @return yyyy-MM-dd HH:mm
     */
    public static String timestampToString1(long time) {
        return sdf7.format(new Date(time));
    }


    /**
     * 时间戳转特定格式时间
     *
     * @param dataFormat
     * @param timeStamp
     * @return
     */
    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        return format.format(new Date(timeStamp));
    }

    /**
     * 将毫秒转换成秒
     *
     * @param time
     * @return
     */
    public static int convertToSecond(Long time) {
        Date date = new Date();
        date.setTime(time);
        return date.getSeconds();
    }

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date          日期时间
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public Date getDateByOffset(Date date, int calendarField, int offset) {
        Calendar c = new GregorianCalendar();
        try {
            c.setTime(date);
            c.add(calendarField, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 描述：获取指定日期时间的字符串(可偏移).
     *
     * @param strDate       String形式的日期时间
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getStringByOffset(String strDate, String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：Date类型转化为String类型(可偏移).
     *
     * @param date          the date
     * @param format        the format
     * @param calendarField the calendar field
     * @param offset        the offset
     * @return String String类型日期时间
     */
    public static String getStringByOffset(Date date, String format, int calendarField, int offset) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(date);
            c.add(calendarField, offset);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * from yyyy-MM-dd HH:mm:ss to MM-dd HH:mm
     */
    public static String formatDate(String before) {
        String after;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .parse(before);
            after = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            return before;
        }
        return after;
    }

    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date   the date
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：获取milliseconds表示的日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 日期时间字符串
     */
    public static String getStringByFormat(long milliseconds, String format) {
        String thisDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            thisDateTime = mSimpleDateFormat.format(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDate(String format) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;

    }


    //获取当前系统当天日期
    public static String getCurrentDay() {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, 0);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统当天日期
    public static String getCurrentDay2() {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, 0);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统前后第几天
    public static String getNextDay(int i) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, i);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统前后第几小时
    public static String getNextHour(int i) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.HOUR_OF_DAY, i);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串(可偏移).
     *
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getCurrentDateByOffset(String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;

    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的天数
     */
    public static int getOffectDay(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        //先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 + maxDays;
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 - maxDays;
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getOffectHour(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h = 0;
        int day = getOffectDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getOffectMinutes(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getOffectHour(date1, date2);
        int m = 0;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 描述：获取本周一.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.MONDAY);
    }

    /**
     * 描述：获取本周日.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.SUNDAY);
    }

    /**
     * 描述：获取本周的某一天.
     *
     * @param format        the format
     * @param calendarField the calendar field
     * @return String String类型日期时间
     */
    private static String getDayOfWeek(String format, int calendarField) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (week == calendarField) {
                strDate = mSimpleDateFormat.format(c.getTime());
            } else {
                int offectDay = calendarField - week;
                if (calendarField == Calendar.SUNDAY) {
                    offectDay = 7 - Math.abs(offectDay);
                }
                c.add(Calendar.DATE, offectDay);
                strDate = mSimpleDateFormat.format(c.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            //当前月的第一天
            c.set(GregorianCalendar.DAY_OF_MONTH, 1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月最后一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            // 当前月的最后一天
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    /**
     * 描述：获取表示当前日期的0点时间毫秒数.
     *
     * @return the first time of day
     */
    public static long getFirstTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate(dateFormatYMD);
            date = getDateByFormat(currentDate + " 00:00:00", dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 描述：获取表示当前日期24点时间毫秒数.
     *
     * @return the last time of day
     */
    public static long getLastTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate(dateFormatYMD);
            date = getDateByFormat(currentDate + " 24:00:00", dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 描述：判断是否是闰年()
     * <p>(year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year 年代（如2012）
     * @return boolean 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 描述：根据时间返回几天前或几分钟的描述.
     *
     * @param strDate the str date
     * @return the string
     */
    public static String formatDateStr2Desc(String strDate, String outFormat) {

        DateFormat df = new SimpleDateFormat(dateFormatYMDHM);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c2.setTime(df.parse(strDate));
            c1.setTime(new Date());
            int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
            if (d == 0) {
                int h = getOffectHour(c1.getTimeInMillis(), c2.getTimeInMillis());
                if (h > 0) {
                    return h + "小时前";
                } else if (h < 0) {
                    return Math.abs(h) + "小时后";
                } else if (h == 0) {
                    int m = getOffectMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
                    if (m > 0) {
                        return m + "分钟前";
                    } else if (m < 0) {
                        return Math.abs(m) + "分钟后";
                    } else {
                        return "刚刚";
                    }
                }
            } else if (d > 0) {
                if (d == 1) {
                    return "昨天";
                } else if (d == 2) {
                    return "前天";
                }
            } else if (d < 0) {
                if (d == -1) {
                    return "明天";
                } else if (d == -2) {
                    return "后天";
                }
                return Math.abs(d) + "天后";
            }

            String out = getStringByFormat(strDate, outFormat);
            if (!TextUtils.isEmpty(out)) {
                return out;
            }
        } catch (Exception e) {
        }

        return strDate;
    }


    /**
     * 取指定日期为星期几
     *
     * @param strDate  指定日期
     * @param inFormat 指定日期格式
     * @return String   星期几
     */
    public static String getWeekNumber(String strDate, String inFormat) {
        String week = "星期日";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "星期日";
                break;
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    private static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param ms
     * @return
     */
    public static String getfriendlyTime(Long ms) {
        if (ms == null) {
            return "";
        }
//      Date time = toDate(sdate);
        Date time = new Date();
        time.setTime(ms);

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                if (((cal.getTimeInMillis() - time.getTime()) / 60000) < 1) {
                    ftime = "刚刚";
                } else {
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 距离当前多少个小时
     *
     * @param dateStr
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int getExpiredHour(String dateStr) {
        int ret = -1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date;
        try {
            date = sdf.parse(dateStr);
            Date dateNow = new Date();

            long times = date.getTime() - dateNow.getTime();
            if (times > 0) {
                ret = ((int) (times / ONE_HOUR_MILLISECONDS));
            } else {
                ret = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 过了多少个小时
     *
     * @param dateStr
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int getExpiredHour2(String dateStr) {
        int ret = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sendDate;
        try {
            sendDate = sdf.parse(dateStr);
            Date dateNow = new Date(System.currentTimeMillis());
            long times = dateNow.getTime() - sendDate.getTime();
            if (times > 0) {
                ret = ((int) (times / ONE_HOUR_MILLISECONDS));
                int sdqf = (int) Math.floor(times / ONE_HOUR_MILLISECONDS);
            } else {
                ret = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    /*
     * 跟今天比较日期大小
     *"2020/01/01 00:00:00"
     * 判断传入的日期是否大于今天
     * */
    public static boolean isDateBiggerToday(String datestring) {
        //LogUtils.e("看传入的" + datestring);
        Calendar today = new GregorianCalendar();
        //2020/08/31 00:00:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long todays = today.getTime().getTime();
        Date dt1 = null;
        long time1 = 0;
        try {
            dt1 = (Date) sdf.parseObject(datestring);
            time1 = dt1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // LogUtils.e("看今天" + todays);
        // LogUtils.e("传入的日期" + time1);
        if (time1 > todays) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(long sdate) {
        boolean b = false;
        Date time = new Date(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    public static String dateFormat1(String time) throws ParseException {
        if (time != null) {
            if (time.contains("T")) {
                time = time.replace("T", " ");
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        String s = time;
        try {
            date = format.parse(time);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            s = format1.format(date);
        } catch (Exception e) {
            LogUtils.e("日期解析错误");
        }

        return s;

    }

    public static String dateFormat2(String time) throws ParseException {
        if (time != null) {
            if (time.indexOf("T") != -1) {
                time = time.replace("T", " ");
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        String s = time;
        try {
            date = format.parse(time);
            SimpleDateFormat format1 = new SimpleDateFormat(dateFormatYMDofChinese);
            s = format1.format(date);
        } catch (Exception e) {
            LogUtils.e("日期解析错误");
        }
        return s;

    }

    public static String dateFormat3(String time) throws ParseException {
        if (time != null) {
            if (time.indexOf("T") != -1) {
                time = time.replace("T", " ");
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        String s = time;
        try {
            date = format.parse(time);
            SimpleDateFormat format1 = new SimpleDateFormat(dateFormatMDofChinese);
            s = format1.format(date);
        } catch (Exception e) {
            LogUtils.e("日期解析错误");
        }
        return s;

    }

    // "gul_type": "h",
    //      "cdate": "2020/01/03 15:48:54",
    public static String dateFormat4(String time) throws ParseException {
        if (time != null) {
            if (time.indexOf("T") != -1) {
                time = time.replace("T", " ");
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        String s = time;
        try {
            date = format.parse(time);
            SimpleDateFormat format1 = new SimpleDateFormat(dateFormatYMDHM);
            s = format1.format(date);
        } catch (Exception e) {
            LogUtils.e("日期解析错误");
        }
        return s;

    }


    public static long getDateMillisecond(String dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getDateMillisecond2(String dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS1);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    //工具方法是否显示日期 默认时间间隔3分钟内是不显示的
    public static boolean isshwoDate(String lastdate, String presentdate) {
        long last = getDateMillisecond2(lastdate);
        long present = getDateMillisecond2(presentdate);
        long value = Math.abs(present - last);
        if (value < 60 * 1000) {
            return false;
        } else {
            return true;
        }

    }

    public static String getStandardDate(String dateTime) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(dateTime);
        long current = System.currentTimeMillis();
        long time = current - (t * 1);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        long month = (long) Math.ceil(time / 30 / 24 / 60 / 60 / 1000.0f);// 月前

        long year = (long) Math.ceil(time / 12 / 30 / 24 / 60 / 60 / 1000.0f);// 年前

        Calendar cal = Calendar.getInstance();

        long showYear = cal.get(Calendar.YEAR);

        long showMonth = cal.get(Calendar.MONTH);

        long showDay = cal.get(Calendar.DATE);

        long showHour = cal.get(Calendar.HOUR_OF_DAY);

        long showMint = cal.get(Calendar.MINUTE);
        if (year - 1 > 0) {
            sb.append((year - 1) + "年前");
        } else if (month - 1 > 0) {
//            if (month >= 12) {
//                sb.append("一年");
//            } else {
            sb.append((month - 1) + "个月");
            //}
        } else if (day - 1 > 0) {
            if (day >= 30) {
                sb.append("1个月");
            } else {
                sb.append(day + "天");
            }
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "个小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1个小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (month <= 12 && !sb.toString().

                equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Long timeStrToSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }


    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        // LogUtils.w("看看为空吗：" + formatType == null);
        if (data != null && !TextUtils.isEmpty(formatType)) {
            return new SimpleDateFormat(formatType).format(data);
        } else {
            return null;
        }

    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        if (!TextUtils.isEmpty(strTime) && !TextUtils.isEmpty(formatType)) {
            SimpleDateFormat formatter = new SimpleDateFormat(formatType);
            Date date = null;
            date = formatter.parse(strTime);
            return date;
        }
        return null;
    }

    /**
     * 秒转hh:mm:ss格式
     *
     * @param secString 秒字符串
     * @return hh小时mm分ss秒
     * String
     */
    public static String secondsToFormat(int secString) {
        Integer seconds = secString;
        Integer hour = 0;
        Integer min = 0;
        Integer second = 0;
        String result = "";

        if (seconds > 60) {   //是否大于零
            min = seconds / 60;  //分钟
            second = seconds % 60;  //秒
            if (min > 60) {   //存在时
                hour = min / 60;
                min = min % 60;
            }
        } else {
            second = seconds;
        }
        if (hour > 0) {
            result = hour + "小时";
        }
        if (min > 0) {
            result = result + min + "分";
        } else if (min == 0 && hour > 0) {  //当分为0时,但是时有值,所以要显示,避免出现2时0秒现象
            result = result + min + "分";
        }
        result = result + second + "秒";   //秒必须出现无论是否大于零
        return result;
    }

    /**
     * 秒转hh:mm:ss格式
     * <p>
     * 时间戳转 Calendar
     */
    public static Calendar DateToFormatCalendar(String DateString) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(DateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}

