package com.dhanadsp1120.drmfortamilnadu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class tpass extends AppCompatActivity {
    public static String state1="Tamilnadu";
    static List<String> city=new ArrayList<>();
    private DatabaseReference db;
    public ACProgressFlower dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpass);
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,homescreen.class));
    }
    public void apply(View v)
    {
        if (isConnected()) {
            dialog = new ACProgressFlower.Builder(tpass.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();
            dialog.show();

            city.clear();
            db = FirebaseDatabase.getInstance().getReference().child("toll-free").child("hospital").child(state1);
            db.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dialog.dismiss();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String name = ds.getKey();
                        city.add(name);

                    }

                    startActivity(new Intent(tpass.this,tpassapply.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                    dialog.dismiss();
                }
            });
        }
        else {
            AlertDialog.Builder on = new AlertDialog.Builder(tpass.this);
            on.setTitle("Warning")
                    .setMessage("Please enable the Internet to Personal Pass Apply")
                    .setPositiveButton("Turn ON", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent om = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                            startActivity(om);
                        }
                    })
                    .setIcon(R.drawable.network);
            AlertDialog m = on.create();
            m.show();
        }


    }

    public void check(View v)
    {

        if (isConnected()) {
            dialog = new ACProgressFlower.Builder(tpass.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();
            dialog.show();

            city.clear();
            db = FirebaseDatabase.getInstance().getReference().child("toll-free").child("hospital").child(state1);
            db.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dialog.dismiss();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String name = ds.getKey();
                        city.add(name);

                    }
                    startActivity(new Intent(tpass.this,tpassstatus.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                    dialog.dismiss();
                }
            });
        }
        else {
            AlertDialog.Builder on = new AlertDialog.Builder(tpass.this);
            on.setTitle("Warning")
                    .setMessage("Please enable the Internet to Personal Pass Apply")
                    .setPositiveButton("Turn ON", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent om = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                            startActivity(om);
                        }
                    })
                    .setIcon(R.drawable.network);
            AlertDialog m = on.create();
            m.show();
        }


    }

}