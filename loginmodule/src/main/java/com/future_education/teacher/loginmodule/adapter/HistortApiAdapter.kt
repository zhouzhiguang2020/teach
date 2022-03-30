package com.future_education.loginmodule.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.future_education.teacher.loginmodule.R
import com.future_education.teacher.loginmodule.modle.APIBean
import com.futureeducation.commonmodule.activities.CommonActivity

/**
 * Created by TalkSelectAdapter.
 * User: ASUS
 * Date: 2019/12/9
 * Time: 15:08
 * talk 选择适配器
 */
class HistortApiAdapter : BaseQuickAdapter<APIBean, BaseViewHolder> {

    private var commonActivity: CommonActivity? = null

    constructor(
        activity: CommonActivity,
        layoutResId: Int,
        data: MutableList<APIBean>?
    ) : super(
        layoutResId,
        data
    ) {
        commonActivity = activity
    }


    override fun convert(helper: BaseViewHolder, item: APIBean) {
        var class_name = helper?.getView<TextView>(R.id.api_name)
        class_name!!.text = item!!.apiUrl
        var line = helper.getView<View>(R.id.line)
        if (line != null) {
            var position = helper.adapterPosition
            if (position == data.size - 1) {
                line.visibility = View.GONE
            } else {
                line.visibility = View.VISIBLE
            }
        }
    }
}