package com.example.foodnow;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private View btnChangePassword, btnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.btnBack);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnLanguage = findViewById(R.id.btnLanguage);

        btnBack.setOnClickListener(v -> finish());

        btnChangePassword.setOnClickListener(v ->
                Toast.makeText(SettingsActivity.this, "Chức năng đổi mật khẩu đang mở", Toast.LENGTH_SHORT).show()
        );

        btnLanguage.setOnClickListener(v ->
                Toast.makeText(SettingsActivity.this, "Ứng dụng hỗ trợ Tiếng Việt", Toast.LENGTH_SHORT).show()
        );
    }
}
