package com.myapplication.antitheft.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.myapplication.antitheft.Utils.ChargingUtil;

public class ChargingService extends Service {
    ChargingUtil chargingUtil;
    com.myapplication.antitheft.Utils.NotificationManager notificationManager;
    public ChargingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        chargingUtil=new ChargingUtil(this);
        notificationManager=new com.myapplication.antitheft.Utils.NotificationManager(this);
        Log.d("Charging Service", "onCreateService");

        startitinForeground();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        chargingUtil.registerIt();
        Log.d("Charging Service", "onStarted");
        notificationManager.showNotification("Charger Removal","Service Started");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("Charging Service", "onDestroyed");
        notificationManager.showNotification("Charger Removal","Service Stopped");

        chargingUtil.unregisterIt();
        super.onDestroy();
    }
    private void startitinForeground() {
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Pocket Service Running")
                    .setContentText("").build();

            startForeground(1, notification);

            Log.d("Pocket Service", "onCreateService");
        }
    }
}