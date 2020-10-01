package com.dhanadsp1120.drmfortamilnadu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class coursedetials extends AppCompatActivity {
TextView cn,cc,ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursedetials);
        cn=findViewById(R.id.name);
        cc=findViewById(R.id.content);
        ct=findViewById(R.id.tips);
        cn.setText(course.rr);
        cc.setText(course.content);
        ct.setText(course.lang);
    }
    public void load(View v)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(course.link)));
    }
}