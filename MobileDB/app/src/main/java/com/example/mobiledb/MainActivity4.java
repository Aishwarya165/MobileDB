package com.example.mobiledb;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;


public class MainActivity4 extends AppCompatActivity {

    private Button logout;
    private TextView username,dob,address,educationalqualification,viewsslcper,viewhscper,viewugper,viewpgper;
    DBhelper DB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        DB=new DBhelper(this);
        logout=findViewById(R.id.logout);
        username=findViewById(R.id.userview);
        dob=findViewById(R.id.dobview);
        address=findViewById(R.id.addressview);
        educationalqualification=findViewById(R.id.eduqview);
        viewsslcper=findViewById(R.id.sslcview);
        viewhscper=findViewById(R.id.hscview);
        viewugper=findViewById(R.id.ugview);
        viewpgper=findViewById(R.id.pgview);

        String us=getIntent().getStringExtra("keyusername");
        String d=getIntent().getStringExtra("keydob");
        String ad=getIntent().getStringExtra("keyaddress");
        String ed=getIntent().getStringExtra("keyeduq");
        String ss=getIntent().getStringExtra("keysslc");
        String hs=getIntent().getStringExtra("keyhsc");
        String ug=getIntent().getStringExtra("keyug");
        String pg=getIntent().getStringExtra("keypg");

        username.setText(us);
        dob.setText(d);
        address.setText(ad);
        educationalqualification.setText(ed);
        viewsslcper.setText(ss);
        viewhscper.setText(hs);
        viewugper.setText(ug);
        viewpgper.setText(pg);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}