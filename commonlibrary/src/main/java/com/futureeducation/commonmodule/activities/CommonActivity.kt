package com.futureeducation.commonmodule.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.futureeducation.commonmodule.R
import com.futureeducation.commonmodule.application.CommonApplication
import com.futureeducation.commonmodule.inter.OnViewHolder
import com.futureeducation.commonmodule.inter.ViewHolder
import com.futureeducation.commonmodule.manager.TeacherManager
import com.futureeducation.commonmodule.view.CatLoadingView
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.OSUtils
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import com.yanzhenjie.kalle.Kalle

/**
 *
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description:     java类作用描述
 * @Author:         作者名
 * @CreateDate:     2019/11/12 13:26
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/11/12 13:26
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class CommonActivity() : RxAppCompatActivity() {

    //    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()
//
//    @CheckResult
//    override fun lifecycle(): Observable<ActivityEvent> {
//        return lifecycleSubject.hide()
//    }
//
//    @CheckResult
//    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
//        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
//    }
//
//    @CheckResult
//    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
//        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
//    }
    private var mCatLoadingView: CatLoadingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //禁止录屏和截屏
        mCatLoadingView = CatLoadingView()
//        if (!CommonConstants.ISDUG) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
//        }
        super.onCreate(savedInstanceState)
        // ActivityManager.getInstance().addActivity(this)
        //lifecycleSubject.onNext(ActivityEvent.CREATE)
        // 所有子类都将继承这些相同的属性,请在设置界面之后设置
//        var istablet = isTablet()
//        if (istablet) {
//            //是平板要横屏
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        } else {
//            //手机竖屏
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        }

//        val config = Kalle.getConfig()
//        val headers = config.headers
//
//        if (headers["Authorization"] == null) {
//            //初始化不在这里
//            var application = application as CommonApplication
//            application.initialize()
//        }

        ImmersionBar.with(this).init();
    }


    override fun onStart() {
        super.onStart()
        //lifecycleSubject.onNext(ActivityEvent.START)
    }


    override fun onResume() {
        super.onResume()
        // lifecycleSubject.onNext(ActivityEvent.RESUME)
        // 非必加
        // 如果你的app可以横竖屏切换，适配了华为emui3系列系统手机，并且navigationBarWithEMUI3Enable为true，
        // 请在onResume方法里添加这句代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、华为emui3系列系统手机；3、navigationBarWithEMUI3Enable为true）
        // 否则请忽略
        if (OSUtils.isEMUI3_x()) {
            // ImmersionBar.with(this).init();
        }
    }


    override fun onPause() {
        super.onPause()
        //lifecycleSubject.onNext(ActivityEvent.PAUSE)

    }


    override fun onStop() {
        // lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
//        if (lifecycleSubject != null) {
//            lifecycleSubject.onNext(ActivityEvent.DESTROY)
//        }
        Kalle.cancel(this)
        // RxBus.getDefault().unregister(this)

    }


    // 跳转
    protected fun jumpActivity(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

    // 跳转
    fun jumpActivity(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }


    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        if (startActivitySelfCheck(intent!!)) {
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    private var mActivityJumpTag: String? = null
    private var mActivityJumpTime: Long = 0

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent          用于跳转的 Intent 对象
     * @return                检查通过返回true, 检查不通过返回false
     */
    fun startActivitySelfCheck(intent: Intent): Boolean {
        // 默认检查通过
        var result = true;
        // 标记对象
        var tag: String;
        var componentname = intent.component

        if (componentname != null) { // 显式跳转
            tag = componentname.getClassName();
        } else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction()!!;
        } else {
            return result;
        }

        if (tag.equals(mActivityJumpTag) && mActivityJumpTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false;
        }

        // 记录启动标记和时间
        mActivityJumpTag = tag;
        mActivityJumpTime = SystemClock.uptimeMillis();
        return result;
    }

    //影藏软键盘
    open fun hintKeyBoard() {
        //拿到InputMethodManager
        var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        //如果window上view获取焦点 && view不为空
        val view = getCurrentFocus()
        if (imm.isActive() && view != null) {
            //拿到view的token 不为空
            //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
            imm.hideSoftInputFromWindow(
                view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS
            );
        }

    }

    /**
     * make a Gloading.Holder wrap with current activity by default
     * override this method in subclass to do special initialization
     * @see SpecialActivity
     */
    protected fun initLoadingStatusViewIfNeed() {

    }

    protected fun onLoadRetry() {
        // override this method in subclass to do retry task
    }

    fun showLoading() {
        initLoadingStatusViewIfNeed()

    }

    fun showLoadSuccess() {
        initLoadingStatusViewIfNeed()

    }

    fun showLoadFailed() {
        initLoadingStatusViewIfNeed()

    }

    fun showEmpty() {
        initLoadingStatusViewIfNeed()

    }


    protected fun Exit() {
        LogUtils.e("执行退出登录了吗");

        finish()
    }

    /**
     * 判断是否是 平板 还是 手机
     * @return true : 平板 ; false : 手机
     */
    private fun isTablet(): Boolean {
        if ((resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return true
        } else {
            return false
        }
    }

    open fun showLoading2() {

        if (mCatLoadingView != null && mCatLoadingView!!.getDialog() != null
            && mCatLoadingView!!.getDialog()!!.isShowing()
        ) {
            //dialog is showing so do something

        } else {
            //dialog is not showing
            mCatLoadingView!!.show(supportFragmentManager, "dialog")
        }
    }

    open fun dismissLoading2() {
        if (mCatLoadingView != null && mCatLoadingView!!.getDialog() != null
            && mCatLoadingView!!.getDialog()!!.isShowing()
        ) {
            mCatLoadingView!!.dismiss()
        }
    }

    protected open fun getHelperView(group: ViewGroup?, layoutId: Int, listener: OnViewHolder?): View {
        val helper = ViewHolder(this!!.layoutInflater.inflate(layoutId, if (group == null) null else if (group is RecyclerView) group.parent as ViewGroup else group, false))
        if (listener != null) {
            listener.helper(helper)
        }
        return helper.getItemView()
    }

    /**
     * 空布局
     */
    protected open fun getEmptyView(mRecyclerView: RecyclerView?, imageId: Int, empty: String?, clickListener: View.OnClickListener?): View {
        return getHelperView(mRecyclerView, R.layout.common_empty, OnViewHolder { helper ->
            if (imageId != -1) {
                helper.setImageResource(R.id.image, imageId)
            }
            if (!TextUtils.isEmpty(empty)) {
                helper.setVisible(R.id.text, true)
                helper.setText(R.id.text, empty)
            }
            if (clickListener != null) {
                helper.setOnClickListener(R.id.empty_layout, clickListener)
            }
        })
    }
}