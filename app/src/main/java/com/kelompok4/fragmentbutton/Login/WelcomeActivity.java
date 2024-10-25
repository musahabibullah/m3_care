package com.kelompok4.fragmentbutton.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.kelompok4.fragmentbutton.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnSign, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Inisialisasi tombol
        btnSign = findViewById(R.id.btnSign);
        btnRegister = findViewById(R.id.btnRegister);

        // Logika untuk tombol SIGN
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Arahkan ke LoginActivity ketika tombol SIGN ditekan
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Logika untuk tombol REGISTER
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Arahkan ke RegisterActivity ketika tombol REGISTER ditekan
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
