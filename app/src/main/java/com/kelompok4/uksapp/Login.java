package com.kelompok4.uksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "user_pref";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_SESSION_ID = "session_id"; // Key untuk session_id

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (!(username.isEmpty() || password.isEmpty())) {

                    // Hash the password
                    String hashedPassword = hashPassword(password);

                    String url = Db_Contract.urlLogin + "?username=" + username + "&password=" + hashedPassword;

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String status = response.getString("status");
                                        if (status.equals("success")) {
                                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                                            // Mendapatkan user_id dan session_id dari respons dan menyimpannya ke SharedPreferences
                                            String userId = response.getString("user_id");
                                            String sessionId = response.getString("session_id");

                                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(KEY_USER_ID, userId);
                                            editor.putString(KEY_SESSION_ID, sessionId); // Menyimpan session_id
                                            editor.apply();

                                            // Melanjutkan ke MainActivity
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    requestQueue.add(jsonObjectRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Password atau Username Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method untuk hashing password menggunakan SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
