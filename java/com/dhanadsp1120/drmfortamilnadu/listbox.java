package com.dhanadsp1120.drmfortamilnadu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class listbox extends AppCompatActivity {
Spinner s0,s1,s2;
int f1,f2,f3;
    ListView lv;
    static List<String> listdetials=new ArrayList<String>();
    List<String> city=new ArrayList<>();
    static String catogory="",city1="" ,state1="";
    private DatabaseReference db;
    TextView t;
    Button b;
    String p[];
    static  String l[];
    List li=new ArrayList<String>();
    ArrayAdapter ad;

    public ACProgressFlower dialog;
    static String hbname,hbaddress,hbphone;
    String sn="";
    ImageView ig,fi;
    Button ka;
    TextView n, a, ph,sh,faa;
    ImageButton s;
    String phn = "",hos="";
    static  String fa="";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbox);
        s0=findViewById(R.id.spinner);
        s2=findViewById(R.id.spinner2);
        t=findViewById(R.id.fill);
        b=findViewById(R.id.show);
        lv=findViewById(R.id.list);

        b.setVisibility(View.VISIBLE);
        lv.setVisibility(View.INVISIBLE);
        t.setVisibility(View.INVISIBLE);
        first.list.add(0,"Category");
        city.add(0,"City");

        ArrayAdapter<String> ad2=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,city);
        s2.setAdapter(ad2);
        ArrayAdapter<String> ad0=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,first.list);
        s0.setAdapter(ad0);

        s0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(listbox.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
             f1=position;
             t.setVisibility(View.INVISIBLE);

             b.setVisibility(View.VISIBLE);
             lv.setVisibility(View.INVISIBLE);
             if (position==0)
             {

             }
             else {
                 s2.setSelection(0);
                 catogory = parent.getItemAtPosition(position).toString();
if(catogory.equals("hospital"))
{
    state1="Tamilnadu";


}
else {
    state1="Tamil Nadu";
    }
                 city.clear();
                 city.add(0, "City");
                db = FirebaseDatabase.getInstance().getReference().child("toll-free").child(catogory).child(state1);
                 //Toast.makeText(Main2Activity.this,arr[finalI1],Toast.LENGTH_LONG).show();
                 db.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         for (DataSnapshot ds : dataSnapshot.getChildren()) {
                             String d = ds.getKey();
                             if(d.contains("NA"))
                             {

                             }
                             else {

                                 city.add(d);
                             }
                            // Toast.makeText(listbox.this, d.toString(), Toast.LENGTH_LONG).show();
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
             }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });





        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f3=position;
                t.setVisibility(View.INVISIBLE);

                b.setVisibility(View.VISIBLE);
                lv.setVisibility(View.INVISIBLE);
                if (position==0)
                {

                }
                else {

                    listdetials.clear();
                    city1 = parent.getItemAtPosition(position).toString();
                    db = FirebaseDatabase.getInstance().getReference().child("toll-free").child(catogory).child(state1).child(city1);
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String d = ds.getKey();

                                listdetials.add(d);
                            }
                            Log.d("ans1", listdetials.get(0));

                            //Intent o = new Intent(listbox.this, onlinelist.class);
                            //startActivity(o);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void show(View v)
    {
        if(f1>0&&f3>0)
        {
            t.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.VISIBLE);
            String hoslist[]=listbox.listdetials.toArray(new String[0]);
            p=hoslist;


            ad = new ArrayAdapter<>(this,R.layout.cardviewlist,R.id.name,p);
            lv.setAdapter(ad);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    dialog = new ACProgressFlower.Builder(listbox.this)
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(Color.WHITE)
                            .fadeColor(Color.DKGRAY).build();
                    dialog.show();
                    li=listbox.listdetials;
                    l= (String[]) li.toArray(new String[0]);
                    String v=parent.getItemAtPosition(position).toString();

                    for (String s : l) {
                        if (s.toLowerCase().contains(v.toLowerCase())) {
                            sn = s;

                        }

                    }

                    db= FirebaseDatabase.getInstance().getReference().child("toll-free").child(catogory).child(state1).child(city1).child(sn);
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            hbname= dataSnapshot.child("name").getValue().toString();
                            hbaddress=dataSnapshot.child("address").getValue().toString();
                            hbphone= dataSnapshot.child("number").getValue().toString();
                            dialog.dismiss();
                            BottomSheetDialog bs=new BottomSheetDialog(listbox.this,R.style.BottomSheetDialogTheme);
                            bs.setContentView(R.layout.activity_onlinedetials);
                            ig = bs.findViewById(R.id.image);
                            fi = bs.findViewById(R.id.fav);
                            faa = bs.findViewById(R.id.favv);
                            ka = bs.findViewById(R.id.ko);
                            n = bs.findViewById(R.id.name);
                            a = bs.findViewById(R.id.address);
                            ph = bs.findViewById(R.id.phone);
                            s = bs.findViewById(R.id.share);
                            sh = bs.findViewById(R.id.sharee);
                            phn=hbphone.replaceAll("\\s","");

                            hos=hbname;
                            sp=getSharedPreferences("check",0);
                            if (phn.contains("Phone")||phn.contains("NA")) {
                                ph.setVisibility(View.INVISIBLE);
                                ka.setVisibility(View.INVISIBLE);
                            }
                            if (phn.contains("/")) {
                                String[] pk = phn.split("/");
                                phn = pk[0];

                            }
                            if (phn.contains(",")) {
                                String[] pk = phn.split(",");
                                phn = pk[0];

                            }
                            if (phn.contains("(")) {
                                int len = phn.length();
                                String f = phn.substring(1, len);
                                phn = f;
                            }
                            if (phn.contains(")")) {
                                int in = phn.indexOf(")");
                                int len = phn.length();
                                String kj = phn.substring(0, in) + phn.substring(in + 1, len);
                                phn = kj;

                            }
                            if (phn.length()>11)
                            {
                                phn=phn.substring(0,11);
                            }
                            String m = phn.substring(0, 1);
                            if (m.contains("9") || m.contains("8") || m.contains("7")||m.contains("0")) {

                            } else
                            {
                                String l="0"+phn;
                                phn=l;
                            }
                            if (catogory.toLowerCase().equals("hospital")) {
                                ig.setImageResource(R.mipmap.h);
                            }

                            if (catogory.toLowerCase().equals("blood bank")) {
                                ig.setImageResource(R.mipmap.b);
                            }

                            if (hbname.toLowerCase().contains(city1.toLowerCase() + " " + state1.toLowerCase())) {
                                int k = hbname.toLowerCase().indexOf(city1
                                        .toLowerCase() + " " + state1.toLowerCase());

                                hos=hbname.substring(0, k);
                            }
                            n.setText(hos);

                            a.setText(hbaddress);
                            if(hbaddress.contains("NA"))
                            {
                                a.setVisibility(view.INVISIBLE);
                            }
                            ph.setText(phn);

                            fa=hos+" @"+hbaddress+" @"+phn+"@"+catogory.toLowerCase();




                            String[] ps = first.fav.toArray(new String[0]);
                            int ko=0;
                            for (int i = 0; i < ps.length; i++)
                            {
                                ko=0;
                                if(ps[i].contains(hos))
                                {
                                    ko=1;
                                    break;
                                }
                            }
                            switch (ko)
                            {
                                case 1:
                                    fi.setImageResource(R.drawable.favred);
                                    faa.setText("Remove From Favourite");
                                    break;
                                case 0:
                                    fi.setImageResource(R.drawable.favourite);
                                    faa.setText("Add To Favourite");
                                    break;


                            }
                            ka.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent k = new Intent(Intent.ACTION_CALL);
                                    k.setData(Uri.parse("tel:" + phn));
                                    if (ActivityCompat.checkSelfPermission(listbox.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions

                                        ActivityCompat.requestPermissions(listbox.this, new String[]{Manifest.permission.CALL_PHONE},1);

                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    startActivity(k);
                                }
                            });


                            s.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    if (phn.contains("Phone")||phn.contains("NA")) {
                                        sharingIntent.putExtra(Intent.EXTRA_TEXT, hos+"\n\n"+"Address:"+hbaddress);

                                    }
                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, hos+"\n\n"+"Address:"+hbaddress+"\n\n"+"Ph.No :"+phn);
                                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Important");
                                    startActivity(Intent.createChooser(sharingIntent, "Share To Friends"));

                                }
                            });
                            sh.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    if (phn.contains("Phone")||phn.contains("NA")) {
                                        sharingIntent.putExtra(Intent.EXTRA_TEXT, hos+"\n\n"+"Address:"+hbaddress);

                                    }
                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, hos+"\n\n"+"Address:"+hbaddress+"\n\n"+"Ph.No :"+phn);
                                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Important");
                                    startActivity(Intent.createChooser(sharingIntent, "Share To Friends"));

                                }
                            });

                            fi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String[] p = first.fav.toArray(new String[0]);
                                    int ko = 0;
                                    for (int i = 0; i < p.length; i++) {
                                        String j = p[i];
                                        Log.d("lis", j);
                                        ko = 0;
                                        if (p[i].contains(hos)) {
                                            ko = 1;
                                            break;
                                        }
                                    }
                                    switch (ko) {
                                        case 0:
                                            first.fav.add(fa);
                                            String o = "";
                                            for (int i = 0; i < first.fav.size(); i++) {
                                                o += first.fav.get(i);
                                                o += "@@";
                                            }
                                            SharedPreferences.Editor ed = sp.edit();
                                            ed.putString("listfav", o);
                                            ed.commit();
                                            fi.setImageResource(R.drawable.favred);
                                            faa.setText("Remove From Favourite");
                                            String rw = sp.getString("listfav", "");
                                            String[] res = rw.split("@@");

                                            break;
                                        case 1:
                                            first.fav.remove(fa);
                                            String ok = "";
                                            for (int i = 0; i < first.fav.size(); i++) {
                                                ok += first.fav.get(i);
                                                ok += "@@";
                                            }
                                            SharedPreferences.Editor edk = sp.edit();
                                            edk.putString("listfav", ok);
                                            edk.commit();
                                            fi.setImageResource(R.drawable.favourite);
                                            faa.setText("Add To Favourite");
                                            String rwz = sp.getString("listfav", "");
                                            String[] resz = rwz.split("@@");
                                            String hz = resz[0];
                                            Log.d("answerz", hz);
                                            break;


                                    }
                                }
                            });
                            faa.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String[] p = first.fav.toArray(new String[0]);
                                    int ko = 0;
                                    for (int i = 0; i < p.length; i++) {
                                        String j = p[i];
                                        Log.d("lis", j);
                                        ko = 0;
                                        if (p[i].contains(hos)) {
                                            ko = 1;
                                            break;
                                        }
                                    }
                                    switch (ko) {
                                        case 0:
                                            first.fav.add(fa);
                                            String o = "";
                                            for (int i = 0; i < first.fav.size(); i++) {
                                                o += first.fav.get(i);
                                                o += "@@";
                                            }
                                            SharedPreferences.Editor ed = sp.edit();
                                            ed.putString("listfav", o);
                                            ed.commit();
                                            fi.setImageResource(R.drawable.favred);
                                            faa.setText("Remove From Favourite");
                                            String rw = sp.getString("listfav", "");
                                            String[] res = rw.split("@@");

                                            break;
                                        case 1:
                                            first.fav.remove(fa);
                                            String ok = "";
                                            for (int i = 0; i < first.fav.size(); i++) {
                                                ok += first.fav.get(i);
                                                ok += "@@";
                                            }
                                            SharedPreferences.Editor edk = sp.edit();
                                            edk.putString("listfav", ok);
                                            edk.commit();
                                            fi.setImageResource(R.drawable.favourite);
                                            faa.setText("Add To Favourite");
                                            String rwz = sp.getString("listfav", "");
                                            String[] resz = rwz.split("@@");
                                            String hz = resz[0];
                                            Log.d("answerz", hz);
                                            break;


                                    }
                                }
                            });

                            bs.show();
                            //startActivity(new Intent(onlinelist.this,onlinedetials.class));
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //Toast.makeText(Main5Activity.this, p[position], Toast.LENGTH_LONG).show();
                }
            });
            //Intent o = new Intent(listbox.this, onlinelist.class);
            //startActivity(o);
        }
        else
        {
            t.setVisibility(View.VISIBLE);
            b.setVisibility(View.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
            //Toast.makeText(this,"Please fill above box",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView sv=(SearchView)menuItem.getActionView();
        sv.setQueryHint("Search By Hospital Name");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ad.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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



}
