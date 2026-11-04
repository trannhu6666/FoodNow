package com.example.foodnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    private List<Voucher> vouchers;
    private OnVoucherClickListener listener;

    public interface OnVoucherClickListener {
        void onUseVoucher(Voucher voucher);
    }

    public VoucherAdapter(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public VoucherAdapter(List<Voucher> vouchers, OnVoucherClickListener listener) {
        this.vouchers = vouchers;
        this.listener = listener;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        if (vouchers == null || vouchers.isEmpty()) return;
        Voucher voucher = vouchers.get(position);
        holder.tvVoucherTitle.setText(voucher.getTitle());
        holder.tvVoucherDescription.setText(voucher.getDescription());
        holder.tvVoucherExpiryCode.setText("HSD: " + voucher.getExpiryDate() + " • Mã: " + voucher.getCode());

        holder.btnUseVoucher.setOnClickListener(v -> {
            if (listener != null) listener.onUseVoucher(voucher);
        });
    }

    @Override
    public int getItemCount() {
        return vouchers != null ? vouchers.size() : 0;
    }

    static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView tvVoucherTitle, tvVoucherDescription, tvVoucherExpiryCode;
        Button btnUseVoucher;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVoucherTitle = itemView.findViewById(R.id.tvVoucherTitle);
            tvVoucherDescription = itemView.findViewById(R.id.tvVoucherDescription);
            tvVoucherExpiryCode = itemView.findViewById(R.id.tvVoucherExpiryCode);
            btnUseVoucher = itemView.findViewById(R.id.btnUseVoucher);
        }
    }
}
