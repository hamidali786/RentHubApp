package com.example.renthubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

public class welcome extends AppCompatActivity {
    TextView tv_marquee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        tv_marquee = (TextView) findViewById(R.id.tv_marquee);
//        tv_marquee.setSelected(true);  // Set focus to the textView
//        tv_marquee.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        tv_marquee.setSingleLine(true);
        Thread myThread = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(welcome.this, FlipperViewActivity.class));
                    finish();
                }catch (Exception e){
                    Toast.makeText(welcome.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        };

        myThread.start();
    }
}