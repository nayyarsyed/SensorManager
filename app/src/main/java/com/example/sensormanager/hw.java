package com.example.sensormanager;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.os.HardwarePropertiesManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;


public class hw extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.x_hw_view);
        TextView j_hw_tv = findViewById(R.id.x_hw_tv);

        GsmCellLocation a = new GsmCellLocation();
        int abc = a.getLac();
        Log.d( "rer", String.valueOf( abc ) );
        // Cpu information string building
        TextView cpu ;
        ProcessBuilder processBuilder;
        String Holder = "";
        String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
        InputStream inputStream;
        Process process ;
        byte[] byteArry ;
        cpu = findViewById(R.id.cpu);
        byteArry = new byte[9024];

        try{
            processBuilder = new ProcessBuilder(DATA);
            process = processBuilder.start();
            inputStream = process.getInputStream();
            while(inputStream.read(byteArry) != -1){
                Holder = Holder + new String(byteArry);
            }

            inputStream.close();

        } catch(IOException ex){

            ex.printStackTrace();
        }

        cpu.setText(Holder);
        // hardware build information string building
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
            //    + "\nID : " + Build.ID
                + "\nMANUFACTURER : " + Build.MANUFACTURER
                + "\nMODEL : " + Build.MODEL
                + "\nPRODUCT : " + Build.PRODUCT
               // + "\nTags : " + Build.TAGS
                + "\nTIME : " + Build.TIME
                + "\nTYPE : " + Build.TYPE
                + "\nUSER : " + Build.USER;
                j_hw_tv.setText(details);
    }

}
