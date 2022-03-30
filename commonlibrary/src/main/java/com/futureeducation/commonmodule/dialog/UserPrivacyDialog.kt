package com.futureeducation.commonmodule.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.futureeducation.commonmodule.R

/**
 * Created by UserPrivacyDialog.
 * User: ASUS
 * Date: 2019/12/10
 * Time: 20:56
 * 隐私政策
 */
class UserPrivacyDialog : AppCompatDialogFragment(), View.OnClickListener {

    //声明引用
    private var mWVmhtml: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.common_dialog)
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
        dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
//        AutoSize.autoConvertDensity(activity, 360f, true)
        var layout = inflater.inflate(R.layout.user_privacy_dialog_layout, null)
        layout.findViewById<TextView>(R.id.confirm).setOnClickListener(this)
        //获取控件对象
        mWVmhtml = layout.findViewById<View>(R.id.WV_Id) as WebView
        initdate()
        return layout
    }

    internal class MyWebChromeClient : WebChromeClient() {
        //监听加载进度
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        //接受网页标题
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            //把当前的Title设置到Activity的title上显示
        }
    }
    private fun initdate() {
        //访问
        mWVmhtml!!.loadUrl("https://ap20.ytwljy.com/html/policy.html")
        mWVmhtml!!.webChromeClient = MyWebChromeClient()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.confirm ->
                dismiss()
        }
    }
}