package com.example.sensormanager;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class accelerom extends AppCompatActivity implements SensorEventListener {


    TextView x,y,z;
    Sensor sensor;
    SensorManager sensormanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerom);

        sensormanager =(SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensormanager.registerListener(accelerom.this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        x= (TextView) findViewById(R.id.x_x);
        y= (TextView) findViewById(R.id.x_y);
        z= (TextView) findViewById(R.id.x_z);
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



}
