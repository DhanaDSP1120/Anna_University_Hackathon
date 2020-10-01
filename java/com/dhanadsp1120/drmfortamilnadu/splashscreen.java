package com.dhanadsp1120.drmfortamilnadu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Handler handler=new Handler();


        if(ActivityCompat.checkSelfPermission(splashscreen.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            ActivityCompat.requestPermissions(splashscreen.this, new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET},1);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(splashscreen.this,first.class);
                    startActivity(intent);
                    finish();



                }
            },8000);
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashscreen.this,first.class);
                startActivity(intent);
                finish();



            }
        },3000);
    }
}
