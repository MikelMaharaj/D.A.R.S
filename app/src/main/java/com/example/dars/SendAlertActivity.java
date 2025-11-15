package com.example.dars;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SendAlertActivity extends AppCompatActivity {

    private Spinner spnDisasterType;
    private EditText etLocation;
    private EditText etMessage;
    private TextView tvTimer;
    private Button btnSendAlert;

    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION_MS = 45000; // 45 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);

        spnDisasterType = findViewById(R.id.spnDisasterType);
        etLocation = findViewById(R.id.etLocation);
        etMessage = findViewById(R.id.etMessage);
        tvTimer = findViewById(R.id.tvTimer);
        btnSendAlert = findViewById(R.id.btnSendAlertFinal);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.disaster_types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDisasterType.setAdapter(adapter);

        btnSendAlert.setOnClickListener(v -> sendAlert());
    }

    private void sendAlert() {
        String disasterType = spnDisasterType.getSelectedItem().toString();
        String location = etLocation.getText().toString().trim();
        String msg = etMessage.getText().toString().trim();

        if (location.isEmpty() || msg.isEmpty()) {
            Toast.makeText(this, "Location and message are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("dars_prefs", MODE_PRIVATE);
        String phoneNumber = prefs.getString("default_phone", "");

        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Set a default phone number in Settings first.", Toast.LENGTH_LONG).show();
            return;
        }

        String fullMessage = "[" + disasterType + "] " +
                "Location: " + location + " | " + msg;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, fullMessage, null, null);
            Toast.makeText(this, "Alert sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
            startTimer();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send SMS.", Toast.LENGTH_LONG).show();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(TIMER_DURATION_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Response window: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Response window expired.");
                Toast.makeText(SendAlertActivity.this,
                        "No response received within time window.",
                        Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
