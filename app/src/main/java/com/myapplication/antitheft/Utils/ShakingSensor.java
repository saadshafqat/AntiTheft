package com.myapplication.antitheft.Utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class ShakingSensor {
    AlarmPlayer alarmPlayer;
    private static final boolean DEBUG = true;
    private float mAccelCurrent = SensorManager.GRAVITY_EARTH;
    private SensorManager mSensorManager;

    private float mAccelLast = SensorManager.GRAVITY_EARTH;
    private float mAccel = 0.00f;
    private Sensor mAccelerometerSensor;
    private Context mContext;
NotificationManager notificationManager;
    public ShakingSensor(Context context) {
        mContext = context;
        alarmPlayer=new AlarmPlayer(mContext);
        notificationManager=new NotificationManager(context);
        mSensorManager = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void enable() {
        if (DEBUG) Log.d("ShakingSensor", "Enabling");
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    public void disable() {
        if (DEBUG) Log.d("ShakingSensor", "Disabling");
        mSensorManager.unregisterListener(mAccelerometerListener, mAccelerometerSensor);
        alarmPlayer.stop();
    }



    SensorEventListener mAccelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
            mAccel = mAccel * 0.9f + (mAccelCurrent - mAccelLast);
            if(mAccel > 3){
                notificationManager.showNotification("Shake Alarm Triggered","Shake Detected");
                alarmPlayer.play();
            }else{
                Log.d("Shaking Sensor:",String.valueOf(mAccel));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            /* Empty */
        }
    };
}
