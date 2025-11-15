package com.example.dars;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus == null) return;

                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getOriginatingAddress();
                    String body = smsMessage.getMessageBody();

                    String disasterType = detectDisasterType(body);
                    String location = extractLocation(body); // simple parsing or leave as "Unknown"

                    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault()).format(new Date());

                    Report report = new Report(disasterType, location, body, sender, timestamp);
                    FileHelper.addReport(context, report);

                    Toast.makeText(context,
                            "Disaster report received: " + disasterType,
                            Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String detectDisasterType(String body) {
        String text = body.toLowerCase();
        if (text.contains("flood")) return "FLOOD";
        if (text.contains("quake") || text.contains("earthquake")) return "EARTHQUAKE";
        if (text.contains("fire")) return "FIRE";
        if (text.contains("hurricane") || text.contains("storm")) return "HURRICANE";
        if (text.contains("landslide")) return "LANDSLIDE";
        if (text.contains("help")) return "HELP";
        return "GENERAL";
    }

    private String extractLocation(String body) {
        // For assignment simplicity, just return "Unknown" or you can parse short format like LOC:...
        return "Unknown";
    }
}