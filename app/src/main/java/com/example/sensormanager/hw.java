package com.example.sensormanager;

import android.app.ActivityManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Console;
import java.text.DecimalFormat;
import java.text.Format;

public class hw extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

                setContentView(R.layout.x_hw_view);
        TextView j_hw_tv = (TextView)findViewById(R.id.x_hw_tv);
        ActivityManager actManager = (ActivityManager) hw.this.getSystemService(hw.this.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        //totalMemory  = totalMemory/1000000000;
       // DecimalFormat decimalFormat = new DecimalFormat("#.####");
//        long totalMemoryf = Long.valueOf(decimalFormat.format(totalMemory));

        String details = "VERSION.RELEASE : " + Build.VERSION.RELEASE
                + "\nVERSION.INCREMENTAL : " + Build.VERSION.INCREMENTAL
                + "\nVERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT
                + "\nBOARD : " + Build.BOARD
                + "\nBOOTLOADER : " + Build.BOOTLOADER
                + "\nBRAND : " + Build.BRAND
                + "\nDISPLAY : " + Build.DISPLAY
                + "\nFINGERPRINT : " + Build.FINGERPRINT
                + "\nHARDWARE : " + Build.HARDWARE
                + "\nHOST : " + Build.HOST
                + "\nID : " + Build.ID
                + "\nMANUFACTURER : " + Build.MANUFACTURER
                + "\nMODEL : " + Build.MODEL
                + "\nPRODUCT : " + Build.PRODUCT
                + "\nTAGS : " + Build.TAGS
                + "\nTIME : " + Build.TIME
                + "\nTYPE : " + Build.TYPE
                + "\nTotal Memory : " +  totalMemory + " Bytes"
                + "\nUSER : " + Build.USER;
                j_hw_tv.setText(details);
    }

}
