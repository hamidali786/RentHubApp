package com.example.renthubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Send extends AppCompatActivity {

    EditText userName, emailAddress, FeedbackBody;
    CheckBox CheckBoxResponse;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        userName = (EditText) findViewById(R.id.userName);
        String name = userName.getText().toString();

        emailAddress = (EditText) findViewById(R.id.emailAddress);
        String email = emailAddress.getText().toString();
        FeedbackBody = (EditText) findViewById(R.id.FeedbackBody);
        String feedback = FeedbackBody.getText().toString();

        CheckBoxResponse = (CheckBox) findViewById(R.id.CheckBoxResponse);
        boolean bRequiresResponse = CheckBoxResponse.isChecked();

        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Send.this,"Thanks! Your Feedback is submitted.",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void sendFeedback(View view) {
        Toast.makeText(Send.this,"Thanks! Your Feedback is submitted.",Toast.LENGTH_LONG).show();
    }
}