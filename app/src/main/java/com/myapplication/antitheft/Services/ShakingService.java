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
import com.myapplication.antitheft.Utils.ShakingSensor;

public class ShakingService extends Service {
    ShakingSensor shakingSensor;
    com.myapplication.antitheft.Utils.NotificationManager notificationManager;
    public ShakingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        shakingSensor=new ShakingSensor(this);
        notificationManager=new com.myapplication.antitheft.Utils.NotificationManager(this);
        startitinForeground();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        shakingSensor.enable();
        Log.d("Shaking Service","onStartService");
        notificationManager.showNotification("Motion Detection","Service Started");
        return START_STICKY;    }

    @Override
    public void onDestroy() {
        shakingSensor.disable();
        notificationManager.showNotification("Motion Detection","Service Stopped");

        Log.d("Shaking Service","onServiceDestroy");
        super.onDestroy();
    }

    private void startitinForeground() {
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "NotChannel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Shaking Service Running")
                    .setContentText("").build();

            startForeground(1, notification);

            Log.d("Shaking Service", "onCreateService");
        }
    }

}