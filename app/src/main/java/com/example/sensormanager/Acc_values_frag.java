package com.example.sensormanager;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sensormanager.Accelerometer_nyy_demo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static android.content.Context.SENSOR_SERVICE;

public class Acc_values_frag extends Fragment implements SensorEventListener {

    TextView x, y, z;
    Accelerometer_nyy_demo pa= new Accelerometer_nyy_demo();
    Sensor sensor;
    SensorManager sm = pa.sensorManager;
    Switch simpleSwitch1;
    public FragmentActivity fa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_acc_values_frag, container, false );

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated( savedInstanceState );
        fa = getActivity();

        sm = (SensorManager) getContext().getSystemService( SENSOR_SERVICE );
        sensor = sm.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );

        x = (TextView) getView().findViewById( R.id.x_x );
        y = (TextView) getView().findViewById( R.id.x_y );
        z = (TextView) getView().findViewById( R.id.x_z );
        simpleSwitch1 = (Switch) getView().findViewById( R.id.simpleSwitch1 );

        simpleSwitch1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1;
                if (simpleSwitch1.isChecked()) {

                    sm.registerListener(Acc_values_frag.this , sensor, SensorManager.SENSOR_DELAY_NORMAL );
                    Toast.makeText( getContext(), "Accelerometer Started", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( getContext(), "Accelerometer stopped", Toast.LENGTH_SHORT ).show();
                    sm.unregisterListener( Acc_values_frag.this, sensor );
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
    public void onPause() {
        super.onPause();
        sm.unregisterListener( Acc_values_frag.this, sensor );
    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener( Acc_values_frag.this, sensor, SensorManager.SENSOR_DELAY_NORMAL );
        simpleSwitch1.setChecked( true );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sm.unregisterListener( Acc_values_frag.this, sensor );
        Toast.makeText( getContext(), "Accelerometer Un-resigtered ", Toast.LENGTH_SHORT ).show();
    }

    }
