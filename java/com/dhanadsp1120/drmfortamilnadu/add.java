package com.dhanadsp1120.drmfortamilnadu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class add extends AppCompatActivity {
    EditText name,address,phone,state,district,catagory;
    private DatabaseReference db;
    public ACProgressFlower dialog;

    static String names, addres,phoneNO,states,dis,catag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        district=findViewById(R.id.district);
        catagory=findViewById(R.id.category);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== R.id.about) {
            startActivity(new Intent(this, about.class));

        }

        if( item.getItemId()==android.R.id.home)
        {

            onBackPressed();

        }
        if(item.getItemId()==R.id.gotohome)
        {

            startActivity(new Intent(this,first.class));
        }



        return true;
    }

    public void submit(View v) {
        if (isConnected()) {
        names = name.getText().toString();
        addres = address.getText().toString();
        phoneNO = phone.getText().toString();
        dis = district.getText().toString();
        catag = catagory.getText().toString();

        if (names.isEmpty()) {
            name.setError("Name is Requried");
        } else if (phoneNO.isEmpty()) {
            phone.setError("phone is Requried");
        } else if (addres.isEmpty()) {
            address.setError("address is Requried");
        }
         else if (dis.isEmpty()) {
            district.setError("district is Requried");
        } else if (catag.isEmpty()) {
            catagory.setError("catagory is Requried");
        } else if (addres.isEmpty()) {
            address.setError("FeedBack is Requried");
        } else {
            dialog = new ACProgressFlower.Builder(add.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();
            dialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // yourMethod();
                    db = FirebaseDatabase.getInstance().getReference().child("request").child(names);

                    HashMap<String, String> b = new HashMap<String, String>();
                    b.put("name", names);
                    b.put("address", addres);
                    b.put("phone", phoneNO);
                    b.put("state", "Tamilnadu");
                    b.put("district", dis);
                    b.put("category", catag);
                    db.setValue(b);

                    startActivity(new Intent(add.this, first.class));
                    finish();
                }
            }, 3900);


        }
    }
          else {
        AlertDialog.Builder on = new AlertDialog.Builder(add.this);
        on.setTitle("Warning")
                .setMessage("Please enable the Internet to Add your detials")
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
    }
