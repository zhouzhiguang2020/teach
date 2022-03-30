package com.future_education.teacher.loginmodule.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText
import cc.taylorzhang.singleclick.onSingleClick
import com.apkfuns.logutils.LogUtils
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.loginmodule.presenter.BindPresenter
import com.future_education.teacher.loginmodule.R
import com.future_education.teacher.loginmodule.constant.LoginConstant
import com.future_education.teacher.loginmodule.contact.BindContact
import com.future_education.teacher.loginmodule.databinding.ActivityBindLayoutBinding
import com.future_education.teacher.loginmodule.dialog.BingSuccessDialog
import com.future_education.teacher.loginmodule.dialog.RebindingDialog
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.dialog.GraceDialog
import com.futureeducation.commonmodule.event.LoginSuccessEvent
import com.futureeducation.commonmodule.filter.ExpressionInputFilter
import com.futureeducation.commonmodule.listener.OnCommonDialogListener
import com.futureeducation.commonmodule.model.TeacherBean
import com.futureeducation.commonmodule.utill.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.jakewharton.rxbinding3.widget.textChanges
import com.threshold.rxbus2.RxBus
import io.reactivex.Observable
import io.reactivex.functions.BiFunction


/**
 * 绑定账号
 */
class BindActivity : CommonActivity(), BindContact.View {

    private var account_edit: AppCompatEditText? = null
    private var passwor_edit: AppCompatEditText? = null
    private var presenter: BindPresenter? = null
    private var unionid: String = ""

    //密码是否可见
    private var passwordvisible = false
    private val binding by binding(ActivityBindLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_bind_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        presenter = BindPresenter(this)
        initView()
        initListener()
        RxBus.getDefault().register(this)
    }

    //初始化事件
    private fun initListener() {
        binding.back.setOnClickListener { finish() }

        binding.accountBind.onSingleClick {
            hintKeyBoard()
            var accounted = account_edit!!.editableText.toString()
            var passworded = passwor_edit!!.editableText.toString()
            if (TextUtils.isEmpty(accounted)) {
                ToastUtils.showCenterToast(this, "请输入帐号!")
                return@onSingleClick
            }

            if (TextUtils.isEmpty(passworded)) {
                ToastUtils.showCenterToast(this, "请输入密码!")
                return@onSingleClick
            }
            presenter!!.putBind(accounted, passworded, unionid)
        }
    }


    private fun initView() {
        unionid = intent.getStringExtra("unionid").toString()
        account_edit = binding.inputAccount
        passwor_edit = binding.inputPassword
        val filters = arrayOf(
            ExpressionInputFilter(),
            InputFilter.LengthFilter(LoginConstant.ACCOUNT_INPUT_LENGTH)
        )
        account_edit!!.filters = filters
        passwor_edit!!.filters = filters
        if (account_edit != null && passwor_edit != null) {
            val accountobservable = account_edit!!.textChanges()
            val passwordobservable = passwor_edit!!.textChanges()
            // 相当于合并
            Observable.combineLatest(accountobservable, passwordobservable,
                BiFunction<CharSequence, CharSequence, Boolean> { _, _ ->
                    val account = account_edit!!.getEditableText().toString()
                    val password = passwor_edit!!.editableText.toString()
                    return@BiFunction !TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)

                })
                .subscribe { aBoolean ->
                    if (aBoolean!!) {
                        binding.accountBind.setEnabled(true)
                        // account_login.setBackgroundResource(R.drawable.account_login_select)
                    } else {
                        binding.accountBind.setEnabled(false)
                        //  account_login.setBackgroundResource(R.drawable.ic_account_not_login)
                    }
                }
        }

        //显示密码按钮
        binding.shownPassword.setOnClickListener {
            if (passwordvisible) {
                binding.shownPassword.isSelected = false
                passwordvisible = false
            } else {
                binding.shownPassword.isSelected = true
                passwordvisible = true
            }

            var position = binding.inputPassword.editableText.length
            shownPassword(passwordvisible)
            binding.inputPassword.setSelection(position)
        }
    }


    private fun shownPassword(isshown: Boolean) {
        if (isshown) {
            binding.inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_CLASS_TEXT);
        } else {
            binding.inputPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }
    }

    override fun getUserSucess(date: TeacherBean?) {
        //绑定成功
        var bingSuccess = BingSuccessDialog()
        bingSuccess.show(supportFragmentManager, "bingSuccess")
        bingSuccess.setMassageText(R.string.bingsuccess)
        bingSuccess.setOnCommonDialogListener(object : OnCommonDialogListener {
            override fun OnCommonDialogCancel() {

            }

            override fun OnCommonDialogConfirm() {
                //跳转到主界面
                LogUtils.e("测试绑定成功")
                RxBus.getDefault().post(LoginSuccessEvent())
                finish()
            }

        })

    }

    override fun getUserFailure() {

    }

    override fun BindFailure(failure: String) {
        var bingfailuredialog = GraceDialog()
        bingfailuredialog.setMassageText(failure)
        bingfailuredialog.show(supportFragmentManager, "bingfailuredialog")

    }

    override fun RebindingAccount() {
        //重新绑定账号
        var rebingdialog = RebindingDialog()
        rebingdialog.show(supportFragmentManager, "rebingdialog")
        rebingdialog.setMassageText(R.string.previous_account_has_tied)
        rebingdialog.setOnCommonDialogListener(object : OnCommonDialogListener {
            override fun OnCommonDialogCancel() {

            }

            override fun OnCommonDialogConfirm() {
                //重新绑定账号

            }

        })
    }

    override fun setPresenter(presenter: BindPresenter) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
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
}
