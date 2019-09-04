package com.example.sensormanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sm;
    TextView sensorcount ;
    ListView jlv;
    List<Sensor> jlsr;
    ArrayList<String> liststring ;
    ArrayAdapter<String> adapter ;
    Button jbt ;
    Button jbt_hw;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_main);
            sm=(SensorManager) getSystemService(SENSOR_SERVICE);

            jbt = (Button) findViewById(R.id.xbt);
            jbt_hw = ( Button) findViewById(R.id.x_hw_btn);
            sensorcount = (TextView)findViewById(R.id.x_tv_sensorcount);

            jlv = (ListView)findViewById(R.id.xlv);
            liststring = new ArrayList<String>();
            jlsr = sm.getSensorList(Sensor.TYPE_ALL);

            sensorcount.setText("Total Sensor(s) found : "+ jlsr.size());

            adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.customrow_listview,
                    R.id.textView2, liststring);
            jlv.setAdapter(adapter);

                for(int i=0; i<jlsr.size(); i++){

                    liststring.add("# " + (i+1) + " " + jlsr.get(i).getName() );
                 //   Log.d("liststrnig",jlsr.get(i).getName());
                }

                jlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int k, long l) {

                        Toast.makeText(MainActivity.this, "Item"+ jlsr.get(k).getName() +
                                "Long"+l, Toast.LENGTH_SHORT).show();
                    }
                });

                jbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, acceleromparticles.class);
                    startActivity(intent);

                        }
                    });


            jbt_hw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view2) {
                    Intent intent_hw = new Intent(MainActivity.this, hw.class);
                    startActivity(intent_hw);

                }
            });

        }
}
