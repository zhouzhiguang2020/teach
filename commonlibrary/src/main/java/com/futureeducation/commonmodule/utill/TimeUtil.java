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

    //???????????????
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
        String prefix = gregorianCalendar.get(Calendar.AM_PM) == Calendar.AM ? "??????" : "??????";

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
            dataString = "??????";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "??????";
        } else if (!currentTime.before(preyesterday)) {
            dataString = "??????";
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
     * ??????????????????????????????????????????
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
            return "?????? " + timeformatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "?????? " + timeformatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "?????? " + timeformatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "?????? " + timeformatter1to12.format(date);
        }
        return "";
    }

    /**
     * ????????????????????????
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????"};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // ??????????????????
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// ??????????????????
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// ?????????????????????????????????
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
     * ????????????????????????????????????
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
            // ??????12???????????????????????????????????????????????????????????????????????????????????????
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
            sb.append(hour).append("??????");
        }
        int minute = (seconds - 60 * 60 * hour) / 60;
        if (minute != 0) {
            sb.append(minute).append("???");
        }
        int second = (seconds - 60 * 60 * hour - 60 * minute);
        if (second != 0) {
            sb.append(second).append("???");
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
     * ??????????????????????????????????????????.
     */
    public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormatYMDHMS1 = "yyyy/MM/dd HH:mm:ss";
    public static String dateFormatYMDHMS_f = "yyyyMMddHHmmss";
    public static String dateFormatMDHM = "MM-dd HH:mm";
    public static String dateFormat = "yyyy-MM-dd HH:mm";
    /**
     * ?????????????????????????????????.
     */
    public static String dateFormatYMD = "yyyy-MM-dd";

    /**
     * ???????????????????????????????????????.????????????
     */
    public static String dateFormatYMDHMofChinese = "yyyy???MM???dd??? HH:mm";

    /**
     * ?????????????????????????????????.????????????
     */
    public static String dateFormatYMDofChinese = "yyyy???MM???dd???";
    /**
     * ??????????????????????????????.????????????
     */
    public static String dateFormatMDofChinese = "MM???dd???";
    /**
     * ???????????????????????????.????????????
     */
    public static String dateFormatMofChinese = "MM???";
    /**
     * ??????????????????????????????.
     */
    public static String dateFormatYM = "yyyy-MM";

    /**
     * ???????????????????????????????????????.
     */
    public static String dateFormatYMDHM = "yyyy-MM-dd HH:mm";

    /**
     * ??????????????????????????????.
     */
    public static String dateFormatMD = "MM/dd";
    public static String dateFormatM_D = "MM-dd";

    public static String dateFormatM = "MM???";
    public static String dateFormatD = "dd";
    public static String dateFormatM2 = "MM";

    public static String dateFormatMDHMofChinese = "MM???dd???HH???mm???";
    public static String dateFormatHMofChinese = "HH???mm???";

    /**
     * ?????????.
     */
    public static String dateFormatHMS = "HH:mm:ss";

    /**
     * ??????.
     */
    public static String dateFormatHM = "HH:mm";

    /**
     * ??????/????????????
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
     * ?????????????????????yyyy-MM-dd HH:mm?????????
     *
     * @param time ?????????
     * @return yyyy-MM-dd HH:mm
     */
    public static String timestampToString1(long time) {
        return sdf7.format(new Date(time));
    }


    /**
     * ??????????????????????????????
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
     * ?????????????????????
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
     * ?????????String??????????????????????????????Date??????.
     *
     * @param strDate String?????????????????????
     * @param format  ???????????????????????????"yyyy-MM-dd HH:mm:ss"
     * @return Date Date??????????????????
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
     * ??????????????????????????????Date.
     *
     * @param date          ????????????
     * @param calendarField Calendar???????????????offset????????? ???(Calendar.DATE,??????+offset???,Calendar.HOUR_OF_DAY,?????????offset??????)
     * @param offset        ??????(?????????0,??????+,?????????0,?????????)
     * @return Date ???????????????????????????
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
     * ?????????????????????????????????????????????(?????????).
     *
     * @param strDate       String?????????????????????
     * @param format        ???????????????????????????"yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar???????????????offset????????? ???(Calendar.DATE,??????+offset???,Calendar.HOUR_OF_DAY,?????????offset??????)
     * @param offset        ??????(?????????0,??????+,?????????0,?????????)
     * @return String String?????????????????????
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
     * ?????????Date???????????????String??????(?????????).
     *
     * @param date          the date
     * @param format        the format
     * @param calendarField the calendar field
     * @param offset        the offset
     * @return String String??????????????????
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
     * ?????????Date???????????????String??????.
     *
     * @param date   the date
     * @param format the format
     * @return String String??????????????????
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
     * ?????????????????????????????????????????????,???????????????????????????.
     *
     * @param strDate String?????????????????????????????????yyyy-MM-dd HH:mm:ss??????
     * @param format  ?????????????????????????????????"yyyy-MM-dd HH:mm:ss"
     * @return String ????????????String?????????????????????
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
     * ???????????????milliseconds?????????????????????????????????.
     *
     * @param format ???????????????????????????"yyyy-MM-dd HH:mm:ss"
     * @return String ?????????????????????
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
     * ???????????????????????????????????????????????????.
     *
     * @param format ???????????????????????????"yyyy-MM-dd HH:mm:ss"
     * @return String String???????????????????????????
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


    //??????????????????????????????
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

    //??????????????????????????????
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

    //?????????????????????????????????
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

    //????????????????????????????????????
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
     * ???????????????????????????????????????????????????(?????????).
     *
     * @param format        ???????????????????????????"yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar???????????????offset????????? ???(Calendar.DATE,??????+offset???,Calendar.HOUR_OF_DAY,?????????offset??????)
     * @param offset        ??????(?????????0,??????+,?????????0,?????????)
     * @return String String?????????????????????
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
     * ??????????????????????????????????????????.
     *
     * @param date1 ??????????????????????????????
     * @param date2 ??????????????????????????????
     * @return int ???????????????
     */
    public static int getOffectDay(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        //?????????????????????
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
     * ?????????????????????????????????????????????.
     *
     * @param date1 ??????????????????????????????
     * @param date2 ??????????????????????????????
     * @return int ??????????????????
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
     * ?????????????????????????????????????????????.
     *
     * @param date1 ??????????????????????????????
     * @param date2 ??????????????????????????????
     * @return int ??????????????????
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
     * ????????????????????????.
     *
     * @param format the format
     * @return String String??????????????????
     */
    public static String getFirstDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.MONDAY);
    }

    /**
     * ????????????????????????.
     *
     * @param format the format
     * @return String String??????????????????
     */
    public static String getLastDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.SUNDAY);
    }

    /**
     * ?????????????????????????????????.
     *
     * @param format        the format
     * @param calendarField the calendar field
     * @return String String??????????????????
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
     * ??????????????????????????????.
     *
     * @param format the format
     * @return String String??????????????????
     */
    public static String getFirstDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            //?????????????????????
            c.set(GregorianCalendar.DAY_OF_MONTH, 1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * ?????????????????????????????????.
     *
     * @param format the format
     * @return String String??????????????????
     */
    public static String getLastDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            // ????????????????????????
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    /**
     * ????????????????????????????????????0??????????????????.
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
     * ?????????????????????????????????24??????????????????.
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
     * ??????????????????????????????()
     * <p>(year??????4?????? ?????? ?????????100??????) ?????? year??????400??????,??????????????????.
     *
     * @param year ????????????2012???
     * @return boolean ???????????????
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ?????????????????????????????????????????????????????????.
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
                    return h + "?????????";
                } else if (h < 0) {
                    return Math.abs(h) + "?????????";
                } else if (h == 0) {
                    int m = getOffectMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
                    if (m > 0) {
                        return m + "?????????";
                    } else if (m < 0) {
                        return Math.abs(m) + "?????????";
                    } else {
                        return "??????";
                    }
                }
            } else if (d > 0) {
                if (d == 1) {
                    return "??????";
                } else if (d == 2) {
                    return "??????";
                }
            } else if (d < 0) {
                if (d == -1) {
                    return "??????";
                } else if (d == -2) {
                    return "??????";
                }
                return Math.abs(d) + "??????";
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
     * ???????????????????????????
     *
     * @param strDate  ????????????
     * @param inFormat ??????????????????
     * @return String   ?????????
     */
    public static String getWeekNumber(String strDate, String inFormat) {
        String week = "?????????";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "??????";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "?????????";
                break;
            case 1:
                week = "?????????";
                break;
            case 2:
                week = "?????????";
                break;
            case 3:
                week = "?????????";
                break;
            case 4:
                week = "?????????";
                break;
            case 5:
                week = "?????????";
                break;
            case 6:
                week = "?????????";
                break;
        }
        return week;
    }

    /**
     * ??????????????????????????????
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
     * ??????????????????????????????
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

        // ????????????????????????
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                if (((cal.getTimeInMillis() - time.getTime()) / 60000) < 1) {
                    ftime = "??????";
                } else {
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "?????????";
                }
            } else {
                ftime = hour + "?????????";
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
                        + "?????????";
            } else {
                ftime = hour + "?????????";
            }
        } else if (days == 1) {
            ftime = "??????";
        } else if (days == 2) {
            ftime = "??????";
        } else if (days > 2 && days <= 10) {
            ftime = days + "??????";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * ???????????????????????????
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
     * ?????????????????????
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
     * ?????????????????????????????????????????????yyyy-MM-dd
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
     * ?????????????????????????????????????????????yyyy-MM-dd
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
     * ???????????????????????????
     *"2020/01/01 00:00:00"
     * ???????????????????????????????????????
     * */
    public static boolean isDateBiggerToday(String datestring) {
        //LogUtils.e("????????????" + datestring);
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

        // LogUtils.e("?????????" + todays);
        // LogUtils.e("???????????????" + time1);
        if (time1 > todays) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * ??????????????????????????????????????????
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
     * ??????????????????????????????????????????
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
     * ??????????????????????????????
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
            LogUtils.e("??????????????????");
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
            LogUtils.e("??????????????????");
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
            LogUtils.e("??????????????????");
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
            LogUtils.e("??????????????????");
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

    //?????????????????????????????? ??????????????????3????????????????????????
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
        long mill = (long) Math.ceil(time / 1000);//??????

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// ?????????

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// ??????

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// ??????

        long month = (long) Math.ceil(time / 30 / 24 / 60 / 60 / 1000.0f);// ??????

        long year = (long) Math.ceil(time / 12 / 30 / 24 / 60 / 60 / 1000.0f);// ??????

        Calendar cal = Calendar.getInstance();

        long showYear = cal.get(Calendar.YEAR);

        long showMonth = cal.get(Calendar.MONTH);

        long showDay = cal.get(Calendar.DATE);

        long showHour = cal.get(Calendar.HOUR_OF_DAY);

        long showMint = cal.get(Calendar.MINUTE);
        if (year - 1 > 0) {
            sb.append((year - 1) + "??????");
        } else if (month - 1 > 0) {
//            if (month >= 12) {
//                sb.append("??????");
//            } else {
            sb.append((month - 1) + "??????");
            //}
        } else if (day - 1 > 0) {
            if (day >= 30) {
                sb.append("1??????");
            } else {
                sb.append(day + "???");
            }
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1???");
            } else {
                sb.append(hour + "?????????");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1?????????");
            } else {
                sb.append(minute + "??????");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1??????");
            } else {
                sb.append(mill + "???");
            }
        } else {
            sb.append("??????");
        }
        if (month <= 12 && !sb.toString().

                equals("??????")) {
            sb.append("???");
        }
        return sb.toString();
    }

    /**
     * ????????????????????????
     * ????????????: yyyy-MM-dd HH:mm:ss
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


    // currentTime????????????long???????????????
    // formatType????????????????????????yyyy-MM-dd HH:mm:ss//yyyy???MM???dd??? HH???mm???ss???
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // ??????long??????????????????????????????date???????????????
        String sDateTime = dateToString(dateOld, formatType); // ???date????????????????????????string
        Date date = stringToDate(sDateTime, formatType); // ???String???????????????Date??????
        return date;
    }

    // formatType?????????yyyy-MM-dd HH:mm:ss//yyyy???MM???dd??? HH???mm???ss???
    // data Date???????????????
    public static String dateToString(Date data, String formatType) {
        // LogUtils.w("??????????????????" + formatType == null);
        if (data != null && !TextUtils.isEmpty(formatType)) {
            return new SimpleDateFormat(formatType).format(data);
        } else {
            return null;
        }

    }

    // strTime????????????string??????????????????formatType??????????????????yyyy-MM-dd HH:mm:ss//yyyy???MM???dd???
    // HH???mm???ss??????
    // strTime???????????????????????????formatType?????????????????????
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
     * ??????hh:mm:ss??????
     *
     * @param secString ????????????
     * @return hh??????mm???ss???
     * String
     */
    public static String secondsToFormat(int secString) {
        Integer seconds = secString;
        Integer hour = 0;
        Integer min = 0;
        Integer second = 0;
        String result = "";

        if (seconds > 60) {   //???????????????
            min = seconds / 60;  //??????
            second = seconds % 60;  //???
            if (min > 60) {   //?????????
                hour = min / 60;
                min = min % 60;
            }
        } else {
            second = seconds;
        }
        if (hour > 0) {
            result = hour + "??????";
        }
        if (min > 0) {
            result = result + min + "???";
        } else if (min == 0 && hour > 0) {  //?????????0???,???????????????,???????????????,????????????2???0?????????
            result = result + min + "???";
        }
        result = result + second + "???";   //????????????????????????????????????
        return result;
    }

    /**
     * ??????hh:mm:ss??????
     * <p>
     * ???????????? Calendar
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

