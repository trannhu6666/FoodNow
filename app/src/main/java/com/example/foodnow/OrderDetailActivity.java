package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class OrderDetailActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvDetailOrderId, tvDetailStatus, tvDetailDate, tvDetailRestaurant, tvDetailAddress, tvDetailPayment, tvDetailItemsSummary, tvDetailTotalAmount;
    private Button btnReorderDetail;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        btnBack = findViewById(R.id.btnBack);
        tvDetailOrderId = findViewById(R.id.tvDetailOrderId);
        tvDetailStatus = findViewById(R.id.tvDetailStatus);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailRestaurant = findViewById(R.id.tvDetailRestaurant);
        tvDetailAddress = findViewById(R.id.tvDetailAddress);
        tvDetailPayment = findViewById(R.id.tvDetailPayment);
        tvDetailItemsSummary = findViewById(R.id.tvDetailItemsSummary);
        tvDetailTotalAmount = findViewById(R.id.tvDetailTotalAmount);
        btnReorderDetail = findViewById(R.id.btnReorderDetail);

        btnBack.setOnClickListener(v -> finish());

        String orderId = getIntent() != null ? getIntent().getStringExtra("orderId") : null;
        if (orderId != null) {
            currentOrder = dbHelper.getOrderById(orderId);
            if (currentOrder != null) {
                tvDetailOrderId.setText("Đơn hàng #" + currentOrder.getOrderId());
                tvDetailStatus.setText(currentOrder.getStatus());
                tvDetailDate.setText(currentOrder.getDate());
                tvDetailRestaurant.setText(currentOrder.getRestaurantName());
                tvDetailAddress.setText("🏠 82 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM");
                tvDetailPayment.setText("💳 Thanh toán: Ví MoMo");
                tvDetailItemsSummary.setText(currentOrder.getItemsSummary());
                tvDetailTotalAmount.setText(String.format("%,.0fđ", currentOrder.getTotalAmount()));
            }
        }

        btnReorderDetail.setOnClickListener(v -> {
            if (currentOrder != null) {
                String userId = sessionManager.getUserId();
                dbHelper.clearCart(userId);
                dbHelper.addToCart(new CartItem("102", "Gà Rán Giòn Sốt Cay Ngọt", 1, Arrays.asList("2 miếng", "Sốt cay ngọt"), 45000), userId);
                Toast.makeText(this, "Đã thêm món vào giỏ hàng!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(OrderDetailActivity.this, CartCheckoutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
