package com.futureeducation.commonmodule.utill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import com.apkfuns.logutils.LogUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtils {
    public static boolean isConnectivityActive(Context context) {


        @SuppressLint("MissingPermission") NetworkInfo networkinfo = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnected();
    }

    public static boolean isMobileConnected(Context context) {
        @SuppressLint("MissingPermission") NetworkInfo networkinfo = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkinfo != null
                && networkinfo.getType() == ConnectivityManager.TYPE_MOBILE
                && networkinfo.isConnected();
    }

    public static boolean isWifiConnected(Context context) {
        @SuppressLint("MissingPermission") NetworkInfo networkinfo = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkinfo != null
                && networkinfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkinfo.isConnected();
    }

    /**
     * 获取本机ip
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
//			Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }

//	// MD5加密
//	public static String sign(String... args) {
//
//		String sign = "^$*computeroutputmicrofilm!)(*#1231";
//
//		for (String s : args) {
//			sign += s;
//		}
//
//		return MD5.getMessageDigest(sign.toString().getBytes());
//	}

    //获取设备联网状态
    public static boolean isConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            //获取网络属性
            NetworkCapabilities networkCapabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                LogUtils.i("Avalible" + "NetworkCapalbilities:" + networkCapabilities.toString());
                return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }
        } else {
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }

}
