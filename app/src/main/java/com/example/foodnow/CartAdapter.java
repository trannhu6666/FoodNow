package com.example.foodnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnCartItemClickListener listener;

    public interface OnCartItemClickListener {
        void onDeleteItem(CartItem item);
        void onEditItem(CartItem item);
    }

    public CartAdapter(List<CartItem> cartItems, OnCartItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        if (cartItems == null || cartItems.isEmpty()) return;
        CartItem item = cartItems.get(position);
        holder.tvCartItemName.setText(item.getQuantity() + "x " + item.getFoodName());
        if (item.getSelectedToppings() != null && !item.getSelectedToppings().isEmpty()) {
            holder.tvCartItemOptions.setText(String.join(" • ", item.getSelectedToppings()));
        } else {
            holder.tvCartItemOptions.setText("Tiêu chuẩn");
        }
        holder.tvCartItemPrice.setText(String.format("%,.0fđ", item.getTotalPrice()));

        int localDrawable = getFoodDrawableRes(item.getFoodId());
        if (localDrawable != 0) {
            holder.ivCartItemImage.setImageResource(localDrawable);
        } else {
            holder.ivCartItemImage.setImageResource(R.drawable.ic_food_placeholder);
        }

        holder.btnDeleteCartItem.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteItem(item);
        });

        holder.btnEditCartItem.setOnClickListener(v -> {
            if (listener != null) listener.onEditItem(item);
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
        return cartItems != null ? cartItems.size() : 0;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCartItemImage;
        TextView tvCartItemName, tvCartItemOptions, tvCartItemPrice;
        ImageButton btnEditCartItem, btnDeleteCartItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCartItemImage = itemView.findViewById(R.id.ivCartItemImage);
            tvCartItemName = itemView.findViewById(R.id.tvCartItemName);
            tvCartItemOptions = itemView.findViewById(R.id.tvCartItemOptions);
            tvCartItemPrice = itemView.findViewById(R.id.tvCartItemPrice);
            btnEditCartItem = itemView.findViewById(R.id.btnEditCartItem);
            btnDeleteCartItem = itemView.findViewById(R.id.btnDeleteCartItem);
        }
    }
}
