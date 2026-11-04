package com.example.foodnow;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderHistoryFragment extends Fragment implements OrderHistoryAdapter.OnOrderActionListener {

    private TabLayout tabLayoutOrders;
    private RecyclerView rvOrderHistory;
    private OrderHistoryAdapter orderHistoryAdapter;
    private List<Order> allOrderList;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private String currentTabFilter = "Đang đến";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        dbHelper = new DatabaseHelper(requireContext());
        sessionManager = new SessionManager(requireContext());

        tabLayoutOrders = view.findViewById(R.id.tabLayoutOrders);
        rvOrderHistory = view.findViewById(R.id.rvOrderHistory);

        rvOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchAndFilterOrders(currentTabFilter);

        tabLayoutOrders.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTabFilter = tab.getText() != null ? tab.getText().toString() : "Đang đến";
                fetchAndFilterOrders(currentTabFilter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    private void fetchAndFilterOrders(String tabFilter) {
        allOrderList = dbHelper.getOrdersByUser(sessionManager.getUserId(), "Tất cả");
        List<Order> filtered = new ArrayList<>();

        if ("Đang đến".equalsIgnoreCase(tabFilter)) {
            for (Order o : allOrderList) {
                if ("Đang đến".equalsIgnoreCase(o.getStatus()) || "Đang xử lý".equalsIgnoreCase(o.getStatus())) {
                    filtered.add(o);
                }
            }
        } else if ("Lịch sử".equalsIgnoreCase(tabFilter)) {
            for (Order o : allOrderList) {
                if ("Đã hoàn thành".equalsIgnoreCase(o.getStatus()) || "Đã hủy".equalsIgnoreCase(o.getStatus())) {
                    filtered.add(o);
                }
            }
        } else {
            filtered.addAll(allOrderList);
        }

        if (orderHistoryAdapter == null) {
            orderHistoryAdapter = new OrderHistoryAdapter(filtered, this);
            rvOrderHistory.setAdapter(orderHistoryAdapter);
        } else {
            orderHistoryAdapter.setOrders(filtered);
        }
    }

    @Override
    public void onDeleteOrder(Order order) {
        if (getContext() == null) return;

        new AlertDialog.Builder(getContext())
                .setTitle("Xóa đơn hàng")
                .setMessage("Bạn có chắc chắn muốn xóa đơn hàng #" + order.getOrderId() + " khỏi danh sách?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteOrder(order.getOrderId());
                    if (deleted) {
                        Toast.makeText(getContext(), "Đã xóa đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                        fetchAndFilterOrders(currentTabFilter);
                    } else {
                        Toast.makeText(getContext(), "Không thể xóa đơn hàng", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onReorder(Order order) {
        String userId = sessionManager.getUserId();

        dbHelper.clearCart(userId);

        if (order.getItemsSummary() != null && order.getItemsSummary().toLowerCase().contains("trà sữa")) {
            dbHelper.addToCart(new CartItem("101", "Trà Sữa Trân Châu Hoàng Gia", 2, Arrays.asList("50% đường", "Đá bình thường"), 70000), userId);
        } else if (order.getItemsSummary() != null && order.getItemsSummary().toLowerCase().contains("phở")) {
            dbHelper.addToCart(new CartItem("106", "Phở Bò Tái Nạm Hà Nội", 1, Arrays.asList("Nhiều hành", "Nước béo"), 55000), userId);
        } else {
            dbHelper.addToCart(new CartItem("102", "Gà Rán Giòn Sốt Cay Ngọt", 1, Arrays.asList("2 miếng", "Sốt cay ngọt"), 45000), userId);
            dbHelper.addToCart(new CartItem("104", "Khoai Tây Chiên Lắc Phô Mai", 1, Arrays.asList("Phô mai đậm đà", "Giòn rụm"), 25000), userId);
        }

        Toast.makeText(getContext(), "Đã thêm các món của đơn #" + order.getOrderId() + " vào giỏ hàng!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), CartCheckoutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onOrderClick(Order order) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("orderId", order.getOrderId());
        startActivity(intent);
    }
}
