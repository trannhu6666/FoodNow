package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderSuccessActivity extends AppCompatActivity {

    private Button btnTrackOrder;
    private TextView btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        btnTrackOrder = findViewById(R.id.btnTrackOrder);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        btnTrackOrder.setOnClickListener(v -> {
            Intent intent = new Intent(OrderSuccessActivity.this, MainActivity.class);
            intent.putExtra("openTab", "orders");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(OrderSuccessActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
