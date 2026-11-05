package com.example.foodnow;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminFoodsActivity extends AppCompatActivity implements AdminFoodAdapter.OnAdminFoodActionListener {

    private ImageButton btnBack;
    private RecyclerView rvAdminFoods;
    private FloatingActionButton fabAddFood;

    private AdminFoodAdapter adminFoodAdapter;
    private List<Food> foodList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_foods);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        rvAdminFoods = findViewById(R.id.rvAdminFoods);
        fabAddFood = findViewById(R.id.fabAddFood);

        btnBack.setOnClickListener(v -> finish());

        rvAdminFoods.setLayoutManager(new GridLayoutManager(this, 2));

        fabAddFood.setOnClickListener(v -> showFoodFormDialog(null));

        loadFoods();
    }

    private void loadFoods() {
        foodList = dbHelper.getAllFoods();
        adminFoodAdapter = new AdminFoodAdapter(foodList, this);
        rvAdminFoods.setAdapter(adminFoodAdapter);
    }

    @Override
    public void onEdit(Food food) {
        showFoodFormDialog(food);
    }

    @Override
    public void onDelete(Food food) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa món ăn")
                .setMessage("Bạn có chắc chắn muốn xóa món \"" + food.getName() + "\" khỏi thực đơn?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteFood(food.getId());
                    if (deleted) {
                        FirebaseDatabase.getInstance().getReference("foods").child(food.getId()).removeValue();
                        Toast.makeText(this, "Đã xóa món ăn thành công!", Toast.LENGTH_SHORT).show();
                        loadFoods();
                    } else {
                        Toast.makeText(this, "Không thể xóa món ăn", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showFoodFormDialog(Food existingFood) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_food, null);
        bottomSheetDialog.setContentView(dialogView);

        TextView tvAddFoodTitle = dialogView.findViewById(R.id.tvAddFoodTitle);
        ImageButton btnCloseDialog = dialogView.findViewById(R.id.btnCloseDialog);
        EditText etFoodNameInput = dialogView.findViewById(R.id.etFoodNameInput);
        EditText etRestNameInput = dialogView.findViewById(R.id.etRestNameInput);
        EditText etFoodPriceInput = dialogView.findViewById(R.id.etFoodPriceInput);
        EditText etFoodCatInput = dialogView.findViewById(R.id.etFoodCatInput);
        EditText etFoodDescInput = dialogView.findViewById(R.id.etFoodDescInput);
        Button btnSaveFood = dialogView.findViewById(R.id.btnSaveFood);

        btnCloseDialog.setOnClickListener(v -> bottomSheetDialog.dismiss());

        if (existingFood != null) {
            tvAddFoodTitle.setText("Chỉnh Sửa Món Ăn");
            etFoodNameInput.setText(existingFood.getName());
            etRestNameInput.setText(existingFood.getRestaurantName());
            etFoodPriceInput.setText(String.valueOf((long) existingFood.getPrice()));
            etFoodCatInput.setText(existingFood.getCategoryId());
            etFoodDescInput.setText(existingFood.getDescription());
            btnSaveFood.setText("LƯU THAY ĐỔI");
        } else {
            tvAddFoodTitle.setText("Thêm Món Ăn Mới");
            btnSaveFood.setText("THÊM MÓN ĂN");
        }

        btnSaveFood.setOnClickListener(v -> {
            String name = etFoodNameInput.getText().toString().trim();
            String restName = etRestNameInput.getText().toString().trim();
            String priceStr = etFoodPriceInput.getText().toString().trim();
            String catId = etFoodCatInput.getText().toString().trim();
            String desc = etFoodDescInput.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
                Toast.makeText(this, "Vui lòng nhập tên món và giá tiền!", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            if (TextUtils.isEmpty(catId)) catId = "1";
            if (TextUtils.isEmpty(restName)) restName = "Cửa hàng FoodNow";
            if (TextUtils.isEmpty(desc)) desc = "Món ăn ngon hấp dẫn do Quản trị viên cập nhật";

            String id = existingFood != null ? existingFood.getId() : String.valueOf(System.currentTimeMillis() % 100000);
            Food food = new Food(id, name, desc, price, "https://images.unsplash.com/photo-1558857563-b371033873b8?w=500", 4.9, catId, restName, "1.0 km", "15 phút");

            boolean success = dbHelper.addFood(food);
            if (success) {
                FirebaseDatabase.getInstance().getReference("foods").child(id).setValue(food);
                Toast.makeText(this, existingFood != null ? "Cập nhật món ăn thành công!" : "Thêm món mới thành công!", Toast.LENGTH_SHORT).show();
                loadFoods();
                bottomSheetDialog.dismiss();
            } else {
                Toast.makeText(this, "Lỗi khi lưu món ăn", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDialog.show();
    }
}
