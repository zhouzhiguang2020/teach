package com.future_education.teacher.loginmodule.contact

import com.fude.commonlibrary.presenter.BasePresenters
import com.fude.commonlibrary.presenter.BaseViews
import com.future_education.loginmodule.presenter.BindPresenter
import com.futureeducation.commonmodule.model.TeacherBean


/**
 * 绑定
 */
interface BindContact {
    public interface View : BaseViews<BindPresenter> {
        fun getUserSucess(date: TeacherBean?)
        fun getUserFailure()
        fun BindFailure(failure: String)
        fun RebindingAccount()

    }

    interface Presenter : BasePresenters {
        //执行登录
        fun putBind(keyword: String, account: String,unionid: String)
    }
}