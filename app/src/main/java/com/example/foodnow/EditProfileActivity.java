package com.example.foodnow;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText txtFullName, txtPhone, txtEmail, txtAddress;
    private Button btnSaveProfile;

    private SessionManager sessionManager;
    private DatabaseHelper dbHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = new SessionManager(this);
        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        txtFullName = findViewById(R.id.txtFullName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        btnBack.setOnClickListener(v -> finish());

        currentUser = sessionManager.getLoggedInUser();
        if (currentUser != null) {
            txtFullName.setText(currentUser.getName());
            txtPhone.setText(currentUser.getPhone());
            txtEmail.setText("nhungoc@gmail.com");
            txtAddress.setText("82 Nguyễn Thị Minh Khai, Quận 3, TP.HCM");
        }

        btnSaveProfile.setOnClickListener(v -> {
            String name = txtFullName.getText().toString().trim();
            String phone = txtPhone.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                Toast.makeText(EditProfileActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            currentUser.setName(name);
            currentUser.setPhone(phone);
            sessionManager.createLoginSession(currentUser);
            dbHelper.updateUserProfile(currentUser.getId(), name, phone);

            Toast.makeText(EditProfileActivity.this, "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });
    }
}
