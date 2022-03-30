package com.future_education.teacher.contact

import com.fude.commonlibrary.presenter.BasePresenters
import com.fude.commonlibrary.presenter.BaseViews
import com.future_education.loginmodule.presenter.WXEntryPresenter

import com.futureeducation.commonmodule.model.TeacherBean

/**
 * 微信
 */
interface WXEntryContact {
     interface View : BaseViews<WXEntryPresenter> {
        fun getUserSucess(date: TeacherBean?)
        fun getUserFailure()
        fun BindFailure(unionid:String)
         fun AccountExpired()
    }

    interface Presenter : BasePresenters {
        //执行登录
        fun putWX(code: String)
    }
}