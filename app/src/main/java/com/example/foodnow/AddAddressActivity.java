package com.example.foodnow;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class AddAddressActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvHeaderTitle;
    private EditText etAddressTitle, etFullAddress, etReceiverName, etReceiverPhone;
    private SwitchCompat switchDefaultAddress;
    private Button btnSaveAddress;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private String editAddressId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        btnBack = findViewById(R.id.btnBack);
        tvHeaderTitle = findViewById(R.id.layoutHeader) != null ? (TextView) findViewById(R.id.layoutHeader).findViewById(android.R.id.text1) : null;
        etAddressTitle = findViewById(R.id.etAddressTitle);
        etFullAddress = findViewById(R.id.etFullAddress);
        etReceiverName = findViewById(R.id.etReceiverName);
        etReceiverPhone = findViewById(R.id.etReceiverPhone);
        switchDefaultAddress = findViewById(R.id.switchDefaultAddress);
        btnSaveAddress = findViewById(R.id.btnSaveAddress);

        btnBack.setOnClickListener(v -> finish());

        if (getIntent() != null && getIntent().hasExtra("editAddressId")) {
            editAddressId = getIntent().getStringExtra("editAddressId");
            Address existing = dbHelper.getAddressById(editAddressId);
            if (existing != null) {
                if (tvHeaderTitle != null) tvHeaderTitle.setText("Chỉnh sửa địa chỉ");
                String titleClean = existing.getTitle() != null ? existing.getTitle().replace("🏠 ", "").replace("🏢 ", "").replace("📍 ", "") : "";
                etAddressTitle.setText(titleClean);
                etFullAddress.setText(existing.getFullAddress());
                etReceiverName.setText(existing.getReceiverName());
                etReceiverPhone.setText(existing.getReceiverPhone());
                switchDefaultAddress.setChecked(existing.isDefault());
            }
        } else {
            User user = sessionManager.getLoggedInUser();
            if (user != null) {
                etReceiverName.setText(user.getName());
                etReceiverPhone.setText(user.getPhone());
            }
        }

        btnSaveAddress.setOnClickListener(v -> {
            String title = etAddressTitle.getText().toString().trim();
            String fullAddr = etFullAddress.getText().toString().trim();
            String recName = etReceiverName.getText().toString().trim();
            String recPhone = etReceiverPhone.getText().toString().trim();
            boolean isDefault = switchDefaultAddress.isChecked();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(fullAddr) || TextUtils.isEmpty(recName) || TextUtils.isEmpty(recPhone)) {
                Toast.makeText(AddAddressActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = editAddressId != null ? editAddressId : "addr_" + System.currentTimeMillis();
            Address address = new Address(id, "📍 " + title, fullAddr, recName, recPhone, isDefault);

            boolean success;
            if (editAddressId != null) {
                success = dbHelper.updateAddress(address, sessionManager.getUserId());
            } else {
                success = dbHelper.addAddress(address, sessionManager.getUserId());
            }

            if (success) {
                Toast.makeText(AddAddressActivity.this, editAddressId != null ? "Cập nhật địa chỉ thành công!" : "Đã lưu địa chỉ mới thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddAddressActivity.this, "Lỗi khi lưu địa chỉ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
