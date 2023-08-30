package in.co.datavoice.bl_read;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.zhj.bluetooth.zhjbluetoothsdk.bean.HealthHeartRate;
import com.zhj.bluetooth.zhjbluetoothsdk.bean.HealthSleepItem;
import com.zhj.bluetooth.zhjbluetoothsdk.bean.HealthSport;
import com.zhj.bluetooth.zhjbluetoothsdk.bean.HealthSportItem;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.BleCallbackWrapper;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.BleSdkWrapper;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.CmdHelper;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.HandlerBleDataResult;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.BluetoothLe;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.OnLeConnectListener;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.OnLeScanListener;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.OnLeWriteCharacteristicListener;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.exception.ConnBleException;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.exception.ScanBleException;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.exception.WriteBleException;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.scanner.ScanRecord;
import com.zhj.bluetooth.zhjbluetoothsdk.ble.bluetooth.scanner.ScanResult;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import in.co.datavoice.bl_read.reciever.BleReceiver;
import in.co.datavoice.bl_read.reciever.NetworkConnectChangedReceiver;

public class MainActivity extends AppCompatActivity {
    public static int isBluetoothOpen = 0;
    private static MainActivity app = null;

    private BluetoothLe mBluetoothLe;

    BluetoothDevice device;
    Button connectBtn, heartRateBtn;
    TextView helloText;

    String[] permissions = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
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

        mBluetoothLe = BluetoothLe.getDefault();

        mBluetoothLe.init(MainActivity.getContext(), new BleCallbackWrapper() {
            @Override
            public void complete(int i, Object o) {
                if (null == mBluetoothLe) {
                    Log.d(TAG, "mBluetoothLe is null");
                } else {
                    Log.d(TAG, "BluetoothLe.getDefault() works");
                }
                setScanListener();
                mBluetoothLe //Set scan time in milliseconds, default 10 seconds
//                        .setScanWithDeviceAddress("A4:C1:38:86:01:A2") //filter the hardware address
                        .setScanWithServiceUUID ("0000ff00-0000-1000-8000-00805f9b34fb")  // settings filter scans by service uuid
                        //.setScanWithDeviceName ("ZG 1616") // settings filter scans against the device name
                        .setReportDelay(1) //If 0, the onScanResult () method, if more than 0, the onBatchScanResults () method cannot be less than 0
                        .startScan(getContext());

            }

            @Override
            public void setSuccess() {
                Log.d(TAG, "setSuccess: Init");
            }
        });
        helloText = findViewById(R.id.textView);
        connectBtn = findViewById(R.id.connect);
        heartRateBtn = findViewById(R.id.heart_rate);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBluetoothLe.getConnected()) {
                    mBluetoothLe.disconnect();
                    connectBtn.setText("Disconnected");
                } else {
                    connectBluetooth(device);
                    connectBtn.setText("Connected");
                }
            }
        });
        heartRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /// TODO: [CmdHelper.a]
                //  onSuccess: HandlerBleDataResult{data=BLEDevice{mDeviceName='null', mDeviceAddress='A4:C1:38:86:01:A2', mDeviceProduct='T20C67N7', mDeviceVersion='1.14', mRssi=0}, hasNext=false, isComplete=true}
                /// TODO: CmdHelper.b
                ///Doesn't work -> Timeout
                /// TODO: CmdHelper.c
                // CmdHelper.c onSuccess: HandlerBleDataResult{data=null, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.d
                ///Doesn't work -> Timeout

                /// TODO: CmdHelper.e
                ///CmdHelper.e onSuccess: HandlerBleDataResult{data=[], hasNext=false, isComplete=true}

                /// TODO: CmdHelper.f
                // CmdHelper.f onSuccess: HandlerBleDataResult{data=LongSit{startHour=11, startMinute=0, endHour=20, endMinute=0, interval=30, repetitions=63, onOff=true, weeks=[true, true, true, true, true, false, false]}, hasNext=false, isComplete=true}
                /// TODO: CmdHelper.g
                //CmdHelper.g onSuccess: HandlerBleDataResult{data=com.zhj.bluetooth.zhjbluetoothsdk.bean.UserBean@320939e, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.h
                //CmdHelper.h onSuccess: data :{ date:1692599133367, day:21, month: 8, year: 2023, totalDistance: 0, totalStepCount: 0, totalCalory: 0}

                /// TODO: CmdHelper.i
                //CmdHelper.h onSuccess: HandlerBleDataResult{data=DeviceState{screenLight=50, screenTime=5, theme=0, language=0, unit=0, timeFormat=1, upHander=1}, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.i
                // CmdHelper.j onSuccess: HandlerBleDataResult{data=null, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.j
                // CmdHelper.j onSuccess: HandlerBleDataResult{data=null, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.k
                // CmdHelper.k onSuccess: HandlerBleDataResult{data=14, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.l
                // CmdHelper.l onSuccess: HandlerBleDataResult{data=Goal{goalStep=10000, goalDistanceKm=5, goalDistanceLb=6, goalCal=300, goalSleep=0, sleepstate=0, stepstate=1, calstate=1, distancestate=1}, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.m
                // CmdHelper.m onSuccess: HandlerBleDataResult{data=com.zhj.bluetooth.zhjbluetoothsdk.bean.SleepBeans@320939e, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.n
                //CmdHelper.n onSuccess: HandlerBleDataResult{data=AppNotice{qq=true, facebook=true, wechat=true, linked=true, skype=true, instagram=true, twitter=true, line=true, whatsApp=true, vk=true, messager=true}, hasNext=false, isComplete=true} \

                /// TODO: CmdHelper.o
                //CmdHelper.o onSuccess: HandlerBleDataResult{data=com.zhj.bluetooth.zhjbluetoothsdk.bean.HeartRateInterval@af46e77, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.p
                //CmdHelper.p onSuccess: HandlerBleDataResult{data=true, hasNext=false, isComplete=true}

                /// TODO: CmdHelper.q
                //CmdHelper.q onSuccess: HandlerBleDataResult{data=4, hasNext=false, isComplete=true}



//                if (mBluetoothLe.getConnected()) {
//
                    BleSdkWrapper.getHeartRate(new OnLeWriteCharacteristicListener() {
                        @Override
                        public void onSuccess(HandlerBleDataResult handlerBleDataResult) {
                            Log.d(TAG, "getCurrentStep onSuccess: " + handlerBleDataResult);
                            if( handlerBleDataResult.data instanceof HealthHeartRate){
                                String data= String.valueOf(((HealthHeartRate) handlerBleDataResult.data).getSilentHeart());
                                helloText.setText(((HealthHeartRate) handlerBleDataResult.data).toString());
                            }

                        }

                        @Override
                        public void onFailed(WriteBleException e) {

                        }
                    });
//
//
//                    BleSdkWrapper.getActivity(new OnLeWriteCharacteristicListener() {
//
//                        @Override
//                        public void onSuccess(HandlerBleDataResult handlerBleDataResult) {
//                            Log.d(TAG, "getActivity onSuccess: " + handlerBleDataResult);
//
//                            helloText = findViewById(R.id.textView);
//                        }
//
//                        @Override
//                        public void onFailed(WriteBleException e) {
//                            Log.d(TAG, "getActivity onFailed: " + e);
//                        }
//                    });
//                }
            }
        });
    }


    private void setScanListener() {
//        mBluetoothLe.setScanPeriod(15000);
        mBluetoothLe.setOnScanListener(new OnLeScanListener() {
            @Override
            public void onScanResult(BluetoothDevice bluetoothDevice, int i, ScanRecord scanRecord) {
                Log.d(TAG, "onScanResult: " + bluetoothDevice + " " + scanRecord);

                if (Objects.equals(bluetoothDevice.getAddress(), "A4:C1:38:86:01:A2")) {
                    device = bluetoothDevice;
                }
            }

            @Override
            public void onBatchScanResults(List<ScanResult> list) {
                Log.d(TAG, "onBatchScanResults: " + list);
                list.forEach(d -> connectBluetooth(d.a()));
            }

            @Override
            public void onScanCompleted() {
                Log.d(TAG, "onScanCompleted:");
            }

            @Override
            public void onScanFailed(ScanBleException e) {
                Log.d(TAG, "onScanFailed:" + e.toString());
            }
        });
    }

    private void connectBluetooth(BluetoothDevice bluetoothDevice) {
        mBluetoothLe.startConnect(bluetoothDevice, new OnLeConnectListener() {
            @Override
            public void onDeviceConnecting() {
                Log.d(TAG, "onDeviceConnecting:");
            }

            @Override
            public void onDeviceConnected() {
                Log.d(TAG, "onDeviceConnected:");
            }

            @Override
            public void onDeviceDisconnected() {
                Log.d(TAG, "onDeviceDisconnected:");
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt bluetoothGatt) {
                Log.d(TAG, "onServicesDiscovered:");
            }

            @Override
            public void onDeviceConnectFail(ConnBleException e) {
                Log.d(TAG, "onDeviceConnectFail:" + e.toString());
            }
        });
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