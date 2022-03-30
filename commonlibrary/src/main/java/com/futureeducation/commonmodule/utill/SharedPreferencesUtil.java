/**
 * $Id: PreferenceUitl.java,v 1.1 2011/05/20 07:28:07 jian.hu Exp $
 */
package com.futureeducation.commonmodule.utill;

import android.content.Context;

import com.futureeducation.commonmodule.constants.CommonConstants;
import com.tencent.mmkv.MMKV;


/**
 * 首选项工具类
 */
public class SharedPreferencesUtil {

    // 首选项
    public static final String PREFERENCE_NAME = CommonConstants.Companion.getPREFERENCE_NAME(); // 首选项名称

    private static SharedPreferencesUtil preferenceUitl;

    private final MMKV mmkv;


    private SharedPreferencesUtil(Context context) {

        mmkv = MMKV.mmkvWithID(PREFERENCE_NAME, MMKV.MULTI_PROCESS_MODE);

    }


    public static SharedPreferencesUtil getInstance(Context context) {
        if (preferenceUitl == null) {
            preferenceUitl = new SharedPreferencesUtil(context);
        }


        return preferenceUitl;
    }

    public void saveLong(String key, long l) {
        mmkv.encode(key, l);
    }

    public long getLong(String key, long defaultlong) {
        return mmkv.decodeLong(key);
    }

    public void saveBoolean(String key, boolean value) {
        mmkv.encode(key, value);
    }

    public boolean getBoolean(String key, boolean defaultboolean) {
        return mmkv.decodeBool(key, defaultboolean);
    }

    public void saveInt(String key, int value) {
        mmkv.encode(key, value);
    }

    public int getInt(String key, int defaultInt) {

        return mmkv.decodeInt(key, defaultInt);
    }

    public String getString(String key, String defaultInt) {
        String result = mmkv.decodeString(key, defaultInt);
        return result;
    }

    public String getString(Context context, String key, String defaultValue) {
        return getString(key, defaultValue);
    }

    public void saveString(String key, String value) {
        mmkv.encode(key, value);
    }

    public void remove(String key) {
        mmkv.remove(key);
    }

    public void destroy() {
    }

    public void clearpreference() {
        mmkv.clearAll();

    }
}
