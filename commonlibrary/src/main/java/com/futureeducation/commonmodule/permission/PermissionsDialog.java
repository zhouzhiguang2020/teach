package com.futureeducation.commonmodule.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.futureeducation.commonmodule.R;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class PermissionsDialog {

    private PermissionStatListener listener;

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(final AppCompatActivity activity, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(activity, permissions);
        String message = activity.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));
        int themeResId=R.style.common_dialog;
        new AlertDialog.Builder(activity,themeResId)

                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (listener!=null){
                            listener.sethasPermissions(true);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener!=null){
                            listener.sethasPermissions(false);
                        }
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission(final Context context, final List<String> list) {

        //AndPermission.with(this).runtime().setting().start(REQUEST_CODE_SETTING);

//        AndPermission.with(context)
//                .runtime()
//                .setting()
//                .onComeback(new Setting.Action() {
//                    @Override
//                    public void onAction() {
//                        String[] permissions = list.toArray(new String[list.size()]);
//                        boolean permissionsStat;
//                        if (AndPermission.hasPermissions(context, permissions)) {
//                            permissionsStat = true;
//                        } else {
//                            permissionsStat = false;
//                        }
//                        LogUtils.e("从设置界面回来", "权限有了吗" + permissionsStat);
//                        if (listener != null) {
//                            listener.sethasPermissions(permissionsStat);
//                        }
//                    }
//                })
//                .start();
    }

    public void setPermissionStatListener(PermissionStatListener listener) {
        this.listener = listener;
    }

    public interface PermissionStatListener {

        void sethasPermissions(boolean permissionsStat);
    }
}
