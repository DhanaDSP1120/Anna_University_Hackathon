package com.dhanadsp1120.drmfortamilnadu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class first extends AppCompatActivity {
    DrawerLayout dr;
    NavigationView nv;
    private DatabaseReference db;

    public ACProgressFlower dialog;
    static List<String> list=new ArrayList<String>();

    static List<String> fav=new ArrayList<String>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        dr=findViewById(R.id.drawerlayout);
        TextView t1 = (TextView)findViewById(R.id.off);
        t1.setPaintFlags(t1.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        TextView t2 = (TextView)findViewById(R.id.onn);
       t2.setPaintFlags(t2.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        TextView t3= (TextView)findViewById(R.id.ffav);
        t3.setPaintFlags(t3.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        TextView t4 = (TextView)findViewById(R.id.mmenu);
        t4.setPaintFlags(t4.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        nv=findViewById(R.id.ngv);
        nv.setItemIconTintList(null);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.offline:
                      gooffline();
                      break;

                    case R.id.online:

                        goonline();
                        break;
                    case R.id.favourite:
                       gofav();
                        break;
                    case R.id.add:
                        startActivity(new Intent(first.this,add.class));
                        //Toast.makeText(first.this,"add",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.feedback:
                        startActivity(new Intent(first.this,feedback.class));
                        //Toast.makeText(first.this,"feedback",Toast.LENGTH_LONG).show();
                        break;



                }
                dr.closeDrawer(GravityCompat.START);
                return false;

            }
        });
    }

    public void opendrawer(View v)
    {
        dr.openDrawer(GravityCompat.START);

    }
public void about(View V)
{
    startActivity(new Intent(this,about.class));
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

    public void online(View v) {
    goonline();
    }
    public void favourite(View v)
    {
        gofav();
       }
    public void menu(View v)
    {
        dr.openDrawer(GravityCompat.START);
    }
    public void offline(View v){
        gooffline();
    }

    public void gofav()
    {
        startActivity(new Intent(this,favourite.class));

    }
    public void gooffline()
    {


            startActivity(new Intent(this,district.class));
    }

    public void goonline()
    {
        if (isConnected()) {

            list.clear();
            dialog = new ACProgressFlower.Builder(first.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();
            dialog.show();
            list.clear();
            db= FirebaseDatabase.getInstance().getReference().child("toll-free");
            db.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String name = ds.getKey();
                        list.add(name);

                    }

                    dialog.dismiss();
                    //Intent o=new Intent(first.this,onlinestate.class);
                    Intent o=new Intent(first.this,listbox.class);
                    startActivity(o);
                    //Toast.makeText(MainActivity.this,"Rectrive success",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(first.this,"failed",Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            AlertDialog.Builder on = new AlertDialog.Builder(first.this);
            on.setTitle("Warning")
                    .setMessage("Please enable the Internet to Go online")
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

    @Override
    public void onBackPressed() {
        if (dr.isDrawerOpen(GravityCompat.START)) {
            dr.closeDrawer(GravityCompat.START);

        } else {
            startActivity(new Intent(this, homescreen.class));
        }
    }

}
