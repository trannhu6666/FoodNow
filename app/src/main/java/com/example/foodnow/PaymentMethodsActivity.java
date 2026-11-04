package com.example.foodnow;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnAddPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        btnBack = findViewById(R.id.btnBack);
        btnAddPaymentMethod = findViewById(R.id.btnAddPaymentMethod);

        btnBack.setOnClickListener(v -> finish());

        btnAddPaymentMethod.setOnClickListener(v ->
                Toast.makeText(PaymentMethodsActivity.this, "Chức năng thêm thẻ / ví mới đang phát triển", Toast.LENGTH_SHORT).show()
        );
    }
}
