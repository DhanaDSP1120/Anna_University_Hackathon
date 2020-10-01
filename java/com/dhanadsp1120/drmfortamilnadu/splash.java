package com.dhanadsp1120.drmfortamilnadu;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler=new Handler();
        SharedPreferences preferences = getSharedPreferences("login", 0);

        if (preferences.contains("isUserLogin")) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(splash.this,homescreen.class);
                    startActivity(intent);
                    finish();



                }
            },3000);


        } else {

            if (ActivityCompat.checkSelfPermission(
                    splash.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    splash.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(splash.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET}, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(splash.this,MainActivity.class);
                        startActivity(intent);
                        finish();



                    }
                },3000);
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(splash.this,MainActivity.class);
                        startActivity(intent);
                        finish();



                    }
                },3000);
            }

        }




    }
}
