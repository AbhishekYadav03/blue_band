package in.co.datavoice.bl_read.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.BluetoothLe;

import in.co.datavoice.bl_read.MainActivity;


public class BleReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
            switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE)) {
                case 10:
                    MainActivity.isBluetoothOpen = 1;
                    BluetoothLe.getDefault().cancelAllTag();
                    if (BluetoothLe.getDefault().getScanning()) {
                        BluetoothLe.getDefault().stopScan();
                    }
                    BluetoothLe.getDefault().close();
                    BluetoothLe.getDefault().disconnect();
                    Log.d("BleReceiver", "STATE_OFF");
                    return;
                case 11:
                     Log.d("BleReceiver", "STATE_TURNING_ON");
                    return;
                case 12:
                    Log.d("BleReceiver", "STATE_ON");
                    MainActivity.isBluetoothOpen = 0;
                    return;
                case 13:
                     Log.d("BleReceiver", "STATE_TURNING_OFF");
                    return;
                default:
            }
        }
    }
}
