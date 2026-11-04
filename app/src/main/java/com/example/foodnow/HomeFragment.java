package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements FoodAdapter.OnFoodItemClickListener {

    private RecyclerView rvCategories, rvRecommendedFoods;
    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodAdapter;
    private List<Category> categoryList;
    private List<Food> allFoodList;
    private EditText etSearch;
    private TextView tvSeeAllRecommended;
    private View btnCartHeader;
    private DatabaseHelper dbHelper;

    private DatabaseReference categoriesRef, foodsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbHelper = new DatabaseHelper(requireContext());

        rvCategories = view.findViewById(R.id.rvCategories);
        rvRecommendedFoods = view.findViewById(R.id.rvRecommendedFoods);
        etSearch = view.findViewById(R.id.etSearch);
        tvSeeAllRecommended = view.findViewById(R.id.tvSeeAllRecommended);
        btnCartHeader = view.findViewById(R.id.btnCartHeader);

        allFoodList = new ArrayList<>();
        categoryList = new ArrayList<>();

        // Category RecyclerView with Click Handler to Filter Foods
        categoryAdapter = new CategoryAdapter(categoryList, (category, selectedPos) -> {
            if (selectedPos == -1) {
                foodAdapter.setFoods(allFoodList);
            } else {
                List<Food> filtered = new ArrayList<>();
                for (Food f : allFoodList) {
                    if (category.getId().equals(f.getCategoryId())) {
                        filtered.add(f);
                    }
                }
                foodAdapter.setFoods(filtered);
            }
        });
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(categoryAdapter);

        // Recommended Foods RecyclerView
        foodAdapter = new FoodAdapter(new ArrayList<>(), this);
        rvRecommendedFoods.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvRecommendedFoods.setAdapter(foodAdapter);
        rvRecommendedFoods.setNestedScrollingEnabled(false);

        // Search Bar Listener
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // "Xem tất cả" button -> Reset search & filters, display all foods
        tvSeeAllRecommended.setOnClickListener(v -> {
            categoryAdapter.setSelectedPosition(-1);
            etSearch.setText("");
            foodAdapter.setFoods(allFoodList);
            Toast.makeText(getContext(), "Hiển thị tất cả " + allFoodList.size() + " món ăn", Toast.LENGTH_SHORT).show();
        });

        // Cart Header Button
        if (btnCartHeader != null) {
            btnCartHeader.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), CartCheckoutActivity.class);
                startActivity(intent);
            });
        }

        // Fetch Data from Firebase Realtime Database
        fetchCategoriesFromFirebase();
        fetchFoodsFromFirebase();

        return view;
    }

    private void filterSearch(String query) {
        if (query.trim().isEmpty()) {
            foodAdapter.setFoods(allFoodList);
            return;
        }
        List<Food> filtered = new ArrayList<>();
        for (Food f : allFoodList) {
            if (f.getName().toLowerCase().contains(query.toLowerCase()) ||
                    (f.getDescription() != null && f.getDescription().toLowerCase().contains(query.toLowerCase())) ||
                    (f.getRestaurantName() != null && f.getRestaurantName().toLowerCase().contains(query.toLowerCase()))) {
                filtered.add(f);
            }
        }
        foodAdapter.setFoods(filtered);
    }

    private void fetchCategoriesFromFirebase() {
        categoriesRef = FirebaseDatabase.getInstance().getReference("categories");
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Category category = data.getValue(Category.class);
                        if (category != null) {
                            categoryList.add(category);
                        }
                    }
                }
                if (categoryList.size() < 7) {
                    categoryList = dbHelper.getAllCategories();
                    for (Category c : categoryList) {
                        categoriesRef.child(c.getId()).setValue(c);
                    }
                }
                categoryAdapter.setCategories(categoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                categoryList = dbHelper.getAllCategories();
                categoryAdapter.setCategories(categoryList);
            }
        });
    }

    private void fetchFoodsFromFirebase() {
        foodsRef = FirebaseDatabase.getInstance().getReference("foods");
        foodsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allFoodList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Food food = data.getValue(Food.class);
                        if (food != null) {
                            allFoodList.add(food);
                        }
                    }
                }
                if (allFoodList.size() < 15) {
                    allFoodList = dbHelper.getAllFoods();
                    for (Food f : allFoodList) {
                        foodsRef.child(f.getId()).setValue(f);
                    }
                }
                foodAdapter.setFoods(allFoodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                allFoodList = dbHelper.getAllFoods();
                foodAdapter.setFoods(allFoodList);
            }
        });
    }

    @Override
    public void onFoodClick(Food food) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("foodId", food.getId());
        startActivity(intent);
    }

    @Override
    public void onAddClick(Food food) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("foodId", food.getId());
        startActivity(intent);
    }
}
