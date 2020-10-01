package com.dhanadsp1120.drmfortamilnadu;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class district extends AppCompatActivity {
    GridView gv;
    String s;
    String[]pn,mm;
    ExpandableListView lv;
    ExpandableListAdapter la;
    List<String> listdata,lm;
    List<List<String>> ld;
    HashMap<String,List<String>> hashMaplist;
    String radiobutton;
TextView b;
    List<String> state;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        Intent k = getIntent();
        lv=findViewById(R.id.elv);
        b= findViewById(R.id.statename);
        state = new ArrayList<String>();

        state.add("Common Numbers@POLICE:100#FIRE:101#AMBULANCE:102,108#Coastal Security Helpline:1093#TRAFFIC POLICE:103#WOMEN HELPLINE:1091#CHILD LINE:1098#RAILWAY POLICE HELPLINE:9962500500 , 1512#DISASTER HELPLINE:1077#GAS LEAKAGE:1716,011-1906#ANTI RAGGING COMPLAIN:18001805522#RAILWAY PROTECTION FORCE:1322#STUDENT AND EXAM HELPLINE:14417");
        state.add("Corona (COVID 19)@CORONA ( COVID 19 ) HELPLINE :  011-23978046 , 1075# TAMIL NADU COVID 19 HELPLINE : 044-29510500 ,044-25615025 ,044-28414513 ,044-28593990");
        state.add("Important Numbers @WOMEN HELPLINE :  1091#CHILD HELPLINE :  1098#NATURAL DISASTER : 1078 , 1077 , 1070 ,011- 26701728#CHENNAI CORPORATION COMPLAIN : 1913#DEPRESSION AND SUICIDE PREVENTION : 104");
        state.add(" Hospital and Ambulance Helpline Numbers(CHENNAI)@National Hospital Ambulance :04425240131#Apollo Ambulance :1066 , 04428294302#St. Johns Ambulance:04428194630#Helpling Point Ambulance:04428280257 #Trauma Care Consortium :04428150700 , 04428240311#Government General Hospital: 04425305000#Government Kilpauk Hospital: 04428364951#Government Royapettah Hospital:04428483051#Government Stanley Govt. Hospital for Women & Children: 04428191982#Kasturba Hospital for Women :04428545449#Institute of Child Health & Hospital :04428191135#Voluntary Health Service:04422541972");
        state.add("Blood Banks(CHENNAI)@Government Stanley Hospital Blood Bank: 04425214941#Rotary Blood Bank :04424881392#Indian Red Cross Society :04428554425#Jeevan Blood Bank :04428220494#Kasturbha Gandhi Government Hospital :04428545001#Child Trust Hospital Blood bank: 04428259601,04428259593#Apollo Hospital Blood Bank :04428294870#Royapettah Hospital ( Govt. ) Blood Bank: 04428533051#Lions Blood Bank: 0442841595#Hospital:044252813479, 04428414949");

        ActionBar actionBar= getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        s = "Tamil Nadu";
        b.setText(s);
        String[] sn = state.toArray(new String[0]);
        pn = sn;
        lm=new ArrayList<>();
        ld=new ArrayList<>();
        //ld.add("dddd");

        for(int i=0;i<pn.length;i++)
        {
            String ss=pn[i];
            String []jj=ss.split("@");
            String title=jj[0];
            String []jk=jj[1].split("#");
            lm.add(title);
            List<String> l=Arrays.asList(jk);

            ld.add(l);
            //l.clear();
        }




       // listdata= Arrays.asList(sn);
        hashMaplist=new HashMap<>();
        for (int h=0;h<ld.size();h++)
        {
            for (int g=0;g<ld.get(h).size();g++)
            {

                hashMaplist.put(lm.get(h),ld.get(h));


            }
        }
        la =new expandablelistview(this,lm,hashMaplist);
        lv.setAdapter(la);

        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                AlertDialog.Builder dialog= new AlertDialog.Builder(district.this);
                String data=hashMaplist.get(lm.get(groupPosition)).get(childPosition);
                final String datasplit[]=data.split(":");
                final String d1=datasplit[0];
                String d2=datasplit[1];

                String []vv=d2.split(",");
                radiobutton=vv[0];
                mm=vv;
                dialog.setTitle(d1)
                        .setSingleChoiceItems(mm, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                radiobutton=mm[which];
                                    }
                        }).setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent k = new Intent(Intent.ACTION_CALL);
                        k.setData(Uri.parse("tel:" + radiobutton));
                        if (ActivityCompat.checkSelfPermission(district.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions

                            ActivityCompat.requestPermissions(district.this, new String[]{Manifest.permission.CALL_PHONE},1);

                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(k);
                    }
                })


                        .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, s+"\n"+d1+"\t:"+radiobutton);
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Important");
                        startActivity(Intent.createChooser(sharingIntent, "Share To Friends"));

                         }

                }) ;

                AlertDialog c=dialog.create();
                c.show();
                return false;
            }
        });

    }

    private class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {

            return pn.length;
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
            View view1 = getLayoutInflater().inflate(R.layout.buttongrid,
                    null);

            TextView name = view1.findViewById(R.id.name);

            name.setText(pn[i]);

            return view1;
        }
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
}