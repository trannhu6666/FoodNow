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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foods;
    private OnFoodItemClickListener listener;

    public interface OnFoodItemClickListener {
        void onFoodClick(Food food);
        void onAddClick(Food food);
    }

    public FoodAdapter(List<Food> foods, OnFoodItemClickListener listener) {
        this.foods = foods;
        this.listener = listener;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_recommend, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        if (foods == null || foods.isEmpty()) return;
        Food food = foods.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodRating.setText(String.valueOf(food.getRating()));
        holder.tvFoodPrice.setText(String.format("%,.0fđ", food.getPrice()));
        holder.tvRestaurantName.setText(food.getRestaurantName());
        holder.tvRestaurantMeta.setText("• " + food.getDistance() + " • " + food.getDeliveryTime());

        int localDrawable = getFoodDrawableRes(food.getId());
        if (food.getImageUrl() != null && !food.getImageUrl().trim().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(food.getImageUrl())
                    .placeholder(localDrawable != 0 ? localDrawable : R.drawable.ic_food_placeholder)
                    .error(localDrawable != 0 ? localDrawable : R.drawable.ic_food_placeholder)
                    .centerCrop()
                    .into(holder.ivFoodImage);
        } else if (localDrawable != 0) {
            holder.ivFoodImage.setImageResource(localDrawable);
        } else {
            holder.ivFoodImage.setImageResource(R.drawable.ic_food_placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onFoodClick(food);
        });

        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) listener.onAddClick(food);
        });
    }

    private int getFoodDrawableRes(String id) {
        if (id == null) return 0;
        switch (id) {
            case "101": return R.drawable.ic_food_milktea;
            case "102": return R.drawable.ic_food_chicken;
            case "103": return R.drawable.ic_food_combo;
            case "104": return R.drawable.ic_food_fries;
            case "105": return R.drawable.ic_food_banhmi;
            case "106": return R.drawable.ic_food_pho;
            case "107": return R.drawable.ic_food_comtam;
            case "108": return R.drawable.ic_food_coffee;
            case "109": return R.drawable.ic_food_trasua_thaido;
            case "110": return R.drawable.ic_food_matcha;
            case "111": return R.drawable.ic_food_chicken;
            case "112": return R.drawable.ic_food_bunbo;
            case "113": return R.drawable.ic_food_bundau;
            case "114": return R.drawable.ic_food_banhmipate;
            case "115": return R.drawable.ic_food_banhmixiumai;
            case "116": return R.drawable.ic_food_nemchua;
            case "117": return R.drawable.ic_food_banhtrang;
            default: return 0;
        }
    }

    @Override
    public int getItemCount() {
        return foods != null ? foods.size() : 0;
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoodImage;
        TextView tvFoodName, tvFoodRating, tvFoodPrice, tvRestaurantName, tvRestaurantMeta;
        ImageButton btnAdd;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodRating = itemView.findViewById(R.id.tvFoodRating);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvRestaurantMeta = itemView.findViewById(R.id.tvRestaurantMeta);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
