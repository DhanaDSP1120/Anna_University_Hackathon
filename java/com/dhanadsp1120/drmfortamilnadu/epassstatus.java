package com.dhanadsp1120.drmfortamilnadu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class epassstatus extends AppCompatActivity {
    Spinner dis;
    public DatabaseReference db1,db2,db3;
    public ACProgressFlower dialog;
    public String district="";
    TextView status;
    EditText no;
    public String no1;
public int acc=0,app=0,rej=0;
    static List<String> accept=new ArrayList<>();

    static List<String> reject=new ArrayList<>();

    static List<String> apply=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epassstatus);
        dis=findViewById(R.id.district);
        status=findViewById(R.id.status);
        no=findViewById(R.id.No);
        epass.city.add(0,"Select city");
        ArrayAdapter<String> ad0=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,epass.city);
        dis.setAdapter(ad0);
        dis.setSelection(0);
        dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    district="";
                }
                else
                {
                    district=parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void check(View v) {
         no1 = no.getText().toString();
        if (district.length() == 0) {
            status.setText("Please Select The District");
        } else if (no1.length() == 0) {
            status.setText("Please Entered a Application Number");
        } else {
            dialog = new ACProgressFlower.Builder(epassstatus.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();
            dialog.show();
            Log.d("d","ddd");
            db1 = FirebaseDatabase.getInstance().getReference().child("epass").child("accept").child(epass.state1).child(district);
            db1.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    accept.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String apno = ds.getKey();
                            accept.add(apno);
                            Log.d("no",apno);
                        }
                    dialog.dismiss();
                    }
                    Log.d("Accept",Integer.toString(accept.size()));
                    Log.d("sssd","sd");
                   data2();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.d("sssd","sdagregt");
                }
            });

        }
    }
    public void data2()
    {
        db2 = FirebaseDatabase.getInstance().getReference().child("epass").child("rejected").child(epass.state1).child(district);
        db2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reject.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String apno = ds.getKey();
                        reject.add(apno);

                        Log.d("no",apno);

                    }

                    Log.d("rejected",Integer.toString(reject.size()));

                }
                data3();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
    public void data3()
    {


        db3 = FirebaseDatabase.getInstance().getReference().child("epass").child("applied").child(epass.state1).child(district);
        db3.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apply.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String apno = ds.getKey();
                        apply.add(apno);

                        Log.d("no",apno);
                    }
                }

                Log.d("Apply",Integer.toString(apply.size()));
                checking();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
    public void checking()
    {


        Log.d("no1",no1);
        for (int i=0;i<apply.size();i++)
        {
            if(no1.equals(apply.get(i)))
            {
                app=1;
                break;
            }
            else
            {
                app=0;
            }
        }
        for (int i=0;i<accept.size();i++)
        {
            if(no1.equals(accept.get(i)))
            {
                acc=1;
                break;
            }
            else
            {
                acc=0;
            }
        }
        for (int i=0;i<reject.size();i++)
        {
            if(no1.equals(reject.get(i)))
            {
                rej=1;
                break;
            }
            else
            {
                rej=0;
            }
        }

        if (rej==1||acc==1||app==1)
        {
            dialog.dismiss();
            if (rej==1)
            {
                status.setText("Rejected by Government of India");
            }
            if (acc==1)
            {
                status.setText("Successfully Approved by Government of India");
            }
            if (app==1)
            {
                status.setText("Validation In Process");
            }
        }
        else
        {
            dialog.dismiss();
            status.setText("Invalid Application Number");
            Log.d("rej",Integer.toString(rej));
            Log.d("acc",Integer.toString(acc));
            Log.d("app",Integer.toString(app));
        }


    }

}