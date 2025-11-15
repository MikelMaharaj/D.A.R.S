package com.example.dars;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import android.widget.ArrayAdapter;

public class ReportsActivity extends AppCompatActivity {

    private ListView lvReports;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        lvReports = findViewById(R.id.lvReports);
        tvEmpty = findViewById(R.id.tvEmptyReports);

        List<Report> reports = FileHelper.loadReports(this);

        if (reports.isEmpty()) {
            tvEmpty.setText("No reports found.");
        } else {
            tvEmpty.setText("");
        }

        ArrayAdapter<Report> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                reports
        );
        lvReports.setAdapter(adapter);
    }
}
