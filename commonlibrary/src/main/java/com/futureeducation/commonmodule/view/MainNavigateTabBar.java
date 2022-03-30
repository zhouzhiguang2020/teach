package com.futureeducation.commonmodule.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.futureeducation.commonmodule.R;

import java.util.ArrayList;
import java.util.List;

public class MainNavigateTabBar extends LinearLayout implements View.OnClickListener {

    private static final String KEY_CURRENT_TAG = "com.startsmake.template。currentTag";

    private List<ViewHolder> mViewHolderList;
    private OnTabSelectedListener mTabSelectListener;
    private FragmentActivity mFragmentActivity;
    private String mCurrentTag;
    private String mRestoreTag;
    /*主内容显示区域View的id*/
    private int mMainContentLayoutId;
    /*选中的Tab文字颜色*/
    private ColorStateList mSelectedTextColor;
    /*正常的Tab文字颜色*/
    private ColorStateList mNormalTextColor;
    /*Tab文字的颜色*/
    private float mTabTextSize;
    /*默认选中的tab index*/
    private int mDefaultSelectedTab = 0;

    private int mCurrentSelectedTab;
    private FragmentManager mFragmentManager;


    public MainNavigateTabBar(Context context) {
        this(context, null);
    }

    public MainNavigateTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainNavigateTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainNavigateTabBar, 0, 0);

        ColorStateList tabTextColor = typedArray.getColorStateList(R.styleable.MainNavigateTabBar_navigateTabTextColor);
        ColorStateList selectedTabTextColor = typedArray.getColorStateList(R.styleable.MainNavigateTabBar_navigateTabSelectedTextColor);

        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.MainNavigateTabBar_navigateTabTextSize, 0);
        mMainContentLayoutId = typedArray.getResourceId(R.styleable.MainNavigateTabBar_containerId, 0);

        mNormalTextColor = (tabTextColor != null ? tabTextColor : ContextCompat.getColorStateList(context, R.color.comui_tab_normal));

        if (selectedTabTextColor != null) {
            mSelectedTextColor = selectedTabTextColor;
        } else {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            mSelectedTextColor = ContextCompat.getColorStateList(context, typedValue.resourceId);
        }

        mViewHolderList = new ArrayList<>();
        typedArray.recycle();
    }


    public void addTab(Fragment frameLayoutClass, TabParam tabParam) {
        int defaultLayout = R.layout.comui_tab_view;

        if (TextUtils.isEmpty(tabParam.title)) {
            tabParam.title = getContext().getString(tabParam.titleStringRes);
        }

        View view = LayoutInflater.from(getContext()).inflate(defaultLayout, null);

        view.setFocusable(true);

        ViewHolder holder = new ViewHolder();

        holder.tabIndex = mViewHolderList.size();

        holder.fragmentClass = frameLayoutClass;
        holder.tag = tabParam.title;
        holder.pageParam = tabParam;

        holder.tabIcon = (ImageView) view.findViewById(R.id.tab_icon);
        holder.tabTitle = ((AppCompatTextView) view.findViewById(R.id.tab_title_text1));

        if (TextUtils.isEmpty(tabParam.title)) {
            holder.tabTitle.setVisibility(View.INVISIBLE);
        } else {
            holder.tabTitle.setVisibility(View.VISIBLE);
            holder.tabTitle.setText(tabParam.title);
        }


        if (mNormalTextColor != null) {
            holder.tabTitle.setTextColor(mNormalTextColor);
        }

        if (tabParam.backgroundColor > 0) {
            view.setBackgroundResource(tabParam.backgroundColor);
        }

        if (tabParam.iconResId > 0) {
            holder.tabIcon.setImageResource(tabParam.iconResId);
        } else {
            holder.tabIcon.setVisibility(View.INVISIBLE);
        }

        view.setTag(holder);
        view.setOnClickListener(this);
        mViewHolderList.add(holder);
        if (tabParam.titleStringRes == R.string.blank) {
            view.setVisibility(View.INVISIBLE);

        }
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0F);
        //params.gravity = Gravity.CENTER_VERTICAL;
        addView(view, params);
        mFragmentActivity = (FragmentActivity) getContext();
        mFragmentManager = mFragmentActivity.getSupportFragmentManager();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onClick(View v) {
        Object object = v.getTag();
        if (object != null && object instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) v.getTag();
            int tabIndex = holder.tabIndex;
            showFragment(holder);

        }
    }

    /**
     * 显示 holder 对应的 fragment
     *
     * @param holder
     */
    private void showFragment(ViewHolder holder) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (isFragmentShown(transaction, holder.tag)) {
            return;
        }
        setCurrSelectedTabByTag(holder.tag);
        Fragment fragment = mFragmentManager.findFragmentByTag(holder.tag);

        if (fragment == null) {

            fragment = getFragmentInstance(holder.tag);
            if (!fragment.isAdded()) {
                transaction.add(mMainContentLayoutId, fragment, holder.tag);
            }
        } else {
            transaction.show(fragment);


        }
        transaction.commitAllowingStateLoss();
        mCurrentSelectedTab = holder.tabIndex;
        if (mTabSelectListener != null) {
            mTabSelectListener.onTabSelected(holder);
        }
    }

    private boolean isFragmentShown(FragmentTransaction transaction, String newTag) {
        if (TextUtils.equals(newTag, mCurrentTag)) {
            return true;
        }

        if (TextUtils.isEmpty(mCurrentTag)) {
            return false;
        }

        Fragment fragment = mFragmentManager.findFragmentByTag(mCurrentTag);
        if (fragment != null && !fragment.isHidden()) {
            transaction.hide(fragment);
        }

        return false;
    }

    /*设置当前选中tab的图片和文字颜色*/
    private void setCurrSelectedTabByTag(String tag) {
        if (TextUtils.equals(mCurrentTag, tag)) {
            return;
        }
        for (ViewHolder holder : mViewHolderList) {
            if (TextUtils.equals(mCurrentTag, holder.tag)) {
                holder.tabIcon.setImageResource(holder.pageParam.iconResId);
                holder.tabTitle.setTextColor(mNormalTextColor);
            } else if (TextUtils.equals(tag, holder.tag)) {
                holder.tabIcon.setImageResource(holder.pageParam.iconSelectedResId);
                holder.tabTitle.setTextColor(mSelectedTextColor);
            }
        }
        mCurrentTag = tag;
    }


    private Fragment getFragmentInstance(String tag) {
        Fragment fragment = null;
        for (int i = 0; i < mViewHolderList.size(); i++) {
            ViewHolder holder = mViewHolderList.get(i);
            if (TextUtils.equals(tag, holder.tag)) {
                fragment = holder.fragmentClass;
                break;

            }
        }
        return fragment;
    }

    private void hideAllFragment() {
        if (mViewHolderList == null || mViewHolderList.size() == 0) {
            return;
        }
        FragmentTransaction transaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();

        for (ViewHolder holder : mViewHolderList) {
            Fragment fragment = mFragmentManager.findFragmentByTag(holder.tag);
            if (fragment != null && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    public void setSelectedTabTextColor(ColorStateList selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
    }

    public void setSelectedTabTextColor(int color) {
        mSelectedTextColor = ColorStateList.valueOf(color);
    }

    public void setTabTextColor(ColorStateList color) {
        mNormalTextColor = color;
    }

    public void setTabTextColor(int color) {

        mNormalTextColor = ColorStateList.valueOf(color);
    }

    public void setFrameLayoutId(int frameLayoutId) {
        mMainContentLayoutId = frameLayoutId;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRestoreTag = savedInstanceState.getString(KEY_CURRENT_TAG);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_CURRENT_TAG, mCurrentTag);
    }


    /**
     * 显示那个fragment
     */
    public void showTabFragment(int index) {
        mDefaultSelectedTab = index;
        ViewHolder defaultHolder = mViewHolderList.get(mDefaultSelectedTab);
        showFragment(defaultHolder);

    }

    public static class ViewHolder {
        public String tag;
        public TabParam pageParam;
        public ImageView tabIcon;
        public AppCompatTextView tabTitle;
        public Fragment fragmentClass;
        public int tabIndex;
    }


    public static class TabParam {
        public int backgroundColor = android.R.color.white;
        public int iconResId;
        public int iconSelectedResId;
        public int titleStringRes;
        public String title;

        public TabParam(int iconResId, int iconSelectedResId, String title) {
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.title = title;
        }

        public TabParam(int iconResId, int iconSelectedResId, int titleStringRes) {
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.titleStringRes = titleStringRes;
        }

        public TabParam(int backgroundColor, int iconResId, int iconSelectedResId, int titleStringRes) {
            this.backgroundColor = backgroundColor;
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.titleStringRes = titleStringRes;
        }

        public TabParam(int backgroundColor, int iconResId, int iconSelectedResId, String title) {
            this.backgroundColor = backgroundColor;
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.title = title;
        }
    }


    public interface OnTabSelectedListener {
        void onTabSelected(ViewHolder holder);

    }

    public void setTabSelectListener(OnTabSelectedListener tabSelectListener) {
        mTabSelectListener = tabSelectListener;
    }

    public void setDefaultSelectedTab(int index) {
        if (index >= 0 && index < mViewHolderList.size()) {
            mDefaultSelectedTab = index;
        }
    }

    public void setCurrentSelectedTab(int index) {
        if (index >= 0 && index < mViewHolderList.size()) {
            ViewHolder holder = mViewHolderList.get(index);
            showFragment(holder);
        }
    }

    public int getCurrentSelectedTab() {

        return mCurrentSelectedTab;
    }


}
