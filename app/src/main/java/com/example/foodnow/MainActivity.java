package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.nav_vouchers) {
                replaceFragment(new VouchersFragment());
                return true;
            } else if (itemId == R.id.nav_orders) {
                replaceFragment(new OrderHistoryFragment());
                return true;
            } else if (itemId == R.id.nav_profile) {
                replaceFragment(new ProfileFragment());
                return true;
            }
            return false;
        });

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && "orders".equals(intent.getStringExtra("openTab"))) {
            bottomNavigation.setSelectedItemId(R.id.nav_orders);
        } else {
            bottomNavigation.setSelectedItemId(R.id.nav_home);
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
