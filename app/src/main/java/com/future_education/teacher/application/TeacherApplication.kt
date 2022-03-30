package com.future_education.teacherassistant.application

import android.content.Context
import androidx.multidex.MultiDex
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.apkfuns.logutils.LogLevel
import com.apkfuns.logutils.LogUtils
import com.futureeducation.commonmodule.R
import com.futureeducation.commonmodule.application.CommonApplication
import com.futureeducation.commonmodule.constants.CommonConstants
import com.tencent.bugly.crashreport.CrashReport
import com.threshold.rxbus2.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import kotlin.jvm.Throws


/**
 * Created by wljy
 * User: ASUS
 * Date: 2020/11/16
 * Time: 13:18
 */
class TeacherApplication : CommonApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //因为引用的包过多，实现多包问题
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        //初始化log
        LogUtils.getLogConfig()
            .configAllowLog(CommonConstants.ISDUG)
            .configTagPrefix("未来教育老师端日志输出：")
            .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
            .configShowBorders(false)
            .configLevel(LogLevel.TYPE_VERBOSE)
        //初始化事件
        RxBus.setMainScheduler(AndroidSchedulers.mainThread())
        setRxJavaErrorHandler()
        CrashReport.initCrashReport(getApplicationContext(), "b3a5ddc352", false);
    }

    override fun onTerminate() {
        super.onTerminate()
        mHandler!!.removeCallbacksAndMessages(null)

    }

    private fun setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(object : Consumer<Throwable?> {
            @Throws(Exception::class)
            override fun accept(throwable: Throwable?) {

            }
        })
    }
}