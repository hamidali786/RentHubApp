package com.example.renthubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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