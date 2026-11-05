package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class CartCheckoutActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private View btnSelectVoucher, layoutDeliveryAddress;
    private TextView tvDeliveryLocation, tvCartItemsTitle, tvVoucherTitle, tvSubtotal, tvDeliveryFee, tvDiscount, tvTotalAmount;
    private RadioGroup rgPaymentMethod;
    private RadioButton rbMoMo;
    private Button btnPlaceOrder;
    private List<CartItem> cartItemList;
    private List<Voucher> voucherList;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private Address selectedDeliveryAddress;

    private double subtotal = 0;
    private double deliveryFee = 15000;
    private double discountAmount = 0;
    private double finalTotal = 0;
    private String selectedVoucherCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        btnBack = findViewById(R.id.btnBack);
        rvCartItems = findViewById(R.id.rvCartItems);
        btnSelectVoucher = findViewById(R.id.btnSelectVoucher);
        layoutDeliveryAddress = findViewById(R.id.layoutDeliveryAddress);
        tvDeliveryLocation = findViewById(R.id.tvDeliveryLocation);
        tvCartItemsTitle = findViewById(R.id.tvCartItemsTitle);
        tvVoucherTitle = findViewById(R.id.tvVoucherTitle);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        rbMoMo = findViewById(R.id.rbMoMo);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        btnBack.setOnClickListener(v -> finish());
        btnSelectVoucher.setOnClickListener(v -> showVoucherSelectionDialog());

        if (layoutDeliveryAddress != null) {
            layoutDeliveryAddress.setOnClickListener(v -> showAddressSelectionDialog());
        }

        loadCartFromDatabase();

        if (getIntent() != null && getIntent().hasExtra("appliedVoucherCode")) {
            String initialCode = getIntent().getStringExtra("appliedVoucherCode");
            applyVoucherCodeDirectly(initialCode);
        }

        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartFromDatabase();

        selectedDeliveryAddress = dbHelper.getDefaultAddress(sessionManager.getUserId());
        if (selectedDeliveryAddress != null && tvDeliveryLocation != null) {
            tvDeliveryLocation.setText("Giao đến " + selectedDeliveryAddress.getTitle() + " (" + selectedDeliveryAddress.getFullAddress() + ")");
        }
    }

    private void showAddressSelectionDialog() {
        List<Address> addressList = dbHelper.getAddressesByUser(sessionManager.getUserId());
        if (addressList == null || addressList.isEmpty()) {
            Toast.makeText(this, "Vui lòng thêm địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddAddressActivity.class);
            startActivity(intent);
            return;
        }

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_select_address, null);
        bottomSheetDialog.setContentView(dialogView);

        ImageButton btnCloseDialog = dialogView.findViewById(R.id.btnCloseDialog);
        RecyclerView rvDialogAddresses = dialogView.findViewById(R.id.rvDialogAddresses);
        Button btnAddNewAddressDialog = dialogView.findViewById(R.id.btnAddNewAddressDialog);

        btnCloseDialog.setOnClickListener(v -> bottomSheetDialog.dismiss());

        AddressAdapter dialogAdapter = new AddressAdapter(addressList, new AddressAdapter.OnAddressActionListener() {
            @Override
            public void onDelete(Address address) {
                dbHelper.deleteAddress(address.getId());
                showAddressSelectionDialog();
            }

            @Override
            public void onEdit(Address address) {
                Intent intent = new Intent(CartCheckoutActivity.this, AddAddressActivity.class);
                intent.putExtra("editAddressId", address.getId());
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onSetDefault(Address address) {
                dbHelper.setDefaultAddress(address.getId(), sessionManager.getUserId());
                selectedDeliveryAddress = address;
                if (tvDeliveryLocation != null) {
                    tvDeliveryLocation.setText("Giao đến " + address.getTitle() + " (" + address.getFullAddress() + ")");
                }
                Toast.makeText(CartCheckoutActivity.this, "Đã chọn " + address.getTitle(), Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        rvDialogAddresses.setLayoutManager(new LinearLayoutManager(this));
        rvDialogAddresses.setAdapter(dialogAdapter);

        btnAddNewAddressDialog.setOnClickListener(v -> {
            Intent intent = new Intent(CartCheckoutActivity.this, AddAddressActivity.class);
            startActivity(intent);
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void showVoucherSelectionDialog() {
        voucherList = dbHelper.getAllVouchers();
        if (voucherList == null || voucherList.isEmpty()) {
            Toast.makeText(this, "Không có mã giảm giá khả dụng", Toast.LENGTH_SHORT).show();
            return;
        }

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_select_voucher, null);
        bottomSheetDialog.setContentView(dialogView);

        ImageButton btnCloseDialog = dialogView.findViewById(R.id.btnCloseDialog);
        EditText etVoucherCodeInput = dialogView.findViewById(R.id.etVoucherCodeInput);
        Button btnApplyInputCode = dialogView.findViewById(R.id.btnApplyInputCode);
        RecyclerView rvDialogVouchers = dialogView.findViewById(R.id.rvDialogVouchers);

        btnCloseDialog.setOnClickListener(v -> bottomSheetDialog.dismiss());

        VoucherAdapter dialogAdapter = new VoucherAdapter(voucherList, voucher -> {
            boolean success = applyVoucher(voucher);
            if (success) {
                bottomSheetDialog.dismiss();
            }
        });
        rvDialogVouchers.setLayoutManager(new LinearLayoutManager(this));
        rvDialogVouchers.setAdapter(dialogAdapter);

        btnApplyInputCode.setOnClickListener(v -> {
            String code = etVoucherCodeInput.getText().toString().trim();
            if (code.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã giảm giá", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean success = applyVoucherCodeDirectly(code);
            if (success) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private boolean applyVoucher(Voucher voucher) {
        calculateSubtotal();
        if (subtotal < voucher.getMinSpend()) {
            Toast.makeText(this, String.format("Đơn hàng tối thiểu %,.0fđ để áp dụng mã %s!", voucher.getMinSpend(), voucher.getCode()), Toast.LENGTH_LONG).show();
            return false;
        }

        selectedVoucherCode = voucher.getCode();
        discountAmount = voucher.getDiscountValue();
        tvVoucherTitle.setText(String.format("Mã: %s (-%,.0fđ)", voucher.getCode(), discountAmount));
        updateTotalsAndUI();
        Toast.makeText(this, "Đã áp dụng mã " + voucher.getCode(), Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean applyVoucherCodeDirectly(String code) {
        calculateSubtotal();
        Voucher matchedVoucher = null;
        if (voucherList != null) {
            for (Voucher v : voucherList) {
                if (v.getCode().equalsIgnoreCase(code)) {
                    matchedVoucher = v;
                    break;
                }
            }
        }

        if (matchedVoucher != null) {
            return applyVoucher(matchedVoucher);
        } else {
            double min = 100000;
            double disc = 20000;
            if ("FOODNOW50".equalsIgnoreCase(code) || "DIEM50".equalsIgnoreCase(code)) {
                min = 150000;
                disc = 50000;
            } else if ("FREESHIP25".equalsIgnoreCase(code) || "FREESHIP800".equalsIgnoreCase(code)) {
                min = 80000;
                disc = 25000;
            }

            if (subtotal < min) {
                Toast.makeText(this, String.format("Đơn hàng tối thiểu %,.0fđ để áp dụng mã %s!", min, code.toUpperCase()), Toast.LENGTH_LONG).show();
                return false;
            }

            selectedVoucherCode = code;
            discountAmount = disc;
            tvVoucherTitle.setText(String.format("Mã: %s (-%,.0fđ)", code.toUpperCase(), discountAmount));
            updateTotalsAndUI();
            Toast.makeText(this, "Đã áp dụng mã " + code.toUpperCase(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void calculateSubtotal() {
        subtotal = 0;
        if (cartItemList != null) {
            for (CartItem item : cartItemList) {
                subtotal += item.getTotalPrice();
            }
        }
    }

    private void loadCartFromDatabase() {
        cartItemList = dbHelper.getCartItems(sessionManager.getUserId());
        int itemCount = cartItemList != null ? cartItemList.size() : 0;
        if (tvCartItemsTitle != null) {
            if (itemCount > 0) {
                tvCartItemsTitle.setText("Món ngon đã chọn (" + itemCount + " món)");
            } else {
                tvCartItemsTitle.setText("Giỏ hàng của bạn đang trống");
            }
        }

        cartAdapter = new CartAdapter(cartItemList, new CartAdapter.OnCartItemClickListener() {
            @Override
            public void onDeleteItem(CartItem item) {
                dbHelper.deleteCartItem(sessionManager.getUserId(), item.getFoodId());
                Toast.makeText(CartCheckoutActivity.this, "Đã xóa món khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                loadCartFromDatabase();
            }

            @Override
            public void onEditItem(CartItem item) {
                Intent intent = new Intent(CartCheckoutActivity.this, ProductDetailActivity.class);
                intent.putExtra("foodId", item.getFoodId());
                startActivity(intent);
            }
        });
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(cartAdapter);

        updateTotalsAndUI();
    }

    private void updateTotalsAndUI() {
        calculateSubtotal();

        deliveryFee = (cartItemList == null || cartItemList.isEmpty()) ? 0 : 15000;
        double effectiveDiscount = (cartItemList == null || cartItemList.isEmpty()) ? 0 : discountAmount;
        finalTotal = Math.max(0, subtotal + deliveryFee - effectiveDiscount);

        tvSubtotal.setText(String.format("%,.0fđ", subtotal));
        tvDeliveryFee.setText(String.format("%,.0fđ", deliveryFee));
        tvDiscount.setText(String.format("-%,.0fđ", effectiveDiscount));
        tvTotalAmount.setText(String.format("%,.0fđ", finalTotal));

        btnPlaceOrder.setText(String.format("ĐẶT ĐƠN NGAY (%,.0fđ)", finalTotal));
    }

    private void placeOrder() {
        if (cartItemList == null || cartItemList.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng của bạn đang trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        String orderId = "NF" + System.currentTimeMillis() % 1000000;
        String payment = rbMoMo.isChecked() ? "Ví MoMo" : "Tiền mặt";
        String summary = cartItemList.size() + " món ngon đã chọn";
        String date = "Hôm nay • " + android.text.format.DateFormat.format("HH:mm", new java.util.Date());

        Address defaultAddr = selectedDeliveryAddress != null ? selectedDeliveryAddress : dbHelper.getDefaultAddress(sessionManager.getUserId());
        String addressText = defaultAddr != null ? defaultAddr.getFullAddress() : "Quận 1, TP.HCM";

        Order order = new Order(orderId, "Chicken House - Quận 1", summary, "Đang đến", finalTotal, date);
        boolean created = dbHelper.createOrder(order, sessionManager.getUserId(), payment, addressText);

        if (created) {
            // Loyalty points earning rule: +10 points for orders >= 100,000đ
            if (finalTotal >= 100000) {
                User user = sessionManager.getLoggedInUser();
                if (user != null) {
                    int newPoints = user.getPoints() + 10;
                    user.setPoints(newPoints);
                    sessionManager.createLoginSession(user);
                    dbHelper.updateUserPoints(user.getId(), newPoints);
                    Toast.makeText(this, "Đơn hàng trên 100.000đ: Bạn được cộng +10 điểm tích lũy!", Toast.LENGTH_LONG).show();
                }
            }

            Intent intent = new Intent(CartCheckoutActivity.this, OrderSuccessActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi tạo đơn hàng!", Toast.LENGTH_SHORT).show();
        }
    }
}
