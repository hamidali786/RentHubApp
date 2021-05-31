package com.example.renthubapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class ShareApp extends AppCompatActivity {
    MaterialButton multiSelectionDialog,progressDialog,horizontalProgressDialog;
    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);
        multiSelectionDialog = (MaterialButton) findViewById(R.id.multiSelectionDialog);
        horizontalProgressDialog = (MaterialButton) findViewById(R.id.horizontalProgressDialog);
        progressDialog = (MaterialButton) findViewById(R.id.progressDialog);

    }



    public void progressDialog(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(ShareApp.this);
        progressDialog.setTitle("Circular Progress Dialog");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void multiSelectionDialog(View view) {

        CharSequence[] choices = {"Choice 1", "Choice 2", "Choice 3"};
        boolean[] choicesInitial = {false, false, false};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShareApp.this);
        alertDialog.setIcon(R.drawable.ic_share)
        .setTitle("Vehicle Option")
        .setPositiveButton("Accept", null)
        .setNeutralButton("Cancel", null)
        .setMultiChoiceItems(choices, choicesInitial,
         new DialogInterface.OnMultiChoiceClickListener() {
            @Override
     public void onClick(DialogInterface dialog, int which, boolean isChecked) {

  } });
        alertDialog.show();
    }


    public void horizontalProgressDialog(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(ShareApp.this);
        progressDialog.setMax(100);
        progressDialog.setTitle("Horizontal Progress Dialog");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(
                    @NonNull
                            Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(5);
            }
        };
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    while
                    (progressDialog.getProgress() <= progressDialog .getMax()) {
                        Thread.sleep(200);
                        handler.sendMessage(handler.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog .getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } }).start();
    }
}

