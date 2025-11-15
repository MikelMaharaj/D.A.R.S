package com.example.dars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_REQUEST_CODE = 101;

    private Button btnSendAlert;
    private Button btnViewReports;
    private Button btnSettings;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendAlert = findViewById(R.id.btnSendAlert);
        btnViewReports = findViewById(R.id.btnViewReports);
        btnSettings = findViewById(R.id.btnSettings);
        btnExit = findViewById(R.id.btnExit);

        btnSendAlert.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SendAlertActivity.class)));

        btnViewReports.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ReportsActivity.class)));

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        btnExit.setOnClickListener(v -> exitApp());

        checkAndRequestSmsPermissions();
    }

    private void exitApp() {
        // Required "Exit" button
        finishAffinity(); // closes all activities
        System.exit(0);
    }

    private void checkAndRequestSmsPermissions() {
        boolean send = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED;
        boolean receive = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                == PackageManager.PERMISSION_GRANTED;
        boolean read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED;

        if (!send || !receive || !read) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.READ_SMS
                    },
                    SMS_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (!allGranted) {
                Toast.makeText(this,
                        "SMS permissions are required for this app to work.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
