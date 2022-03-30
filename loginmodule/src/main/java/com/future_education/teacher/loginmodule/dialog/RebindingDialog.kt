package com.future_education.teacher.loginmodule.dialog

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.future_education.teacher.loginmodule.R
import com.futureeducation.commonmodule.listener.OnCommonDialogListener
import com.futureeducation.commonmodule.view.CommonShapeButton

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/19
 * Time: 9:48
 * 重新绑定老师账号
 */
class RebindingDialog : DialogFragment(), View.OnClickListener {
    private var listener: OnCommonDialogListener? = null
    private var msg: String? = null
    private var confirmstring: kotlin.String? = null
    private var msgid = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //full screen dialog fragment
        setStyle(STYLE_NORMAL, R.style.select_setmeal_dialog)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialogWindow = dialog!!.window
        val lp = dialogWindow!!.attributes
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialogWindow.attributes = lp
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialog = dialog
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogWindow = dialog!!.window
        dialogWindow!!.decorView.setPadding(0, 0, 0, 0) // 这个很重
        dialogWindow!!.setGravity(Gravity.CENTER)
        // dialogWindow.setWindowAnimations(com.gongwuyuan.commonlibrary.R.style.tipsAnimation);
        // dialogWindow.setWindowAnimations(com.gongwuyuan.commonlibrary.R.style.tipsAnimation);
        dialog!!.setCancelable(true)
        dialog!!.setCanceledOnTouchOutside(true)
        val view = inflater.inflate(
            R.layout.grace_simple_dialog_layout,
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
        confirm.setText(R.string.rebinding)
        confirm.setOnClickListener(this)
        val cancel: CommonShapeButton = view.findViewById(R.id.cancel)
        cancel.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.confirm -> {
                if (listener != null) {
                    listener!!.OnCommonDialogConfirm()
                }
                dismiss()
            }
            R.id.cancel -> {
                if (listener != null) {
                    listener!!.OnCommonDialogCancel()
                }
                dismiss()
            }
        }

    }

    fun setMassageText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            msg = text
        }
    }


    fun setMassageText(ids: Int) {
        msgid = ids
    }
    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss()
    }

    fun setOnCommonDialogListener(listener: OnCommonDialogListener) {
        this.listener = listener
    }
}