package com.futureeducation.commonmodule.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.futureeducation.commonmodule.R
import com.futureeducation.commonmodule.listener.OnCommonDialogListener
import com.futureeducation.commonmodule.view.CommonShapeButton

/**
 * Created by CancelOutDialog.
 * User: ASUS
 * Date: 2020/1/17
 * Time: 9:04
 * q取消派发
 */
class PromptContentDialog : DialogFragment(), View.OnClickListener {

    private var listener: OnCommonDialogListener? = null
    private var msg: String? = null
    private var msgid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.common_dialog)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialogWindow = dialog!!.window
        val lp = dialogWindow!!.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
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
        val dialogWindow = dialog.window
        dialogWindow!!.decorView.setPadding(0, 0, 0, 0) // 这个很重
        dialogWindow.setGravity(Gravity.CENTER)
        dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var layout = inflater.inflate(R.layout.prompt_content_dialog_layout, null)
        layout.findViewById<CommonShapeButton>(R.id.cancel).setOnClickListener(this)
        layout.findViewById<CommonShapeButton>(R.id.confirm).setOnClickListener(this)
        var message = layout.findViewById<TextView>(R.id.msg)
        if (!TextUtils.isEmpty(msg)) {
            message.setText(msg)
        }
        if (msgid != 0) {
            message.setText(msgid)
        }
        return layout
    }

    fun setMassageText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            msg = text
        }
    }


    fun setMassageText(ids: Int) {
        msgid = ids
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.cancel -> {
                if (listener != null) {
                    listener!!.OnCommonDialogCancel()
                }
                dismiss()
            }
            R.id.confirm -> {
                if (listener != null) {
                    listener!!.OnCommonDialogConfirm()
                }
                dismiss()
            }
        }
    }

    fun setOnCommonDialogListener(listener: OnCommonDialogListener?) {
        this.listener = listener
    }

    override fun show(
        manager: FragmentManager,
        tag: String?
    ) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss()
    }
}