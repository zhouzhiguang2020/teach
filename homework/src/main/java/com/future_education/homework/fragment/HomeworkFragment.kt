package com.future_education.homework.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.future_education.homework.R

import com.future_education.homework.databinding.FragmentHomeworkLayoutBinding
import com.futureeducation.commonmodule.extension.viewBinding

import com.futureeducation.commonmodule.fragment.CommonFragment


class HomeworkFragment : CommonFragment() {

    private val binding by viewBinding(FragmentHomeworkLayoutBinding::bind)
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_homework_layout)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.text.text = "使用了"
    }
}


