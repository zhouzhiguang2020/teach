package com.future_education.person.adapter

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.future_education.person.R
import com.future_education.person.model.DailyClasseBean
import com.future_education.person.model.StudentBean
import com.futureeducation.commonmodule.activities.CommonActivity

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/24
 * Time: 15:26
 * 老师上课适配器
 */
class DailyCoursePageAdapter : BaseQuickAdapter<DailyClasseBean, BaseViewHolder> {
    private var commonActivity: CommonActivity? = null

    constructor(
        layoutResId: Int,
        data: MutableList<DailyClasseBean>?,
        commonActivity: CommonActivity?
    ) : super(layoutResId, data) {
        this.commonActivity = commonActivity
    }

    override fun convert(holder: BaseViewHolder, item: DailyClasseBean) {
        var lesson_number = holder.getView<TextView>(R.id.lesson_number)
        var subject = holder.getView<TextView>(R.id.subject)
        var grade = holder.getView<TextView>(R.id.grade)
        var class_period = holder.getView<TextView>(R.id.class_period)
        var line = holder.getView<View>(R.id.line)
        var item_layout = holder.getView<ConstraintLayout>(R.id.item_layout)
        if (item != null) {


            var subjecttext = item.subject
            var gradetext = item.grade
            if (TextUtils.isEmpty(subjecttext) && TextUtils.isEmpty(gradetext)) {
                grade.visibility = View.GONE
                subject.text = "-"
            } else {
                grade.visibility = View.VISIBLE
                subject.text = subjecttext
                grade.text = "(${gradetext})"
            }
            class_period.text = item.classtimeperiod
            if (item.isIslunchbreak) {
                lesson_number.visibility = View.GONE
                subject.text = commonActivity!!.getString(R.string.lunch_break)
                grade.text = ""
                line.visibility = View.GONE
                item_layout.setBackgroundResource(R.drawable.lunch_break_background_shape)
            } else {
                lesson_number.visibility = View.VISIBLE

                var position = holder.layoutPosition
                if (position > 4) {
                    lesson_number.text = (position ).toString()
                } else {
                    lesson_number.text = item.sortingclass
                }
                if (position == data.size - 1) {
                    line.visibility = View.GONE
                } else {
                    line.visibility = View.VISIBLE
                }
                item_layout.setBackgroundResource(0)
            }

        }
    }
}