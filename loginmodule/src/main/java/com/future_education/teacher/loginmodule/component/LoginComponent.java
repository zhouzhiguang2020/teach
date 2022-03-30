package com.future_education.teacher.loginmodule.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.billy.cc.core.component.remote.RemoteCC;
import com.future_education.teacher.loginmodule.activity.BindActivity;
import com.future_education.teacher.loginmodule.activity.LoginActivity;
import com.future_education.teacher.loginmodule.constant.LoginConstant;
import com.future_education.teacher.loginmodule.event.AccountExpiredEvent;
import com.futureeducation.commonmodule.component.CompocomponentName;
import com.threshold.rxbus2.RxBus;


/**
 * 调用login全新入口
 */
public class LoginComponent implements IComponent {
    private CC cc;
    private Context context;

    @Override
    public String getName() {
        return CompocomponentName.Companion.getLOGIN_COMPINENR();
    }

    /**
     * 组件被调用时的入口
     * 要确保每个逻辑分支都会调用到CC.sendCCResult，
     * 包括try-catch,if-else,switch-case-default,startActivity
     *
     * @param cc 组件调用对象，可从此对象中获取相关信息
     * @return true:将异步调用CC.sendCCResult(...),用于异步实现相关功能，例如：文件加载、网络请求等
     * false:会同步调用CC.sendCCResult(...),即在onCall方法return之前调用，否则将被视为不合法的实现
     */
    @Override
    public boolean onCall(CC cc) {
        this.cc = cc;
        if (context == null) {
            context = cc.getContext();
        }
        String actionName = cc.getActionName();
        if (TextUtils.equals(actionName, CompocomponentName.Companion.getLOGIN())) {
            openLoginActivity(cc);
            return true;

        } else if (TextUtils.equals(actionName, CompocomponentName.Companion.getLOGIN_OVERDE())) {
            //我的token已经过期
            LoginAgain();
            return false;
        } else if (TextUtils.equals(actionName, CompocomponentName.Companion.getLOGIN_OUT())) {
            Loginout();
            return false;
        } else if (TextUtils.equals(actionName, CompocomponentName.Companion.getBind())) {
            BindActivity(cc);
            return false;
        } else if (TextUtils.equals(actionName, CompocomponentName.Companion.getACCOUNT_EXPIRED())) {

            //账号过期
            AccountExpiredEvent event = new AccountExpiredEvent();
            RxBus.getDefault().post(event);
            CC.sendCCResult(cc.getCallId(), CCResult.success());
            return false;
        }
        return false;
    }

    //退出登录
    private void Loginout() {
        Intent intent = CCUtil.createNavigateIntent(cc, LoginActivity.class);
        RemoteCC remoteCC = new RemoteCC(cc);
        intent.putExtra(CCUtil.EXTRA_KEY_REMOTE_CC, remoteCC);
        intent.putExtra(CCUtil.EXTRA_KEY_CALL_ID, cc.getCallId());
        intent.putExtra(LoginConstant.Companion.getLOGIN_TYPE(), LoginConstant.Companion.getLOGIN_OUT_TYPE());
        if (!(context instanceof Activity)) {
            //调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }

    //
    private void LoginAgain() {
        Intent intent = CCUtil.createNavigateIntent(cc, LoginActivity.class);
        RemoteCC remoteCC = new RemoteCC(cc);
        intent.putExtra(CCUtil.EXTRA_KEY_REMOTE_CC, remoteCC);
        intent.putExtra(CCUtil.EXTRA_KEY_CALL_ID, cc.getCallId());
        intent.putExtra(LoginConstant.Companion.getLOGIN_INVALID(), true);
        intent.putExtra(LoginConstant.Companion.getLOGIN_TYPE(), LoginConstant.Companion.getLOGIN_DATE_TYPE());
        if (!(context instanceof Activity)) {
            //调用方没有设置context或app间组件跳转，context为application FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }

    //异步跳转到登录界面
    private void openLoginActivity(CC cc) {
        Intent intent = CCUtil.createNavigateIntent(cc, LoginActivity.class);
        RemoteCC remoteCC = new RemoteCC(cc);
        intent.putExtra(CCUtil.EXTRA_KEY_REMOTE_CC, remoteCC);
        intent.putExtra(CCUtil.EXTRA_KEY_CALL_ID, cc.getCallId());
        if (!(context instanceof Activity)) {
            //调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        context.startActivity(intent);

    }


    private void BindActivity(CC cc) {
        Intent intent = CCUtil.createNavigateIntent(cc, BindActivity.class);
        String unionid = cc.getParamItem("unionid");
        intent.putExtra("unionid", unionid);
        intent.putExtra(CCUtil.EXTRA_KEY_CALL_ID, cc.getCallId());
        context.startActivity(intent);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }
}
