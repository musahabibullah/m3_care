package com.kelompok4.uksapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment;

            if (item.getItemId() == R.id.fr_healthrecord) {
                selectedFragment = new SuratIzinFragment();
            } else if (item.getItemId() == R.id.fr_reminder) {
                selectedFragment = new ReminderFragment();
            } else if (item.getItemId() == R.id.fr_stokobat) {
                selectedFragment = new StockObatFragment();
            } else if (item.getItemId() == R.id.fr_profil) {
                selectedFragment = new ProfileFragment();
            } else {
                selectedFragment = new HomeFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

        // Muat fragment default (Home) hanya saat pertama kali
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }
}