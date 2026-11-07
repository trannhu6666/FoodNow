package com.example.foodnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private List<Order> orders;
    private OnOrderActionListener listener;

    public interface OnOrderActionListener {
        void onDeleteOrder(Order order);
        void onReorder(Order order);
        void onOrderClick(Order order);
    }

    public OrderHistoryAdapter(List<Order> orders) {
        this.orders = orders;
    }

    public OrderHistoryAdapter(List<Order> orders, OnOrderActionListener listener) {
        this.orders = orders;
        this.listener = listener;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        if (orders == null || orders.isEmpty()) return;
        Order order = orders.get(position);
        holder.tvOrderRestaurantName.setText(order.getRestaurantName());
        holder.tvOrderDate.setText(order.getDate());
        holder.tvOrderItemsSummary.setText(order.getItemsSummary());
        holder.tvOrderTotalPrice.setText(String.format("%,.0fđ", order.getTotalAmount()));
        holder.tvOrderStatus.setText(order.getStatus());

        String summary = order.getItemsSummary() != null ? order.getItemsSummary().toLowerCase() : "";
        if (summary.contains("trà sữa")) {
            holder.ivOrderFoodImage.setImageResource(R.drawable.ic_food_milktea);
        } else if (summary.contains("gà")) {
            holder.ivOrderFoodImage.setImageResource(R.drawable.ic_food_chicken);
        } else if (summary.contains("phở")) {
            holder.ivOrderFoodImage.setImageResource(R.drawable.ic_food_pho);
        } else if (summary.contains("bánh mì")) {
            holder.ivOrderFoodImage.setImageResource(R.drawable.ic_food_banhmi);
        } else if (summary.contains("cơm")) {
            holder.ivOrderFoodImage.setImageResource(R.drawable.ic_food_comtam);
        } else {
            holder.ivOrderFoodImage.setImageResource(R.drawable.ic_food_combo);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onOrderClick(order);
        });

        holder.btnDeleteOrder.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteOrder(order);
        });

        holder.btnReorder.setOnClickListener(v -> {
            if (listener != null) listener.onReorder(order);
        });
    }

    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderRestaurantName, tvOrderDate, tvOrderItemsSummary, tvOrderTotalPrice, tvOrderStatus;
        ImageView ivOrderFoodImage;
        ImageButton btnDeleteOrder;
        Button btnReorder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderRestaurantName = itemView.findViewById(R.id.tvOrderRestaurantName);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderItemsSummary = itemView.findViewById(R.id.tvOrderItemsSummary);
            tvOrderTotalPrice = itemView.findViewById(R.id.tvOrderTotalPrice);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            ivOrderFoodImage = itemView.findViewById(R.id.ivOrderFoodImage);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
            btnReorder = itemView.findViewById(R.id.btnReorder);
        }
    }
}
