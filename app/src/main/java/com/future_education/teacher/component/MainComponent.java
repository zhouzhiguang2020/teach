package com.future_education.teacher.component;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.future_education.teacher.activity.SplashActivity;
import com.future_education.teacher.activity.TeacherActivity;
import com.futureeducation.commonmodule.component.CompocomponentName;


/**
 * 主模块
 */
public class MainComponent implements IComponent {
    private CC cc;
    private Context context;


    @Override
    public String getName() {

        return CompocomponentName.Companion.getMAIN_COMPINENT();
    }

    /**
     * @param cc
     * @return true:将异步调用CC.sendCCResult(...),用于异步实现相关功能，例如：文件加载、网络请求等
     * *          false:会同步调用CC.sendCCResult(...),即在onCall方法return之前调用，否则将被视为不合法的实现
     */
    @Override
    public boolean onCall(CC cc) {
        this.cc = cc;
        if (context == null) {
            context = cc.getContext();
        }
        String actionName = cc.getActionName();
        LogUtils.e("我要调起：" + actionName);
        if (TextUtils.equals(actionName, CompocomponentName.Companion.getMAIN())) {
            opeanTeacherActivity(cc);
            return true;
        }

        return false;

    }

    private void StartSplash(CC cc) {
        CC.sendCCResult(cc.getCallId(), CCResult.success());
        Intent intent = CCUtil.createNavigateIntent(cc, SplashActivity.class);
        context.startActivity(intent);
    }

    private void opeanTeacherActivity(CC cc) {
//        Intent intent = CCUtil.createNavigateIntent(cc, TeacherActivity.class);
//        //设置标识数据被清空了
//        RemoteCC remoteCC = new RemoteCC(cc);
//        intent.putExtra(CCUtil.EXTRA_KEY_REMOTE_CC, remoteCC);
//        intent.putExtra(CCUtil.EXTRA_KEY_CALL_ID, cc.getCallId());
//        if (!(context instanceof Activity)) {
//            //调用方没有设置context或app间组件跳转，context为application
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
        //   context.startActivity(intent);

        CC.sendCCResult(cc.getCallId(), CCResult.success());
        Intent intent = CCUtil.createNavigateIntent(cc, TeacherActivity.class);
        context.startActivity(intent);

    }

}
