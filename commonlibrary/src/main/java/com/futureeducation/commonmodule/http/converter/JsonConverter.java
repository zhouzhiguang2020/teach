package com.futureeducation.commonmodule.http.converter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.apkfuns.logutils.LogUtils;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

/**
 * Created by ${zhou}.
 * User: Administrator
 * Date: 2019/4/30
 * Time: 10:54
 * 请求转换器
 */
public class JsonConverter implements Converter {
    private Context mContext;
    // private Gson gson;

    public JsonConverter(Context context) {
        //   gson = new Gson();
        this.mContext = context;
    }

    @Override
    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception {
        S succeedData = null; // The data when the business successful. 请求成功了
        F failedData = null; // The data when the business failed.
        int code = response.code();
        String serverJson = response.body().string();
        LogUtils.e("转换前解析xml后结果" + serverJson);
        if (code >= 200 && code < 300) { // Http is successful.
            succeedData = JSON.parseObject(serverJson, succeed);
//            RequestResult httpEntity;
//            try {
//                httpEntity = JSON.parseObject(serverJson, RequestResult.class);
//                //这个状态
//                httpEntity.set(true);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                httpEntity = new RequestResult();
//                httpEntity.setSuccess(false);
//                httpEntity.setMessage(mContext.getString(R.string.http_server_data_format_error));
//            }
//            //正常请求有个success
//            if (httpEntity.isSuccess()) { // The server successfully processed the business.
//                try {
//                    String date = httpEntity.getData();
//                    boolean issu = httpEntity.isSuccess();
//                    if (issu) {
//                        if (TextUtils.isEmpty(date)) {
//                            succeedData = (S) httpEntity;
//                        } else {
//                            succeedData = JSON.parseObject(date, succeed);
//                        }
//                    }
//                } catch (Exception e) {
//                    failedData = (F) mContext.getString(R.string.http_server_data_format_error);
//                }
//            } else {
//                // 业务失败，获取服务端提示信息。
//                failedData = (F) httpEntity.getMessage();
//            }
//
//        } else if (code >= 400 && code < 500) {
//            failedData = (F) mContext.getString(R.string.http_unknow_error);
//        } else if (code >= 500) {
//            failedData = (F) mContext.getString(R.string.http_server_error);
       }
        return SimpleResponse.
                <S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .failed(failedData)
                .build();
    }


}
