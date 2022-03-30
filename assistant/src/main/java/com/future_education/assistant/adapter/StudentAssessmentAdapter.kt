package com.future_education.assistant.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.future_education.assistant.R
import com.future_education.assistant.model.StudentAssessmentBean
import com.futureeducation.commonmodule.activities.CommonActivity
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/26
 * Time: 15:45
 * 学生评价适配器
 */
class StudentAssessmentAdapter : BaseQuickAdapter<StudentAssessmentBean, BaseViewHolder> {
    private var commonActivity: CommonActivity? = null

    constructor(
        layoutResId: Int,
        data: MutableList<StudentAssessmentBean>?,
        commonActivity: CommonActivity?
    ) : super(layoutResId, data) {
        this.commonActivity = commonActivity
    }

    override fun convert(holder: BaseViewHolder, item: StudentAssessmentBean) {
        var head_portrait = holder.getView<CircleImageView>(R.id.head_portrait)
        var student_name = holder.getView<TextView>(R.id.student_name)
        var student_number = holder.getView<TextView>(R.id.student_number)
        var remark = holder.getView<TextView>(R.id.remark)
        var score = holder.getView<TextView>(R.id.score)
        var evaluation_time = holder.getView<TextView>(R.id.evaluation_time)
    }

}