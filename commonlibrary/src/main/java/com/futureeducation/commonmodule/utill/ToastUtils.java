package com.futureeducation.commonmodule.utill;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dovar.dtoast.DToast;
import com.dovar.dtoast.inner.IToast;
import com.futureeducation.commonmodule.R;


/**
 * 管理所有Toast 防止连续点击产生的消息滞留
 *
 * @author wangpeng
 */
public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context mContext, int strResId) {
        if (mContext == null) return;

        if (strResId == 0) return;
        IToast toast = DToast.make(mContext);
        TextView tv_text = (TextView) toast.getView().findViewById(R.id.tv_content_default);
        if (tv_text != null) {
            tv_text.setText(strResId);
        }
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 30).show();
    }


    public static void showToast(Context mContext, String msg) {
        if (mContext == null) return;
        if (msg == null) return;
        IToast toast = DToast.make(mContext);
        TextView tv_text = (TextView) toast.getView().findViewById(R.id.tv_content_default);
        if (tv_text != null) {
            tv_text.setText(msg);
        }
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 30).show();
    }

    //第三个参数代表是横屏
    public static void showToast(Context mContext, int strResId, boolean landscape) {
        if (mContext == null) return;

        if (strResId == 0) return;
        IToast toast = DToast.make(mContext);
        TextView tv_text = (TextView) toast.getView().findViewById(R.id.tv_content_default);
        if (tv_text != null) {
            tv_text.setText(strResId);
            if (landscape) {
                tv_text.setTextSize(DimenUtils.sp2px(5));
            }
        }
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 30).show();
    }
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static void showCenterToast(Context mContext, String msg) {
        if (mContext == null) return;
        if (msg == null) return;
        View toastRoot = View.inflate(mContext, R.layout.layout_toast_center, null);
        TextView tv_text = (TextView) toastRoot.findViewById(R.id.tv_content_default);
        if (tv_text != null) {
            tv_text.setText(msg);
        }
        DToast.make(mContext)
                .setView(toastRoot)
                .setGravity(Gravity.CENTER, 0, 0)
                .show();
    }

    public static void showCenterToast(Context mContext, int strResId) {
        if (mContext == null) return;
        if (strResId == 0) return;
        View toastRoot = View.inflate(mContext, R.layout.layout_toast_center, null);
        TextView tv_text = (TextView) toastRoot.findViewById(R.id.tv_content_default);
        if (tv_text != null) {
            tv_text.setText(strResId);
        }
        DToast.make(mContext)
                .setView(toastRoot)
                .setGravity(Gravity.CENTER, 0, 0)
                .show();
    }

    /**
     * 显示通用的Toast提醒
     *
     * @param context
     */
    public static void showCommToast(Context context) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, "服务不可用", Toast.LENGTH_LONG);
        mToast.show();
    }

    //退出APP时调用
    public static void cancelAll() {
        DToast.cancel();
    }
}
