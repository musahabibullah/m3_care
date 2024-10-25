package com.kelompok4.fragmentbutton.Login;

import android.content.Intent;
import android.util.Patterns;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kelompok4.fragmentbutton.R;
import com.kelompok4.fragmentbutton.network.ApiResponse;
import com.kelompok4.fragmentbutton.network.ApiService;
import com.kelompok4.fragmentbutton.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etUsername, etPassword, etVerificationPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);  // Pastikan ID input benar di layout
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etVerificationPassword = findViewById(R.id.etVerificationPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setListeners() {
        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String verifyPassword = etVerificationPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || !password.equals(verifyPassword)) {
                Toast.makeText(RegisterActivity.this, "Tolong isi data yang benar", Toast.LENGTH_SHORT).show();
            } else {
                register(email, username, password);
            }
        });
    }

    private void register(String email, String username, String password) {
        // Log proses pendaftaran dimulai
        Log.d("DEBUG", "Memulai proses register");

        // Validasi input
        if (!isInputValid(email, username, password)) {
            Log.e("ERROR", "Input tidak valid");
            return;
        }

        // Inisialisasi API service
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ApiResponse> call = apiService.registerUser(null, email, username, password);

        // Kirim request dan tangani respons
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Tanggapan berhasil
                    String successMessage = "Pendaftaran berhasil: " + response.body().getMessage();
                    Log.d("DEBUG", successMessage);
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, successMessage, Toast.LENGTH_LONG).show(); // Tampilkan toast
                        // Navigasi ke halaman login setelah pendaftaran berhasil
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Mengakhiri RegisterActivity agar tidak bisa kembali dengan tombol back
                    });
                } else {
                    // Tanggapan gagal
                    String errorMessage = "Pendaftaran gagal: " + response.message();
                    Log.e("ERROR", errorMessage);
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show()); // Tampilkan toast
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Penanganan error saat gagal terhubung ke server
                String failureMessage = "Pendaftaran gagal: " + t.getMessage();
                Log.e("ERROR", failureMessage);
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, failureMessage, Toast.LENGTH_LONG).show()); // Tampilkan toast
            }
        });
    }

    // Fungsi validasi input
    private boolean isInputValid(String email, String username, String password) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.e("ERROR", "Email tidak valid");
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show(); // Tambahan toast
            return false;
        }
        if (username.isEmpty()) {
            Log.e("ERROR", "Username tidak boleh kosong");
            Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show(); // Tambahan toast
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            Log.e("ERROR", "Password harus lebih dari 6 karakter");
            Toast.makeText(this, "Password harus lebih dari 6 karakter", Toast.LENGTH_SHORT).show(); // Tambahan toast
            return false;
        }
        return true;
    }
}
