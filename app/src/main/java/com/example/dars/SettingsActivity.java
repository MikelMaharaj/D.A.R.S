package com.example.dars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private EditText etPhoneNumber;
    private Button btnSavePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSavePhone = findViewById(R.id.btnSavePhone);

        SharedPreferences prefs = getSharedPreferences("dars_prefs", MODE_PRIVATE);
        String savedPhone = prefs.getString("default_phone", "");
        etPhoneNumber.setText(savedPhone);

        btnSavePhone.setOnClickListener(v -> {
            String phone = etPhoneNumber.getText().toString().trim();
            if (phone.isEmpty()) {
                Toast.makeText(SettingsActivity.this,
                        "Phone number cannot be empty.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.edit().putString("default_phone", phone).apply();
            Toast.makeText(SettingsActivity.this,
                    "Default phone saved.",
                    Toast.LENGTH_SHORT).show();
        });
    }
}
