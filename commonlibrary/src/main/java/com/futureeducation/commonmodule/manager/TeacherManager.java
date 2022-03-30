package com.futureeducation.commonmodule.manager;


import android.content.Context;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.application.CommonApplication;
import com.futureeducation.commonmodule.constants.CommonConstants;
import com.futureeducation.commonmodule.model.TeacherBean;
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil;

import org.jetbrains.annotations.NotNull;


/**
 * 管理用户的登录信息和个人信息
 * 登录c端的用户保存信息
 */
public class TeacherManager {


    private static Context context;
    private static TeacherBean user;
    private static TeacherManager sInstance;

    public TeacherManager(Context context) {
        TeacherManager.context = context;
        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(context);
        String userId = util.getString(CommonConstants.Companion.getUSER_ID(), null);
        String userToken = util.getString(CommonConstants.Companion.getUSER_TOKEN(), null);
        String unitId = util.getString(CommonConstants.Companion.getUSER_UNOID(), null);
        String userName = util.getString(CommonConstants.Companion.getUSER_NAME(), null);
        String userAccount = util.getString(CommonConstants.Companion.getUSER_ACCOUNT(), null);
        String unit_session_uid = util.getString(CommonConstants.Companion.getUNIT_SESSION_UID(), null);
        String per_pic = util.getString(CommonConstants.Companion.getPER_PIC(), null);
        int session_week = util.getInt(CommonConstants.Companion.getSESSION_WEEK(), 0);
        LogUtils.e("查看："+unitId);
        LogUtils.e("查看："+userToken);
        LogUtils.e("查看："+unitId);
        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(userToken)) {
            user = new TeacherBean();
            user.setUserId(userId);
            user.setToken(userToken);
            user.setUnitId(unitId);
            user.setUserName(userName);
            user.setUserAccount(userAccount);
            user.setPer_pic(per_pic);
            user.setUnit_session_uid(unit_session_uid);
            user.setSession_week(session_week);
        } else {
            user = null;
        }
        LogUtils.e("初始化数据" + user);
    }

    /**
     * 判断用户是否已经登录
     */
    public static boolean isLogined() {
        return user != null;
    }

    public static TeacherBean getUser() {
        if (context == null) {
            context = CommonApplication.Companion.getInstance();
        }
        if (user == null) {
            SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(context);
            String userId = util.getString(CommonConstants.Companion.getUSER_ID(), null);
            String userToken = util.getString(CommonConstants.Companion.getUSER_TOKEN(), null);
            String unitId = util.getString(CommonConstants.Companion.getUSER_UNOID(), null);
            String userName = util.getString(CommonConstants.Companion.getUSER_NAME(), null);
            String userAccount = util.getString(CommonConstants.Companion.getUSER_ACCOUNT(), null);
            String unit_session_uid = util.getString(CommonConstants.Companion.getUNIT_SESSION_UID(), null);
            String per_pic = util.getString(CommonConstants.Companion.getPER_PIC(), null);
            int session_week = util.getInt(CommonConstants.Companion.getSESSION_WEEK(), 0);
            if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(userToken)) {
                user = new TeacherBean();
                user.setUserId(userId);
                user.setToken(userToken);
                user.setUnitId(unitId);
                user.setUserName(userName);
                user.setUserAccount(userAccount);
                user.setPer_pic(per_pic);
                user.setUnit_session_uid(unit_session_uid);
                user.setSession_week(session_week);
            }
        }

        return user;
    }

    public void setUser(TeacherBean user) {
        if (user != null) {
            SharedPreferencesUtil sp = SharedPreferencesUtil.getInstance(context);
            sp.saveString(CommonConstants.Companion.getUSER_ID(), user.getUserId());
            sp.saveString(CommonConstants.Companion.getUSER_TOKEN(), user.getToken());
            sp.saveString(CommonConstants.Companion.getUSER_UNOID(), user.getUnitId());
            sp.saveString(CommonConstants.Companion.getUSER_NAME(), user.getUserName());
            sp.saveString(CommonConstants.Companion.getUSER_ACCOUNT(), user.getUserAccount());
            sp.saveString(CommonConstants.Companion.getUNIT_SESSION_UID(), user.getUnit_session_uid());
            sp.saveString(CommonConstants.Companion.getPER_PIC(), user.getPer_pic());
            sp.saveInt(CommonConstants.Companion.getSESSION_WEEK(), user.getSession_week());


//        if(null == user){
//            JPushInterface.setAlias(CustomApplication.getInstance(), "", null);
//        }else {
//            JPushInterface.setAlias(CustomApplication.getInstance(), user.getJpush_alias(), null);
//            Log.e("OkHttp", "Alia:" + user.getJpush_alias());
//        }
            LogUtils.e("登录保存数据 token:" + user.toString());
        }
    }


    /**
     * 设置推送别名
     */
    public void setAlias(String alias) {

    }

    @NotNull
    public boolean isLogineds() {
        return isLogined();
    }

}