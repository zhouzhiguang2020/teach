package com.future_education.assistant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.apkfuns.logutils.LogUtils
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.assistant.R
import com.future_education.assistant.databinding.ActivityEditEvaluationLayoutBinding
import com.future_education.assistant.databinding.ActivityNewEvaluationLayoutBinding
import com.futureeducation.commonmodule.activities.CommonActivity
import com.gyf.immersionbar.ktx.immersionBar

/*
 * @Author wljy
 * @Date 2021/11/27
 * @Des 新建评价
 */
class NewEvaluationActivity : CommonActivity(), View.OnClickListener {


    private val binding by binding(ActivityNewEvaluationLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_evaluation_layout)
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
        binding.topLayout.titleTxt.text = getString(R.string.new_evaluation_text)
        binding.praise.isSelected = true
        binding.amendment.isSelected = false
        binding.praise.setOnClickListener(this)
        binding.amendment.setOnClickListener(this)
        binding.submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> {
                finish()
            }
            R.id.praise -> {
                //选择的是表扬
                binding.praise.isSelected = true
                binding.amendment.isSelected = false
            }
            R.id.amendment -> {
                //选择的是表扬
                binding.praise.isSelected = false
                binding.amendment.isSelected = true
            }
            R.id.submit -> {
                //选择的是表扬
                LogUtils.w("更改评论=")
                hintKeyBoard()
            }
        }
    }
}