package com.futureeducation.commonmodule.utill;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.config.CommonConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @author zhou
 */
public class CommonUtil {

    public static final int BUFFER_SIZE = 4096;
    public static final int MAX_STRING_LENGTH = 1024000;
    private static final double EARTH_RADIUS = 6378137;
    private static long lastClickTime;

    private Application application;

    public CommonUtil(Application application) {
        this.application = application;
    }

    /**
     * @param str
     * @return Md 加密数据
     */
    public String md5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : b) {
                String hex = Integer.toHexString(0xFF & aByte);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException ignored) {

        }
        return "";
    }

    public File getCacheDir() {
        return application.getExternalCacheDir();
    }

    public boolean isNetworkReachable() {
        ConnectivityManager cm = (ConnectivityManager) application
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        return current != null && (current.isAvailable());
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public String readFileToString(File file, String charset) {
        if (file == null)
            return null;
        String data = null;
        byte[] buffer = new byte[BUFFER_SIZE];
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            for (int len = -1; (len = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, len);
                if (out.size() > MAX_STRING_LENGTH) {
                    Log.e("IoUtils", "File too large, maybe not a string. " + file.getAbsolutePath());
                    return null;
                }
            }
            data = out.toString(charset);
        } catch (IOException e) {
            e.printStackTrace();
            data = null;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
            try {
                out.close();
            } catch (IOException e) {
            }
            in = null;
            out = null;
            buffer = null;
        }
        return data;
    }

    public static boolean saveStringToFile(String data, File file, String charset) {
        if (file == null || data == null)
            return false;
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(data.getBytes(charset));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                }
        }
        return false;
    }


    public ColorDrawable getCachePlaceHolder() {
        return new ColorDrawable(0xffdcdcdc);
    }

    public String getInterval(String createTime) {
        String interval = null;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date d1 = sd.parse(createTime, pos);
        long time = new Date().getTime() - d1.getTime();
        if (time / 1000 < 10) {
            interval = "刚刚";
        } else if (time / 3600000 < 24 && time / 3600000 > 0) {
            int h = (int) (time / 3600000);
            interval = h + "小时前";
        } else if (time / 60000 < 60 && time / 60000 > 0) {
            int m = (int) ((time % 3600000) / 60000);
            interval = m + "分钟前";
        } else if (time / 1000 < 60 && time / 1000 > 0) {
            int se = (int) ((time % 60000) / 1000);
            interval = se + "秒前";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            ParsePosition pos2 = new ParsePosition(0);
            Date d2 = sdf.parse(createTime, pos2);

            interval = sdf.format(d2);
        }
        return interval;
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     *                传入13位时间戳
     * @return
     */
    public static String  getStandardDate(String timeStr) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
        long month = (long) Math.ceil(time / 30 / 24 / 60 / 60 / 1000.0f);// 月
        if (month - 1 > 0) {
            //2019-08-15 17:29:18

            String date = DateUtils.stampToDate1(timeStr);
            LogUtils.e("转换后的时间" + date);
            String dateres = date;
            //sb.append(month + "月");
            //大于一个月直接返回日期
            return dateres;
        } else if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
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
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    public String getNextDayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return getTimeString(calendar);
    }

    public String getTimeString(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(calendar.getTime());
    }

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public String getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        if (s > 1000) {
            return String.format("%skm", Math.round(s / 1000 * 10) / 10.f);
        }
        return String.format("%sm", s);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return context.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取版本名
     *
     * @return 当前应用的版本号
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取包名
     *
     * @return 获取包名
     */
    public static String getPackageNameName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.packageName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastExecutek() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 解决oppoR9 TimeoutExceptions
     */
    public static void fix() {
//        try {
//            Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
//
//            Method method = clazz.getSuperclass().getDeclaredMethod("stop");
//            method.setAccessible(true);
//
//            Field field = clazz.getDeclaredField("INSTANCE");
//            field.setAccessible(true);
//            if (field != null) {
//                method.invoke(field.get(null));
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
        try {
            Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
            Method method = clazz.getSuperclass().getDeclaredMethod("stop");
            method.setAccessible(true);
            Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);
            method.invoke(field.get(null));
        } catch (Throwable e) {
            //e.printStackTrace();
            LogUtils.e("e:" + e.getMessage());
        }
        try {
            Class<?> c = Class.forName("java.lang.Daemons");
            Field maxField = c.getDeclaredField("MAX_FINALIZE_NANOS");
            maxField.setAccessible(true);
            maxField.set(null, Long.MAX_VALUE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前的时间戳
     *
     * @return
     */
    public static String getTimeStame() {
        //获取当前的毫秒值
        long time = System.currentTimeMillis();
        //将毫秒值转换为String类型数据
        String time_stamp = String.valueOf(time);
        //返回出去
        return time_stamp;
    }

    public static String getRealPathFromURI(Uri contentUri, Context context) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * @param
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    /**
     * 临时保存方法
     */
    public static String saveBitmap(Bitmap bitmap) {
        String imagepath = CommonConfig.getInstance().PATH_APP_IMAGE;
        // StringUtils.getTimeStame()
        String picName = CreateNoncestr(4) + ".png";
        File f = new File(imagepath, picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 1, out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }



//    /**
//     * 加密
//     *
//     * @param data 需要加密的字符串
//     * @return 返回加密的字符串
//     */
//    public String customEncryption(String data) {
//        System.out.println("------------------------------------ 自定义用法-加密 ------------------------------------");
//        /*
//         * 如果要更改加密行为，
//         * 可以减少迭代次数以获得更好的性能，
//         * 也可以将算法更改为可自定义的算法。
//         * 你可以使用你自己的加密来做这些事情。
//         * builder，你可以得到默认的e更改一些事情
//         */
//        Encryption encryption = null;
//        //调用
//        encryption = initEncryption();
//        //添加随机数
////        String m = m(data);
//        //只是打印加密文本以方便查看
//        String decrypt = encryption.encryptOrNull(m(data));
//        String decrypts = decrypt.substring(0, decrypt.length() - 1);
//        LogUtils.e("这是我们加密的密文：\n" +
//                "+------------------------------------------------------------------------------+\n" +
//                "| " + decrypts + " \n" +
//                "+------------------------------------------------------------------------------+\n");
//        return decrypts;
//    }


//    /**
//     * 解密
//     *
//     * @param data 需要解密的字符串
//     * @return 返回解密的字符串
//     */
//    public String customizedUsage(String data) {
//        /*
//         * 如果要更改加密行为，
//         * 可以减少迭代次数以获得更好的性能，
//         * 也可以将算法更改为可自定义的算法。
//         * 你可以使用你自己的加密来做这些事情。
//         * builder，你可以得到默认的e更改一些事情
//         */
//        Encryption encryption = null;
//        encryption = initEncryption();
//
//        //只是打印解密文本以方便查看
//        String decrypt = encryption.decryptOrNull(data);
//        String deciphering = deciphering(decrypt);
//        LogUtils.d("这是我们解密的密文：\n" +
//                "+----------------------------------------------------------+\n" +
//                "|" + deciphering + " \n" +
//                "+----------------------------------------------------------+\n");
//        return deciphering;
//    }

//    //加密配置
//    public Encryption initEncryption() {
//        Encryption encryption = null;
//        try {
//            encryption = new Encryption.Builder()
//                    .setKeyLength(128)
//                    .setKeyAlgorithm("AES")
//                    .setCharsetName("UTF8")
//                    .setIterationCount(65536)
//                    .setKey(CommonConstants.ENCRYPTION_KEY)
//                    .setDigestAlgorithm("SHA1")
//                    .setSalt("A beautiful salt")
//                    .setBase64Mode(Base64.DEFAULT)
//                    .setAlgorithm("AES/CBC/PKCS5Padding")
//                    .setSecureRandomAlgorithm("SHA1PRNG")
//                    .setSecretKeyType("PBKDF2WithHmacSHA1")
//                    .setIv(new byte[]{29, 88, -79, -101, -108, -38, -126, 90, 52, 101, -35, 114, 12, -48, -66, -30})
//                    .build();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return encryption;
//    }

    /**
     * 自定义解密字符
     *
     * @param data
     * @return
     */
    private static String deciphering(String data) {
        String str = "";
        for (int i = 0; i < data.length(); i++) {
            char charAt = data.charAt(i);
            if (i == 4 || i == 6 || i == 9 || i == 11 || i == 14 || i == 17) {
                str += charAt;
            }
        }
        return str;
    }

    /**
     * 自定义加密
     *
     * @param data
     * @return
     */
    private static String m(String data) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(CreateNoncestr(3))
                .append(getRandomChinese())
                .append(insert(data))
                .append(getRandomChinese());
        return stringBuffer.toString();
    }

    /**
     * 插入字符
     *
     * @param data
     * @return
     */
    private static String insert(String data) {
        StringBuffer sb = new StringBuffer(data);
        sb.insert(6, getRandomChinese());
        sb.insert(5, CreateNoncestr(2));
        sb.insert(4, CreateNoncestr(2));
        sb.insert(3, getRandomChinese());
        sb.insert(2, CreateNoncestr(2));
        sb.insert(1, getRandomChinese());
        return sb.toString();
    }

    /**
     * 创建随机字符
     */
    private static String CreateNoncestr(int num) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < num; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * 创建随机中文字符
     *
     * @return
     */
    private static String getRandomChinese() {
        return new String(new char[]{(char) (new Random().nextInt(20902) + 19968)});
    }

    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    public boolean copy(String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) application.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public String mul(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }


    public String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) application.getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = application.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 获取应用程序名称
     */
    public  synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Boolean isTuesdayData() {
        final Calendar c = Calendar.getInstance();
        //c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK) - 1);
        LogUtils.e("查看是周二吗" + mWay);
        if (TextUtils.equals("2", mWay)) {
            return true;
        } else {
            return false;
        }
//        if ("1".equals(mWay)) {
//            mWay = "天";
//        } else if ("2".equals(mWay)) {
//            mWay = "一";
//        } else if ("3".equals(mWay)) {
//            mWay = "二";
//        } else if ("4".equals(mWay)) {
//            mWay = "三";
//        } else if ("5".equals(mWay)) {
//            mWay = "四";
//        } else if ("6".equals(mWay)) {
//            mWay = "五";
//        } else if ("7".equals(mWay)) {
//            mWay = "六";
//        }
//        return mWay;
    }


    //到外部储存sdcard的状态
    private String sdcard = Environment.getExternalStorageState();
    //外部存储
    private String state = Environment.MEDIA_MOUNTED;

    /**
     * 计算Sdcard的剩余大小
     *
     * @return MB
     */
    public long getAvailableSize() {
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        if (sdcard.equals(state)) {
            //获得Sdcard上每个block的size
            long blockSize = statFs.getBlockSize();
            //获取可供程序使用的Block数量
            long blockavailable = statFs.getAvailableBlocks();
            //计算标准大小使用：1024，当然使用1000也可以
            long blockavailableTotal = blockSize * blockavailable / 1024 / 1024;
            return blockavailableTotal;
        } else {
            return -1;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 临时保存方法
     */
    public static String saveBitmap(Bitmap bitmap, String imagepath) {

        // StringUtils.getTimeStame()
        String picName = CreateNoncestr(8) + ".png";
        File f = new File(imagepath, picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 1, out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }


    //Android 10 判断文件是否存在
    public static boolean isAndroidQFileExists(Context context, String path) {
        AssetFileDescriptor afd = null;
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(path);
            afd = cr.openAssetFileDescriptor(uri, "r");
            if (afd == null) {
                return false;
            } else {
                close(afd);
            }
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            close(afd);
        }
        return true;
    }

    private static void close(AssetFileDescriptor afd) {
        if (afd != null) {
            try {
                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }

    }

}

