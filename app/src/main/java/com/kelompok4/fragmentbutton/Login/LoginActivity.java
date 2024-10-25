package com.kelompok4.fragmentbutton.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.kelompok4.fragmentbutton.R;
import com.kelompok4.fragmentbutton.network.ApiResponse;
import com.kelompok4.fragmentbutton.network.ApiService;
import com.kelompok4.fragmentbutton.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setListeners();
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsernameLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);
    }

    private void setListeners() {
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInput(username, password)) {
                // Panggil API untuk login
                login(username, password);
            } else {
                Toast.makeText(LoginActivity.this, "Username dan Password harus diisi", Toast.LENGTH_SHORT).show();
            }
        });

        tvGoToRegister.setOnClickListener(v -> {
            // Arahkan pengguna ke RegisterActivity jika belum memiliki akun
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInput(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private void login(String username, String password) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ApiResponse> call = apiService.loginUser(username, password); // Memanggil API yang sesuai untuk login

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null) {
                        handleLoginResponse(apiResponse);
                    } else {
                        showToast("Respons kosong dari server");
                    }
                } else {
                    showToast("Kesalahan pada server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API Failure", "Koneksi gagal: " + t.getMessage());
                showToast("Koneksi gagal: " + t.getMessage());
            }
        });
    }

    private void handleLoginResponse(ApiResponse apiResponse) {
        Log.d("API Response", apiResponse.getMessage());
        showToast(apiResponse.getMessage());

        if (apiResponse.getMessage().equals("Login sukses")) {
            // Arahkan ke MainActivity setelah login sukses
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Menutup LoginActivity agar tidak bisa kembali dengan tombol back
        }
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
