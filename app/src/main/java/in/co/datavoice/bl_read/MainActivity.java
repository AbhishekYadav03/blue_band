package in.co.datavoice.bl_read;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.zhj.bluetooth.zhjbluetoothsdk.ble.BleCallbackWrapper;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.BluetoothLe;

import in.co.datavoice.bl_read.reciever.BleReceiver;
import in.co.datavoice.bl_read.reciever.NetworkConnectChangedReceiver;

public class MainActivity extends AppCompatActivity {
    public static int isBluetoothOpen = 0;
    private static MainActivity app = null;
    String[] permissions = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    public static Context getContext() {
        return app.getApplicationContext();
    }

    public static MainActivity getInstance() {
        return app;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);

        BluetoothLe mBluetoothLe= BluetoothLe.getDefault();

        mBluetoothLe.init(this, new BleCallbackWrapper() {
            @Override
            public void complete(int i, Object o) {
                Log.d(TAG, "complete: " + o);
                Log.d(TAG, "complete: " + i);
            }

            @Override
            public void setSuccess() {
                Log.d(TAG, "setSuccess: Init");
            }
        });



//        mBluetoothLe.setOnScanListener(new OnLeScanListener() {
//            @Override
//            public void onScanResult(BluetoothDevice bluetoothDevice, int i, ScanRecord scanRecord) {
//                Log.d(TAG, "onScanResult: " + bluetoothDevice + scanRecord);
//            }
//
//            @Override
//            public void onBatchScanResults(List<ScanResult> list) {
//                Log.d(TAG, "onBatchScanResults: " + list);
//
//            }
//
//            @Override
//            public void onScanCompleted() {
//
//            }
//
//            @Override
//            public void onScanFailed(ScanBleException e) {
//
//            }
//        });
//
//        registerBleReceiver();
//        mBluetoothLe.setScanPeriod(15000);


//      mBluetoothLe.getConnected();

//        BLEDevice bindBLEDevice = SPHelper.getBindBLEDevice(this);
//        Log.d(TAG, "onCreate: " + bindBLEDevice);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void registerBleReceiver() {
        BleReceiver bleReceiver = new BleReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        registerReceiver(bleReceiver, intentFilter);
    }

    private void registerNetorkReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new NetworkConnectChangedReceiver(), intentFilter);
    }
}