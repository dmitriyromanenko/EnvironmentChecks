package com.dexprotector.detector.envchecks;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class MainActivity extends Activity {

    private static List<String> checkResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.text);
        checkResults.clear();
        doProbe(this);
        StringBuilder resultBuilder = new StringBuilder();
        for (String checkResult : checkResults) {
            resultBuilder.append(checkResult).append('\n');
        }

        tv.setText(resultBuilder.toString());
    }

    private static synchronized void logCheckResult(String name, Object data, boolean positive) {
        StringBuilder checkResult = new StringBuilder(name).append('\n')
                .append("Is positive: ").append(positive).append('\n')
                .append("Type: ").append( data.getClass().getCanonicalName()).append('\n')
                .append("toString() value: ").append( data.toString()).append('\n');

        if (data instanceof Properties) {
            Properties properties = (Properties) data;
            Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                String propName = (String) propertyNames.nextElement();
                checkResult.append("getProperty(").append(propName).append(")")
                        .append('=').append(properties.getProperty(propName)).append('\n');

                checkResult.append("get(").append(propName).append(")")
                        .append('=').append(properties.get(propName)).append('\n');
            }
        }

        checkResults.add(checkResult.toString());
        Log.e("MainActivity", checkResult.toString());
    }

    public void doProbe(Context ctx) {
        System.out.println("doProbe" + ctx);
    }

    public static void positiveRootCheck(Object data) {
        logCheckResult("Root", data, true);
    }

    public static void negativeRootCheck(Object data) {
        logCheckResult("Root", data, false);
    }

    public static void positiveDebugCheck(Object data) {
        logCheckResult("Debug", data, true);
    }

    public static void negativeDebugCheck(Object data) {
        logCheckResult("Debug", data, false);
    }

    public static void positiveEmulatorCheck(Object data) {
        logCheckResult("Emulator", data, true);
    }

    public static void negativeEmulatorCheck(Object data) {
        logCheckResult("Emulator", data, false);
    }

    public static void positiveXposedCheck(Object data) {
        logCheckResult("Xposed", data, true);
    }

    public static void negativeXposedCheck(Object data) {
        logCheckResult("Xposed", data, false);
    }

    public static void positiveCustomFirmwareCheck(Object data) {
        logCheckResult("CustomFirmware", data, true);
    }

    public static void negativeCustomFirmwareCheck(Object data) {
        logCheckResult("CustomFirmware", data, false);
    }

    public static void positiveIntegrityCheck(Object data) {
        logCheckResult("Integrity", data, true);
    }

    public static void negativeIntegrityCheck(Object data) {
        logCheckResult("Integrity", data, false);
    }

    public static void positiveWirelessSecurityCheck(Object data) {
        logCheckResult("WirelessSecurity", data, true);
    }

    public static void negativeWirelessSecurityCheck(Object data) {
        logCheckResult("WirelessSecurity", data, false);
    }
}
