package com.futureeducation.commonmodule.permission;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.activities.CommonActivity;
import com.futureeducation.commonmodule.fragment.CommonFragment;
import com.futureeducation.commonmodule.utill.ToastUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

/**
 * 权限请求工具类
 */
public class AndPermissionUtill {
    private AppCompatActivity activity;
    private CommonFragment fragment;
    private Context context;
    private PermissionResultListener listener;

    public AndPermissionUtill(AppCompatActivity activity) {

        this.activity = activity;
        this.context = activity;
    }

    public AndPermissionUtill(CommonFragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.activity = (CommonActivity) context;
    }

    public AndPermissionUtill(Fragment fragment) {
        this.context = fragment.getContext();
        this.activity = (AppCompatActivity) fragment.getActivity();
    }

    /**
     * Request permissions.
     */
    @SuppressLint("WrongConstant")
    public void requestPermission(String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new DefaultRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (listener != null) {
                            listener.OnPermissionSuccess();
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            //拒绝总是拒绝判断
                            if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                                PermissionsDialog dialog = new PermissionsDialog();
                                dialog.showSettingDialog(activity, permissions);
                                dialog.setPermissionStatListener(new PermissionsDialog.PermissionStatListener() {
                                    @Override
                                    public void sethasPermissions(boolean permissionsStat) {
                                        if (permissionsStat) {
                                            //去设置界面
                                            LogUtils.e("跳转到设置界面");
                                            if (listener != null) {
                                                listener.OnPermissionSeting();
                                            }
                                        } else {
                                            //取消操作
                                            ToastUtils.showToast(context, R.string.authorization_failed);
                                            LogUtils.e("点击取消按钮了");
                                            activity.finish();
                                            //requestPermission(permissions);
                                        }
                                    }
                                });
                                // Toast.makeText(SpalshActivity.this, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                            } else {
                                requestPermission();
                                ToastUtils.showToast(context, "授权失败了---");

                            }
                        }
                    }

                }).start();
    }

    public void setPermissionStatListener(PermissionResultListener listener) {
        this.listener = listener;
    }

    public interface PermissionResultListener {
        void OnPermissionSuccess();

        void OnPermissionSeting();
    }

    //使用XXPermissions逐渐替换AndPermission 权限申请
    public void requestXXPermission(String  singlepermission) {
        XXPermissions.with(context)
                // 不适配 Android 11 可以这样写
                .permission(singlepermission)
                // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        //已经授予权限
                        if (all) {
                            if (listener != null) {
                                listener.OnPermissionSuccess();
                            }
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionslist, boolean never) {
                        //权限拒绝
                        if (never) {
                            //永久拒绝
                            if (listener != null) {
                                listener.OnPermissionSeting();
                            }

                        } else {

                            requestXXPermission(singlepermission);
                            ToastUtils.showCenterToast(context, "获取权限失败");
                        }
                    }


                });
    }

    //用xxXXPermissions 申请权限传入string数组
    public void requestXXPermission(String[]  permissionsarray) {
        XXPermissions.with(context)
                // 不适配 Android 11 可以这样写
                .permission(permissionsarray)
                // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        //已经授予权限
                        if (all) {
                            if (listener != null) {
                                listener.OnPermissionSuccess();
                            }
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionslist, boolean never) {
                        //权限拒绝
                        if (never) {
                            //永久拒绝
                            if (listener != null) {
                                listener.OnPermissionSeting();
                            }

                        } else {

                            requestXXPermission(permissionsarray);
                            ToastUtils.showCenterToast(context, "获取权限失败");
                        }
                    }


                });
    }

    //用xxXXPermissions 申请权限传入string集合
    public void requestXXPermission(List<String> permissionslist) {
        XXPermissions.with(context)
                // 不适配 Android 11 可以这样写
                .permission(permissionslist)
                // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        //已经授予权限
                        if (all) {
                            if (listener != null) {
                                listener.OnPermissionSuccess();
                            }
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionslist, boolean never) {
                        //权限拒绝
                        if (never) {
                            //永久拒绝
                            if (listener != null) {
                                listener.OnPermissionSeting();
                            }

                        } else {

                            requestXXPermission(permissionslist);
                            ToastUtils.showCenterToast(context, "获取权限失败");
                        }
                    }


                });
    }
}
