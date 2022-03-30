package com.future_education.person.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.person.R
import com.future_education.person.databinding.ActivityResetPasswordLayoutBinding
import com.futureeducation.commonmodule.activities.CommonActivity
import com.gyf.immersionbar.ktx.immersionBar

/*
 * @Author wljy
 * @Date 2021/11/22
 * @Des 重置密码
 */
class ResetPasswordActivity : CommonActivity(), View.OnClickListener {

     private val binding by binding(ActivityResetPasswordLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_reset_password_layout)
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
        binding.topLayout.titleTxt.text = getText(R.string.reset_passwor)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.back->{
                finish()
            }
        }
    }
}