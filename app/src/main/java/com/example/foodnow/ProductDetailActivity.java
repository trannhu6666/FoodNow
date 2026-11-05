package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
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
    private RadioButton rbSugar0, rbSugar30, rbSugar50, rbSugar100;
    private RadioButton rbIceNone, rbIceLess, rbIceNormal;
    private Button btnAddToCart;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    private String foodId;
    private Food currentFood;
    private double basePrice = 35000;
    private int quantity = 1;
    private double topping1Price = 5000;
    private double topping2Price = 5000;
    private double topping3Price = 7000;

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

        rbSugar0 = findViewById(R.id.rbSugar0);
        rbSugar30 = findViewById(R.id.rbSugar30);
        rbSugar50 = findViewById(R.id.rbSugar50);
        rbSugar100 = findViewById(R.id.rbSugar100);

        rbIceNone = findViewById(R.id.rbIceNone);
        rbIceLess = findViewById(R.id.rbIceLess);
        rbIceNormal = findViewById(R.id.rbIceNormal);

        btnAddToCart = findViewById(R.id.btnAddToCart);

        setupFoodCategoryOptions();

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
            if (cbTopping1.isChecked()) toppings.add(cbTopping1.getText().toString());
            if (cbTopping2.isChecked()) toppings.add(cbTopping2.getText().toString());
            if (cbTopping3.isChecked()) toppings.add(cbTopping3.getText().toString());

            double itemTotal = getUnitPrice() * quantity;
            CartItem cartItem = new CartItem(foodId, currentFood != null ? currentFood.getName() : "Món ngon", quantity, toppings, itemTotal);
            dbHelper.addToCart(cartItem, sessionManager.getUserId());

            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProductDetailActivity.this, CartCheckoutActivity.class);
            startActivity(intent);
        });

        calculateCurrentPrice();
    }

    private void setupFoodCategoryOptions() {
        if (currentFood == null) return;

        String catId = currentFood.getCategoryId();
        if ("2".equals(catId)) { // Gà rán
            cbTopping1.setText("Thêm Phô mai tuyết (+10.000đ)");
            topping1Price = 10000;
            cbTopping2.setText("Thêm Khoai tây chiên nhỏ (+15.000đ)");
            topping2Price = 15000;
            cbTopping3.setText("Thêm 1 miếng Gà giòn (+22.000đ)");
            topping3Price = 22000;

            rbSugar0.setText("Sốt Cay");
            rbSugar30.setText("Sốt Phô mai");
            rbSugar50.setText("Sốt Bơ tỏi");
            rbSugar100.setText("Sốt Hàn Quốc");

            rbIceNone.setText("Không nước");
            rbIceLess.setText("Pepsi");
            rbIceNormal.setText("7Up");
        } else if ("3".equals(catId) || "4".equals(catId) || "6".equals(catId)) { // Bún phở / Bánh mì / Cơm tấm
            cbTopping1.setText("Thêm Trứng ốp la / Trứng cút (+7.000đ)");
            topping1Price = 7000;
            cbTopping2.setText("Thêm Thịt tái / Sườn nướng (+15.000đ)");
            topping2Price = 15000;
            cbTopping3.setText("Thêm Quẩy giòn / Bánh mì phụ (+5.000đ)");
            topping3Price = 5000;

            rbSugar0.setText("Không cay");
            rbSugar30.setText("Ít cay");
            rbSugar50.setText("Cay vừa");
            rbSugar100.setText("Cay nhiều");

            rbIceNone.setText("Không hành");
            rbIceLess.setText("Nhiều hành");
            rbIceNormal.setText("Tiêu chuẩn");
        } else if ("5".equals(catId)) { // Ăn vặt
            cbTopping1.setText("Thêm Bột phô mai lắc (+5.000đ)");
            topping1Price = 5000;
            cbTopping2.setText("Thêm Sốt mayonaise (+5.000đ)");
            topping2Price = 5000;
            cbTopping3.setText("Thêm Trứng cút rán (+10.000đ)");
            topping3Price = 10000;

            rbSugar0.setText("Tương ớt");
            rbSugar30.setText("Tương cà");
            rbSugar50.setText("Sốt bơ");
            rbSugar100.setText("Sốt mắm");

            rbIceNone.setText("Nóng hổi");
            rbIceLess.setText("Để riêng sốt");
            rbIceNormal.setText("Tiêu chuẩn");
        } else { // Trà sữa / Cà phê
            cbTopping1.setText("Trân châu đen (+5.000đ)");
            topping1Price = 5000;
            cbTopping2.setText("Trân châu trắng (+5.000đ)");
            topping2Price = 5000;
            cbTopping3.setText("Kem cheese béo ngậy (+7.000đ)");
            topping3Price = 7000;

            rbSugar0.setText("0%");
            rbSugar30.setText("30%");
            rbSugar50.setText("50%");
            rbSugar100.setText("100%");

            rbIceNone.setText("Không đá");
            rbIceLess.setText("Ít đá");
            rbIceNormal.setText("Bình thường");
        }
    }

    private double getUnitPrice() {
        double unit = basePrice;
        if (cbTopping1.isChecked()) unit += topping1Price;
        if (cbTopping2.isChecked()) unit += topping2Price;
        if (cbTopping3.isChecked()) unit += topping3Price;
        return unit;
    }

    private void calculateCurrentPrice() {
        double totalPrice = getUnitPrice() * quantity;
        btnAddToCart.setText(String.format("Thêm vào giỏ - %,.0fđ", totalPrice));
    }
}
