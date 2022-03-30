package com.futureeducation.commonmodule.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.listener.OnCommonDialogListener;
import com.futureeducation.commonmodule.view.CommonShapeButton;


/**
 * Created by ${zhou}.
 * User: Administrator
 * Date: 2019/3/27
 * Time: 9:31
 * 一个优雅的对话框
 */
public class GraceSimpleDialog extends DialogFragment implements View.OnClickListener {

    private OnCommonDialogListener listener;
    private String msg, confirmstring;
    private int msgid;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //full screen dialog fragment
        setStyle(DialogFragment.STYLE_NORMAL, R.style.select_setmeal_dialog);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);// 这个很重
        dialogWindow.setGravity(Gravity.CENTER);
        // dialogWindow.setWindowAnimations(com.gongwuyuan.commonlibrary.R.style.tipsAnimation);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.grace_simple_dialog_layout,
                null);
        TextView massage = view.findViewById(R.id.msg);
        if (!TextUtils.isEmpty(msg)) {
            massage.setText(msg);
        }
        if (msgid != 0) {
            massage.setText(msgid);
        }
        CommonShapeButton confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        CommonShapeButton cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        return view;
    }

    public void setMassageText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.msg = text;
        }
    }


    public void setMassageText(int ids) {

        this.msgid = ids;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            dismiss();
            if (listener != null) {
                listener.OnCommonDialogConfirm();
            }
        } else if (v.getId() == R.id.cancel) {
            if (listener != null) {
                listener.OnCommonDialogCancel();
            }
            dismiss();
        }
    }
    @Override
    public void show(FragmentManager manager, String tag) {

        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss();
    }

    public void setOnCommonDialogListener(OnCommonDialogListener listener) {
        this.listener = listener;
    }
}


