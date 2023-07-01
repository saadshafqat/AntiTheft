package com.myapplication.antitheft.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.myapplication.antitheft.Services.ChargingService;
import com.myapplication.antitheft.Services.PocketService;
import com.myapplication.antitheft.R;

import com.myapplication.antitheft.Services.ShakingService;
import com.myapplication.antitheft.Utils.ChargingUtil;
import com.myapplication.antitheft.Utils.ShakingSensor;
import com.myapplication.antitheft.Utils.Tools;

public class MainActivity extends AppCompatActivity {
CardView card_cr,card_md;
CardView card_pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startIt();
        setListeners();

    }

    private void setListeners() {

        card_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Tools.isMyServiceRunning(PocketService.class,MainActivity.this)){

                    Intent intent=new Intent(MainActivity.this, PocketService.class);
                    stopService(intent);
                    Toast.makeText(MainActivity.this, "Pocket Service Stopped", Toast.LENGTH_SHORT).show();
                    setButtons();
                    
                }else{
                    Toast.makeText(MainActivity.this, "Pocket Service Started", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this, PocketService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    }else{
                        startService(intent);
                    }
                    setButtons();
                }
            }
        });
        card_cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Tools.isMyServiceRunning(ChargingService.class, MainActivity.this)) {


                    Intent intent = new Intent(MainActivity.this, ChargingService.class);
                    stopService(intent);
                    Toast.makeText(MainActivity.this, "Charging Service Stopped", Toast.LENGTH_SHORT).show();
setButtons();
                } else {


                    Toast.makeText(MainActivity.this, "Charging Service Started", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ChargingService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                    setButtons();
                }
            }
        });
        card_md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Tools.isMyServiceRunning(ShakingService.class, MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, ShakingService.class);
                    stopService(intent);
                    Toast.makeText(MainActivity.this, "Shaking Service Stopped", Toast.LENGTH_SHORT).show();
                    card_md.setCardBackgroundColor(getResources().getColor(R.color.red));

                } else {
                    Toast.makeText(MainActivity.this, "Shaking Service Started", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ShakingService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                    card_md.setCardBackgroundColor(getResources().getColor(R.color.teal_700));
                }
            }
        });
    }

    private void startIt() {
        card_pr=findViewById(R.id.card_pr);
        card_cr=findViewById(R.id.card_cr);
        card_md=findViewById(R.id.card_md);
        setButtons();

    }

    private void setButtons() {
        if(Tools.isMyServiceRunning(ChargingService.class,MainActivity.this)){
            card_cr.setCardBackgroundColor(getResources().getColor(R.color.teal_700));
        }else{
            card_cr.setCardBackgroundColor(getResources().getColor(R.color.red));
        }


        if(Tools.isMyServiceRunning(PocketService.class,MainActivity.this)){
            card_pr.setCardBackgroundColor(getResources().getColor(R.color.teal_700));
        }else{
            card_pr.setCardBackgroundColor(getResources().getColor(R.color.red));
        } if(Tools.isMyServiceRunning(ShakingService.class,MainActivity.this)){
            card_md.setCardBackgroundColor(getResources().getColor(R.color.teal_700));
        }else{
            card_md.setCardBackgroundColor(getResources().getColor(R.color.red));
        }



    }


}