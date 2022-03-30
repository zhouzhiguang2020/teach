package com.future_education.person.activity


import android.os.Bundle
import android.view.View
import com.future_education.person.R
import com.future_education.person.databinding.ActivityContactUsLayoutBinding
import com.futureeducation.commonmodule.activities.CommonActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.dylanc.viewbinding.nonreflection.binding
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


/*
 * @Author wljy
 * @Date 2021/11/23
 * @Des 联系我们
 */
class ContactUsActivity : CommonActivity(), View.OnClickListener {
    private val binding by binding(ActivityContactUsLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_contact_us_layout)
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
        binding.topLayout.titleTxt.text = getText(R.string.contact_us)
        binding.copyQq.setOnClickListener(this)
        binding.copyPhone.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> {
                finish()
            }
            R.id.copy_qq -> {
                var qqisselect = binding.copyQq.isSelected
                if (!qqisselect) {
                    binding.copyQq.isSelected = true
                    binding.copyPhone.isSelected = false
                    binding.copyQq.text=getString(R.string.copy_success)
                    binding.copyPhone.text=getString(R.string.key_copy)
                    copyClieck(binding.qq.text.toString())
                }

            }
            R.id.copy_phone -> {
                var Phoneisselect = binding.copyPhone.isSelected
                if (!Phoneisselect) {
                    binding.copyPhone.isSelected = true
                    binding.copyQq.isSelected = false
                    binding.copyQq.text=getString(R.string.key_copy)
                    binding.copyPhone.text=getString(R.string.copy_success)
                    copyClieck(binding.phone.text.toString())
                }
            }
        }
    }

    fun copyClieck(copystr:String) {

        // 获取系统剪贴板
        val clipboard: ClipboardManager =
            this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        val clipData = ClipData.newPlainText(null, copystr)
        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData)
    }
}