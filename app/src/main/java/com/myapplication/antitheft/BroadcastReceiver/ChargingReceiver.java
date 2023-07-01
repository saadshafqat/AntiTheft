package com.myapplication.antitheft.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.widget.Toast;

import com.myapplication.antitheft.Utils.AlarmPlayer;
import com.myapplication.antitheft.Utils.NotificationManager;

public class ChargingReceiver extends BroadcastReceiver {
   Context context;
   AlarmPlayer alarmPlayer;
   NotificationManager notificationManager;

    public ChargingReceiver(Context context) {
        this.context = context;
        alarmPlayer=new AlarmPlayer(context);
        notificationManager=new NotificationManager(context);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction()==Intent.ACTION_USER_PRESENT){
            notificationManager.showNotification("Charging Alarm Stopped","USER UNLOCKED SCREEN");
            alarmPlayer.stop();

        }
        if(intent.getAction()==Intent.ACTION_POWER_DISCONNECTED){
            notificationManager.showNotification("Alarm Triggered","POWER DISCONNECTED");
            alarmPlayer.play();
        }  if(intent.getAction()==Intent.ACTION_POWER_CONNECTED){
            if(alarmPlayer.isPlaying()){
                notificationManager.showNotification("Alarm Stopped","POWER RECONNECTED");
            }
            alarmPlayer.stop();
        }

    }

}
