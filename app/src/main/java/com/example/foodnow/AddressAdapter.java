package com.example.foodnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addresses;
    private OnAddressActionListener listener;

    public interface OnAddressActionListener {
        void onDelete(Address address);
        void onEdit(Address address);
        void onSetDefault(Address address);
    }

    public AddressAdapter(List<Address> addresses, OnAddressActionListener listener) {
        this.addresses = addresses;
        this.listener = listener;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        if (addresses == null || addresses.isEmpty()) return;
        Address address = addresses.get(position);

        holder.tvAddressTitle.setText(address.getTitle());
        holder.tvFullAddress.setText(address.getFullAddress());
        holder.tvReceiverInfo.setText("Người nhận: " + address.getReceiverName() + " • " + address.getReceiverPhone());
        holder.tvDefaultTag.setVisibility(address.isDefault() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onSetDefault(address);
        });

        holder.btnEditAddress.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(address);
        });

        holder.btnDeleteAddress.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(address);
        });
    }

    @Override
    public int getItemCount() {
        return addresses != null ? addresses.size() : 0;
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddressTitle, tvDefaultTag, tvFullAddress, tvReceiverInfo;
        ImageButton btnEditAddress, btnDeleteAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddressTitle = itemView.findViewById(R.id.tvAddressTitle);
            tvDefaultTag = itemView.findViewById(R.id.tvDefaultTag);
            tvFullAddress = itemView.findViewById(R.id.tvFullAddress);
            tvReceiverInfo = itemView.findViewById(R.id.tvReceiverInfo);
            btnEditAddress = itemView.findViewById(R.id.btnEditAddress);
            btnDeleteAddress = itemView.findViewById(R.id.btnDeleteAddress);
        }
    }
}
