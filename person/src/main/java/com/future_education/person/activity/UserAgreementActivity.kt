package com.future_education.person.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.person.R
import com.future_education.person.databinding.ActivityUserAgreementLayoutBinding
import com.futureeducation.commonmodule.activities.CommonActivity
import com.gyf.immersionbar.ktx.immersionBar

/*
 * @Author wljy
 * @Date 2021/11/23
 * @Des 用户须知
 */
class UserAgreementActivity : CommonActivity(), View.OnClickListener {
    private val binding by binding(ActivityUserAgreementLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_user_agreement_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        initview()
    }

    private fun initview() {
        binding.topLayout.back.setOnClickListener(this)
        binding.topLayout.titleTxt.text=getString(R.string.user_agreement)

        val settings = binding.webview.settings
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        binding.webview!!.webChromeClient = MyWebChromeClient()
        //访问
        binding.webview!!.loadUrl("https://ap20.ytwljy.com/html/app.html")
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> {
                finish()
            }
        }
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
}