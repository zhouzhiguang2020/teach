package com.future_education.teacher.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.future_education.teacher.R

import com.futureeducation.commonmodule.listener.OnCommonDialogListener
import com.futureeducation.commonmodule.view.CommonShapeButton

/**
 * Created by .
 * User: ASUS
 * Date: 2021/7/12
 * Time: 16:58
 */
class PermissionDialog : DialogFragment(), View.OnClickListener {

    var listener: OnCommonDialogListener? = null
    var msg: String? = null
    private  var confirmstring:kotlin.String? = null
    var msgid = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //full screen dialog fragment
        setStyle(DialogFragment.STYLE_NORMAL, R.style.select_setmeal_dialog)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialogWindow: Window = getDialog()!!.getWindow()!!
        val lp = dialogWindow.attributes
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialogWindow.attributes = lp
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialog: Dialog = getDialog()!!
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogWindow = dialog.window
        dialogWindow!!.decorView.setPadding(0, 0, 0, 0) // 这个很重
        dialogWindow.setGravity(Gravity.CENTER)
        // dialogWindow.setWindowAnimations(com.gongwuyuan.commonlibrary.R.style.tipsAnimation);
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val view: View = inflater.inflate(
            R.layout.grace_requetpermission_dialog_layout,
            null
        )
        val massage = view.findViewById<TextView>(R.id.msg)
        if (!TextUtils.isEmpty(msg)) {
            massage.text = msg
        }
        if (msgid != 0) {
            massage.setText(msgid)
        }
        val confirm: CommonShapeButton = view.findViewById(R.id.confirm)
        confirm.setOnClickListener(this)
        val cancel: CommonShapeButton = view.findViewById(R.id.cancel)
        cancel.setOnClickListener(this)

        return view
    }

    fun setMassageText(text: String) {
        if (!TextUtils.isEmpty(text)) {
            this.msg = text
        }
    }


    fun setMassageText(ids: Int) {
        this.msgid = ids
    }

    override fun onClick(v: View) {
        if (v.id == com.futureeducation.commonmodule.R.id.confirm) {
            dismiss()
            if (listener != null) {
                listener!!.OnCommonDialogConfirm()
            }
        } else if (v.id == com.futureeducation.commonmodule.R.id.cancel) {
            if (listener != null) {
                listener!!.OnCommonDialogCancel()
            }
            dismiss()
        }
    }

    fun setOnCommonDialogListener(listener: OnCommonDialogListener) {
        this.listener = listener
    }

     fun showDialog(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss()
    }

}