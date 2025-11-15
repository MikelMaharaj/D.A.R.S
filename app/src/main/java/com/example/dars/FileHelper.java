package com.example.dars;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    private static final String FILE_NAME = "reports.dat";

    @SuppressWarnings("unchecked")
    public static List<Report> loadReports(Context context) {
        List<Report> reports = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            reports = (List<Report>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            // First run: no file yet, return empty list
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public static void saveReports(Context context, List<Report> reports) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(reports);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addReport(Context context, Report report) {
        List<Report> reports = loadReports(context);
        reports.add(report);
        saveReports(context, reports);
    }
}
