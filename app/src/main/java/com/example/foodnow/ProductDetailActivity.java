package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageButton btnBack, btnShare, btnDecreaseQty, btnIncreaseQty;
    private TextView tvFoodTitle, tvFoodRating, tvFoodPrice, tvFoodDescription, tvQuantity;
    private CheckBox cbTopping1, cbTopping2, cbTopping3;
    private RadioGroup rgSugarLevel, rgIceLevel;
    private Button btnAddToCart;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    private String foodId;
    private Food currentFood;
    private double basePrice = 35000;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        if (getIntent() != null && getIntent().hasExtra("foodId")) {
            foodId = getIntent().getStringExtra("foodId");
        } else {
            foodId = "101";
        }

        currentFood = dbHelper.getFoodById(foodId);
        if (currentFood != null) {
            basePrice = currentFood.getPrice();
        }

        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        btnDecreaseQty = findViewById(R.id.btnDecreaseQty);
        btnIncreaseQty = findViewById(R.id.btnIncreaseQty);

        tvFoodTitle = findViewById(R.id.tvFoodTitle);
        tvFoodRating = findViewById(R.id.tvFoodRating);
        tvFoodPrice = findViewById(R.id.tvFoodPrice);
        tvFoodDescription = findViewById(R.id.tvFoodDescription);
        tvQuantity = findViewById(R.id.tvQuantity);

        cbTopping1 = findViewById(R.id.cbTopping1);
        cbTopping2 = findViewById(R.id.cbTopping2);
        cbTopping3 = findViewById(R.id.cbTopping3);

        rgSugarLevel = findViewById(R.id.rgSugarLevel);
        rgIceLevel = findViewById(R.id.rgIceLevel);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        if (currentFood != null) {
            tvFoodTitle.setText(currentFood.getName());
            tvFoodRating.setText(String.valueOf(currentFood.getRating()));
            tvFoodPrice.setText(String.format("%,.0fđ", currentFood.getPrice()));
            tvFoodDescription.setText(currentFood.getDescription());
        }

        btnBack.setOnClickListener(v -> finish());
        btnShare.setOnClickListener(v -> Toast.makeText(this, "Chia sẻ món ăn", Toast.LENGTH_SHORT).show());

        cbTopping1.setOnCheckedChangeListener((buttonView, isChecked) -> calculateCurrentPrice());
        cbTopping2.setOnCheckedChangeListener((buttonView, isChecked) -> calculateCurrentPrice());
        cbTopping3.setOnCheckedChangeListener((buttonView, isChecked) -> calculateCurrentPrice());

        btnDecreaseQty.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                calculateCurrentPrice();
            }
        });

        btnIncreaseQty.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            calculateCurrentPrice();
        });

        btnAddToCart.setOnClickListener(v -> {
            List<String> toppings = new ArrayList<>();
            if (cbTopping1.isChecked()) toppings.add("Trân châu đen");
            if (cbTopping2.isChecked()) toppings.add("Trân châu trắng");
            if (cbTopping3.isChecked()) toppings.add("Thạch phô mai");

            double itemTotal = getUnitPrice() * quantity;
            CartItem cartItem = new CartItem(foodId, currentFood != null ? currentFood.getName() : "Trà Sữa", quantity, toppings, itemTotal);
            dbHelper.addToCart(cartItem, sessionManager.getUserId());

            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProductDetailActivity.this, CartCheckoutActivity.class);
            startActivity(intent);
        });

        calculateCurrentPrice();
    }

    private double getUnitPrice() {
        double unit = basePrice;
        if (cbTopping1.isChecked()) unit += 5000;
        if (cbTopping2.isChecked()) unit += 5000;
        if (cbTopping3.isChecked()) unit += 7000;
        return unit;
    }

    private void calculateCurrentPrice() {
        double totalPrice = getUnitPrice() * quantity;
        btnAddToCart.setText(String.format("Thêm vào giỏ - %,.0fđ", totalPrice));
    }
}
