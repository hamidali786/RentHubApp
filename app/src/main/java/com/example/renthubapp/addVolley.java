package com.example.renthubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class addVolley extends AppCompatActivity implements View.OnClickListener{
    EditText usernameET, passwordET;
    Button loginButton;

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_volley);
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);

        loginButton = (Button) findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(this);

        queue = Volley.newRequestQueue(this);

    }

    @Override
    public void onClick(View view) {

        ResponseHandle responseHandle = new ResponseHandle();

        JSONObject loginData = new JSONObject();

        try {
            loginData.put("username", usernameET.getText().toString());
            loginData.put("password", passwordET.getText().toString());
//            Log.d("LoginActivity", "POST Request: " + loginData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                "http://easyeducationmaterial.com/volley/login.php",
                loginData,
                responseHandle,
                responseHandle);


        queue.add(req);

        Log.d("LoginActivity","Request added to queue");

    }

    private class ResponseHandle implements Response.Listener<JSONObject>, Response.ErrorListener{

        @Override
        public void onResponse(JSONObject response) {
            Log.d("LoginActivity", "Response Called");
            Log.d("LoginActivity", response.toString());

            try{

                int success = response.getInt("success");
                if(success == 1){
                    Toast.makeText(addVolley.this, "You are logged in", Toast.LENGTH_SHORT).show();
                }else{
                    String s = response.getString("reason");
                    Toast.makeText(addVolley.this, s, Toast.LENGTH_SHORT).show();
                }


//                Toast.makeText(LoginActivity.this, jsonResponse, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("LoginActivity", "Error Occured: " + error.getMessage());
        }

    }
}