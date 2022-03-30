package com.futureeducation.commonmodule.update.module;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.update.Requestinterface.UpdateInfoResult;
import com.futureeducation.commonmodule.update.config.DownloadKey;
import com.futureeducation.commonmodule.update.config.UpdateKey;
import com.futureeducation.commonmodule.update.location.AppUpdateUrlFactory;
import com.futureeducation.commonmodule.update.request.RequestProtocol;
import com.futureeducation.commonmodule.update.utils.GetAppInfo;


/**
 * 请求网络数据检查跟新版本 线程
 */
public class Update extends Thread {

    private String result;
    private String url; // "http://api.fir.im/apps/latest/" + UpdateKey.APP_ID
    //+ "?api_token=" + UpdateKey.API_TOKEN;//UpdateKey.CHECK_APP_URL;
    private Context context;
    private OnUpdateInfoListener listener;
    private UpdateBean updateBean;
    private int versioncode;

    public Update(Context context) {

        this.context = context;
        versioncode = (int) GetAppInfo.getVerCode(context);

    }


    @Override
    public void run() {

        if (updateBean == null) {
            RequestProtocol protocol = new RequestProtocol(context);
            url = AppUpdateUrlFactory.getAppUpdateUrl();
            LogUtils.e("查看地址：" + url);
            protocol.getUpdateInfo(url, new UpdateInfoResult() {

                @Override
                public void getUpdateResult(UpdateBean bean) {

                    if (bean != null) {
                        LogUtils.e("回调有了吗--" + bean.toString());
                        updateBean = bean;
                        DownloadKey.version = bean.getVersion_code();
                        DownloadKey.apkUrl = bean.getApp_url();
                        DownloadKey.changeLog = bean.getUpgrade_point();
                        int isCompel = bean.getType();
                        if (isCompel == 2) {
                            //非强制神经
                            DownloadKey.IsCompel = true;
                        } else {
                            //强制升级
                            DownloadKey.IsCompel = false;

                        }

                        int version_id = bean.getVersion_id();
                        LogUtils.e("调试升级：" + version_id);
                        LogUtils.e("调试versioncode：" + versioncode);
//                        versioncode = 0;
                        if (versioncode >= version_id) {
                            DownloadKey.IsNeedUpdate = false;
                        } else {
                            DownloadKey.IsNeedUpdate = true;
                        }

                        //测试下载类型
                        int UpdateType = 0;
                        if (UpdateType == 0) {
                            //dilog下载
                            UpdateKey.DialogOrNotification = UpdateKey.WITH_DIALOG;

                        } else if (UpdateType == 1) {
                            //notice下载 有时候没有权限
                            UpdateKey.DialogOrNotification = UpdateKey.WITH_NOTIFITION;
                        } else if (UpdateType == 2) {
                            UpdateKey.DialogOrNotification = 3;
                        } else {
                            UpdateKey.DialogOrNotification = UpdateKey.WITH_DEFAULT;
                        }
                        listener.OnRequestFinish();
                    } else {
                        LogUtils.e("请求为空了：" + bean == null);
                        listener.OnRequestFinish();
                    }


                }
            });
        }


    }

    private void interpretingData(String result) {
        LogUtils.e("打印请求结果" + result);
//        try {
//            JSONObject object = new JSONObject(result);
//            DownloadKey.changeLog = object.getString("changelog");
//            DownloadKey.version = object.getString("versionShort");
//            DownloadKey.apkUrl = object.getString("installUrl");
//            LogUtils.e("UpdateFun TAG" +
//                    String.format("ChangeLog:%s, Version:%s, ApkDownloadUrl:%s",
//                            DownloadKey.changeLog, DownloadKey.version, DownloadKey.apkUrl));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public void setOnUpdateInfoListener(OnUpdateInfoListener listener) {
        this.listener = listener;

    }

    public interface OnUpdateInfoListener {
        void OnRequestFinish();

        //请求接口信息失败了
        void OnRequestdefeated();
    }
}
