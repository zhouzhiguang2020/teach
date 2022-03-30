package com.future_education.assistant.scrollbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import com.shizhefei.view.indicator.slidebar.ScrollBar;

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/26
 * Time: 15:03
 */
public class DrawableBar implements ScrollBar {
    protected ScrollBar.Gravity gravity;
    protected View view;
    protected int drawableId;
    protected Drawable drawable;
    private Context context;

    public DrawableBar(Context context, int drawableId) {
        this(context, drawableId, Gravity.BOTTOM);
        this.context = context;
    }

    public DrawableBar(Context context, int drawableId, Gravity gravity) {
        this(context, context.getResources().getDrawable(drawableId), gravity);
        this.context = context;
    }

    public DrawableBar(Context context, Drawable drawable) {
        this(context, drawable, Gravity.BOTTOM);
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public DrawableBar(Context context, Drawable drawable, Gravity gravity) {
        view = new View(context);
        this.drawable = drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        this.gravity = gravity;
    }

    public int getColor() {
        return drawableId;
    }

    public void setColor(int color) {
        this.drawableId = color;
        view.setBackgroundColor(color);
    }

    @Override
    public int getHeight(int tabHeight) {

        return tabHeight ;
    }

    @Override
    public int getWidth(int tabWidth) {
        return tabWidth ;

    }

    @Override
    public View getSlideView() {
        return view;
    }

    @Override
    public Gravity getGravity() {
        return gravity;
    }

    public DrawableBar setGravity(Gravity gravity) {
        this.gravity = gravity;
        return this;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * 根据dip值转化成px值
     *
     * @param dip
     * @return
     */
    private int dipToPix(float dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }
}
