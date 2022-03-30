package com.futureeducation.commonmodule.extension

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.futureeducation.commonmodule.activities.CommonActivity

/*
 * @Author wljy
 * @Date 2020/12/22
 * @Des activity 使用viewbing
 */
inline fun <T : ViewBinding> CommonActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }