package com.example.renthubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ramotion.circlemenu.CircleMenuView;

public class Dashboard extends AppCompatActivity {
    ViewFlipper viewFlipper;
    TextView Next, Previous;
    CardView cardViewUserProfile, cardViewDashboard, cardViewNearByLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        cardViewDashboard = (CardView) findViewById(R.id.cardViewDashboard);
        cardViewUserProfile = (CardView) findViewById(R.id.cardViewUserProfile);
        cardViewNearByLocation = (CardView) findViewById(R.id.cardViewNearByLocation);
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);

        cardViewDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
        cardViewUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), userProfile.class);
                startActivity(intent);
                finish();
            }
        });
        cardViewNearByLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NearByLocation.class);
                startActivity(intent);
                finish();
            }
        });
        //On click listener on Nest and previous start
        Next = (TextView) findViewById(R.id.Next);
        Previous = (TextView) findViewById(R.id.Previous);
        Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //   TODO Auto-generated method stub
                viewFlipper.showNext();
            }	});
        Previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewFlipper.showPrevious();

            }	});

    }
    //On click listener on Nest and previous end
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  getMenuInflater().inflate(R.menu.FlipperViewActivity, menu);
        return true;

    }
    //Flipper view end
}