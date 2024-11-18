package com.kelompok4.uksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText etNamaLengkap, etUsername, etPassword, etEmail, etNIS, etTelepon, etTanggalLahir, etKelas;
    private Button btnRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi elemen-elemen input
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etNIS = findViewById(R.id.etNIS);
        etTelepon = findViewById(R.id.etTelepon);
        etTanggalLahir = findViewById(R.id.etTanggalLahir);
        etKelas = findViewById(R.id.etKelas);
        btnRegister = findViewById(R.id.btnRegister);

        // Setup DatePicker untuk tanggal lahir
        etTanggalLahir.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Register.this,
                    (view, yearSelected, monthOfYear, dayOfMonth) -> {
                        String date = yearSelected + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        etTanggalLahir.setText(date);
                    },
                    year,
                    month,
                    day
            );
            datePickerDialog.show();
        });

        btnRegister.setOnClickListener(view -> {

            // Ambil data dari input
            String namaLengkap = etNamaLengkap.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String nis = etNIS.getText().toString().trim();
            String telepon = etTelepon.getText().toString().trim();
            String tanggalLahir = etTanggalLahir.getText().toString().trim();
            String kelas = etKelas.getText().toString().trim();

            // Validasi input
            if (namaLengkap.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || nis.isEmpty() || telepon.isEmpty() || tanggalLahir.isEmpty() || kelas.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Toast.makeText(getApplicationContext(), "Email tidak valid", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hash password
            String hashedPassword = hashPassword(password);

            // Kirim data ke server menggunakan Volley
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlRegister,
                    response -> {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    },
                    error -> Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected HashMap<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("nama_lengkap", namaLengkap);
                    params.put("username", username);
                    params.put("password", hashedPassword); // Gunakan password yang di-hash
                    params.put("email", email);
                    params.put("nis", nis);
                    params.put("telepon", telepon);
                    params.put("tanggal_lahir", tanggalLahir);
                    params.put("kelas", kelas);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
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

    // Method untuk validasi format email
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
