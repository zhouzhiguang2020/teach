package com.future_education.teacher.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.apkfuns.logutils.LogUtils;
import com.billy.cc.core.component.CC;
import com.future_education.loginmodule.presenter.WXEntryPresenter;
import com.future_education.teacher.contact.WXEntryContact;
import com.futureeducation.commonmodule.activities.CommonActivity;
import com.futureeducation.commonmodule.component.CompocomponentName;
import com.futureeducation.commonmodule.constants.CommonConstants;
import com.futureeducation.commonmodule.event.LoginSuccessEvent;
import com.futureeducation.commonmodule.model.TeacherBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.threshold.rxbus2.RxBus;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class WXEntryActivity extends CommonActivity implements IWXAPIEventHandler, WXEntryContact.View {

    private IWXAPI api;
    private WXEntryPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, CommonConstants.APP_ID, false);
        presenter = new WXEntryPresenter(this);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RxBus.getDefault().register(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:

                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (resp instanceof SendAuth.Resp) {
                    SendAuth.Resp newResp = (SendAuth.Resp) resp;
                    String code = newResp.code;
                    presenter.putWX(code);
                    LogUtils.e("code=" + code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                break;
            default:
                break;
        }

        finish();
    }


    @Override
    public void setPresenter(WXEntryPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void BindFailure(@NotNull String unionid) {
        CC.obtainBuilder(CompocomponentName.Companion.getLOGIN_COMPINENR())
                .setActionName(CompocomponentName.Companion.getBind())
                .addParam("unionid", unionid)
                .build().call();
    }

    @Override
    public void getUserSucess(@Nullable TeacherBean date) {
        CC.obtainBuilder(CompocomponentName.Companion.getMAIN_COMPINENT())
                .setActionName(CompocomponentName.Companion.getMAIN())
                .build().call();
        RxBus.getDefault().post(new LoginSuccessEvent());
        finish();
    }

    @Override
    public void getUserFailure() {

    }

    @Override
    public void AccountExpired() {
        CC.obtainBuilder(CompocomponentName.Companion.getLOGIN_COMPINENR())
                .setActionName(CompocomponentName.Companion.getACCOUNT_EXPIRED())
                .build().call();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }
}