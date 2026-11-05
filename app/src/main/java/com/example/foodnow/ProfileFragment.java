package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ProfileFragment extends Fragment {

    private TextView tvUserName, tvUserPhone, tvUserPoints;
    private TextView btnEditProfile;
    private View layoutPointsCard, btnSavedAddresses, btnPaymentMethods, btnHelpCenter, btnSettings;
    private Button btnLogout;

    private SessionManager sessionManager;
    private DatabaseHelper dbHelper;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(requireContext());
        dbHelper = new DatabaseHelper(requireContext());

        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserPhone = view.findViewById(R.id.tvUserPhone);
        tvUserPoints = view.findViewById(R.id.tvUserPoints);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        layoutPointsCard = view.findViewById(R.id.tvUserPoints) != null ? (View) view.findViewById(R.id.tvUserPoints).getParent().getParent() : null;
        btnSavedAddresses = view.findViewById(R.id.btnSavedAddresses);
        btnPaymentMethods = view.findViewById(R.id.btnPaymentMethods);
        btnHelpCenter = view.findViewById(R.id.btnHelpCenter);
        btnSettings = view.findViewById(R.id.btnSettings);
        btnLogout = view.findViewById(R.id.btnLogout);

        currentUser = sessionManager.getLoggedInUser();
        bindUserData(currentUser);

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        if (layoutPointsCard != null) {
            layoutPointsCard.setOnClickListener(v -> showRedeemVoucherDialog());
        }

        btnSavedAddresses.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SavedAddressesActivity.class);
            startActivity(intent);
        });

        btnPaymentMethods.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PaymentMethodsActivity.class);
            startActivity(intent);
        });

        btnHelpCenter.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HelpCenterActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(getContext(), "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void showRedeemVoucherDialog() {
        if (getContext() == null || currentUser == null) return;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_redeem_voucher, null);
        bottomSheetDialog.setContentView(dialogView);

        ImageButton btnCloseDialog = dialogView.findViewById(R.id.btnCloseDialog);
        TextView tvUserPointsBalance = dialogView.findViewById(R.id.tvUserPointsBalance);
        Button btnRedeem500 = dialogView.findViewById(R.id.btnRedeem500);
        Button btnRedeem800 = dialogView.findViewById(R.id.btnRedeem800);
        Button btnRedeem1000 = dialogView.findViewById(R.id.btnRedeem1000);

        btnCloseDialog.setOnClickListener(v -> bottomSheetDialog.dismiss());
        if (tvUserPointsBalance != null) {
            tvUserPointsBalance.setText("Điểm tích lũy hiện có: " + currentUser.getPoints() + " điểm");
        }

        btnRedeem500.setOnClickListener(v -> redeemPoints(500, "Giảm 30.000đ", "Đổi bằng 500 điểm • Đơn từ 100k", "DIEM30", "Giảm giá", 100000, 30000, bottomSheetDialog));
        btnRedeem800.setOnClickListener(v -> redeemPoints(800, "Freeship 25.000đ", "Đổi bằng 800 điểm • Freeship", "FREESHIP800", "Freeship", 80000, 25000, bottomSheetDialog));
        btnRedeem1000.setOnClickListener(v -> redeemPoints(1000, "Giảm 50.000đ", "Đổi bằng 1000 điểm • Đơn từ 150k", "DIEM50", "Giảm giá", 150000, 50000, bottomSheetDialog));

        bottomSheetDialog.show();
    }

    private void redeemPoints(int cost, String title, String desc, String code, String type, double minSpend, double discountVal, BottomSheetDialog dialog) {
        if (currentUser.getPoints() >= cost) {
            int remainingPoints = currentUser.getPoints() - cost;
            currentUser.setPoints(remainingPoints);
            sessionManager.createLoginSession(currentUser);
            dbHelper.updateUserPoints(currentUser.getId(), remainingPoints);

            Voucher newVoucher = new Voucher("v_" + System.currentTimeMillis(), title, desc, "31/12/2025", code, type, minSpend, discountVal);
            dbHelper.insertVoucher(newVoucher);

            Toast.makeText(getContext(), "Đổi voucher " + code + " thành công! Đã thêm vào Ưu đãi.", Toast.LENGTH_LONG).show();
            bindUserData(currentUser);
            dialog.dismiss();
        } else {
            Toast.makeText(getContext(), "Bạn chưa đủ điểm! Cần " + cost + " điểm (Hiện có " + currentUser.getPoints() + " điểm).", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sessionManager != null) {
            currentUser = sessionManager.getLoggedInUser();
            bindUserData(currentUser);
        }
    }

    private void bindUserData(User user) {
        if (user != null) {
            tvUserName.setText(user.getName() != null && !user.getName().isEmpty() ? user.getName() : "Ngọc Như");
            tvUserPhone.setText(user.getPhone() != null && !user.getPhone().isEmpty() ? user.getPhone() : "0901234567");
            tvUserPoints.setText(user.getPoints() + " điểm");
        }
    }
}
