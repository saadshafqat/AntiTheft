package com.myapplication.antitheft.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.myapplication.antitheft.Utils.PocketSensor;

public class PocketService extends Service {
    PocketSensor pocketSensor;
    com.myapplication.antitheft.Utils.NotificationManager notificationManager;
    public PocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
         pocketSensor=new PocketSensor(this);
         notificationManager=new com.myapplication.antitheft.Utils.NotificationManager(this);

          startitinForeground();
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pocketSensor.enable();
        notificationManager.showNotification("Pocket Removal","Service Started");
        Log.d("Pocket Service","onStartService");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        pocketSensor.disable();
        notificationManager.showNotification("Pocket Removal","Service Stopped");

        Log.d("Pocket Service","onServiceDestroy");
        super.onDestroy();
    }
}