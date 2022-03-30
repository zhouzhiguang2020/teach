package com.futureeducation.commonmodule.permission;

import android.content.Context;

import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

public class DefaultRationale implements Rationale<List<String>> {
    @Override
    public void showRationale(Context context, List<String> data, RequestExecutor executor) {
        executor.execute();
    }

}
