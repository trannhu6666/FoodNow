package com.example.foodnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private OnCategoryClickListener listener;
    private int selectedPosition = -1;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category, int position);
    }

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (categories == null || categories.isEmpty()) return;
        Category category = categories.get(position);
        holder.tvCategoryName.setText(category.getName());

        int currentPos = holder.getAdapterPosition();

        if (currentPos == selectedPosition) {
            holder.layoutIconBg.setBackgroundResource(R.drawable.bg_chip_pink);
            holder.tvCategoryName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary_pink));
            holder.tvCategoryName.getPaint().setFakeBoldText(true);
        } else {
            holder.layoutIconBg.setBackgroundResource(R.drawable.bg_soft_pink_card);
            holder.tvCategoryName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_dark));
            holder.tvCategoryName.getPaint().setFakeBoldText(false);
        }

        int localIconRes = getCategoryDrawableRes(category.getId());
        if (localIconRes != 0) {
            holder.ivCategoryIcon.setImageResource(localIconRes);
        } else if (category.getIconUrl() != null && !category.getIconUrl().trim().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(category.getIconUrl())
                    .placeholder(R.drawable.ic_food_placeholder)
                    .error(R.drawable.ic_food_placeholder)
                    .into(holder.ivCategoryIcon);
        } else {
            holder.ivCategoryIcon.setImageResource(R.drawable.ic_food_placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            int clickedPos = holder.getAdapterPosition();
            if (selectedPosition == clickedPos) {
                selectedPosition = -1;
            } else {
                selectedPosition = clickedPos;
            }
            notifyDataSetChanged();
            if (listener != null) {
                listener.onCategoryClick(category, selectedPosition);
            }
        });
    }

    private int getCategoryDrawableRes(String id) {
        if (id == null) return 0;
        switch (id) {
            case "1": return R.drawable.ic_cat_trasua;
            case "2": return R.drawable.ic_cat_garan;
            case "3": return R.drawable.ic_cat_bunpho;
            case "4": return R.drawable.ic_cat_banhmi;
            case "5": return R.drawable.ic_cat_anvat;
            case "6": return R.drawable.ic_cat_comtam;
            case "7": return R.drawable.ic_cat_caphe;
            default: return 0;
        }
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        View layoutIconBg;
        ImageView ivCategoryIcon;
        TextView tvCategoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutIconBg = itemView.findViewById(R.id.layoutIconBg);
            ivCategoryIcon = itemView.findViewById(R.id.ivCategoryIcon);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}
