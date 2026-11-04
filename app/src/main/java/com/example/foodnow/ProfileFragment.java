package com.example.foodnow;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        String[] options = {
                "🎁 Mã giảm 30.000đ (Cần 500 điểm)",
                "🎁 Mã Freeship 25.000đ (Cần 800 điểm)",
                "🎁 Mã giảm 50.000đ (Cần 1000 điểm)"
        };

        new AlertDialog.Builder(getContext())
                .setTitle("Đổi điểm nhận Voucher (Đang có " + currentUser.getPoints() + " điểm)")
                .setItems(options, (dialog, which) -> {
                    int cost = which == 0 ? 500 : (which == 1 ? 800 : 1000);
                    String title = which == 0 ? "Giảm 30.000đ" : (which == 1 ? "Freeship 25.000đ" : "Giảm 50.000đ");
                    String desc = which == 0 ? "Đổi bằng 500 điểm • Đơn từ 100k" : (which == 1 ? "Đổi bằng 800 điểm • Freeship" : "Đổi bằng 1000 điểm • Đơn từ 150k");
                    String code = which == 0 ? "DIEM30" : (which == 1 ? "FREESHIP800" : "DIEM50");
                    String type = which == 1 ? "Freeship" : "Giảm giá";
                    double minSpend = which == 0 ? 100000.0 : (which == 1 ? 80000.0 : 150000.0);
                    double discVal = which == 0 ? 30000.0 : (which == 1 ? 25000.0 : 50000.0);

                    if (currentUser.getPoints() >= cost) {
                        int remainingPoints = currentUser.getPoints() - cost;
                        currentUser.setPoints(remainingPoints);
                        sessionManager.createLoginSession(currentUser);
                        dbHelper.updateUserPoints(currentUser.getId(), remainingPoints);

                        Voucher newVoucher = new Voucher("v_" + System.currentTimeMillis(), title, desc, "31/12/2025", code, type, minSpend, discVal);
                        dbHelper.insertVoucher(newVoucher);

                        Toast.makeText(getContext(), "Đổi voucher " + code + " thành công! Đã thêm vào Ưu đãi.", Toast.LENGTH_LONG).show();
                        bindUserData(currentUser);
                    } else {
                        Toast.makeText(getContext(), "Bạn không đủ điểm! Đang có " + currentUser.getPoints() + " điểm.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Đóng", null)
                .show();
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
