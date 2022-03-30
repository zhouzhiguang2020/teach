package com.futureeducation.commonmodule.location;

import com.futureeducation.commonmodule.application.CommonApplication;
import com.futureeducation.commonmodule.constants.CommonConstants;
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil;

/**
 * Created by BaseurlLocation.
 * User: ASUS
 * Date: 2019/12/23
 * Time: 19:07
 * <p>
 * 亚太未来教育Kevin <l19910309@gmail.com> 2020/4/13 8:55:43
 * 测试api地址:http://test.api.apfeg.com
 * 项目访问地址:http://test.ap20.apfeg.com
 */
public class BaseurlLocation {

    public static final String SCHEMES = "https";
    public static final String SCHEME = "http";
    private static final String SEPARATOR = "://";//separator
    //正式地址
    private static String BASE_URL = "api.apfeg.com";
    //public static String PAD_BAST_URL = "https://uatcmsapi.apfeg.com";//uat测试
    //测试地址1 ://139.159.145.191:8230"
    // https://sitcmsapi.apfeg.com;
    // 测试2 http://dev1.api.apfeg.com
    private static String TEST_BASE_URL = "test.api.apfeg.com";
    //本地测试
    public static String LOCATION_TEST_URL = "http://13 ";
    //Local https
    private static String LOCAL_TEST = "api.apfeg.com";
    //private static String IM_SERVER_URL = "mq.apfeg.com/im";
    //Imageurl api.apfeg.com:5080
    //private static String IMAGE_URL = "api.apfeg.com:5080";
    private static String IMAGE_URL = "121.37.204.141:5080";
    //oss 地址
    //private static String OSS_URL = "fegrd2019.obs.cn-south-1.myhuaweicloud.com";
    public static String OSS_URL = "feg2020.obs.cn-south-1.myhuaweicloud.com";
    public static String PAD_BAST_URL = "https://api.ytwljy.com";
    public static SharedPreferencesUtil sp = SharedPreferencesUtil.getInstance(CommonApplication.Companion.getMApplication());

    public static String BASEURL = sp.getString(CommonConstants.Companion.getSERVICEAPI(), PAD_BAST_URL);


    /**
     * //假如是uat环境，需要把im测试地址改成跟正式一样
     */
    //IM地址 正式uat环境一体
    public static String IM_BASE_URL = "https://mq.ytwljy.com";
    //public static String IM_TEST_URL = "https://mq.ytwljy.com";//uat
    //im测试环境1
    //public static String IM_TEST_URL = "http://121.37.204.141:8000";
    //im测试 环境2
    public static String IM_TEST_URL = "http://139.159.145.191:8000";
    /**
     * //假如是uat环境，需要把im测试地址改成跟正式一样
     * OSS地址
     */
    public static String OSS_URL_WLJY = "https://wljyim.obs.cn-south-1.myhuaweicloud.com/";//正式
    //public static String OSS_URL_TEST = "https://wljyim.obs.cn-south-1.myhuaweicloud.com/";//uat
    public static String OSS_URL_TEST = "https://fegrdim.obs.cn-south-1.myhuaweicloud.com/";//测试

//    public static String BASEURL = "http://192.168.10.116:5000";

    /**
     * OSS地址
     */
    //public static String BAST_OSS_URL = "http://fegrd2019.obs.cn-south-1.myhuaweicloud.com/snapshot/";
    //接口api
    public static String getBASEURL() {
        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(SCHEMES);
//        stringBuffer.append(SEPARATOR);
        stringBuffer.append(BASEURL);
        //return stringBuffer.toString();
        return BASEURL;
    }

    public static String getTESTBASEURL() {
        StringBuffer stringBuffer = new StringBuffer();
        if (CommonConstants.Companion.getISDUG()) {
            stringBuffer.append(SCHEME);
            stringBuffer.append(SEPARATOR);
            stringBuffer.append(TEST_BASE_URL);
        } else {
            stringBuffer.append(BASEURL);
        }
        return stringBuffer.toString();
    }

    //及时IM通讯的地址
    public static String getInstantMessageServerUrl() {
        if (BASEURL.indexOf("ytwljy") != -1 || BASEURL.indexOf("uatcmsapi") != -1) {
            //uat和正式的im地址
            return IM_BASE_URL + "/messageHub";
        } else {
            return IM_TEST_URL + "/messageHub";
        }
    }

    //获取ossurl
    public static String getBastOssUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(SCHEME);
        stringBuffer.append(SEPARATOR);
        stringBuffer.append(OSS_URL);
        stringBuffer.append("/snapshot/");
        return stringBuffer.toString();
    }


    //图片视频的URL头部
    public static String getBaseImageUrl() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(SCHEME);
        stringBuffer.append(SEPARATOR);
        stringBuffer.append(IMAGE_URL);
        stringBuffer.append("/yiyuneduapi/fileDownload?id=");
        return BASEURL + "/Api/Files/fileDownload?fileId=";
    }

    //图片视频的URL头部，官方试题查看
    public static String getBaseImageUrl2() {
        return BASEURL + "/Api/OfficialCloudFiles/fileDownload?fileId=";
    }
}
