package com.example.foodnow;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText txtRegisterName, txtRegisterPhone, txtRegisterPassword, txtRegisterConfirmPassword;
    private Button btnRegisterSubmit;
    private TextView tvLoginRedirect;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        txtRegisterName = findViewById(R.id.txtRegisterName);
        txtRegisterPhone = findViewById(R.id.txtRegisterPhone);
        txtRegisterPassword = findViewById(R.id.txtRegisterPassword);
        txtRegisterConfirmPassword = findViewById(R.id.txtRegisterConfirmPassword);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect);

        btnBack.setOnClickListener(v -> finish());
        tvLoginRedirect.setOnClickListener(v -> finish());

        btnRegisterSubmit.setOnClickListener(v -> {
            String name = txtRegisterName.getText().toString().trim();
            String phone = txtRegisterPhone.getText().toString().trim();
            String password = txtRegisterPassword.getText().toString().trim();
            String confirmPassword = txtRegisterConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                txtRegisterName.setError("Vui lòng nhập họ và tên");
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                txtRegisterPhone.setError("Vui lòng nhập số điện thoại");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                txtRegisterPassword.setError("Vui lòng nhập mật khẩu");
                return;
            }
            if (!password.equals(confirmPassword)) {
                txtRegisterConfirmPassword.setError("Mật khẩu xác nhận không trùng khớp!");
                return;
            }

            boolean registered = dbHelper.registerUser(name, phone, password);
            if (registered) {
                Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công! Vui lòng đăng nhập.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Số điện thoại đã được đăng ký! Vui lòng đăng nhập.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
