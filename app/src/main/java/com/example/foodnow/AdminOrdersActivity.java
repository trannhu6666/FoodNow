package com.example.foodnow;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminOrdersActivity extends AppCompatActivity implements OrderHistoryAdapter.OnOrderActionListener {

    private ImageButton btnBack;
    private RecyclerView rvAdminOrders;

    private OrderHistoryAdapter orderAdapter;
    private List<Order> orderList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        rvAdminOrders = findViewById(R.id.rvAdminOrders);

        btnBack.setOnClickListener(v -> finish());

        rvAdminOrders.setLayoutManager(new LinearLayoutManager(this));

        loadOrders();
    }

    private void loadOrders() {
        orderList = dbHelper.getAllOrdersAdmin();
        orderAdapter = new OrderHistoryAdapter(orderList, this);
        rvAdminOrders.setAdapter(orderAdapter);
    }

    @Override
    public void onDeleteOrder(Order order) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa đơn hàng")
                .setMessage("Bạn có chắc muốn xóa đơn hàng #" + order.getOrderId() + " khỏi hệ thống?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteOrder(order.getOrderId());
                    if (deleted) {
                        Toast.makeText(this, "Đã xóa đơn hàng!", Toast.LENGTH_SHORT).show();
                        loadOrders();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onReorder(Order order) {
        showStatusUpdateDialog(order);
    }

    @Override
    public void onOrderClick(Order order) {
        showStatusUpdateDialog(order);
    }

    private void showStatusUpdateDialog(Order order) {
        String[] statuses = {
                "🚚 Đang đến (Đang giao hàng)",
                "✅ Đã hoàn thành (Giao hàng thành công)",
                "❌ Đã hủy đơn hàng"
        };

        new AlertDialog.Builder(this)
                .setTitle("Cập nhật trạng thái đơn #" + order.getOrderId())
                .setItems(statuses, (dialog, which) -> {
                    String newStatus;
                    if (which == 0) {
                        newStatus = "Đang đến";
                    } else if (which == 1) {
                        newStatus = "Đã hoàn thành";
                    } else {
                        newStatus = "Đã hủy";
                    }

                    boolean updated = dbHelper.updateOrderStatus(order.getOrderId(), newStatus);
                    if (updated) {
                        Toast.makeText(this, "Đã chuyển đơn #" + order.getOrderId() + " thành: " + newStatus, Toast.LENGTH_SHORT).show();
                        loadOrders();
                    } else {
                        Toast.makeText(this, "Lỗi khi cập nhật trạng thái đơn hàng", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Đóng", null)
                .show();
    }
}
