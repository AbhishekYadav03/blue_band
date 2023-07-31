package in.co.datavoice.bl_read.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import in.co.datavoice.bl_read.MainActivity;

public class NetUtils {

    public static boolean isConnected() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    public static boolean isWifi(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null && connectivityManager.getActiveNetworkInfo().getType() == 1) {
            return true;
        }
        return false;
    }

    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.WirelessSettings"));
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
