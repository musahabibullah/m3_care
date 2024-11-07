package com.kelompok4.fragmentbutton.fragment;

import android.content.Intent; // Untuk menangani intent
import android.os.Bundle; // Untuk menangani bundle data saat membuat activity
import android.widget.Button; // Untuk menggunakan tombol
import android.widget.EditText; // Untuk menggunakan EditText
import androidx.appcompat.app.AppCompatActivity; // Untuk menggunakan AppCompatActivity

import com.kelompok4.fragmentbutton.R;

import retrofit2.Call; // Jika Anda menggunakan Retrofit untuk panggilan API
import retrofit2.Callback; // Callback untuk panggilan Retrofit
import retrofit2.Response; // Untuk menangani respons dari panggilan API

public class EditProfileActivity extends AppCompatActivity {
    private EditText etUsername, etEmail, etContact;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etContact = findViewById(R.id.et_contact);
        btnSave = findViewById(R.id.btn_save);
// Ambil data dari intent dan set ke EditText
        // Ambil data dari intent dan set ke EditText
        String userId = getIntent().getStringExtra("USER_ID");
        loadUserProfile(userId);

        btnSave.setOnClickListener(v -> {
            // Simpan perubahan ke database
            updateProfile(userId);
        });
    }

    private void loadUserProfile(String userId) {
        // Panggil API untuk mengambil data pengguna
        // Isi EditText dengan data pengguna
    }

    private void updateProfile(String userId) {
        // Panggil API untuk memperbarui data pengguna
        // Kirim data baru ke server
    }
}
