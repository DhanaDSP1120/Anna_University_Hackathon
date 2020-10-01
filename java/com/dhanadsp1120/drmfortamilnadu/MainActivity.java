package com.dhanadsp1120.drmfortamilnadu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class MainActivity extends AppCompatActivity {
    public EditText un,pas;

    public ACProgressFlower dialog;
    SharedPreferences preferences;
    public  String phone,password;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        un=findViewById(R.id.phone);
        pas=findViewById(R.id.loginpassword);
        preferences = getSharedPreferences("login", 0);
    }
public void signup(View V)
{
    Dialog m=new Dialog(this);
    m.setContentView(R.layout.dialogcustom);
    WebView w=m.findViewById(R.id.web);
    WebSettings webSettings=w.getSettings();
    webSettings.setJavaScriptEnabled(true);
    w.setWebViewClient(new WebViewClient());
    w.setWebChromeClient(new WebChromeClient());
    w.loadUrl("http://dhanadsp.freesite.vip/web/emailverificationforapp.html");
    m.show();
    m.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    m.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    m.setCanceledOnTouchOutside(false);
    // m.requestWindowFeature(Window.FEATURE_NO_TITLE);
    m.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
}
    public void forgot(View V)
    {
        Dialog m=new Dialog(this);
        m.setContentView(R.layout.dialogcustom);
        WebView w=m.findViewById(R.id.web);
        WebSettings webSettings=w.getSettings();
        webSettings.setJavaScriptEnabled(true);
        w.setWebViewClient(new WebViewClient());
        w.setWebChromeClient(new WebChromeClient());
        w.loadUrl("http://dhanadsp.freesite.vip/web/forgot.html");
        m.show();
        m.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        m.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        m.setCanceledOnTouchOutside(false);
        // m.requestWindowFeature(Window.FEATURE_NO_TITLE);
        m.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void lok(View v) {
        dialog = new ACProgressFlower.Builder(MainActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
        phone = un.getText().toString();
        password = pas.getText().toString();
        db= FirebaseDatabase.getInstance().getReference().child("apkregister").child(phone);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String pp=dataSnapshot.child("Password").getValue().toString();
                    if (pp.equals(password))
                    {
                        dialog.dismiss();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isUserLogin", true);
                        editor.commit();
                        Intent o=new Intent(MainActivity.this,homescreen.class) ;
                        startActivity(o);
                    }
                    else
                    {
                        dialog.dismiss();
                        pas.setError("Incorrect password");

                    }

                }
                else
                {dialog.dismiss();
                    un.setError("InValid User");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Network Problem",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
