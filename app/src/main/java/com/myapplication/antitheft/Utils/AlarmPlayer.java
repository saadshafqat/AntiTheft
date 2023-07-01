package com.myapplication.antitheft.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class AlarmPlayer {
    Context context;
    MediaPlayer mp;
    Uri notification;
    public AlarmPlayer(Context context) {
        this.context=context;
        notification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       mp = MediaPlayer.create(context, notification);

    }
    public boolean isPlaying(){
        return mp.isPlaying();
    }

    public void play(){
        if(!mp.isPlaying()){
           try {
               mp.start();
               mp.setLooping(true);

           }catch (Exception e){
               Log.d("Alerm Exception:",e.getMessage());
           }
        }
    }
    public  void stop(){
        if(mp.isPlaying()){
            mp.pause();
        }
    }





}
