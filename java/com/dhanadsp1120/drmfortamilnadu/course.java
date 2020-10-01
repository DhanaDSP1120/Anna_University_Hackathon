package com.dhanadsp1120.drmfortamilnadu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class course extends AppCompatActivity {
    GridView gv;
    public List<String> image=new ArrayList<>();
    public List<String> name=new ArrayList<>();
    public static String rr="";
    public ACProgressFlower dialog;
    private DatabaseReference db;
    public static  String content,link,lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        gv=findViewById(R.id.gridview1);
        String iname[]=homescreen.coursename.split("@@");

        String ilink[]=homescreen.courseimage.split("@@");
        Log.d("link1",ilink[0]);
        for(int i=0;i< iname.length;i++)
        {
            image.add(ilink[i]);
            name.add(iname[i]);
            Log.d("link",ilink[i]);
        }
    CustomAdapter Custom=new CustomAdapter();
        gv.setAdapter(Custom);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rr=name.get(position);
                dialog = new ACProgressFlower.Builder(course.this)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.DKGRAY).build();
                dialog.show();
                db= FirebaseDatabase.getInstance().getReference().child("course").child("web").child(rr);

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dialog.dismiss();
                        if(snapshot.exists()) {
                            lang=snapshot.child("tips").getValue().toString();
                            content = snapshot.child("content").getValue().toString();
                            link = snapshot.child("link").getValue().toString();
                            startActivity(new Intent(course.this,coursedetials.class));

                        }
                        //Log.d("lin",counclingname);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return image.size();
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

            TextView nam = view1.findViewById(R.id.name);
            ImageView imag = view1.findViewById(R.id.image);

            nam.setText(name.get(i));
            Picasso.get().load(image.get(i)).into(imag);
            return view1;

        }
    }
}
