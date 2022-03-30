package com.futureeducation.commonmodule.http.converter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

public class RequestGsonConverter implements Converter {
    private Context mContext;
    //private Gson gson;

    public RequestGsonConverter(Context constant) {
        this.mContext = constant;
        //gson = new Gson();

    }

    @Override
    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response,
                                               boolean fromCache) throws Exception {
        S succeedData = null; // The data when the business successful.
        F failedData = null; // The data when the business failed.
        int code = response.code();

        try {

            if (code >= 200 && code < 300) { // Http is successful.
                String serverJson = response.body().string();
                LogUtils.e("请求结果：" + serverJson);
                succeedData = JSON.parseObject(serverJson, succeed);
            } else if (code >= 400 && code < 500) {
                failedData = (F) ("请求状态码：" + (String.valueOf(code) + ":" + mContext.getString(R.string.http_unknow_error)));
            } else if (code >= 500) {
                failedData = (F) ("请求状态码：" + (String.valueOf(code) + ":" + mContext.getString(R.string.http_server_error)));
            }


        } catch (Exception e) {
            LogUtils.e("解析错误: " + e);
            failedData = (F) mContext.getString(R.string.http_server_data_format_error);
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
