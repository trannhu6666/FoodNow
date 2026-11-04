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

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    private List<Food> menuItems;

    public MenuItemAdapter(List<Food> menuItems) {
        this.menuItems = menuItems;
    }

    public void setMenuItems(List<Food> menuItems) {
        this.menuItems = menuItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_food, parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        if (menuItems == null || menuItems.isEmpty()) return;
        Food food = menuItems.get(position);
        holder.tvMenuItemName.setText(food.getName());
        holder.tvMenuItemDescription.setText(food.getDescription());
        holder.tvMenuItemPrice.setText(String.format("%,.0fđ", food.getPrice()));

        int localDrawable = getFoodDrawableRes(food.getId());
        if (food.getImageUrl() != null && !food.getImageUrl().trim().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(food.getImageUrl())
                    .placeholder(localDrawable != 0 ? localDrawable : R.drawable.ic_food_placeholder)
                    .error(localDrawable != 0 ? localDrawable : R.drawable.ic_food_placeholder)
                    .centerCrop()
                    .into(holder.ivMenuItemImage);
        } else if (localDrawable != 0) {
            holder.ivMenuItemImage.setImageResource(localDrawable);
        } else {
            holder.ivMenuItemImage.setImageResource(R.drawable.ic_food_placeholder);
        }
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
            default: return 0;
        }
    }

    @Override
    public int getItemCount() {
        return menuItems != null ? menuItems.size() : 0;
    }

    static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMenuItemImage;
        TextView tvMenuItemName, tvMenuItemDescription, tvMenuItemPrice;
        ImageButton btnAddMenuItem;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenuItemImage = itemView.findViewById(R.id.ivMenuItemImage);
            tvMenuItemName = itemView.findViewById(R.id.tvMenuItemName);
            tvMenuItemDescription = itemView.findViewById(R.id.tvMenuItemDescription);
            tvMenuItemPrice = itemView.findViewById(R.id.tvMenuItemPrice);
            btnAddMenuItem = itemView.findViewById(R.id.btnAddMenuItem);
        }
    }
}
