package com.future_education.person.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.dylanc.viewbinding.binding
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.person.R
import com.future_education.person.adapter.StudentManagementAdapter
import com.future_education.person.constant.PersonConstant
import com.future_education.person.databinding.ActivityStudentManagementLayoutBinding
import com.future_education.person.dialog.SelectClassDialog
import com.future_education.person.listener.OnSelectClassOptionListener
import com.future_education.person.model.StudentBean
import com.future_education.person.model.TeacherClassBean
import com.futureeducation.commonmodule.activities.CommonActivity
import com.gyf.immersionbar.ktx.immersionBar
import java.util.ArrayList

/*
 * @Author wljy
 * @Date 2021/11/20
 * @Des 学生管理
 * 只有班主任才能操作
 */
class StudentManagementActivity : CommonActivity(), View.OnClickListener {

    private val binding by binding(ActivityStudentManagementLayoutBinding::inflate)
    private var recyclerView: RecyclerView? = null
    private var adapter: StudentManagementAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_student_management_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        initview()
    }

    private fun initview() {
        binding.topLayout.back.setOnClickListener(this)
        binding.topLayout.titleTxt.text = getText(R.string.student_management)
        binding.topLayout.rightText.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.button_normal_style2
            )
        )
        binding.topLayout.rightText.setText(R.string.class_text)
        binding.topLayout.rightText.setOnClickListener(this)
        binding.topLayout.rightText.visibility = View.VISIBLE
        recyclerView = binding.recyclerview
        var manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = manager
        var datas = mutableListOf<StudentBean>()
        datas.add(StudentBean())
        datas.add(StudentBean())
        adapter = StudentManagementAdapter(R.layout.item_student_management_layout, datas, this)
        recyclerView!!.adapter = adapter
        adapter!!.setOnItemClickListener { adapter, view, position ->
            LogUtils.e("点击条目" + position)
            var intent = Intent(this, ResetPasswordActivity::class.java)
            jumpActivity(intent)

        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> {
                finish()
            }
            R.id.right_text -> {
                //选择班级
                var classList = mutableListOf<TeacherClassBean>()
                var classBean = TeacherClassBean()
                classBean.class_name = "一年级一班"
                var classBean2 = TeacherClassBean()
                classBean2.class_name = "一年级二班"
                var classBean3 = TeacherClassBean()
                classBean3.class_name = "一年级三班"
                var classBean4 = TeacherClassBean()
                classBean4.class_name = "一年级四班"
                var classBean5 = TeacherClassBean()
                classBean5.class_name = "一年级五班"
                var classBean6 = TeacherClassBean()
                classBean6.class_name = "一年级六班"
                var classBean7 = TeacherClassBean()
                classBean7.class_name = "一年级七班"
                classList.add(classBean)
                classList.add(classBean2)
                classList.add(classBean3)
                classList.add(classBean4)
                classList.add(classBean5)
                classList.add(classBean6)
                classList.add(classBean7)
                var dialog = SelectClassDialog()
                var args = Bundle()
                args.putParcelableArrayList(
                    PersonConstant.SUBJECT_COURSE,
                    classList as ArrayList<out TeacherClassBean>
                )
                dialog.arguments = args
                dialog.show(supportFragmentManager, "dialog")
                dialog.setOnSelectCoursesOptionListener(object : OnSelectClassOptionListener {
                    override fun OnSelectClassOptionsItem(selectbean: TeacherClassBean) {
                        LogUtils.e("选择的班级是：" + selectbean.toString())
                    }
                }

                )
            }

        }
    }
}