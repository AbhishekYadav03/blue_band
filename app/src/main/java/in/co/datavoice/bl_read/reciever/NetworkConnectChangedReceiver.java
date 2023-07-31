package in.co.datavoice.bl_read.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Locale;
import java.util.Objects;

import in.co.datavoice.bl_read.utils.NetUtils;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkChangedReceiver";

    public void onReceive(Context context, Intent intent) {
        if (!Objects.equals(intent.getAction(), "android.net.conn.CONNECTIVITY_CHANGE")) {
            return;
        }
        if (!NetUtils.isConnected()) {
//            EventBusHelper.post(1004);
        } else {
//            EventBusHelper.post(1005);
        }
    }
}