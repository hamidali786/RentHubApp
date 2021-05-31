package com.example.renthubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthenticationActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PhoneAuthActivity";

    //Adding a member variable for the key verification in progress
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    //Creating FirebaseAuth member variable
    private FirebaseAuth mAuth;
    private EditText mPhoneNumberField;
    private EditText mVerificationField;

    private Button mStartButton;
    private Button mVerifyButton;
    private Button mResendButton;
    //Setting Boolean to say whether or not we are in progress.
    private boolean mVerificationInProgress = false;

    //Adding verification id as a member variable.
    private String mVerificationId;

    //Adding a member variable for PhoneAuthProvider.ForceResendingToken callback.
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    //Adding a member variable for a callback which is our PhoneAuthProvider.OnVerificationStateChangeCallbacks.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);


        // Restoring the instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        mPhoneNumberField = findViewById(R.id.mPhoneNumberField);
        mVerificationField = findViewById(R.id.mVerificationField);

        mStartButton = findViewById(R.id.mStartButton);
        mVerifyButton = findViewById(R.id.mVerifyButton);
        mResendButton = findViewById(R.id.mResendButton);


        // Setting all the click listeners
        mStartButton.setOnClickListener(this);
        mVerifyButton.setOnClickListener(this);
        mResendButton.setOnClickListener(this);



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initializing phone auth callbacks  (For verification, Not entering code yet, To get text send to device)
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // It will be invoked in two situations, i.e., instant verification and auto-retrieval:
                // 1 - In few of the cases, the phone number can be instantly verified without needing to  enter or send a verification code.
                // 2 - On some devices, Google Play services can automatically detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;

                //Calling signInWithPhoneAuthCredential.
                signInWithPhoneAuthCredential(credential);
            }


            //Creating onVerificationFailed() method.
            @Override
            public void onVerificationFailed(FirebaseException e) {
                // It is invoked when an invalid request is made for verification.                 //For instance, if the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // Setting error to text field
                    mPhoneNumberField.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota has been exceeded for the project                     Toast.makeText(getApplicationContext(), "Quota exceeded", Toast.LENGTH_SHORT).show();
                }
            }
            // Creating onCodeSent() method called after the verification code has been sent by SMS to the provided phone number.
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code will be sent to the provided phone number
                // Now need to ask the user for entering the code and then construct a credential
                // through integrating the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save the verification ID and resend token to use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }
    // Creating onStart method.
    @Override
    public void onStart() {
        super.onStart();

        // Checking if the user is signed in or not. If signed in, then update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Log.d(TAG, "Currently Signed in: " + currentUser.getEmail());
            Toast.makeText(getApplicationContext(), "Currently Logged in: " + currentUser.getEmail(), Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }

        //check if a verification is in progress. If it is then we have to re verify.
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
        }
    }
    //Implementing SaveInstanceState to save the flag.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    //Implementing RestoreInstanceState to restore the flag.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    // Creating startPhoneNumberVerification() method
    //Getting text code sent. So we can use it to sign-in.
    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        //Setting flag to say that the verification is in process.
        mVerificationInProgress = true;
    }

    //Creating a helper method for verification of phone number with code.
    // Entering code and manually signing in with that code
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    // Creating helper method signInWithPhoneAuthCredential().
    //Use text to sign-in.
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        //Adding onCompleteListener to signInWithCredential.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign-In is successful, update the UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            // If the Sign-In fails, it will display a message and also update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                mVerificationField.setError("Invalid code.");
                            }
                        }
                    }
                });
    }
    // Creating helper method for validating phone number.
    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    //Creating helper method for resending verification code.
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    //Adding onClick method which handles the button clicks.
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mStartButton:
                if (!validatePhoneNumber()) {
                    return;
                }
                //Calling startPhoneNumberVerification helper method for verifying phone number.
                startPhoneNumberVerification(mPhoneNumberField.getText().toString());
                break;
            case R.id.mVerifyButton:
                String code = mVerificationField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerificationField.setError("Cannot be empty.");
                    return;
                }
                //Call the verifyPhoneNumberWithCode () method.
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.mResendButton:
                //Call the resendVerificationCode () method.
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
                break;
        }
    }
}