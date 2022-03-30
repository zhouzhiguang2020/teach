package com.futureeducation.commonmodule.http.converter;

import com.apkfuns.logutils.LogUtils;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

/**
 * 网络请求自定义转换器
 *
 *  object : TypeReference<ResponseList<APIBean>>()
 */
public class StringConverter implements Converter {


    @Override
    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception {
        S succeedData = null; // The data when the business successful.
        F failedData = null; // The data when the business failed.
        int code = response.code();

        if (code >= 200 && code < 300) { // Http is suc
            succeedData = (S) response.body().string();

        } else {
            failedData = (F) ((F) "请求状态码：" + (String.valueOf(code)));
        }
        LogUtils.e("请求成功返回" + response.body().string());
        return SimpleResponse.<S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .failed(failedData)
                .build();

    }
}
