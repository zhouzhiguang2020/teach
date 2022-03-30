package com.gongwuyuan.commonlibrary.update.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import com.apkfuns.logutils.LogUtils
import com.dylanc.viewbinding.binding
import com.dylanc.viewbinding.nonreflection.binding
import com.futureeducation.commonmodule.R
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.databinding.ActivityUpdateWebLayoutBinding
import com.futureeducation.commonmodule.databinding.UpdateDialogLayoutBinding

import com.gyf.immersionbar.ImmersionBar


/**
 *
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description:     紧急状态跳转都浏览器下载
 * @Author:         作者名
 * @CreateDate:     2020/4/20 15:07
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/4/20 15:07
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */

class UpdateWebActivity : CommonActivity() {


    private val viewbinding by binding(ActivityUpdateWebLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_update_web_layout)
        ImmersionBar.with(this)
            .statusBarColor(R.color.colorPrimaryDark)
            .navigationBarColor(R.color.white)
            .statusBarDarkFont(
                true,
                0.2f
            ) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
            .init()
        initView()
    }

    private fun initView() {
        viewbinding.imgHeadLeft.setOnClickListener {
            finish()
        }
        viewbinding.tvHeadTitle.text = getText(R.string.app_name)
        var string = intent.getStringExtra(CommonConstants.LINK_UPDATE)
        LogUtils.e("穿过来apk地址：" + string)
        openBrowser(string!!)
//        var mContainer = findViewById<LinearLayout>(R.id.container)
//        var mAgentWeb = AgentWeb.with(this)//
//                .setAgentWebParent(mContainer, -1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
//                .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
//                //.setAgentWebWebSettings(AbsAgentWebSettings())//设置 IAgentWebSettings。
//                //.setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
//                //.setWebChromeClient(mWebChromeClient) //WebChromeClient
//                //.setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
//                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
//                //.setAgentWebUIController(UIController(activity)) //自定义UI  AgentWeb3.0.0 加入。
//                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
//                //.useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
//                //.useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
//                //                .setDownloadListener(mDownloadListener) 4.0.0 删除该API//下载回调
//                //                .openParallelDownload()// 4.0.0删除该API 打开并行下载 , 默认串行下载。 请通过AgentWebDownloader#Extra实现并行下载
//                //                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api //下载通知图标。4.0.0后的版本请通过AgentWebDownloader#Extra修改icon
//                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
//                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
//                .createAgentWeb()//创建AgentWeb。
//                .ready()//设置 WebSettings。
//                .go(string) //WebView载入该url地址的页面并显示。
    }

    /**
     * 调用第三方浏览器打开
     * @param context
     * @param url 要浏览的资源地址
     */
    fun openBrowser(url: String) {
        var i = Intent();
        i.setAction("android.intent.action.VIEW");
        var u = Uri.parse(url);
        i.setDataAndType(u, "text/html");
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (i.resolveActivity(this.getPackageManager()) != null)
            startActivity(i);
        LogUtils.e("跳转到浏览器下载" + url)
    }

}
