package com.futureeducation.commonmodule.update.module;


import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.update.callback.DownloadCallback;
import com.futureeducation.commonmodule.update.config.DownloadKey;
import com.futureeducation.commonmodule.update.config.UpdateKey;
import com.futureeducation.commonmodule.update.utils.GetAppInfo;
import com.futureeducation.commonmodule.update.utils.InstallApk;
import com.futureeducation.commonmodule.update.utils.StorageUtils;
import com.futureeducation.commonmodule.update.view.DownLoadDialog;
import com.futureeducation.commonmodule.utill.ToastUtils;
import com.yanzhenjie.kalle.Canceller;
import com.yanzhenjie.kalle.Kalle;

import java.io.File;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;

/**
 * 更改用kalle的下载
 */
public class Download extends Thread {

    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private static final int DOWN_ORROR = 3;//下载失败了
    private static final int CHECK_APK_ERROR = 4;//checkApk检查apk文件错误
    private static final int CHECK_APK_ERROR1 = 5;//checkApk检查apk文件错误
    private Down_handler handler;

    private static int length;
    private static int count;

    private Context context;
    private static File apkFile;
    private static Canceller mCanceller;
    private int tempproess;//零时进度

    public Download(Context context) {
        this.context = context;
        handler = new Down_handler(context);
    }

    public Download(Context context, androidx.core.app.NotificationCompat.Builder builder) {
        this.context = context;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        handler = new Down_handler(context, builder, notificationManager);
    }


    private class Down_handler extends Handler {
        WeakReference<Context> mContextReference;

        NotificationCompat.Builder builder;
        NotificationManager notificationManager;

        Down_handler(Context context) {
            mContextReference = new WeakReference<>(context);
        }

        Down_handler(Context context, androidx.core.app.NotificationCompat.Builder builder, NotificationManager notificationManager) {
            mContextReference = new WeakReference<>(context);
            this.builder = builder;
            this.notificationManager = notificationManager;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            Context context = mContextReference.get();

            switch (msg.what) {
                case DOWN_UPDATE:
                    int progress = (int) msg.arg1;
                    if (UpdateKey.DialogOrNotification == 1) {
                        String speed = (String) msg.obj;
                        ((DownLoadDialog) context).progressBar.setProgress(progress);
                        String hasprogress = context.getString(R.string.downloader_component_size);
                        String hastring = String.format(hasprogress, progress);
                        ((DownLoadDialog) context).textView.setText(hastring + "%");
                        String speedstring = context.getString(R.string.download_speed);
                        ((DownLoadDialog) context).speed.setText(speed);
                    } else if (UpdateKey.DialogOrNotification == 2) {

                        if (count >= length) {
                            builder.setProgress(length, count, false)
                                    .setContentText("下载进度:" + 100 + "%");
                            builder.setTicker("下载完成");
                            NotificationManagerCompat.from(context).notify(UpdateKey.NOTIFICATIONID, builder.build());
                            NotificationManagerCompat.from(context).cancel(UpdateKey.NOTIFICATIONID);
                        } else {
                            LogUtils.e("显示出来了吗==");
                            builder.setProgress(length, count, false)
                                    .setContentText("下载进度:" + progress + "%");
                            NotificationManagerCompat.from(context).notify(UpdateKey.NOTIFICATIONID, builder.build());

                        }
                    }
                    break;
                case DOWN_OVER:
                    if (UpdateKey.DialogOrNotification == 1) {
                        ((DownLoadDialog) context).finish();
                    } else if (UpdateKey.DialogOrNotification == 2) {
                        builder.setTicker(context.getString(R.string.download_completes));
                        notificationManager.notify(UpdateKey.NOTIFICATIONID, builder.build());
                        notificationManager.cancel(UpdateKey.NOTIFICATIONID);
                    }
                    length = 0;
                    count = 0;
                    DownloadKey.TOShowDownloadView = 1;
                    if (DownloadKey.ISManual) {
                        DownloadKey.LoadManual = false;
                    }
                    if (mCanceller != null) {
                        mCanceller.isCancelled();
                    }
                    if (checkApk(context)) {
                        Log.i("UpdateFun TAG", "APK路径:" + apkFile);
                        InstallApk.startInstall(context, apkFile);
                    }

                    break;
                case DOWN_ORROR:
                    String string = (String) msg.obj;
                    if (UpdateKey.DialogOrNotification == 1) {
                        ((DownLoadDialog) context).finish();
                    } else if (UpdateKey.DialogOrNotification == 2) {
                        notificationManager.cancel(UpdateKey.NOTIFICATIONID);
                    }
                    ToastUtils.showToast(context, string);
                    break;
                case CHECK_APK_ERROR:
                    ToastUtils.showToast(context, "服务器下载的文件错误");

                    break;
                case CHECK_APK_ERROR1:
                    ToastUtils.showToast(context, "apk检验:包名不同,不进行安装,原因可能是运营商劫持");
                    break;
                default:
                    break;
            }
        }

    }

    public void run() {
//        URL url = null;
//        HttpURLConnection conn = null;
//        InputStream is = null;
//
//        if (TextUtils.isEmpty(DownloadKey.apkUrl)) {
//            return;
//        }
//        try {
//            url = new URL(DownloadKey.apkUrl);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(20 * 1000);
//            conn.connect();
//            length = conn.getContentLength();
//
//            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
//                is = conn.getInputStream();
//            } else {
//                handler.sendEmptyMessageDelayed(DOWN_ORROR, 1000);
//            }
//
//        } catch (FileNotFoundException e0) {
//            e0.printStackTrace();
//            try {
//                conn.disconnect();
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setInstanceFollowRedirects(false);
//                conn.connect();
//                String location = new String(conn.getHeaderField("Location").getBytes("ISO-8859-1"), "UTF-8").replace(" ", "");
//                url = new URL(location);
//                conn = (HttpURLConnection) url.openConnection();
//                conn.connect();
//                length = conn.getContentLength();
//
//                if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
//                    is = conn.getInputStream();
//                } else {
//                    handler.sendEmptyMessageDelayed(DOWN_ORROR, 1000);
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
//        if (is != null) {
//
//            try {
//                File file = StorageUtils.getCacheDirectory(context);
//                if (!file.exists()) {
//                    file.mkdir();
//                }
//
//                apkFile = new File(file, DownloadKey.saveFileName);
//                FileOutputStream fos = new FileOutputStream(apkFile);
//                long tempFileLength = file.length();
//                byte buf[] = new byte[1024];
//                int times = 0; //这很重要
//                int numread;
//
//                do {
//                    numread = is.read(buf);
//                    count += numread;
//                   //progress = (int) (((double) sum / total) * 100);
//                    progress = (int) (((double) count / length) * 100);
//                    if ((times == 512) || (tempFileLength == length)) {
//                        handler.sendEmptyMessageDelayed(DOWN_UPDATE, 1000);
//                        times = 0;
//                    }
//                    times++;
//                    if (numread <= 0) {
//                        handler.sendEmptyMessage(DOWN_OVER);
//                        break;
//                    }
//                    fos.write(buf, 0, numread);
//                } while (!DownloadKey.interceptFlag);// 点击取消就停止下载.
//                fos.flush();
//                fos.close();
//                is.close();
//                conn.disconnect();
//
//                if (DownloadKey.interceptFlag) {
//                    Log.i("UpdateFun TAG", "interrupt");
//                    length = 0;
//                    count = 0;
//                    DownloadKey.interceptFlag = false;
//                    this.interrupt();
//                }
//            } catch (IOException e3) {
//                e3.printStackTrace();
//            }
//        }

        File file = StorageUtils.getCacheDirectory(context);
        if (!file.exists()) {
            file.mkdir();
        }
        apkFile = new File(file, DownloadKey.saveFileName);
        String downur = DownloadKey.apkUrl;
        LogUtils.e("查看下载url：" + downur);

        mCanceller = Kalle.Download.get(downur).
                removeHeader("Authorization").
                directory(file.getAbsolutePath())
                .fileName(DownloadKey.saveFileName)
                .onProgress(new com.yanzhenjie.kalle.download.Download.ProgressBar() {
                    @Override
                    public void onProgress(int progress, long byteCount, long speed) {
                        BigDecimal bg = new BigDecimal(speed / 1024D);
                        String speedText = bg.setScale(2, BigDecimal.ROUND_CEILING).toPlainString();
                        speedText = context.getString(R.string.download_speed, speedText);
                        LogUtils.d("正在下载：速度是" + speedText);
                        if (length == 0 && progress != 0) {
                            length = (int) ((byteCount / progress) * 100);
                        }
                        count = (int) byteCount;
                        Message message = new Message();
                        message.what = DOWN_UPDATE;
                        message.arg1 = progress;
                        message.obj = speedText;
                        if (progress > tempproess) {
                            handler.sendMessageDelayed(message, 2000);
                            tempproess = progress;
                        }

                    }
                })
                .perform(new DownloadCallback(context) {
                    @Override
                    public void onException(String message) {
                        Message msg = new Message();
                        msg.what = DOWN_ORROR;
                        msg.obj = message;
                        handler.sendMessage(msg);
                        LogUtils.e("下载报错了");
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onStart() {
                        LogUtils.e("开始下载了");

                    }

                    @Override
                    public void onFinish(String path) {
                        handler.sendEmptyMessage(DOWN_OVER);
                    }
                });
    }


    /**
     * 停止取消下载
     */
    public void Canceller() {
        if (mCanceller != null) {
            if (!mCanceller.isCancelled()) {
                mCanceller.cancel();
                LogUtils.e("取消下载--");
            }
        }
    }

    private boolean checkApk(Context context) {
        String apkName = GetAppInfo.getAPKPackageName(context, apkFile.getAbsolutePath());
        LogUtils.e("查看apk文件路径：" + apkFile.getAbsolutePath());
        String appName = GetAppInfo.getAppPackageName(context);
        if (TextUtils.isEmpty(apkName)) {
            Message msg = new Message();
            msg.what = CHECK_APK_ERROR;
            handler.sendMessage(msg);
            LogUtils.e("查看包名：" + apkName);
            return false;
        }
        if (apkName.equals(appName)) {
            LogUtils.e("UpdateFun TAG" + "apk检验:包名相同,安装apk");

            return true;
        } else {
            LogUtils.e("UpdateFun TAG" +
                    String.format("apk检验:包名不同。该app包名:%s，apk包名:%s", appName, apkName));
            Message msg = new Message();
            msg.what = CHECK_APK_ERROR1;
            handler.sendMessage(msg);

            return false;
        }
    }

}