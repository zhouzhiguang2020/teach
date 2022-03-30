package com.futureeducation.commonmodule.http;

import com.alibaba.fastjson.JSON;
import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.application.CommonApplication;
import com.futureeducation.commonmodule.model.RequestResult;
import com.yanzhenjie.kalle.Request;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.ResponseBody;
import com.yanzhenjie.kalle.StringBody;
import com.yanzhenjie.kalle.connect.Interceptor;
import com.yanzhenjie.kalle.connect.http.Chain;

import java.io.IOException;

/**
 * Created by ${zhou}.
 * User: Administrator
 * Date: 2019/4/30
 * Time: 9:40
 * 网请求登录过期帐户状态拦截器
 */
public class LoginInterceptor implements Interceptor {
    private CommonApplication application;

    public LoginInterceptor(CommonApplication application) {
        this.application = application;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originResponse = chain.proceed(request);

        int responsecode = originResponse.code();
        LogUtils.e("响应码：" + responsecode);

        if (responsecode == 401) { // 授权已经过期
            //application 处理token已经过期了
            String msg = originResponse.body().toString();
            application.LoginOverdue(2);
            return originResponse;
        } else if (responsecode == 500 || responsecode == 503) {
            //服务器错误
            ResponseBody responseBody = originResponse.body();

            Response.Builder builder = new Response.Builder();
            if (responseBody == null) {
                builder.code(500);
                RequestResult result = new RequestResult();
                result.setMessage("服务器错误");
                result.setStatesCode(1002);
                builder.headers(originResponse.headers());
                builder.body((ResponseBody) new StringBody(JSON.toJSONString(result)));
            }

            //服务器错误;
            return builder.build();

        } else if (responsecode == 403) {
            LogUtils.e("全局的已经封号--");
            // application.SealAccount();
            return originResponse;
        }
        return originResponse;
    }

}
