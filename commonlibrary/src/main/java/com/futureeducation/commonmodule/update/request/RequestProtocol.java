package com.futureeducation.commonmodule.update.request;

import android.content.Context;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.http.callback.SimpleCallback;
import com.futureeducation.commonmodule.http.converter.JsonConverter;
import com.futureeducation.commonmodule.http.converter.RequestGsonConverter;
import com.futureeducation.commonmodule.manager.TeacherManager;
import com.futureeducation.commonmodule.model.RequestResult;
import com.futureeducation.commonmodule.model.TeacherBean;
import com.futureeducation.commonmodule.update.Requestinterface.UpdateInfoResult;
import com.futureeducation.commonmodule.update.module.UpdateBean;
import com.futureeducation.commonmodule.update.utils.GetAppInfo;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleResponse;
import com.yanzhenjie.kalle.simple.cache.CacheMode;

import java.util.concurrent.TimeUnit;


public class RequestProtocol {
    /**
     * 用来请求升级数据的工具类
     */
    private Context context;


    private String app_id, token;

    public RequestProtocol(Context context) {
        this.context = context;
        TeacherBean teacherBean = TeacherManager.getUser();
        if (teacherBean != null) {
            token = teacherBean.getToken();
        }
        String PackageName = GetAppInfo.getAppPackageName(context);
        if (TextUtils.equals(PackageName, "com.future_education.teacher")) {
            //教师端
            app_id = String.valueOf(4);
        } else if (TextUtils.equals(PackageName, "com.future_education.teacherassistant")) {
            //教师助手
            app_id = String.valueOf(5);
        }

    }

    /*
    app id 2未来教育学生，4未来教育教师',5老师助手
    * */
    public void getUpdateInfo(String url, final UpdateInfoResult resultCallback) {
        if (TextUtils.isEmpty(token)) {
            TeacherBean bean = TeacherManager.getUser();
            if (bean != null) {
                token= bean.getToken();
            }
        }
        if (TextUtils.isEmpty(token)) {
            return;
        }

        Kalle.post(url)
                .cacheMode(CacheMode.NETWORK)
                .tag(this).converter(new RequestGsonConverter(context))
                .param("app_id", app_id)
                .setHeader("Authorization", "Bearer " + token)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .converter(new JsonConverter(context))
                .perform(new SimpleCallback<RequestResult<UpdateBean>>(context) {
                    @Override
                    public void onResponse(SimpleResponse<RequestResult<UpdateBean>, String> response) {

                        if (response != null) {
                            RequestResult result = response.succeed();
                            if (result != null) {
                                int code = result.getStatesCode();
                                if (code == 1001) {
                                    UpdateBean updateBean = (UpdateBean) result.getData();
                                    resultCallback.getUpdateResult(updateBean);
                                } else {
                                    LogUtils.e("错误了：" + result.getMessage());
                                    resultCallback.getUpdateResult(null);
                                }
                            } else {
                                LogUtils.e("错误：" + response.failed());
                                resultCallback.getUpdateResult(null);
                            }
                        } else {
                            LogUtils.e("错误了：" + response == null);
                            resultCallback.getUpdateResult(null);
                        }

                    }
                });

    }
}
