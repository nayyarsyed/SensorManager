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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sm;
    TextView sensorcount ;
    ListView jlv;
    List<Sensor> jlsr;
    List<String> liststring ;
    ArrayAdapter<String> adapter ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            sm=(SensorManager) getSystemService(SENSOR_SERVICE);
            jlv = (ListView)findViewById(R.id.xlv);
            sensorcount = (TextView)findViewById(R.id.x_tv_sensorcount);
            liststring = new ArrayList<String>();
            jlsr = sm.getSensorList(Sensor.TYPE_ALL);
            Integer a = jlsr.size();
            sensorcount.setText("Total number(s) of sensors found : "+ jlsr.size());


            for(int i=0; i<jlsr.size(); i++){
                liststring.add(jlsr.get(i).getName());
            }
            adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.activity_list_item,android.R.id.text1, liststring);
            jlv.setAdapter(adapter);

            Button jbt ;

            jbt = (Button) findViewById(R.id.xbt);
            jbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, accelerom.class);
                    startActivity(intent);

                }
        });
    }
}
