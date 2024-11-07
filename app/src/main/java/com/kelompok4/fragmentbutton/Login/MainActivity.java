package com.kelompok4.fragmentbutton.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


import com.kelompok4.fragmentbutton.ProfileFragment;
import com.kelompok4.fragmentbutton.R;
import com.kelompok4.fragmentbutton.fragment.HomeFragment;
import com.kelompok4.fragmentbutton.fragment.ReminderFragment;
import com.kelompok4.fragmentbutton.fragment.SuratIzinSakitFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(this);
        navigationView.setSelectedItemId(R.id.fr_home);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int selected = item.getItemId();

        if (selected == R.id.fr_healthrecord) {
            fragment = new SuratIzinSakitFragment();
        } else if (selected == R.id.fr_home) {
            fragment = new HomeFragment();
        } else if (selected == R.id.fr_reminder) {
            fragment = new ReminderFragment();
        } else if (selected == R.id.fr_stockobat) {
            fragment = new HomeFragment();
        } else if (item.getItemId() == R.id.fr_profil) { // Ensure this ID matches the one in your menu resource
            fragment = new ProfileFragment();
        }


        return loadFragment(fragment);
    }

}