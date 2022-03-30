package com.futureeducation.commonmodule.update.location;


import com.futureeducation.commonmodule.location.BaseurlLocation;


/**
 * 应用跟新url地址
 */
public class AppUpdateUrlFactory extends BaseurlLocation {
    /**
     * @return app 服务器上面应用升级信息
     */


    public static String getAppUpdateUrl() {
        StringBuilder stringBuilder = new StringBuilder();
//        if (CommonConstants.Companion.getISDUG()) {
//            stringBuilder.append(LOCATION_TEST_URL);
//        } else {
        stringBuilder.append(getBASEURL());
        // }
        stringBuilder.append("/Api/AppVersion/GetVersionUpgrade");
        return stringBuilder.toString();
    }
}
