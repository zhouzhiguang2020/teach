package com.future_education.homework.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View

import com.future_education.learningcondition.R
import com.future_education.learningcondition.databinding.FragmentLearningConditionLayoutBinding
import com.futureeducation.commonmodule.extension.viewBinding

import com.futureeducation.commonmodule.fragment.CommonFragment

/*
 * @Author wljy
 * @Date 2021/11/19
 * @Des 学情主页
 */
class LearningConditionFragment : CommonFragment() {

    private val binding by viewBinding(FragmentLearningConditionLayoutBinding::bind)
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_learning_condition_layout)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.text.text = "学情况使用了"
    }
}


