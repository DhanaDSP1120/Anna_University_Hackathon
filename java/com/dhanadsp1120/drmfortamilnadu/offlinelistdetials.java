package com.dhanadsp1120.drmfortamilnadu;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class offlinelistdetials extends AppCompatActivity {
    ImageView ig,fi;
    TextView n, a, p;
    ImageButton c, s;
    String ofn,ofa,ofp,ofc;
    List g = new ArrayList<String>();
    List favz = new ArrayList<String>();
    SharedPreferences sp;

    LinearLayout ko;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_offlinelistdetials);
        sp=getSharedPreferences("check",0);
        ig = findViewById(R.id.image);
        fi = findViewById(R.id.fav);
        n = findViewById(R.id.name);
        a = findViewById(R.id.address);

        p = findViewById(R.id.phone);
       // c = findViewById(R.id.call);
        s = findViewById(R.id.share);
        ofn=favourite.offname;
        ofa=favourite.offaddress;
        ofp=favourite.offphn;
        ofc=favourite.offcat;
        String rw=sp.getString("listfav","");
        String[]res=rw.split("@@");
        for(int i=0;i<res.length;i++)
        {
            favz.add(res[i]);
        }

        if (ofp.contains("Phone")) {
            p.setVisibility(View.INVISIBLE);
            ko.setVisibility(View.INVISIBLE);
        }
        if (ofc.equals("hospital")) {
            ig.setImageResource(R.mipmap.h);
        }

        if (ofc.equals("blood bank")) {
            ig.setImageResource(R.mipmap.b);
        }
        n.setText(ofn);
        a.setText(ofa);
        p.setText(ofp);
    }
    public void fav(View v) {
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
                startActivity(new Intent(offlinelistdetials.this,favourite.class));
                break;
            }
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

    public void call(View v)
    {
        Intent k = new Intent(Intent.ACTION_CALL);
        k.setData(Uri.parse("tel:" + ofa));
        if (ActivityCompat.checkSelfPermission(offlinelistdetials.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            ActivityCompat.requestPermissions(offlinelistdetials.this, new String[]{Manifest.permission.CALL_PHONE},1);

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(k);
    }
    public void share(View v)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        if (ofp.contains("Phone")) {
            sharingIntent.putExtra(Intent.EXTRA_TEXT, ofn + "\n\n" + "Address:" + ofa);
        }
        sharingIntent.putExtra(Intent.EXTRA_TEXT, ofn + "\n\n" + "Address:" + ofa + "\n\n" + "Ph.No :" + ofp);

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Important");
        startActivity(Intent.createChooser(sharingIntent, "Share To Friends"));
    }
}
