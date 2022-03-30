package com.future_education.teacher.loginmodule.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.apkfuns.logutils.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.loginmodule.adapter.HistortApiAdapter
import com.future_education.teacher.loginmodule.R
import com.future_education.teacher.loginmodule.databinding.ActivityHistoryApiLayoutBinding
import com.future_education.teacher.loginmodule.databinding.HistoryApiLayoutBinding
import com.future_education.teacher.loginmodule.modle.APIBean
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.location.BaseurlLocation
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.gyf.immersionbar.ktx.immersionBar



/**
 *
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description:     历史API
 * @Author:         作者名
 * @CreateDate:     2019/12/10 9:59
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/12/10 9:59
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class HistoryApiActivity : CommonActivity() {
    private var adapter: HistortApiAdapter? = null
    var sp = SharedPreferencesUtil.getInstance(this)
    private val binding by binding(ActivityHistoryApiLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_api_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.login_style_color)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.black_2)
        }
        initview()
    }

    private fun initview() {
        binding.back.setOnClickListener { finish() }
        var namelist: ArrayList<APIBean> = ArrayList()
        var service_api_list = sp.getString(CommonConstants.SERVICEAPILIST,"")
        if(service_api_list!=""){
            namelist  = JSON.parseObject(service_api_list,object : TypeReference<ArrayList<APIBean>>() {})
            var aPIBean1=  APIBean()
            aPIBean1.apiUrl="https://uatcmsapi.apfeg.com"
            namelist.add(aPIBean1)
        }else{
            var bean = APIBean()
            bean.apiUrl = "https://api.ytwljy.com"
            namelist.add(bean)
            var bean2 = APIBean()
            bean2.apiUrl = "http://sit.api.apfeg.com"
            namelist.add(bean2)
            var bean3 = APIBean()
            bean3.apiUrl = "https://sitcmsapi.apfeg.com"
            namelist.add(bean3)
            var aPIBean1=  APIBean()
            aPIBean1.apiUrl="https://uatcmsapi.apfeg.com"
            namelist.add(aPIBean1)
            var jsonStr = JSON.toJSON(namelist).toString()
            sp.saveString(CommonConstants.SERVICEAPILIST,jsonStr)
        }
        var manager = GridLayoutManager(this, 1)
        binding.recyclerview.layoutManager = manager
        adapter = HistortApiAdapter(this, R.layout.history_api_layout, namelist)
        binding.recyclerview!!.adapter = adapter
        adapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                var bean = adapter!!.getItem(position) as APIBean
                sp.saveString(CommonConstants.SERVICEAPI,bean.apiUrl)
                BaseurlLocation.BASEURL = bean.apiUrl
                if(SettingApiActivity.ACTIVITY!=null){
                    SettingApiActivity!!.ACTIVITY!!.finish()
                }
                finish()
            }
        })
    }
}
