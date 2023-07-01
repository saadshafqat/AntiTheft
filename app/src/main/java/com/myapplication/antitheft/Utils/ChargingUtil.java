package com.myapplication.antitheft.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.myapplication.antitheft.BroadcastReceiver.ChargingReceiver;

public class ChargingUtil {
    Context context;
    ChargingReceiver chargingReceiver;


    public ChargingUtil(Context context) {
        this.context = context;
        chargingReceiver =new ChargingReceiver(context);
    }

    public  void registerIt(){
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_USER_PRESENT);

        final Intent batteryIntent = context.registerReceiver(chargingReceiver,filter);
    }
    public  void unregisterIt(){
        context.unregisterReceiver(chargingReceiver);
    }

}
