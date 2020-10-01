package com.dhanadsp1120.drmfortamilnadu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class favourite extends AppCompatActivity {
    ArrayAdapter ad;
    TextView tv;
    ListView lv;
    public ACProgressFlower dialog;

    String p[];
    ImageView ig,fi;
    Button ka;
    TextView n, a, ph,sh,faa;
    ImageButton s;
    SharedPreferences sp;
    String ofn,ofa,ofp,ofc;
    static String ls[], offname, offaddress, offphn,offcat;
    List li = new ArrayList<String>();
    List favz = new ArrayList<String>();

    List favzz = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp=getSharedPreferences("check",0);
        setContentView(R.layout.activity_favourite);
        String rw=sp.getString("listfav","");
        String[]res=rw.split("@@");
        for(int i=0;i<res.length;i++)
        {
            favz.add(res[i]);
        }
        String h[] = (String[]) favz.toArray(new String[0]);
        p = h;
        tv = findViewById(R.id.text);
        tv.setVisibility(View.INVISIBLE);
        if (h.length > 0) {


            tv.setVisibility(View.INVISIBLE);
            for (int i = 0; i < h.length; i++) {
                String d = h[i];
                String f[] = d.split("@");
                p[i] = f[0];
            }
            lv = findViewById(R.id.list);
            ad = new ArrayAdapter<>(this, R.layout.cardviewlist, R.id.name, p);
            lv.setAdapter(ad);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dialog = new ACProgressFlower.Builder(favourite.this)
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(Color.WHITE)
                            .fadeColor(Color.DKGRAY).build();
                    dialog.show();
                    li = favz;
                    ls = (String[])li.toArray(new String[0]);
                    String v = parent.getItemAtPosition(position).toString();
                    BottomSheetDialog bs=new BottomSheetDialog(favourite.this,R.style.BottomSheetDialogTheme);
                    bs.setContentView(R.layout.activity_offlinelistdetials);
                  //  Toast.makeText(favourite.this,v,Toast.LENGTH_LONG).show();
                    for (String s : ls) {
                        if (s.contains(v)) {
                            String[] n = s.split("@");
                            offname = n[0];
                            offaddress = n[1];
                            offphn = n[2];
                            offcat = n[3];
                            dialog.dismiss();
                            break;

                        }

                    }


                    ig = bs.findViewById(R.id.image);
                    fi = bs.findViewById(R.id.fav);
                    faa = bs.findViewById(R.id.favv);
                    ka = bs.findViewById(R.id.ko);
                    n = bs.findViewById(R.id.name);
                    a = bs.findViewById(R.id.address);
                    ph = bs.findViewById(R.id.phone);
                    s = bs.findViewById(R.id.share);
                    sh = bs.findViewById(R.id.sharee);
                    ofn=offname;
                    ofa=offaddress;
                    ofp=offphn;
                    ofc=offcat;
                    /*String rw=sp.getString("listfav","");
                    String[]res=rw.split("@@");
                    for(int i=0;i<res.length;i++)
                    {
                        favzz.add(res[i]);
                    }
*/
                    if (ofp.contains("Phone")||ofn.contains("NA")) {
                        ph.setVisibility(View.INVISIBLE);
                        ka.setVisibility(View.INVISIBLE);
                    }
                    if (ofc.equals("hospital")) {
                        ig.setImageResource(R.mipmap.h);
                    }

                    if (ofc.equals("blood bank")) {
                        ig.setImageResource(R.mipmap.b);
                    }
                    n.setText(ofn);
                    a.setText(ofa);
                    ph.setText(ofp);
                    fi.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                                  String[] pp = (String[]) favz.toArray(new String[0]);

                                                  for (int i = 0; i < pp.length; i++)
                                                  {

                                                      if(pp[i].contains(ofn))
                                                      {
                                                          favz.remove(pp[i]);
                                                          String o="";
                                                          for (int ik=0;ik<favz.size();ik++)
                                                          {
                                                              o+=favz.get(ik);
                                                              o+="@@";
                                                          }
                                                          SharedPreferences.Editor ed=sp.edit();
                                                          ed.putString("listfav",o);
                                                          ed.commit();
                                                          //onBackPressed();
                                                          startActivity(new Intent(favourite.this,favourite.class));
                                                          break;
                                                      }
                                                  }
                                              }
                                          });
                    faa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String[] pp = (String[]) favz.toArray(new String[0]);

                            for (int i = 0; i < pp.length; i++)
                            {

                                if(pp[i].contains(ofn))
                                {
                                    favz.remove(pp[i]);
                                    String o="";
                                    for (int ik=0;ik<favz.size();ik++)
                                    {
                                        o+=favz.get(ik);
                                        o+="@@";
                                    }
                                    SharedPreferences.Editor ed=sp.edit();
                                    ed.putString("listfav",o);
                                    ed.commit();
                                    //onBackPressed();
                                    startActivity(new Intent(favourite.this,favourite.class));
                                    break;
                                }
                            }
                        }
                    });
                            ka.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent k = new Intent(Intent.ACTION_CALL);
                                    k.setData(Uri.parse("tel:" + ofp));
                                    if (ActivityCompat.checkSelfPermission(favourite.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions

                                        ActivityCompat.requestPermissions(favourite.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

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
                            if (ofp.contains("Phone")||ofp.contains("NA")) {
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, ofn+"\n\n"+"Address:"+ofa);

                            }
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, ofn+"\n\n"+"Address:"+ofa+"\n\n"+"Ph.No :"+ofp);
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Important");
                            startActivity(Intent.createChooser(sharingIntent, "Share To Friends"));

                        }
                    });
                    sh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            if (ofp.contains("Phone")||ofp.contains("NA")) {
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, ofn+"\n\n"+"Address:"+ofa);

                            }
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, ofn+"\n\n"+"Address:"+ofa+"\n\n"+"Ph.No :"+ofn);
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Important");
                            startActivity(Intent.createChooser(sharingIntent, "Share To Friends"));

                        }
                    });
                    //startActivity(new Intent(favourite.this, offlinelistdetials.class));
bs.show();
                    //Toast.makeText(Main5Activity.this, p[position], Toast.LENGTH_LONG).show();
                }
            });


        }
        if(h[0]=="")
        {
            tv.setVisibility(View.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
        }
        if(h.length<=0) {

            tv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.about) {
            startActivity(new Intent(this, about.class));

        }

        if (item.getItemId() == android.R.id.home) {

            onBackPressed();

        }
        if (item.getItemId() == R.id.gotohome) {

            startActivity(new Intent(this, first.class));
        }


        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, first.class));

    }
}
