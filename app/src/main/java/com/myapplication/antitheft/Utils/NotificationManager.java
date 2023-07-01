package com.myapplication.antitheft.Utils;

import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.myapplication.antitheft.R;

public class NotificationManager {
    private Context mContext;
    private android.app.NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";


    public NotificationManager(Context context) {
        mContext=context;
    }


    public  void showNotification(String title,String message){


        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        mNotificationManager = (android.app.NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.setLightColor(Color.RED);
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(Integer.parseInt(NOTIFICATION_CHANNEL_ID), mBuilder.build());
    }
}
