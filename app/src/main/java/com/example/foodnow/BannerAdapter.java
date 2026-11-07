package com.example.foodnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private List<Banner> banners;

    public BannerAdapter(List<Banner> banners) {
        this.banners = banners;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        if (banners == null || banners.isEmpty()) return;
        Banner banner = banners.get(position);
        holder.tvBannerTitle.setText(banner.getTitle());
        holder.tvBannerDesc.setText(banner.getDescription());
        holder.ivBannerArt.setImageResource(banner.getImageResId());

        holder.btnBannerAction.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Khám phá khuyến mãi: " + banner.getTitle(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return banners != null ? banners.size() : 0;
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        TextView tvBannerTitle, tvBannerDesc;
        ImageView ivBannerArt;
        Button btnBannerAction;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBannerTitle = itemView.findViewById(R.id.tvBannerTitle);
            tvBannerDesc = itemView.findViewById(R.id.tvBannerDesc);
            ivBannerArt = itemView.findViewById(R.id.ivBannerArt);
            btnBannerAction = itemView.findViewById(R.id.btnBannerAction);
        }
    }
}
