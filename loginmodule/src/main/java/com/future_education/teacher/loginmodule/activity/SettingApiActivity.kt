package com.future_education.teacher.loginmodule.activity

import android.os.Bundle
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.teacher.loginmodule.R
import com.future_education.teacher.loginmodule.databinding.ActivityHistoryApiLayoutBinding
import com.future_education.teacher.loginmodule.databinding.ActivitySettingApiLayoutBinding
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
 * @Description:     联系我们
 * @Author:         作者名
 * @CreateDate:     2019/12/10 9:59
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/12/10 9:59
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SettingApiActivity : CommonActivity() {
    var sp = SharedPreferencesUtil.getInstance(this)
    companion object {
        var ACTIVITY:SettingApiActivity? = null
    }
    private val binding by binding(ActivitySettingApiLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_api_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.login_style_color)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.black_2)
        }
        ACTIVITY = this
        initview()
    }

    private fun initview() {
        binding.back.setOnClickListener { finish() }
        var manager = GridLayoutManager(this, 1)
        var service_api = sp.getString(CommonConstants.SERVICEAPI,BaseurlLocation.PAD_BAST_URL)
        var namelist: ArrayList<APIBean> = ArrayList()
        var service_api_list = sp.getString(CommonConstants.SERVICEAPILIST,"")
        if(service_api_list!=""){
            namelist  = JSON.parseObject(service_api_list,object : TypeReference<ArrayList<APIBean>>() {})
        }
        binding.serviceEdit.setText(service_api)
        binding.define!!.setOnClickListener{
            var api = binding.serviceEdit!!.editableText.toString()
            BaseurlLocation.BASEURL = api
            sp.saveString(CommonConstants.SERVICEAPI,api)
            var flag = false
            for(item in namelist){
                if(item.apiUrl == api){
                    flag = true
                }
            }
            if(!flag){
                var bean = APIBean()
                bean.apiUrl = api
                namelist.add(bean)
                var jsonStr = JSON.toJSON(namelist).toString()
                sp.saveString(CommonConstants.SERVICEAPILIST,jsonStr)
            }

            finish()
        }
        binding.history.setOnClickListener{
            jumpActivity(HistoryApiActivity::class.java)
        }
    }
}
