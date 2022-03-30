package com.futureeducation.commonmodule.http.callback;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.yanzhenjie.kalle.download.SimpleCallback;
import com.yanzhenjie.kalle.exception.ConnectTimeoutError;
import com.yanzhenjie.kalle.exception.HostError;
import com.yanzhenjie.kalle.exception.NetworkError;
import com.yanzhenjie.kalle.exception.ReadTimeoutError;
import com.yanzhenjie.kalle.exception.URLError;
import com.yanzhenjie.kalle.exception.WriteException;


/**
 * 下载监听回调
 */
public abstract class DownloadCallback extends SimpleCallback {

    private Context mContext;

    public DownloadCallback(Context context) {
        mContext = context;
    }

    @Override
    public final void onException(Exception e) {
        String message;
        if (e instanceof NetworkError) {
            message = mContext.getString(R.string.http_exception_network);
        } else if (e instanceof URLError) {
            message = mContext.getString(R.string.http_exception_url);
        } else if (e instanceof HostError) {
            message = mContext.getString(R.string.http_exception_host);
        } else if (e instanceof ConnectTimeoutError) {
            message = mContext.getString(R.string.http_exception_connect_timeout);
        } else if (e instanceof WriteException) {
            message = mContext.getString(R.string.http_exception_write);
        } else if (e instanceof ReadTimeoutError) {
            message = mContext.getString(R.string.http_exception_read_timeout);
        } else {
            message = mContext.getString(R.string.http_exception_unknow_error);
        }
        LogUtils.e("报错了：" + e.getMessage());
        e.printStackTrace();
        onException(message);
    }

    /**
     * Error message.
     */
    public abstract void onException(String message);

}
