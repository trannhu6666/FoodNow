package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminDashboardActivity extends AppCompatActivity {

    private CardView cardManageFoods, cardManageOrders, cardManageVouchers;
    private Button btnLogoutAdmin;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        sessionManager = new SessionManager(this);

        cardManageFoods = findViewById(R.id.cardManageFoods);
        cardManageOrders = findViewById(R.id.cardManageOrders);
        cardManageVouchers = findViewById(R.id.cardManageVouchers);
        btnLogoutAdmin = findViewById(R.id.btnLogoutAdmin);

        cardManageFoods.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AdminFoodsActivity.class);
            startActivity(intent);
        });

        cardManageOrders.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AdminOrdersActivity.class);
            startActivity(intent);
        });

        cardManageVouchers.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AdminVouchersActivity.class);
            startActivity(intent);
        });

        btnLogoutAdmin.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(this, "Đã đăng xuất tài khoản Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
