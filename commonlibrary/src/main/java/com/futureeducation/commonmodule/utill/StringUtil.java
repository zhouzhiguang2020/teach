package com.futureeducation.commonmodule.utill;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String getPercentString(float percent) {
        return String.format(Locale.US, "%d%%", (int) (percent * 100));
    }

    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    /**
     * 获取32位uuid
     *
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isEmpty(String input) {
        return TextUtils.isEmpty(input);
    }

    /**
     * 生成唯一号
     *
     * @return
     */
    public static String get36UUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }
    /**
     * 产生 1 个 GUID
     * Java 产生 UUID / GUID，利用导入类 java.util.UUID，
     * 生成的 UUID / GUID为一串32位字符组成的128位数据，可以做到全球唯一。
     *
     * @return
     */
    public static String generateGUID() {
        // 产生 1 个 GUID
        String guid = "";
        // 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        String a = uuid.toString();
        // 转换为大写
        a = a.toUpperCase();
        // 替换 -
        // a = a.replaceAll("-", "");
        guid = a;
        return guid;
    }

    public static String makeMd5(String source) {
        return MD5.getStringMD5(source);
    }

    public static final String filterUCS4(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        if (str.codePointCount(0, str.length()) == str.length()) {
            return str;
        }

        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (index < str.length()) {
            int codePoint = str.codePointAt(index);
            index += Character.charCount(codePoint);
            if (Character.isSupplementaryCodePoint(codePoint)) {
                continue;
            }

            sb.appendCodePoint(codePoint);
        }

        return sb.toString();
    }

    /**
     * counter ASCII character as one, otherwise two
     *
     * @param str
     * @return count
     */
    public static int counterChars(String str) {
        // return
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            int tmp = (int) str.charAt(i);
            if (tmp > 0 && tmp < 127) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    //保留后两位数字
    public static float getBigDecimal(float percent) {
        BigDecimal b = new BigDecimal(percent);
        float m_price = b.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
        return m_price;
    }

    /**

     * 利用正则表达式判断字符串是否是数字

     * @param str

     * @return

     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
         return baos.toByteArray();
     }

    // 将数字转换成字母
    public static String numToLetter(String input) {
        String letter = "";
        if(input!=null){
            if(isNumerics(input)){
                for (byte b : input.getBytes()) {
                    String let = ((char) (b + 48))+"";
                    letter = letter+let.toUpperCase();
                }
                return letter;
            }else{
                return input;
            }
        }else{
            return letter;
        }

    }

    //判断字符串是不是数字
    public static boolean isNumerics(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static boolean isNumber(String str){

        String reg = "^[0-9]+(.[0-9]+)?$";

        return str.matches(reg);

    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
