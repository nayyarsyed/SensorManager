package com.example.sensormanager;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sm;
    TextView sensorcount;
    ListView jlv;
    List<Sensor> jlsr;
    ArrayList<String> liststring;
    ArrayAdapter<String> adapter;
    Button jbt,jbt_hw,j_simple_acc_demo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        overridePendingTransition(R.anim.slide_in_right,  R.anim.slide_out_right);

        setContentView( R.layout.starthere );
        sm = (SensorManager) getSystemService( SENSOR_SERVICE );
     //   jbt = findViewById( R.id.xbt );
        jbt = findViewById( R.id.xbt );
        jbt_hw = findViewById( R.id.x_hw_btn );
        j_simple_acc_demo = findViewById( R.id.simple_acc_move_btn );
        sensorcount = findViewById( R.id.x_tv_sensorcount );
        jlv = findViewById( R.id.xlv );
        liststring = new ArrayList<String>();
        jlsr = sm.getSensorList( Sensor.TYPE_ALL );
        sensorcount.setText( "Total Sensor(s)  found : " + jlsr.size() );
        adapter = new ArrayAdapter<>( MainActivity.this, R.layout.customrow_listview,
                R.id.textView2, liststring );
        jlv.setAdapter( adapter );

        // alternative way to get the information in a single string builder object
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : jlsr) {
            sensorText.append( currentSensor.getName() ).append(
                    System.getProperty( "line.separator" ) );
        }


        if ((jlsr.size() != 0)) {
            // block of code to be executed if the condition is true
            for (int i = 0; i < jlsr.size(); i++) {
                liststring.add( "Sensor"   + (i + 1) + " -> " + jlsr.get( i ).getName() +
                        "\n" + "Max Range" + "\f" +  jlsr.get( i ).getMaximumRange()+
                        "\n" + "Max Delay" + "\f"+ jlsr.get( i ).getMaxDelay()+
                        "\n" + "Min Delay" + "\f"+ jlsr.get( i ).getMinDelay()+
                        "\n" + "Power usage (mA)"+ "\f"+  jlsr.get( i ).getPower()
                );
                //   Log.d("liststrnig",jlsr.get(i).getName());
            }
            jlv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int k, long l) {
                    //  Toast.makeText( MainActivity.this, "Item" + jlsr.get( k ).getName() +
                    //          "Long" + l, Toast.LENGTH_SHORT ).show();
                }
            } );
        } else {
            // block of code to be executed if the condition is false
            Toast.makeText( this, "No Sensor Found ", Toast.LENGTH_SHORT ).show();
        }

        jbt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( MainActivity.this, acceleromparticles.class );
                startActivity( intent );
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        } );

        jbt_hw.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                Intent intent_hw = new Intent( MainActivity.this, hw.class );
                startActivity( intent_hw );
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        } );

        j_simple_acc_demo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View j_simple_acc_demo_view) {
                Intent intent_hw = new Intent( MainActivity.this, Accelerometer_nyy_demo.class );
                startActivity( intent_hw );
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);            }
        } );





    }
}
