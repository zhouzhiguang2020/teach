package com.future_education.assistant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.future_education.assistant.R
import com.futureeducation.commonmodule.activities.CommonActivity
import com.gyf.immersionbar.ktx.immersionBar

/*
 * @Author wljy
 * @Date 2021/11/25
 * @Des 学习评价
 */
class LearningEvaluationActivity : CommonActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning_evaluation_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        initview()
    }

    private fun initview() {

    }
}