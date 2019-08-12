package com.example.sensormanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sm;
    ListView jlv;
    List<Sensor> jlsr;
    String aa;
    List<String> liststring ;
    ArrayAdapter<String> adapter ;
    Intent j_acc_intnt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        sm=(SensorManager) getSystemService(SENSOR_SERVICE);

        jlv = (ListView)findViewById(R.id.xlv);
        liststring = new ArrayList<String>();
        jlsr = sm.getSensorList(Sensor.TYPE_ALL);

        for(int i=0; i<jlsr.size(); i++){
           // liststring.add(jlsr.get(i).getName());
            liststring.add(jlsr.get(i).getName());
            Log.d("Pint",liststring.toString());
        }
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, liststring
        );
        jlv.setAdapter(adapter);


    }
}
