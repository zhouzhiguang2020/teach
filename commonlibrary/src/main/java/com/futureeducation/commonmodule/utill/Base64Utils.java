package com.futureeducation.commonmodule.utill;

import android.util.Base64;

/**
 * @ClassName Base64Utils工具类
 * @Description TODO
 * @Author ASUS
 * @Date 2020/4/23 15:30
 * @Version 2.0
 */
public class Base64Utils {
    /**
     * 字符Base64加密
     *
     * @param str
     * @return
     */
    public static String encodeToString(String str) {
        if(str==null){
            return "";
        }
        try {
            return Base64.encodeToString(str.getBytes("UTF-8"), Base64.CRLF);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字符Base64解密
     *
     * @param str
     * @return
     */
    public static String decodeToString(String str) {
        if(str==null){
            return "";
        }
        try {
            return new String(Base64.decode(str.getBytes("UTF-8"), Base64.CRLF));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
