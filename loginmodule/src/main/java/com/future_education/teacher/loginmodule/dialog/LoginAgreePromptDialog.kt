package com.future_education.teacher.loginmodule.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.future_education.teacher.loginmodule.R
import com.futureeducation.commonmodule.listener.OnCommonDialogListener
import com.futureeducation.commonmodule.view.CommonShapeButton
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/17
 * Time: 15:53
 *
 * 登录同意弹窗
 */
class LoginAgreePromptDialog : DialogFragment(), View.OnClickListener {

    private var listener: OnCommonDialogListener? = null
    private var msg: String? = null
    private var msgid = 0
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.common_dialog2)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialogWindow = dialog!!.window
        val lp = dialogWindow!!.attributes
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
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
        var layout = inflater.inflate(R.layout.dialog_login_agree_prompt_layout, null)

        var message = layout.findViewById<TextView>(R.id.msg)
        if (!TextUtils.isEmpty(msg)) {
            message.setText(msg)
        }
        if (msgid != 0) {
            message.setText(msgid)
        }
        disposable = Observable.timer(3000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())//io线程执行
            .observeOn(AndroidSchedulers.mainThread())
            //.observeOn(Schedulers.IO());//因为上面是Thread.sleep,让主线程sleep了，所以下面要切换线程，真实环境则不需要这一行代码
            .subscribe {
                dismiss()


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

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onClick(view: View?) {
        when (view!!.id) {

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

    override fun onDestroyView() {
        super.onDestroyView()
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}