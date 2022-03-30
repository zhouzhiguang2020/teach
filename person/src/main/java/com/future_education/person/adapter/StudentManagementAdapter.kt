package com.future_education.person.adapter

import android.view.View
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dylanc.viewbinding.brvah.getViewBinding
import com.future_education.person.R
import com.future_education.person.databinding.ItemStudentManagementLayoutBinding
import com.future_education.person.model.StudentBean
import com.futureeducation.commonmodule.activities.CommonActivity
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/22
 * Time: 9:56
 */
class StudentManagementAdapter : BaseQuickAdapter<StudentBean, BaseViewHolder> {
    private var commonActivity: CommonActivity? = null
    private var requestOptions: RequestOptions? = null

    constructor(
        layoutResId: Int,
        data: MutableList<StudentBean>?,
        commonActivity: CommonActivity?
    ) : super(layoutResId, data) {
        this.commonActivity = commonActivity
        requestOptions = RequestOptions().error(R.drawable.ic_profile_photo)
            .placeholder(R.drawable.ic_profile_photo)
    }

    override fun convert(holder: BaseViewHolder, item: StudentBean) {
        var position = holder.layoutPosition
        holder.apply {
            var head_portrait = this.getView<CircleImageView>(R.id.head_portrait)
            var student_name = getView<TextView>(R.id.student_name)
            var userid = getView<TextView>(R.id.userid)
            var gender = getView<TextView>(R.id.gender)
            var line = getView<View>(R.id.line)


        }
    }
}