package com.futureeducation.commonmodule.http.converter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.application.CommonApplication;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

public class RequestConverter implements Converter {
    private Context mContext;
    //private Gson gson;


    public RequestConverter() {
    }

    public RequestConverter(Context constant) {
        //  this.gson = new Gson();
        this.mContext = constant;

    }

    @Override
    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception {
        S succeedData = null; // The data when the business successful.
        F failedData = null; // The data when the business failed.
        int code = response.code();
        String serverJson = "";
        if (code >= 200 && code < 300) { // Http is successful.
            serverJson = response.body().string();
            LogUtils.e("返回结果json：" + serverJson);
            succeedData = JSON.parseObject(serverJson, succeed);
        } else if (code >= 400 && code < 500) {

            if (mContext != null) {
                failedData = (F) ("请求状态码：" + code + ":" + mContext.getString(R.string.http_unknow_error));
            } else {
                failedData = (F) (("请求状态码：" + code));
            }
        } else if (code >= 500) {
            if (mContext != null) {
                failedData = (F) ("请求状态码：" + (String.valueOf(code) + ":" + mContext.getString(R.string.http_server_error)));
            } else {
                failedData = (F) ("请求状态码：" + (String.valueOf(code)));
            }
        }
        LogUtils.w("网络请求返回 :" + serverJson);
        ResponseBean bean = JSONObject.parseObject(serverJson, ResponseBean.class);
        if (bean != null) {
            if (bean.getStatesCode() != null) {
                if (bean.getStatesCode().equals("1011")) {
                    CommonApplication.Companion.getInstance().LoginOverdue(2);
                }
            }
        }

        return SimpleResponse.<S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .failed(failedData)
                .build();
    }
}
