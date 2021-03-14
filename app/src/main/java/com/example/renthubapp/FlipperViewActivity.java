package com.example.renthubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class FlipperViewActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    TextView Next, Previous, Skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipper_view);
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        Next = (TextView) findViewById(R.id.Next);
        Previous = (TextView) findViewById(R.id.Previous);
        Skip = (TextView) findViewById(R.id.Skip);

        Skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //   TODO Auto-generated method stub
                viewFlipper.showNext();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }	});
        Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewFlipper.showPrevious();

            }	});
        Previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewFlipper.showPrevious();

            }	});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.FlipperViewActivity, menu);
        return true;
    }

}
