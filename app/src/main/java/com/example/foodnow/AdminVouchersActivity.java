package com.example.foodnow;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminVouchersActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvAdminVouchers;
    private FloatingActionButton fabAddVoucher;

    private VoucherAdapter voucherAdapter;
    private List<Voucher> voucherList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_vouchers);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        rvAdminVouchers = findViewById(R.id.rvAdminVouchers);
        fabAddVoucher = findViewById(R.id.fabAddVoucher);

        btnBack.setOnClickListener(v -> finish());

        rvAdminVouchers.setLayoutManager(new LinearLayoutManager(this));

        fabAddVoucher.setOnClickListener(v -> showAddVoucherDialog());

        loadVouchers();
    }

    private void loadVouchers() {
        voucherList = dbHelper.getAllVouchers();
        voucherAdapter = new VoucherAdapter(voucherList, voucher ->
                Toast.makeText(this, "Mã: " + voucher.getCode() + " • Giảm: " + String.format("%,.0fđ", voucher.getDiscountValue()), Toast.LENGTH_SHORT).show()
        );
        rvAdminVouchers.setAdapter(voucherAdapter);
    }

    private void showAddVoucherDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_voucher, null);
        bottomSheetDialog.setContentView(dialogView);

        ImageButton btnCloseDialog = dialogView.findViewById(R.id.btnCloseDialog);
        EditText etVoucherCodeInput = dialogView.findViewById(R.id.etVoucherCodeInput);
        EditText etVoucherTitleInput = dialogView.findViewById(R.id.etVoucherTitleInput);
        EditText etVoucherDescInput = dialogView.findViewById(R.id.etVoucherDescInput);
        EditText etMinSpendInput = dialogView.findViewById(R.id.etMinSpendInput);
        EditText etDiscountValueInput = dialogView.findViewById(R.id.etDiscountValueInput);
        Button btnSaveVoucher = dialogView.findViewById(R.id.btnSaveVoucher);

        btnCloseDialog.setOnClickListener(v -> bottomSheetDialog.dismiss());

        btnSaveVoucher.setOnClickListener(v -> {
            String code = etVoucherCodeInput.getText().toString().trim().toUpperCase();
            String title = etVoucherTitleInput.getText().toString().trim();
            String desc = etVoucherDescInput.getText().toString().trim();
            String minStr = etMinSpendInput.getText().toString().trim();
            String discStr = etDiscountValueInput.getText().toString().trim();

            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(title) || TextUtils.isEmpty(minStr) || TextUtils.isEmpty(discStr)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin mã giảm giá!", Toast.LENGTH_SHORT).show();
                return;
            }

            double minSpend = Double.parseDouble(minStr);
            double discountVal = Double.parseDouble(discStr);

            Voucher newVoucher = new Voucher("v_" + System.currentTimeMillis(), title, desc, "31/12/2025", code, "Giảm giá", minSpend, discountVal);
            boolean inserted = dbHelper.insertVoucher(newVoucher);

            if (inserted) {
                Toast.makeText(this, "Tạo mã giảm giá " + code + " thành công!", Toast.LENGTH_SHORT).show();
                loadVouchers();
                bottomSheetDialog.dismiss();
            } else {
                Toast.makeText(this, "Lỗi khi tạo mã giảm giá", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDialog.show();
    }
}
