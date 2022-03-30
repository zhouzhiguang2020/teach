package com.future_education.teacher.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponentCallback
import com.future_education.teacher.R
import com.future_education.teacher.constant.MainConstant
import com.future_education.teacher.dialog.PermissionDialog
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.application.CommonApplication
import com.futureeducation.commonmodule.component.CompocomponentName
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.listener.OnCommonDialogListener
import com.futureeducation.commonmodule.manager.TeacherManager
import com.futureeducation.commonmodule.permission.AndPermissionUtill
import com.futureeducation.commonmodule.utill.NetworkUtils
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.futureeducation.commonmodule.utill.TimeUtil
import com.futureeducation.commonmodule.utill.ToastUtils
import com.hjq.permissions.XXPermissions
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.tencent.bugly.crashreport.CrashReport
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager

/**
 *
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description:
 * @Author:         作者名
 * @CreateDate:     2019/12/25 15:18
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/12/25 15:18
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */

class SplashActivity : CommonActivity() {


    private var teacherManager: TeacherManager? = null
    private var application: CommonApplication? = null
    private var isloging: Boolean = false//用来判断当前用户是否已经登录默认是没有登录的
    private var andPermissionUtill: AndPermissionUtill? = null
    private var sharedPreferencesUtil: SharedPreferencesUtil? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(this)
        application = getApplication() as CommonApplication?
        initDate()
    }

    private fun initDate() {
        LogUtils.w("开始执行--")
        andPermissionUtill = AndPermissionUtill(this)
        andPermissionUtill!!.setPermissionStatListener(object :
            AndPermissionUtill.PermissionResultListener {
            override fun OnPermissionSuccess() {
                NextStep()
            }

            override fun OnPermissionSeting() {
                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                NextStep()
                var currenttime = System.currentTimeMillis()
                sharedPreferencesUtil!!.saveLong(MainConstant.STATTUPTIME, currenttime)
            }
        })

        //判断权限是否有
        if (XXPermissions.isGranted(this, com.hjq.permissions.Permission.MANAGE_EXTERNAL_STORAGE)) {
            NextStep()
            LogUtils.w("开始执行-222-")
        } else {
            //检查登录时间一段时间内不在检查
            var currenttime = System.currentTimeMillis()
            var savetime = sharedPreferencesUtil!!.getLong(MainConstant.STATTUPTIME, 0)
            var needapply =
                currenttime - savetime
            if (Math.abs(needapply) - ( 60 * 1000) > 0 && savetime != 0L) {
                //没有超过时间方形
                NextStep()
                LogUtils.w("开始执行-3333-")
            } else {
                //永久拒绝了

                var hasDenied = XXPermissions.isPermanentDenied(
                    this,
                    com.hjq.permissions.Permission.MANAGE_EXTERNAL_STORAGE
                )

                var requestPermissionDialog = PermissionDialog()
                requestPermissionDialog.showDialog(supportFragmentManager, "dialog")
                requestPermissionDialog.setMassageText(R.string.requst_store_permissions)
                requestPermissionDialog.setOnCommonDialogListener(object :
                    OnCommonDialogListener {
                    override fun OnCommonDialogCancel() {
                        NextStep()
                        var currenttime = System.currentTimeMillis()
                        sharedPreferencesUtil!!.saveLong(
                            MainConstant.STATTUPTIME,
                            currenttime
                        )
                    }

                    override fun OnCommonDialogConfirm() {
                        if (hasDenied) {
                            XXPermissions.startPermissionActivity(
                                this@SplashActivity,
                                com.hjq.permissions.Permission.MANAGE_EXTERNAL_STORAGE
                            )
                        } else {
                            andPermissionUtill!!.requestXXPermission(com.hjq.permissions.Permission.MANAGE_EXTERNAL_STORAGE)
                        }
                    }
                })
            }
        }

    }


    //下一步
    private fun NextStep() {
        LogUtils.w("下一步")

        if (!NetworkUtils.isConnectivityActive(this)) {
            ToastUtils.showToast(
                this, R.string.http_exception_network
            )
            LogUtils.w("网络不可用")
        } else {
            LogUtils.w("检查登录状态")
            teacherManager = TeacherManager(this)
            isloging = teacherManager!!.isLogineds
            if (isloging) {
                //已经登录了
                application!!.initialize(TeacherManager.getUser().token)
                val userId = TeacherManager.getUser().userId
                LogUtils.e("已经登录了")
                jumpActivity(TeacherActivity::class.java)
                finish()
            } else {
                LogUtils.e("还未登录")
                //跳转到主页
//                jumpActivity(TeacherActivity::class.java)
//                finish()
                //正常流程跳转到登录界面
                val cc = CC.obtainBuilder(CompocomponentName.LOGIN_COMPINENR)
                    .setActionName(CompocomponentName.LOGIN)
                    .build()
                cc.callAsyncCallbackOnMainThread(object : IComponentCallback {
                    override fun onResult(cc: CC?, result: CCResult?) {
                        var result = result!!.isSuccess
                        LogUtils.e("登录成功了返回" + result)
                        if (result) {
                            var manager = TeacherManager(this@SplashActivity)
                            var user = TeacherManager.getUser()
                            if (user != null) {
                                var config = Kalle.getConfig()
                                var header = config.headers
                                if (header.containsKey("Authorization")) {
                                    header.remove("Authorization")
                                }
                                header.add("Authorization", "Bearer " + user.token)
                                Kalle.setConfig(config)
                            }

                            jumpActivity(TeacherActivity::class.java)
                            finish()
                        }
                    }
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            XXPermissions.REQUEST_CODE -> {
                //权限被授予
                if (XXPermissions.isGranted(
                        this,
                        com.hjq.permissions.Permission.MANAGE_EXTERNAL_STORAGE
                    )
                ) {
                    NextStep()

                } else {
                    NextStep()
                    var currenttime = System.currentTimeMillis()
                    sharedPreferencesUtil!!.saveLong(MainConstant.STATTUPTIME, currenttime)

//                    andPermissionUtill!!.setPermissionStatListener(object :
//                        AndPermissionUtill.PermissionResultListener {
//                        override fun OnPermissionSuccess() {
//                        }
//
//                        override fun OnPermissionSeting() {
//                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
//                            XXPermissions.startPermissionActivity(
//                                this@SplashActivity,
//                                com.hjq.permissions.Permission.MANAGE_EXTERNAL_STORAGE
//                            )
//
//                        }
//                    })
//                    andPermissionUtill!!.requestXXPermission(com.hjq.permissions.Permission.MANAGE_EXTERNAL_STORAGE)
//
                }
            }
        }
    }
}