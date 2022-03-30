package com.future_education.homework.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.future_education.assistant.R
import com.future_education.assistant.activity.ClassroomAssessmentActivity
import com.future_education.assistant.activity.LearningEvaluationActivity
import com.future_education.assistant.databinding.FragmentAssistantLayoutBinding
import com.futureeducation.commonmodule.extension.viewBinding


import com.futureeducation.commonmodule.fragment.CommonFragment

/*
 * @Author wljy
 * @Date 2021/11/19
 * @Des 学情主页
 */
class AssistantFragment : CommonFragment(), View.OnClickListener {

    private val binding by viewBinding(FragmentAssistantLayoutBinding::bind)
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_assistant_layout)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
    }

    private fun initview() {
        binding.topLayout.back.visibility = View.GONE
        binding.topLayout.titleTxt.text = getString(R.string.assistan)
        var evaluation_list =
            binding.evaluationListLayout.headerLayout.findViewById<TextView>(R.id.evaluation_list)
        var header_root_layout =
            binding.evaluationListLayout.headerLayout.findViewById<ConstraintLayout>(R.id.header_root_layout)
        val drawable2 = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_right_arrows
        )
        drawable2!!.setBounds(0, 0, 42, 42);    //需要设置图片的大小才能显示
        val drawable1 = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_up_arrows
        )
        drawable1!!.setBounds(0, 0, 42, 42);

        val drawable3 = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_evaluation_list
        )
        drawable3!!.setBounds(0, 0, drawable3!!.getMinimumWidth(), drawable3.getMinimumHeight());
        drawable1!!.setBounds(0, 0, 42, 42);
        binding.evaluationListLayout.setLayoutAnimationListener(object :
            Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                var isopen = binding.evaluationListLayout!!.isOpened
                if (isopen) {
                    header_root_layout.setBackgroundResource(R.drawable.evaluation_list_header_open_background_shape)
                    evaluation_list!!.setCompoundDrawables(drawable3, null, drawable1, null)
                } else {
                    evaluation_list!!.setCompoundDrawables(drawable3, null, drawable2, null)
                    header_root_layout.setBackgroundResource(R.drawable.evaluation_list_header_normal_background_shape)
                }

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        binding.evaluationListLayout.contentLayout.findViewById<TextView>(R.id.learning_evaluation)
            .setOnClickListener(this)
        binding.evaluationListLayout.contentLayout.findViewById<TextView>(R.id.classroom_assessment)
            .setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.learning_evaluation -> {
                //学习评价
                var inten = Intent(activity, LearningEvaluationActivity::class.java)
                startActivity(inten)

            }
            R.id.classroom_assessment -> {
                //课堂评价
                var inten = Intent(activity, ClassroomAssessmentActivity::class.java)
                startActivity(inten)
            }
        }
    }
}


