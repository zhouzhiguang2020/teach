package com.future_education.teacher.loginmodule.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.apkfuns.logutils.LogUtils
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil

import com.future_education.loginmodule.presenter.IPresenter
import com.future_education.loginmodule.presenter.LoginPresenter
import com.future_education.teacher.loginmodule.R
import com.future_education.teacher.loginmodule.constant.LoginConstant
import com.future_education.teacher.loginmodule.contact.LoginContact
import com.future_education.teacher.loginmodule.dialog.LoginAgreePromptDialog

import com.future_education.teacher.loginmodule.event.AccountExpiredEvent
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.application.CommonApplication
import com.futureeducation.commonmodule.component.CompocomponentName
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.dialog.GlobalLoadingDialog
import com.futureeducation.commonmodule.dialog.GraceDialog
import com.futureeducation.commonmodule.dialog.UserAgreementDialog
import com.futureeducation.commonmodule.dialog.UserPrivacyDialog
import com.futureeducation.commonmodule.event.LoginSuccessEvent
import com.futureeducation.commonmodule.filter.ExpressionInputFilter

import com.futureeducation.commonmodule.model.TeacherBean
import com.futureeducation.commonmodule.utill.CommonUtil
import com.futureeducation.commonmodule.utill.NetworkUtils
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.futureeducation.commonmodule.utill.ToastUtils
import com.futureeducation.commonmodule.view.CommonShapeButton
import com.github.razir.progressbutton.DrawableButton
import com.github.razir.progressbutton.showProgress
import com.google.android.material.button.MaterialButton
import com.gyf.immersionbar.ktx.immersionBar
import com.jakewharton.rxbinding3.widget.textChanges
import com.tencent.mm.opensdk.modelmsg.SendAuth

import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.threshold.rxbus2.RxBus
import com.threshold.rxbus2.annotation.RxSubscribe
import com.threshold.rxbus2.util.EventThread
import io.reactivex.Observable
import io.reactivex.functions.BiFunction


import java.util.*


/**
 *
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description:    登录主界面这里是两个帐户类型登录入口
 * @Author:         作者名: wljy
 * @CreateDate:     2019/11/18 19:53
 * @UpdateUser:     更新者：
 * @UpdateDate:     2021/11/17 13:53
 * @UpdateRemark:   更新说明：
 * @Version:        2.0
 */
class LoginActivity : CommonActivity(), LoginContact.View, View.OnClickListener {
    inline fun <reified T : IPresenter> getPresenter(): T {
        return T::class.java.newInstance()
    }

    private var logintpe = 0
    private var loadingDialog: GlobalLoadingDialog? = null
    private var commonUtil: CommonUtil? = null;
    private var account_edit: AppCompatEditText? = null
    private var passwor_edit: AppCompatEditText? = null
    private var accountLogin: CommonShapeButton? = null
    private var shown_password: ImageView? = null
    private var WeChatLogin: ImageView? = null

    //用户协议
    private var tvXieyi: TextView? = null
    private var tvYinsi: TextView? = null
    private var checkBox: CheckBox? = null
    private var presenter: LoginPresenter? = null

    //密码是否可见
    private var passwordvisible = false

    //是否是登录过期默认false
    private var islogingoverd = false
    private var loginNum = 0
    var sp = SharedPreferencesUtil.getInstance(this)

    var wx_api: IWXAPI? = null

    // private val binding by binding(ActivityLoginLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        presenter = LoginPresenter(this)
        initView()
        initListener()
        loadingDialog = GlobalLoadingDialog()
        RxBus.getDefault().register(this)
    }

    //初始化事件
    private fun initListener() {
        islogingoverd = intent.getBooleanExtra(LoginConstant.LOGIN_INVALID, false)
        logintpe = intent.getIntExtra(LoginConstant.LOGIN_TYPE, 0)



        if (sp.getBoolean(CommonConstants.FLAG_READ, false)) {
            checkBox!!.isChecked = true
        }

        checkBox!!.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                sp.saveBoolean(CommonConstants.FLAG_READ, isChecked)
            }
        })
        // tvXieyi!!.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        tvXieyi!!.paint.isAntiAlias = true;//抗锯齿
        tvXieyi!!.setOnClickListener {
            if (NetworkUtils.isConnectivityActive(this)) {
                //用户协议
                var dialog = UserAgreementDialog()
                dialog.show(supportFragmentManager, "dialog")
            }
        }
        //tvYinsi!!.paint.flags = Paint.UNDERLINE_TEXT_FLAG;
        tvYinsi!!.paint.isAntiAlias = true;//抗锯齿
        tvYinsi!!.setOnClickListener {
            //隐私政策
            if (NetworkUtils.isConnectivityActive(this)) {
                var dialog = UserPrivacyDialog()
                dialog.show(supportFragmentManager, "dialog")
            }
        }

        accountLogin!!.setOnClickListener {
            hintKeyBoard()
            //帐户登录了
            if (!checkBox!!.isChecked) {
                var dialog = LoginAgreePromptDialog()
                dialog.show(supportFragmentManager, "dialog")
                dialog.setMassageText(R.string.login_agree_prompt)
                //ToastUtils.showCenterToast(this, "请先阅读并同意用户须知和隐私政策!")


                return@setOnClickListener
            }

            var accounted = account_edit!!.editableText.toString()
            var passworded = passwor_edit!!.editableText.toString()
            if (!TextUtils.isEmpty(accounted) && !TextUtils.isEmpty(passworded)) {
                if (loginNum >= CommonConstants.LOGINFAILURE) {
                    var loginFailureTime = sp.getInt(CommonConstants.LOGINFAUILURESP, 0)
                    var nowTime = Date().time.toInt()
                    var ctime = (nowTime - loginFailureTime) / 1000
                    var time = CommonConstants.LOGINFAILURETIME - ctime
                    if (time > 0) {
                        var dialog = GraceDialog()
                        var fen = (time / 60).toInt() + 1
                        dialog.setMassageText("您登录次数过多，等 " + fen + " 分钟再试")
                        dialog.show(supportFragmentManager, "dialog")
                    } else {
                        loginNum = 0
                        //执行登录操作
                        presenter!!.ExecutiveLogging(passworded, accounted)
                        // showProgressCenter(account_login)
                        hintKeyBoard()
                        loadingDialog!!.show(supportFragmentManager, "loading")
                        accountLogin!!.isEnabled = false
                    }
                } else {
                    //执行登录操作
                    presenter!!.ExecutiveLogging(passworded, accounted)
                    // showProgressCenter(account_login)
                    hintKeyBoard()
                    loadingDialog!!.show(supportFragmentManager, "loading")
                    accountLogin!!.isEnabled = false
                }
            }
        }

        if (sp.getInt(CommonConstants.LOGIN_INVALID, 0) == 2) {
            sp.saveInt(CommonConstants.LOGIN_INVALID, 1)
            var dialog = GraceDialog()
            dialog.setMassageText(R.string.loging_overd)
            dialog.show(supportFragmentManager, "dialog")
        } else if (sp.getInt(CommonConstants.LOGIN_INVALID, 0) == 3) {
            sp.saveInt(CommonConstants.LOGIN_INVALID, 1)
            var dialog = GraceDialog()
            dialog.setMassageText(R.string.login_mutual_exclusion)
            dialog.show(supportFragmentManager, "dialog")
        }


        /**
         * 微信登录
         */
        WeChatLogin!!.setOnClickListener {
            if (!checkBox!!.isChecked) {
                var dialog = LoginAgreePromptDialog()
                dialog.show(supportFragmentManager, "dialog")
                dialog.setMassageText(R.string.login_agree_prompt)
                return@setOnClickListener
            }

            if (!wx_api!!.isWXAppInstalled()) {
                ToastUtils.showCenterToast(this, "您的设备未安装微信客户端!")
            } else {
                val req: SendAuth.Req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                req.state = "wechat_sdk_demo_test"
                wx_api!!.sendReq(req)
            }

        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun showProgressCenter(button: MaterialButton?) {
        var yi = commonUtil!!.dip2px(this, resources.getDimension(R.dimen.dp_0_5))
        accountLogin!!.isEnabled = false
        button!!.showProgress {
            progressStrokePx = yi
            progressColor =
                ContextCompat.getColor(this@LoginActivity, R.color.login_progress_color)
            gravity = DrawableButton.GRAVITY_CENTER
        }
    }

    private fun initView() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        wx_api = WXAPIFactory.createWXAPI(this, CommonConstants.APP_ID, true);
        // 将应用的appId注册到微信
        wx_api!!.registerApp(CommonConstants.APP_ID);
        commonUtil = CommonUtil(this.application)
        checkBox = findViewById(R.id.checkBox)
        tvYinsi = findViewById(R.id.tv_yinsi)
        shown_password = this.findViewById(R.id.showPassword)
        tvXieyi = findViewById(R.id.tv_xieyi)
        WeChatLogin = findViewById(R.id.WeChatLogin)
        findViewById<ImageView>(R.id.back).visibility = View.GONE
        findViewById<TextView>(R.id.title_txt).setText(R.string.future_classroom)
        findViewById<ImageView>(R.id.search).apply {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.ic_setting_up)
            this.setOnClickListener(this@LoginActivity)
        }
        account_edit = findViewById(R.id.input_account)
        passwor_edit = findViewById(R.id.input_password)
        accountLogin = findViewById(R.id.account_login)
        account_edit!!.setText(sp.getString(CommonConstants.USER_ACCOUNT, ""))
        passwor_edit!!.setText(sp.getString(CommonConstants.USER_PASS, ""))
        shown_password!!.setOnClickListener(this)
        //
        val filters = arrayOf(
            ExpressionInputFilter(),
            LengthFilter(LoginConstant.ACCOUNT_INPUT_LENGTH)
        )
        val filters1 = arrayOf(
            ExpressionInputFilter(),
            LengthFilter(LoginConstant.ACCOUNT_INPUT_LENGTH)
        )
        account_edit!!.setFilters(filters)
        passwor_edit!!.setFilters(filters1)
        if (account_edit != null && passwor_edit != null) {
            val accountobservable = account_edit!!.textChanges()
            val passwordobservable = passwor_edit!!.textChanges()
//// 相当于合并
            Observable.combineLatest(accountobservable, passwordobservable,
                BiFunction<CharSequence, CharSequence, Boolean> { _, _ ->
                    val account = account_edit!!.getEditableText().toString()
                    val password = passwor_edit!!.editableText.toString()
                    return@BiFunction !TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)

                })
                .subscribe { aBoolean ->
                    if (aBoolean!!) {
                        accountLogin!!.setEnabled(true)
                        // account_login.setBackgroundResource(R.drawable.account_login_select)
                    } else {
                        accountLogin!!.setEnabled(false)
                        //  account_login.setBackgroundResource(R.drawable.ic_account_not_login)
                    }
                }

        }

        //显示密码按钮
        LogUtils.e("查看为空了吗：" + shown_password == null)

        //关闭service连接
        CC.obtainBuilder(CompocomponentName.IM_COMPINENR)
            .setActionName(CompocomponentName.IM_UNLINK)
            .build().call()

    }


    private fun shownPassword(isshown: Boolean) {
        if (isshown) {
            passwor_edit!!.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_CLASS_TEXT);
        } else {
            passwor_edit!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (event != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                var home = Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    override fun shownLoginSucess(date: TeacherBean?) {
        LogUtils.e("登录成功了：" + date)
        if (date != null && date.isAskUpdatePwd != null) {
            CommonApplication.askUpdatePwd = date.isAskUpdatePwd
        }
        accountLogin!!.isEnabled = true
        //account_login.hideProgress(R.string.login_success)
        if (islogingoverd) {
            //登录过期

            var cc = CC.obtainBuilder(CompocomponentName.MAIN_COMPINENT)
                .setActionName(CompocomponentName.MAIN)
                .build()
            cc.callAsync()
        } else {
            //退出登录
            if (logintpe == LoginConstant.LOGIN_OUT_TYPE) {
                var cc = CC.obtainBuilder(CompocomponentName.MAIN_COMPINENT)
                    .setActionName(CompocomponentName.MAIN)
                    .build()
                cc.callAsync()

            } else {
                //跳转到主界面
                var callids = CCUtil.getNavigateCallId(this)
                CC.sendCCResult(callids, CCResult.success())
            }
        }
        finish()
        loadingDialog!!.dismiss()
    }

    override fun logingFailure() {
        accountLogin!!.isEnabled = true
        loadingDialog!!.dismiss()
        //account_login.hideProgress(R.string.account_login)
        loginNum++
        if (loginNum >= CommonConstants.LOGINFAILURE) {
            var nowTime = Date().time.toInt()
            sp.saveInt(CommonConstants.LOGINFAUILURESP, nowTime)
        }
    }

    //文件夹
    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun event(event: LoginSuccessEvent) {
        var callids = CCUtil.getNavigateCallId(this)
        CC.sendCCResult(callids, CCResult.success())
        finish()

    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun event(event: AccountExpiredEvent) {
        LogUtils.e("登录过期事件--")
        var dialog = GraceDialog()
        var msg = getString(R.string.account_overdue)
        dialog.setMassageText(msg)
        dialog.show(supportFragmentManager, "dialog")
    }

    override fun setPresenter(presenter: LoginPresenter) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.showPassword -> {
                transitionShownPassword()
            }
            R.id.search -> {
                var intent = Intent(this, SettingApiActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun transitionShownPassword() {
        if (shown_password != null) {
            if (passwordvisible) {
                shown_password!!.isSelected = false
                passwordvisible = false
            } else {
                shown_password!!.isSelected = true
                passwordvisible = true
            }

            var position = passwor_edit!!.editableText.length
            shownPassword(passwordvisible)
            passwor_edit!!.setSelection(position)

        } else {
            LogUtils.e("执行了吗==" + shown_password == null)
        }
    }
}
