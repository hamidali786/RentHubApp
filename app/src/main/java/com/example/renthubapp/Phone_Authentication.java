package com.example.renthubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Phone_Authentication extends AppCompatActivity {

    CountryCodePicker countryCode;
    EditText etPhoneNumber;
    TextView tvErrorMessage;
    ProgressBar progressBar;

    String sentOTP;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    FirebaseAuth fAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__authentication);

        // These two lines are for back button in ActionBar
//        assert getSupportActionBar() != null;
  //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        countryCode = (CountryCodePicker) findViewById(R.id.ccp);
        etPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        countryCode.registerCarrierNumberEditText(etPhoneNumber);

        tvErrorMessage = (TextView) findViewById(R.id.errorMessage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
      // super.context = this;
        //These two lines are for back button in ActionBar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //  Function for ActionBar back button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void sendCode(View view) {

        if (!(etPhoneNumber.getText().toString().replaceAll(" ", "").length() < 10)
                && !(etPhoneNumber.getText().toString().replaceAll(" ", "").length() > 11))
        {
            String phoneNumber = countryCode.getFullNumberWithPlus();

            progressBar.setVisibility(View.VISIBLE);

            requestOTP(phoneNumber);

        }
        else
        {
            tvErrorMessage.setText("Invalid Phone Number");
        }
    }

    private void requestOTP(String phoneNumber) {


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Toast.makeText(Phone_Authentication.this, "Verification Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(Phone_Authentication.this, "Could not send OTP: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                sentOTP = verificationId;
                resendingToken = token;

                Intent intent = new Intent(getApplication().getApplicationContext(), Verify_OTP.class);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("sentOTP", sentOTP);
//                intent.putExtra("resendingToken", resendingToken);
                startActivity(intent);
                finish();

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

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar.setVisibility(View.INVISIBLE);
    }

//    public void gotoHome(View view) {
//        startActivity(new Intent(this, MainActivity.class));
//    }
}
