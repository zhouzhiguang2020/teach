package com.futureeducation.commonmodule.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.futureeducation.commonmodule.R


/**
 * Created by ${zhou}.
 * User: Administrator
 * Date: 2019/8/4
 * Time: 12:16
 * 一个全局的小菊花进度条
 */
class GlobalLoadingDialog : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //full screen dialog fragment
        setStyle(DialogFragment.STYLE_NORMAL, R.style.LoadingDialog)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialogWindow = getDialog()!!.getWindow()
        val lp = dialogWindow!!.getAttributes()
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialogWindow.setAttributes(lp)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dialog = getDialog()
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogWindow = dialog.getWindow()
        dialogWindow!!.setBackgroundDrawableResource(R.color.transparent)
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0)// 这个很重
        dialogWindow.setGravity(Gravity.BOTTOM)
        //dialogWindow.setWindowAnimations(R.style.tipsAnimation);
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var view = inflater.inflate(R.layout.global_loading_dialog_layout, null)
        return view
    }

    override fun show(manager: FragmentManager, tag: String?) {

        val ft = manager.beginTransaction()
        ft.add(this, tag)
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss()
    }

}