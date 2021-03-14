package com.example.renthubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainDrawerLayout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    CardView cardViewUserProfile, cardViewDashboard, cardViewNearByLocation;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super .onCreate(savedInstanceState) ;
        setContentView(R.layout. activity_main_drawer_layout ) ;
        cardViewDashboard = (CardView) findViewById(R.id.cardViewDashboard);
        cardViewUserProfile = (CardView) findViewById(R.id.cardViewUserProfile);
        cardViewNearByLocation = (CardView) findViewById(R.id.cardViewNearByLocation);
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



        toolbar = findViewById(R.id. toolbar ) ;
        setSupportActionBar(toolbar) ;
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer , toolbar , R.string. navigation_drawer_open ,
                R.string. navigation_drawer_close ) ;
        drawer.addDrawerListener(toggle) ;
        toggle.syncState() ;
        NavigationView navigationView = findViewById(R.id. nav_view ) ;
        navigationView.setNavigationItemSelectedListener( this ) ;
    }
    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        if (drawer.isDrawerOpen(GravityCompat. START )) {
            drawer.closeDrawer(GravityCompat. START ) ;
        } else {
            super .onBackPressed() ;
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu. activity_main_drawer , menu) ;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId() ;
        if (id == R.id. button_send ) {
            return true;
        }
        return super .onOptionsItemSelected(item) ;
    }
    @Override
    public boolean onNavigationItemSelected ( @NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId() ;
        switch(item.getItemId())
        {
            case R.id.nav_dashboard:   Intent intentDashboard= new Intent(this,Dashboard.class);
                startActivity(intentDashboard);break;
            case R.id.nav_profile:Intent intentUserProfile= new Intent(this,userProfile.class);
                startActivity(intentUserProfile);break;

            case R.id.nav_Vehicle:Intent intentVehicle= new Intent(this,Vehicle_register.class);
                startActivity(intentVehicle);break;
        }
//        return true;
//        } else if (id == R.id. nav_profile ) {
//            Intent intent2 = new Intent(getApplicationContext(), userProfile.class);
//            startActivity(intent2);
//            finish();
//        } else if (id == R.id. nav_logOut ) {
//
//
//        } else if (id == R.id. nav_Vehicle ) {
//            Intent intent3 = new Intent(getApplicationContext(), Vehicle_register.class);
//            startActivity(intent3);
//            finish();
//
//        } else if (id == R.id. nav_Forgot ) {
//
//        }
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        drawer.closeDrawer(GravityCompat. START ) ;
        return true;
    }
}