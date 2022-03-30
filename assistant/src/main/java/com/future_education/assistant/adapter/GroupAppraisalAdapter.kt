package com.future_education.assistant.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.future_education.assistant.R
import com.future_education.assistant.model.GroupAppraisalBean
import com.futureeducation.commonmodule.activities.CommonActivity
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/26
 * Time: 16:36
 */
class GroupAppraisalAdapter : BaseQuickAdapter<GroupAppraisalBean, BaseViewHolder> {
    private var commonActivity: CommonActivity? = null

    constructor(
        layoutResId: Int,
        data: MutableList<GroupAppraisalBean>?,
        commonActivity: CommonActivity?
    ) : super(layoutResId, data) {
        this.commonActivity = commonActivity
    }

    override fun convert(holder: BaseViewHolder, item: GroupAppraisalBean) {
        var group_head_portrait = holder.getView<CircleImageView>(R.id.group_head_portrait)
        var group_name = holder.getView<TextView>(R.id.group_name)
        var remark = holder.getView<TextView>(R.id.remark)
        var score = holder.getView<TextView>(R.id.score)
        var group_list = holder.getView<TextView>(R.id.group_list)
        var evaluation_time = holder.getView<TextView>(R.id.evaluation_time)
    }
}