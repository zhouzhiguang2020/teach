package com.future_education.assistant.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.future_education.assistant.R
import com.future_education.assistant.model.EvaluationIndicatorItemBean
import com.futureeducation.commonmodule.activities.CommonActivity

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/27
 * Time: 11:15
 */
class EvaluationIndicatorsAdapter : BaseQuickAdapter<EvaluationIndicatorItemBean, BaseViewHolder> {
    private var commonActivity: CommonActivity? = null

    constructor(
        layoutResId: Int,
        data: MutableList<EvaluationIndicatorItemBean>?,
        commonActivity: CommonActivity?
    ) : super(layoutResId, data) {
        this.commonActivity = commonActivity
        addChildClickViewIds(R.id.modification, R.id.remove)
    }

    override fun convert(holder: BaseViewHolder, item: EvaluationIndicatorItemBean) {
        var comment = holder.getView<TextView>(R.id.comment)
        var score = holder.getView<TextView>(R.id.score)
        //修改稿
        var modification = holder.getView<ImageView>(R.id.modification)
        var remove = holder.getView<ImageView>(R.id.remove)
        var evaluation_label = holder.getView<ImageView>(R.id.evaluation_label)
        var position = holder.layoutPosition
        if (position == data.size - 1) {
            evaluation_label.setImageResource(R.drawable.ic_amendment)
        } else {
            evaluation_label.setImageResource(R.drawable.ic_praise)
        }

    }
}