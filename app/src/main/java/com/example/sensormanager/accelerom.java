package com.example.sensormanager;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class accelerom extends AppCompatActivity implements SensorEventListener {

    TextView x, y, z;
    Sensor sensor;
    Switch simpleSwitch1;
    MainActivity ma= new MainActivity();
    SensorManager sensormanager_accelerom = ma.mSensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_accelerom );
        sensormanager_accelerom = (SensorManager) getSystemService( SENSOR_SERVICE );
        sensor = sensormanager_accelerom.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );

        x = (TextView) findViewById( R.id.x_x );
        y = (TextView) findViewById( R.id.x_y );
        z = (TextView) findViewById( R.id.x_z );
        simpleSwitch1 = (Switch) findViewById( R.id.simpleSwitch1 );


        simpleSwitch1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1;
                if (simpleSwitch1.isChecked()) {

                    sensormanager_accelerom.registerListener( accelerom.this, sensor, SensorManager.SENSOR_DELAY_NORMAL );
                    Toast.makeText( accelerom.this, "Accelerometer Started", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( accelerom.this, "Accelerometer stopped", Toast.LENGTH_SHORT ).show();
                    sensormanager_accelerom.unregisterListener( accelerom.this, sensor );
                    ma.mSimulationView.stopSimulation();
                }
            }
        } );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x.setText( "X Value :" + sensorEvent.values[0] );
        y.setText( "Y Value :" + sensorEvent.values[1] );
        z.setText( "Z Value :" + sensorEvent.values[2] );
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensormanager_accelerom.unregisterListener( accelerom.this, sensor );
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensormanager_accelerom.registerListener( accelerom.this, sensor, SensorManager.SENSOR_DELAY_NORMAL );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensormanager_accelerom.unregisterListener( accelerom.this, sensor );
        Toast.makeText( this, "Accelerometer Un-resigtered ", Toast.LENGTH_SHORT ).show();
    }
}


