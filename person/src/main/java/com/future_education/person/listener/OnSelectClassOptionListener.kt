package com.future_education.person.listener

import com.future_education.person.model.TeacherClassBean

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/22
 * Time: 11:25
 * 选择班级回调监听
 */
interface OnSelectClassOptionListener {
    fun  OnSelectClassOptionsItem(selectbean: TeacherClassBean)
}