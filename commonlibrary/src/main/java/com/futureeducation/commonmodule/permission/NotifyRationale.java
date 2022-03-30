package com.futureeducation.commonmodule.permission;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.activities.CommonActivity;
import com.futureeducation.commonmodule.dialog.GraceSimpleDialog;
import com.futureeducation.commonmodule.fragment.CommonFragment;
import com.futureeducation.commonmodule.listener.OnCommonDialogListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

/**
 * Created by ${zhou}.
 * User: Administrator
 * Date: 2019/3/29
 * Time: 14:24
 * 通知栏权限
 */
public class NotifyRationale implements Rationale<Void> {
    private CommonActivity activity;
    private GraceSimpleDialog dialog;

    public NotifyRationale() {
    }

    public NotifyRationale(CommonActivity activity) {
        this.activity = activity;
    }

    public NotifyRationale(CommonFragment fragment) {
        this.activity = (CommonActivity) fragment.getActivity();
    }

    @Override
    public void showRationale(Context context, Void data, final RequestExecutor executor) {
        //通知栏权限对话框
        if (activity != null) {
            if (dialog == null) {
                dialog = new GraceSimpleDialog();
                dialog.setMassageText(R.string.message_notification_rationale);

                dialog.setOnCommonDialogListener(new OnCommonDialogListener() {
                    @Override
                    public void OnCommonDialogCancel() {
                        executor.cancel();

                        dialog.dismiss();
                    }

                    @Override
                    public void OnCommonDialogConfirm() {

                        dialog.dismiss();
                        executor.execute();
                    }
                });


//        if (dialog != null && dialog.getDialog() != null
//                && dialog.getDialog().isShowing()) {
//            dialog.show(activity.getSupportFragmentManager(), "dialog");
                LogUtils.e("测试执行了几次");
//        }
                dialog.show(activity.getSupportFragmentManager(), "dialog");
            }
        }else {
            executor.cancel();
        }
    }
}
