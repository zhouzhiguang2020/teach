package com.future_education.teacher.loginmodule.contact

import com.fude.commonlibrary.presenter.BasePresenters
import com.fude.commonlibrary.presenter.BaseViews
import com.future_education.loginmodule.presenter.LoginPresenter
import com.futureeducation.commonmodule.model.TeacherBean


/**
 * Created by LoginContact.
 * User: ASUS
 * Date: 2019/12/23
 * Time: 17:00
 */
interface LoginContact {

    public interface View : BaseViews<LoginPresenter> {
        fun shownLoginSucess(date: TeacherBean?)
        fun logingFailure()
    }

    interface Presenter : BasePresenters {
        //执行登录

        fun ExecutiveLogging(keyword: String, account: String)
    }
}