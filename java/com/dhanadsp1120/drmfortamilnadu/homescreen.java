package com.dhanadsp1120.drmfortamilnadu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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

public class homescreen extends AppCompatActivity {
    DrawerLayout dr;

    public String state1="Tamilnadu";
    NavigationView nv;
    private LocationSettingsRequest.Builder builder;
    private LocationCallback locationCallback;
    LocationRequest request;
    public ACProgressFlower dialog;
    private DatabaseReference db;
    static List<String> city=new ArrayList<>();
    SharedPreferences preferences;
    public static String counclingimage="",counclingname="",courseimage="",coursename="";
    String[] modulename={"Doctor's Appoinment","Toll-Free Numbers","Medical-Shop Near You","Hospital Admission","Counseling","Personal Pass","Essential service Pass","Online Study Courses"};
    int [] module={R.drawable.doctor,R.drawable.tollfree6,R.drawable.medicalshop1,R.drawable.hospital1,R.drawable.counseling,R.drawable.personal,R.drawable.transportpass,R.drawable.course};
    GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        dr=findViewById(R.id.drawerlayout);


       preferences = getSharedPreferences("login", 0);
        gv=findViewById(R.id.gridview1);


        CustomAdapter Custom=new CustomAdapter();
        gv.setAdapter(Custom);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    doctor();
                }
                if(position==1) {
                    toll();
                }
                if(position==2) {
                    map();
                }
                if(position==3) {
                    hospital();
                }
                if (position==4)
                {
                    councling();
                }
                if(position==5)
                {
                    epass();
                }
                if (position==6)
                {
                    tpass();
                }
                if (position==7)
                {
                    course();
                }
            }
        });
        nv=findViewById(R.id.ngv);

        nv.setItemIconTintList(null);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.tollfree:
                        toll();
                        break;
                    case R.id.logout:
                        logout();
                        break;
                    case R.id.doctorappoinment:
                        doctor();
                        break;
                    case R.id.medicalshop:
                        map();
                        break;
                    case R.id.hospital:
                        hospital();
                        break;
                    case R.id.councling:
                        councling();
                        break;
                    case R.id.personal:
                        epass();
                        break;
                    case R.id.essential:
                        tpass();
                        break;
                    case R.id.course:
                        course();
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

    public void epass()
    {
        startActivity(new Intent(this,epass.class));
    }
    public void tpass()
    {
        startActivity(new Intent(this,tpass.class));
    }
    public void doctor()
    {
        if (isConnected()) {
            dialog = new ACProgressFlower.Builder(homescreen.this)
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

                    startActivity(new Intent(homescreen.this, doctorappoinment.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
dialog.dismiss();
                }
            });
        }
        else {
            AlertDialog.Builder on = new AlertDialog.Builder(homescreen.this);
            on.setTitle("Warning")
                    .setMessage("Please enable the Internet to Doctor Appoinment")
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
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }
    public void councling()
    {
        dialog = new ACProgressFlower.Builder(homescreen.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
        db= FirebaseDatabase.getInstance().getReference().child("councling").child("root");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dialog.dismiss();
                if(snapshot.exists()) {
                    counclingname = snapshot.child("rootname").getValue().toString();
                    counclingimage = snapshot.child("rootimage").getValue().toString();
                }
                Log.d("lin",counclingname);
                startActivity(new Intent(homescreen.this,councling.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void course()
    {
        dialog = new ACProgressFlower.Builder(homescreen.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
        db= FirebaseDatabase.getInstance().getReference().child("course").child("root");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dialog.dismiss();
                if(snapshot.exists()) {
                    coursename = snapshot.child("rootname").getValue().toString();
                    courseimage = snapshot.child("rootimage").getValue().toString();
                }
                Log.d("lin",counclingname);
                startActivity(new Intent(homescreen.this,course.class));

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void logout()
    {
        AlertDialog.Builder di= new AlertDialog.Builder(homescreen.this);
        di.setMessage("Are you sure want to LogOut");
        di.setCancelable(true);
        di.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("isUserLogin");
                editor.commit();

                finish();

                Intent intent = new Intent(homescreen.this, MainActivity.class);
                startActivity(intent);

            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ad=di.create();
        ad.show();

    }
    public void map()
    {
        if(isLocationEnabled()) {


            Uri gmmIntentUri = Uri.parse("geo:0,0?q=medicalshop");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        else
        {
            request = new LocationRequest()
                    .setFastestInterval(1500)
                    .setInterval(3000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            builder = new LocationSettingsRequest.Builder().addLocationRequest(request);
            Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(homescreen.this).checkLocationSettings(builder.build());
            result.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {

                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        try {
                            resolvableApiException.startResolutionForResult(homescreen.this, 8989);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }


    }
    public void toll()
    {
        startActivity(new Intent(homescreen.this,splashscreen.class));


    }
    public void hospital()
    {
        startActivity(new Intent(homescreen.this,hospitaladmission.class));


    }
   @Override
    public void onBackPressed() {
        if (dr.isDrawerOpen(GravityCompat.START)) {
            dr.closeDrawer(GravityCompat.START);
        } else { AlertDialog.Builder dialog= new AlertDialog.Builder(this);
            dialog.setMessage("Are you sure want to Exit");
            dialog.setCancelable(true);
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);

                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog ad=dialog.create();
            ad.show();


        }
    }


    private class CustomAdapter extends BaseAdapter {
        int h = 0;

        @Override
        public int getCount() {
            return module.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.mainpagedesign, null);

            TextView name = view1.findViewById(R.id.name);
            ImageView image = view1.findViewById(R.id.image);

            name.setText(modulename[i]);
            image.setImageResource(module[i]);
            return view1;

        }
    }
}
