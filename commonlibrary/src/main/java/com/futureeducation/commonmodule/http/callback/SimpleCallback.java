package com.futureeducation.commonmodule.http.callback;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.utill.ToastUtils;
import com.yanzhenjie.kalle.exception.ConnectTimeoutError;
import com.yanzhenjie.kalle.exception.HostError;
import com.yanzhenjie.kalle.exception.NetworkError;
import com.yanzhenjie.kalle.exception.ReadTimeoutError;
import com.yanzhenjie.kalle.exception.URLError;
import com.yanzhenjie.kalle.exception.WriteException;
import com.yanzhenjie.kalle.simple.Callback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @param <S> Context上下文对象
 *            网络请求回调信息
 */
public abstract class SimpleCallback<S> extends Callback<S, String> {

    private Context mContext;

    public SimpleCallback(Context context) {
        this.mContext = context;
    }

    @Override
    public Type getSucceed() {
        Type superClass = getClass().getGenericSuperclass();
        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    @Override
    public Type getFailed() {
        return String.class;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onException(Exception e) {
        String message;
        if (e instanceof NetworkError) {
            message = mContext.getString(R.string.http_exception_network);
            ToastUtils.showToast(mContext, message);
        } else if (e instanceof URLError) {
            message = mContext.getString(R.string.http_exception_url);
        } else if (e instanceof HostError) {
            message = mContext.getString(R.string.http_exception_host);
        } else if (e instanceof ConnectTimeoutError) {
            message = mContext.getString(R.string.http_exception_connect_timeout);
            ToastUtils.showToast(mContext, message);
        } else if (e instanceof WriteException) {
            message = mContext.getString(R.string.http_exception_write);
        } else if (e instanceof ReadTimeoutError) {
            message = mContext.getString(R.string.http_exception_read_timeout);
        } else {
            message = mContext.getString(R.string.http_exception_unknow_error);
        }
        LogUtils.e("网络请求什么错误 :" + message);
        e.printStackTrace();
        onResponse(SimpleResponse.<S, String>newBuilder()
                .failed(message)
                .build());
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onEnd() {
    }
}
