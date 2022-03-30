package com.futureeducation.commonmodule.application

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.text.TextUtils
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.multidex.MultiDex
import com.apkfuns.logutils.LogLevel
import com.apkfuns.logutils.LogUtils
import com.billy.cc.core.component.CC
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.component.CompocomponentName

import com.futureeducation.commonmodule.config.CommonConfig
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.http.LoginInterceptor
import com.futureeducation.commonmodule.http.converter.JsonConverter
import com.futureeducation.commonmodule.utill.CommonUtil
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.hjq.permissions.XXPermissions

import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.kalle.KalleConfig
import com.yanzhenjie.kalle.OkHttpConnectFactory
import com.yanzhenjie.kalle.connect.BroadcastNetwork
import com.yanzhenjie.kalle.connect.RealTimeNetwork
import com.yanzhenjie.kalle.connect.http.LoggerInterceptor
import com.yanzhenjie.kalle.cookie.DBCookieStore
import com.yanzhenjie.kalle.simple.cache.DiskCacheStore
import com.yanzhenjie.kalle.urlconnect.URLConnectionFactory
import okhttp3.internal.huc.OkHttpURLConnection
import java.util.concurrent.TimeUnit

/**
 * Created by CommonApplication.
 * User: ASUS
 * Date: 2019/11/12
 * Time: 13:26
 * 创建程序application
 */
open class CommonApplication : Application(), CameraXConfig.Provider {
    companion object {
        private const val PROCESS_NAME = "com.future_education.teacher"
        var askUpdatePwd = false
        var LoadingUnreadMsg = false
        var isNotification = true//是否在通知栏显示
        var refCount = 0
        var isInteraction = false//是否在互动
        var mApplication: CommonApplication? = null
        open fun getInstance() = mApplication

        //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
        var mContext //上下文
                : Context? = null
        var mMainThreadId //主线程id
                : Long = 0
        var mHandler //主线程Handler
                : Handler? = null

        open fun getContext(): Context? {
            return mContext
        }
    }

    var teacherActivity: CommonActivity? = null
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //因为引用的包过多，实现多包问题
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        val rootDir = MMKV.initialize(this)
        LogUtils.e("mmkv root: $rootDir")
        CommonApplication.mContext = applicationContext
        CommonApplication.mMainThreadId = Process.myTid().toLong()
        CommonApplication.mHandler = Handler(Looper.getMainLooper())
        // 当前项目是否已经适配了分区存储的特性
        //XXPermissions.setScopedStorage(true);
        var pagename = CommonUtil.getPackageNameName(this)
        if (isAppMainProcess() || TextUtils.equals(
                pagename,
                "com.future_education.teacherassistant"
            )
        ) {
            //CC初始化组件化
            CC.enableVerboseLog(CommonConstants.ISDUG)
            CC.enableDebug(CommonConstants.ISDUG)
            CC.init(this)
            CC.enableRemoteCC(CommonConstants.ISDUG)
            LogUtils.e("cc初始化了：")
            //初始化log
            LogUtils.getLogConfig()
                .configAllowLog(CommonConstants.ISDUG)
                .configTagPrefix("未来教育日志输出：")
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
                .configShowBorders(false)
                .configLevel(LogLevel.TYPE_VERBOSE)
            CrashReport.initCrashReport(applicationContext, "45ccce6a04", true)

            customAdaptForExternal()
        } else {
            LogUtils.e("不是主进程")
        }
    }

    override fun onTerminate() {
        MMKV.onExit()
        super.onTerminate()
    }

    /**
     * 需要权限才能实例化
     */
    fun initialize(token: String) {
        CommonConfig.getInstance().initFileDir()
        //配置https的安全证书
        //val sslSocketFactory = ClientCertificateUtil.provideSSLSocketFactory()
        var cachebuilder = DiskCacheStore.newBuilder(CommonConfig.getInstance().PATH_APP_CACHE);
        cachebuilder.password("我是不会告诉你密码的")
        Kalle.setConfig(
            KalleConfig.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .connectionTimeout(100000, TimeUnit.MICROSECONDS)
                .readTimeout(100000, TimeUnit.MICROSECONDS)
                .connectFactory(URLConnectionFactory.newBuilder().build())
                .cookieStore(DBCookieStore.newBuilder(this).build())
                .cacheStore(cachebuilder.build())
                // .sslSocketFactory(sslSocketFactory)
                .cacheStore(
                    DiskCacheStore.newBuilder(CommonConfig.getInstance().PATH_APP_CACHE).build()
                )
                .network(RealTimeNetwork(this))
                .addInterceptor(LoginInterceptor(this))
                .addInterceptor(LoggerInterceptor("未來教育：", CommonConstants.ISDUG))

                .converter(JsonConverter(this))
                .build()
        )

    }

    //登录的token已经过期了
    fun LoginOverdue(state: Int) {
        com.futureeducation.commonmodule.activities.ActivityManager.getInstance().exit()
        //退出
        CC.obtainBuilder(CompocomponentName.LOGIN_COMPINENR)
            .setActionName(CompocomponentName.LOGIN_OUT)
            .build().callAsync()
        var sp = SharedPreferencesUtil.getInstance(mApplication)
        sp.saveInt(CommonConstants.LOGIN_INVALID, state)
        sp.remove(CommonConstants.USER_TOKEN)
        sp.remove(CommonConstants.USER_ID)
    }




    /**
     * 给外部的三方库 [Activity] 自定义适配参数, 因为三方库的 [Activity] 并不能通过实现
     * [CustomAdapt] 接口的方式来提供自定义适配参数 (因为远程依赖改不了源码)
     * 所以使用 [ExternalAdaptManager] 来替代实现接口的方式, 来提供自定义适配参数
     */
    private fun customAdaptForExternal() {
        /**
         * [ExternalAdaptManager] 是一个管理外部三方库的适配信息和状态的管理类, 详细介绍请看 [ExternalAdaptManager] 的类注释
         */
//        AutoSizeConfig.getInstance().externalAdaptManager

        //加入的 Activity 将会放弃屏幕适配, 一般用于三方库的 Activity, 详情请看方法注释
        //如果不想放弃三方库页面的适配, 请用 addExternalAdaptInfoOfActivity 方法, 建议对三方库页面进行适配, 让自己的 App 更完美一点
        //                .addCancelAdaptOfActivity(DefaultErrorActivity.class)

        //为指定的 Activity 提供自定义适配参数, AndroidAutoSize 将会按照提供的适配参数进行适配, 详情请看方法注释
        //一般用于三方库的 Activity, 因为三方库的设计图尺寸可能和项目自身的设计图尺寸不一致, 所以要想完美适配三方库的页面
        //就需要提供三方库的设计图尺寸, 以及适配的方向 (以宽为基准还是高为基准?)
        //三方库页面的设计图尺寸可能无法获知, 所以如果想让三方库的适配效果达到最好, 只有靠不断的尝试
        //由于 AndroidAutoSize 可以让布局在所有设备上都等比例缩放, 所以只要您在一个设备上测试出了一个最完美的设计图尺寸
        //那这个三方库页面在其他设备上也会呈现出同样的适配效果, 等比例缩放, 所以也就完成了三方库页面的屏幕适配
        //即使在不改三方库源码的情况下也可以完美适配三方库的页面, 这就是 AndroidAutoSize 的优势
        //但前提是三方库页面的布局使用的是 dp 和 sp, 如果布局全部使用的 px, 那 AndroidAutoSize 也将无能为力
        //经过测试 DefaultErrorActivity 的设计图宽度在 380dp - 400dp 显示效果都是比较舒服的
        /*     .addExternalAdaptInfoOfActivity(
                 Activity::class.java,
                 ExternalAdaptInfo(true, 400f)
             )*/
    }


    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    open fun isAppMainProcess(): Boolean {
        return try {
            val pid = Process.myPid()
            val process = getAppNameByPID(this, pid)
            if (TextUtils.isEmpty(process)) {
                true
            } else if (PROCESS_NAME.equals(process, true)) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }

    /**
     * 根据Pid得到进程名
     */
    @SuppressLint("ServiceCast")
    open fun getAppNameByPID(context: Context, pid: Int): String {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName
            }
        }
        return ""
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}