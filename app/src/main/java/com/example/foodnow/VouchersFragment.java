package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class VouchersFragment extends Fragment {

    private TabLayout tabLayoutVouchers;
    private TextView tvVoucherSubtitle;
    private RecyclerView rvVouchers;
    private VoucherAdapter voucherAdapter;
    private List<Voucher> allVoucherList;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers, container, false);

        dbHelper = new DatabaseHelper(requireContext());

        tabLayoutVouchers = view.findViewById(R.id.tabLayoutVouchers);
        tvVoucherSubtitle = view.findViewById(R.id.tvVoucherSubtitle);
        rvVouchers = view.findViewById(R.id.rvVouchers);

        allVoucherList = dbHelper.getAllVouchers();
        voucherAdapter = new VoucherAdapter(allVoucherList, voucher -> {
            Toast.makeText(getContext(), "Áp dụng mã " + voucher.getCode() + " cho đơn hàng!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), CartCheckoutActivity.class);
            intent.putExtra("appliedVoucherCode", voucher.getCode());
            startActivity(intent);
        });
        rvVouchers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVouchers.setAdapter(voucherAdapter);

        filterVouchers("Tất cả");

        tabLayoutVouchers.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String filter = tab.getText() != null ? tab.getText().toString() : "Tất cả";
                filterVouchers(filter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    private void filterVouchers(String filter) {
        if (allVoucherList == null) return;
        List<Voucher> filtered = new ArrayList<>();
        if ("Freeship".equalsIgnoreCase(filter)) {
            for (Voucher v : allVoucherList) {
                if ("Freeship".equalsIgnoreCase(v.getType()) || (v.getTitle() != null && v.getTitle().toLowerCase().contains("freeship"))) {
                    filtered.add(v);
                }
            }
        } else if ("Giảm giá".equalsIgnoreCase(filter)) {
            for (Voucher v : allVoucherList) {
                if ("Giảm giá".equalsIgnoreCase(v.getType()) || (v.getTitle() != null && v.getTitle().toLowerCase().contains("giảm"))) {
                    filtered.add(v);
                }
            }
        } else {
            filtered.addAll(allVoucherList);
        }

        voucherAdapter.setVouchers(filtered);
        tvVoucherSubtitle.setText(filtered.size() + " ưu đãi dành riêng cho bạn");
    }
}
