package com.example.foodnow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HelpCenterActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnCallHotline, btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        btnBack = findViewById(R.id.btnBack);
        btnCallHotline = findViewById(R.id.btnCallHotline);
        btnSendEmail = findViewById(R.id.btnSendEmail);

        btnBack.setOnClickListener(v -> finish());

        btnCallHotline.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:19001234"));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Đang kết nối Hotline 1900 1234", Toast.LENGTH_SHORT).show();
            }
        });

        btnSendEmail.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:hotro@foodnow.vn"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hỗ trợ ứng dụng FoodNow");
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Email CSKH: hotro@foodnow.vn", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
