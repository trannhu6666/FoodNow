package com.example.foodnow;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText txtPhone, txtPassword;
    private ImageView btnTogglePassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvRegister;
    private boolean isPasswordVisible = false;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);

        btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        btnLogin.setOnClickListener(v -> {
            String phone = txtPhone.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                txtPhone.setError("Vui lòng nhập số điện thoại");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                txtPassword.setError("Vui lòng nhập mật khẩu");
                return;
            }

            performLogin(phone, password);
        });

        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "Chức năng Quên mật khẩu đang phát triển", Toast.LENGTH_SHORT).show()
        );

        tvRegister.setOnClickListener(v -> {
            boolean registered = dbHelper.registerUser("Ngọc Như", "0901234567", "123456");
            if (registered) {
                Toast.makeText(LoginActivity.this, "Tạo tài khoản thành công! Đăng nhập ngay", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Tài khoản đã tồn tại. Đăng nhập ngay!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btnTogglePassword.setImageResource(R.drawable.ic_eye);
            isPasswordVisible = false;
        } else {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btnTogglePassword.setImageResource(R.drawable.ic_eye);
            isPasswordVisible = true;
        }
        txtPassword.setSelection(txtPassword.getText().length());
    }

    private void performLogin(String phone, String password) {
        User user = dbHelper.checkLogin(phone, password);
        if (user != null) {
            sessionManager.createLoginSession(user);
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Số điện thoại hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }
}
