package com.future_education.person.transition

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.future_education.person.R
import com.shizhefei.view.indicator.Indicator
import com.shizhefei.view.utils.ColorGradient

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/24
 * Time: 11:39
 * 头部变换实现
 */
class ClassScheduleTransition : Indicator.OnTransitionListener {
    private var selectSize = -1f
    private var unSelectSize = -1f
    private var gradient: ColorGradient? = null
    private var dFontFize = -1f

    private var isPxSize = false

    fun ClassScheduleTransition() {

    }

    fun ClassScheduleTransition(
        selectSize: Float,
        unSelectSize: Float,
        selectColor: Int,
        unSelectColor: Int
    ) {

        setColor(selectColor, unSelectColor)
        setSize(selectSize, unSelectSize)
    }

    fun setSize(selectSize: Float, unSelectSize: Float): ClassScheduleTransition {
        isPxSize = false
        this.selectSize = selectSize
        this.unSelectSize = unSelectSize
        dFontFize = selectSize - unSelectSize
        return this
    }

    fun setValueFromRes(
        context: Context, selectColorId: Int, unSelectColorId: Int, selectSizeId: Int,
        unSelectSizeId: Int
    ): ClassScheduleTransition {
        setColorId(context, selectColorId, unSelectColorId)
        setSizeId(context, selectSizeId, unSelectSizeId)
        return this
    }

    fun setColorId(
        context: Context,
        selectColorId: Int,
        unSelectColorId: Int
    ): ClassScheduleTransition {
        val res = context.resources
        setColor(ContextCompat.getColor(context, selectColorId), ContextCompat.getColor(context,unSelectColorId))
        return this
    }

    fun setSizeId(
        context: Context,
        selectSizeId: Int,
        unSelectSizeId: Int
    ): ClassScheduleTransition {
        val res = context.resources
        setSize(
            res.getDimensionPixelSize(selectSizeId).toFloat(),
            res.getDimensionPixelSize(unSelectSizeId).toFloat()
        )
        isPxSize = true
        return this
    }

    fun setColor(selectColor: Int, unSelectColor: Int): ClassScheduleTransition {
        gradient = ColorGradient(unSelectColor, selectColor, 100)
        return this
    }

    /**
     * 如果tabItemView 不是目标的TextView，那么你可以重写该方法返回实际要变化的TextView
     *
     * @param tabItemView
     * Indicator的每一项的view
     * @param position
     * view在Indicator的位置索引
     * @return
     */
    fun getTextView(tabItemView: View, position: Int): TextView {
        var tabItemView=tabItemView.findViewById<TextView>(R.id.text)
        return tabItemView
    }

    override fun onTransition(view: View, position: Int, selectPercent: Float) {
        val selectTextView = getTextView(view, position)
        if (gradient != null) {
            selectTextView.setTextColor(gradient!!.getColor((selectPercent * 100).toInt()))
        }
        if (unSelectSize > 0 && selectSize > 0) {
            if (isPxSize) {
                selectTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    unSelectSize + dFontFize * selectPercent
                )
            } else {
                selectTextView.textSize = unSelectSize + dFontFize * selectPercent
            }
        }
    }
}