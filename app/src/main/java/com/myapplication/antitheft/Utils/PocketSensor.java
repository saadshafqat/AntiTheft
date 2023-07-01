package com.myapplication.antitheft.Utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class PocketSensor {
    AlarmPlayer alarmPlayer;
    private static final boolean DEBUG = true;
    NotificationManager notificationManager;
    private SensorManager mSensorManager;
    private Sensor mProximitySensor;
    private Context mContext;

    public PocketSensor(Context context) {
        mContext = context;
        notificationManager=new NotificationManager(context);
        alarmPlayer=new AlarmPlayer(mContext);
        mSensorManager = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }
    public void enable() {
        if (DEBUG) Log.d("Pocket Sensor", "Enabling");
        mSensorManager.registerListener(mProximityListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    public void disable() {
        if (DEBUG) Log.d("Pocket Sensor", "Disabling");
        mSensorManager.unregisterListener(mProximityListener, mProximitySensor);
        alarmPlayer.stop();
    }
    SensorEventListener mProximityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // check if the proximity sensor is covered
            boolean isNear = event.values[0] < mProximitySensor.getMaximumRange();

            if (isNear) {
                alarmPlayer.stop();
                notificationManager.showNotification("In Pocket Alarm Stopped","IN POCKET DETECTED");
                if (DEBUG) Log.d("Pocket Sensor", "Phone in Pocket Detected");
            }
            else {
                notificationManager.showNotification("In Pocket Alarm Triggered","Phone is not in the Pocket");
                alarmPlayer.play();
                if (DEBUG) Log.d("Pocket Sensor", "Phone is not in Pocket");

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            /* Empty */
        }
    };



}
