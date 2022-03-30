package com.future_education.person.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.apkfuns.logutils.LogUtils
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.contrarywind.listener.OnItemSelectedListener
import com.contrarywind.view.WheelView
import com.future_education.person.R
import com.future_education.person.constant.PersonConstant
import com.future_education.person.listener.OnSelectClassOptionListener
import com.future_education.person.model.TeacherClassBean
import com.futureeducation.commonmodule.listener.OnCommonDialogListener
import com.futureeducation.commonmodule.view.CommonShapeButton

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/22
 * Time: 11:19
 * 学生管理选择班级对话框
 */
class SelectClassDialog : AppCompatDialogFragment(),View.OnClickListener  {


    private var mWheelView: WheelView? = null
    private var selectclasslist = mutableListOf<TeacherClassBean>()
    private var selecteOptionsItemsBean: TeacherClassBean? = null
    private var listener: OnSelectClassOptionListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //full screen dialog fragment
        setStyle(DialogFragment.STYLE_NORMAL, R.style.common_dialog)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialogWindow = dialog!!.window
        val lp = dialogWindow!!.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.gravity = Gravity.BOTTOM
        dialogWindow.attributes = lp
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialog = getDialog()
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogWindow = dialog?.getWindow()
        dialogWindow!!.setBackgroundDrawableResource(com.futureeducation.commonmodule.R.color.transparent)
        dialogWindow!!.getDecorView().setPadding(0, 0, 0, 0)// 这个很重
        dialogWindow!!.setGravity(Gravity.CENTER)
        dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnimationbottom);
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var view = inflater.inflate(R.layout.dialog_select_courses_layout, null)
        mWheelView = view.findViewById(R.id.wheelview)
        var titeltext = view.findViewById<TextView>(R.id.text)
        titeltext.text = getString(R.string.select_courser)
        view.findViewById<TextView>(R.id.confirm).setOnClickListener(this)
        initview()
        return view
    }


    @SuppressLint("UseRequireInsteadOfGet")
    private fun initview() {
        selectclasslist = arguments!!.getParcelableArrayList<TeacherClassBean>(
            PersonConstant.SUBJECT_COURSE
        )!!
//        selectclasslist = temp.distinctBy {
//            it.class_name == getString(R.string.all_the_class)
//
//        } as MutableList<TeacherClassBean>
        LogUtils.w("查看结果：" + selectclasslist.toString())
        mWheelView!!.setTextSize(15f)
        mWheelView!!.setTextColorOut(ContextCompat.getColor(context!!, R.color.textGrayDeep_6))
        mWheelView!!.setTextColorCenter(ContextCompat.getColor(context!!, R.color.text_normal_color2))
        mWheelView!!.setLineSpacingMultiplier(4f)
        mWheelView!!.setAlphaGradient(true)
        mWheelView!!.setItemsVisibleCount(5)
        mWheelView!!.setDividerColor(ContextCompat.getColor(context!!, R.color.borderDeep))
        mWheelView!!.setDividerType(WheelView.DividerType.FILL)
        mWheelView!!.setCyclic(false)
        val mOptionsItems: MutableList<String?> = mutableListOf()
        selecteOptionsItemsBean = selectclasslist.get(0)
        selectclasslist.forEachIndexed { index, screeningSubjectsBean ->
            var stringBuffer = StringBuffer()
            var levelname = screeningSubjectsBean.class_levelView
            var classname = screeningSubjectsBean.class_name
            if (!TextUtils.isEmpty(levelname)) {
                stringBuffer.append(levelname)
            }
            if (!TextUtils.isEmpty(classname)) {
                stringBuffer.append(classname)
            }
            var temp = stringBuffer.toString()
            mOptionsItems.add(temp)
        }
        mWheelView!!.setAdapter(ArrayWheelAdapter<String?>(mOptionsItems))
        mWheelView!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                LogUtils.w("选择的index是：" + index)
                selecteOptionsItemsBean = selectclasslist.get(index)

            }

        })

    }
    fun setOnSelectCoursesOptionListener(listener: OnSelectClassOptionListener) {
        this.listener = listener

    }

    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.confirm -> {
                //
                if (listener != null && selecteOptionsItemsBean != null) {
                    listener!!.OnSelectClassOptionsItem(selecteOptionsItemsBean!!)
                }
                dismiss()
            }
        }
    }

}