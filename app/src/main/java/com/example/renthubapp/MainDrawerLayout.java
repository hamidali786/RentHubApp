package com.example.renthubapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renthubapp.firebase.FirebaseHandler;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainDrawerLayout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView ivDisplayPicture;
    private ImageView ivHeaderDisplayPicture;
    private TextView tvWelcomeUser;
    private TextView tvHeaderUserName;
    private TextView tvHeaderEmail;

    private TextView tvTotalAds;
    private TextView tvTotalUsers;

    private String USER_TABLE = "User";
    private String RENT_TABLE = "Rent";

    private DatabaseReference userDatabase;
    private DatabaseReference rentDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDatabase = new FirebaseHandler().getFirebaseConnection(USER_TABLE);
        rentDatabase = new FirebaseHandler().getFirebaseConnection(RENT_TABLE);

        ivDisplayPicture = findViewById(R.id.ivDisplayPicture);
        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);

        tvTotalAds = findViewById(R.id.tvTotalAds);
        tvTotalUsers = findViewById(R.id.tvTotalUsers);

        try {
            if (Availablity.currentUser.getRole().equals("Renter")) {
                NavigationView navigationView = findViewById(R.id.nav_view);
                navigationView.getMenu().findItem(R.id.itemAddRent).setVisible(false);
                navigationView.getMenu().findItem(R.id.itemMyPosts).setVisible(false);
            } else if (Availablity.currentUser.getRole().equals("Owner")) {
                NavigationView navigationView = findViewById(R.id.nav_view);
                navigationView.getMenu().findItem(R.id.itemAddRent).setVisible(true);
                navigationView.getMenu().findItem(R.id.itemMyPosts).setVisible(true);
            }

            AlertDialog.Builder alert = new AlertDialog.Builder(MainDrawerLayout.this);
            alert.setTitle("Welcome to Rent|Hub");
            alert.setMessage("" + Availablity.currentUser.getName());
            alert.show();
            tvWelcomeUser.setText("Welcome\n" + Availablity.currentUser.getEmail());

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View headerView = navigationView.getHeaderView(0);
            ivHeaderDisplayPicture = headerView.findViewById(R.id.ivHeaderDisplayPicture);
            tvHeaderUserName = headerView.findViewById(R.id.tvHeaderUserName);
            tvHeaderEmail = headerView.findViewById(R.id.tvHeaderEmail);

            tvHeaderUserName.setText(Availablity.currentUser.getName());
            tvHeaderEmail.setText(Availablity.currentUser.getEmail());
        } catch (NullPointerException ignored) {
            startActivity(new Intent(getApplicationContext(), signUp.class));
        }

        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();

                if (count == 0) {
                    tvTotalUsers.setText(count + "\nUsers");
                } else if (count > 500) {
                    tvTotalUsers.setText(500 + "+\nUsers");
                } else {
                    tvTotalUsers.setText(count + "\nUsers");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rentDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();

                if (count == 0) {
                    tvTotalAds.setText(count + "\nAds");
                } else if (count > 500) {
                    tvTotalAds.setText(500 + "+\nAds");
                } else {
                    tvTotalAds.setText(count + "\nAds");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuAbout) {
            AlertDialog aboutme = new AlertDialog.Builder(MainDrawerLayout.this)
                    .setTitle("ABOUT Developer")
                    .setIcon(android.R.drawable.ic_menu_agenda)
                    .setMessage("Names: Hamid Ali & Hussain Ahmad\n" +
                            "Email: hamidali7682@gmail.com\n" +
                            "Email: hussainafghan098@gmail.com")
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.itemHome) {
            Intent homeIntent = new Intent(MainDrawerLayout.this, HomeActivity.class);
            startActivity(homeIntent);
        } else if(id == R.id.nav_dashboard) {
            Intent postAdIntent = new Intent(MainDrawerLayout.this, Dashboard.class);
            startActivity(postAdIntent);
        } else if (id == R.id.nav_Vehicle) {
            Intent myPostIntent = new Intent(MainDrawerLayout.this, MyPosts.class);
            startActivity(myPostIntent);
        } else if(id == R.id.itemAddRent) {
            Intent postAdIntent = new Intent(MainDrawerLayout.this, PostAdActivity.class);
            startActivity(postAdIntent);
        } else if (id == R.id.itemMyPosts) {
            Intent myPostIntent = new Intent(MainDrawerLayout.this, MyPosts.class);
            startActivity(myPostIntent);
        } else if (id == R.id.itemProfile) {
            Intent profileIntent = new Intent(MainDrawerLayout.this, Profile.class);
            startActivity(profileIntent);
        } else if(id == R.id.nav_Send) {
            Intent postAdIntent = new Intent(MainDrawerLayout.this, Send.class);
            startActivity(postAdIntent);
        } else if (id == R.id.nav_Share) {
            Intent myPostIntent = new Intent(MainDrawerLayout.this, ShareApp.class);
            startActivity(myPostIntent);
        } else if (id == R.id.nav_feedBack) {
            Intent profileIntent = new Intent(MainDrawerLayout.this, Feedback.class);
            startActivity(profileIntent);
        }

        else if (id == R.id.itemSignOut) {
            super.onBackPressed();
            Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
