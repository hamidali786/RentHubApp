package com.example.renthubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   }

    public void gotoRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), signUp.class);
        startActivity(intent);
        finish();
    }

    public void gotoLogIn(View view) {
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
        finish();
    }
}