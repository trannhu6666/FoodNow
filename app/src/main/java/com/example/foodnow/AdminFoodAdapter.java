package com.example.foodnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.AdminFoodViewHolder> {

    private List<Food> foods;
    private OnAdminFoodActionListener listener;

    public interface OnAdminFoodActionListener {
        void onEdit(Food food);
        void onDelete(Food food);
    }

    public AdminFoodAdapter(List<Food> foods, OnAdminFoodActionListener listener) {
        this.foods = foods;
        this.listener = listener;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_food, parent, false);
        return new AdminFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodViewHolder holder, int position) {
        if (foods == null || foods.isEmpty()) return;
        Food food = foods.get(position);

        holder.tvFoodName.setText(food.getName());
        holder.tvRestaurantName.setText(food.getRestaurantName());
        holder.tvFoodPrice.setText(String.format("%,.0fđ", food.getPrice()));

        if (food.getImageUrl() != null && !food.getImageUrl().trim().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(food.getImageUrl())
                    .placeholder(R.drawable.ic_food_placeholder)
                    .error(R.drawable.ic_food_placeholder)
                    .centerCrop()
                    .into(holder.ivFoodImage);
        } else {
            holder.ivFoodImage.setImageResource(R.drawable.ic_food_placeholder);
        }

        holder.btnAdminEditFood.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(food);
        });

        holder.btnAdminDeleteFood.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(food);
        });
    }

    @Override
    public int getItemCount() {
        return foods != null ? foods.size() : 0;
    }

    static class AdminFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoodImage;
        TextView tvFoodName, tvRestaurantName, tvFoodPrice;
        ImageButton btnAdminEditFood, btnAdminDeleteFood;

        public AdminFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            btnAdminEditFood = itemView.findViewById(R.id.btnAdminEditFood);
            btnAdminDeleteFood = itemView.findViewById(R.id.btnAdminDeleteFood);
        }
    }
}
