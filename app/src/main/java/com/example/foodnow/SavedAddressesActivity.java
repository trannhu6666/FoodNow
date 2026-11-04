package com.example.foodnow;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedAddressesActivity extends AppCompatActivity implements AddressAdapter.OnAddressActionListener {

    private ImageButton btnBack;
    private RecyclerView rvSavedAddresses;
    private Button btnAddNewAddress;

    private AddressAdapter addressAdapter;
    private List<Address> addressList;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_addresses);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        btnBack = findViewById(R.id.btnBack);
        rvSavedAddresses = findViewById(R.id.rvSavedAddresses);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);

        btnBack.setOnClickListener(v -> finish());

        rvSavedAddresses.setLayoutManager(new LinearLayoutManager(this));

        btnAddNewAddress.setOnClickListener(v -> {
            Intent intent = new Intent(SavedAddressesActivity.this, AddAddressActivity.class);
            startActivity(intent);
        });

        loadAddresses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAddresses();
    }

    private void loadAddresses() {
        addressList = dbHelper.getAddressesByUser(sessionManager.getUserId());
        if (addressAdapter == null) {
            addressAdapter = new AddressAdapter(addressList, this);
            rvSavedAddresses.setAdapter(addressAdapter);
        } else {
            addressAdapter.setAddresses(addressList);
        }
    }

    @Override
    public void onDelete(Address address) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa địa chỉ")
                .setMessage("Bạn có chắc muốn xóa địa chỉ " + address.getTitle() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteAddress(address.getId());
                    if (deleted) {
                        Toast.makeText(this, "Đã xóa địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                        loadAddresses();
                    } else {
                        Toast.makeText(this, "Lỗi khi xóa địa chỉ", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onEdit(Address address) {
        Intent intent = new Intent(SavedAddressesActivity.this, AddAddressActivity.class);
        intent.putExtra("editAddressId", address.getId());
        startActivity(intent);
    }

    @Override
    public void onSetDefault(Address address) {
        boolean updated = dbHelper.setDefaultAddress(address.getId(), sessionManager.getUserId());
        if (updated) {
            Toast.makeText(this, "Đã chọn " + address.getTitle() + " làm địa chỉ mặc định!", Toast.LENGTH_SHORT).show();
            loadAddresses();
        }
    }
}
