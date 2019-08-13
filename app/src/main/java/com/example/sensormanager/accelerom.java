package com.example.sensormanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class accelerom extends AppCompatActivity implements SensorEventListener {


    TextView x, y, z;
    Sensor sensor;
    SensorManager sensormanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerom);


        sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sensormanager.registerListener(accelerom.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        x = (TextView) findViewById(R.id.x_x);
        y = (TextView) findViewById(R.id.x_y);
        z = (TextView) findViewById(R.id.x_z);

        final Switch simpleSwitch1;
        simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);
        simpleSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2;
                if (simpleSwitch1.isChecked()) {

                    sensormanager.registerListener(accelerom.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                    Toast.makeText(accelerom.this, "Accelerometer Started", Toast.LENGTH_SHORT).show();
                }
                else

                {
                    Toast.makeText(accelerom.this, "Accelero meter stopped", Toast.LENGTH_SHORT).show();
                    sensormanager.unregisterListener(accelerom.this,sensor);

                }
            }
        });


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        x.setText("X Value :" + sensorEvent.values[0]);
        y.setText("Y Value :" + sensorEvent.values[1]);
        z.setText("Z Value :" + sensorEvent.values[2]);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensormanager.unregisterListener(accelerom.this,sensor);


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensormanager.registerListener(accelerom.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensormanager.unregisterListener(accelerom.this,sensor);
        Toast.makeText(this, "Accelerometer Un Resigtered ", Toast.LENGTH_SHORT).show();

    }
}


