package com.futureeducation.commonmodule.http.converter;

import com.apkfuns.logutils.LogUtils;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

public class ByteArrayConverter implements Converter {
    @Override
    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception {
        S succeedData = (S) response.body().stream();

        String serverJson = response.body().string();
        LogUtils.e("邀请好友：" + serverJson);
        return SimpleResponse.<S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .build();

    }
}
