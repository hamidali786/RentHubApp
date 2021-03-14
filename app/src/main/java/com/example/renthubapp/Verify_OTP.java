package com.example.renthubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify_OTP extends AppCompatActivity {
    TextView tvMessageSent, tvResendCode;
    EditText etOTP;
    ProgressBar progressBar;

    String phoneNumber, sentOTP;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseAuth fAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__o_t_p);
        //These two lines are for back button in ActionBar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        sentOTP = intent.getStringExtra("sentOTP");

        tvMessageSent = (TextView)findViewById(R.id.messageSent);
        tvMessageSent.append(phoneNumber);

        etOTP = (EditText)findViewById(R.id.otp);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tvResendCode = (TextView)findViewById(R.id.resendCode);

        fAuth = FirebaseAuth.getInstance();

        countDownTimer(60);
    }
    //Function for ActionBar back button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void verify(View view) {

        String enteredOTP = etOTP.getText().toString();
        if (!enteredOTP.isEmpty())
        {
            if (enteredOTP.length() == 6)
            {
                progressBar.setVisibility(View.VISIBLE);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sentOTP, enteredOTP);
                verifyAuth(credential);
            }
            else
            {
                etOTP.setError("Enter a valid 6 digits code");
            }
        }
        else
        {
            etOTP.setError("Enter 6 digits code here");
        }

    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if (task.isSuccessful())
                {
                    Intent intent = new Intent(getApplicationContext(), signUp.class);
                    intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Verify_OTP.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void resendCode(View view) {

        progressBar.setVisibility(View.VISIBLE);
        etOTP.setText("");
        tvResendCode.setEnabled(false);
        tvResendCode.setTextColor(Color.parseColor("#555555"));
        requestOTP(phoneNumber);
    }



    private void requestOTP(String phoneNumber) {


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Toast.makeText(getApplicationContext(), "Verification Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(getApplicationContext(), "Could not send OTP: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                sentOTP = verificationId;
                resendingToken = token;
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Verify_OTP.this, "Code Resent!", Toast.LENGTH_SHORT).show();

                countDownTimer(60);
            }
        };


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(fAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void countDownTimer(int seconds)
    {
        new CountDownTimer(seconds*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvResendCode.setText("Resend in: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvResendCode.setEnabled(true);
                tvResendCode.setTextColor(Color.parseColor("#000000"));
                tvResendCode.setText("Resend Now");
            }
        }.start();
    }


    public void gotoChangePhone(View view) {
        startActivity(new Intent(this, Phone_Authentication.class));
        finish();
    }
//    public void gotoHome(View view) {
//        startActivity(new Intent(this, MainActivity.class));
//    }
}