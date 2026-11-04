package com.example.foodnow;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantProfileActivity extends AppCompatActivity {

    private ImageButton btnBack, btnFavorite;
    private TextView tvRestaurantName, tvRatingDistance;
    private RecyclerView rvMenu;
    private MenuItemAdapter menuItemAdapter;
    private List<Food> menuList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        btnFavorite = findViewById(R.id.btnFavorite);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvRatingDistance = findViewById(R.id.tvRatingDistance);
        rvMenu = findViewById(R.id.rvMenu);

        btnBack.setOnClickListener(v -> finish());

        menuList = dbHelper.getAllFoods();
        menuItemAdapter = new MenuItemAdapter(menuList);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(menuItemAdapter);
    }
}
