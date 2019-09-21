package com.example.sensormanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class Accelerometer_nyy_demo extends Activity implements SensorEventListener
{
    /** Called when the activity is first created. */
    AnimationAct_layout_001 ll = null;
    public  float x;
    public  float y;
    public SensorManager sensorManager ;
    public Sensor s ;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Get a reference to a SensorManager
       sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        s = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ll = new AnimationAct_layout_001(this);
        TextView message = new TextView(this  );
        message.setText( "Tilt your phone to visualize the acceleormeter X and Y values" );
        addContentView( message,new LinearLayout.LayoutParams( 100,100 ) );
        setContentView(ll);



        //  setContentView(R.layout.activity_main);

        //  mCustomDrawableView.animate();

//        ValueAnimator animator = ValueAnimator.ofInt(0, 1000);
//        animator.setDuration(2000);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//            }
//        });
//        animator.start();
//
//        @SuppressLint("ResourceType") View img = findViewById( R.drawable.ball );
////             ObjectAnimator animation = ObjectAnimator.ofFloat(img, "translationX", 70f);
////            animation.setDuration(200);
////            animation.start();
    }


//    // This method will update the UI on new sensor events
//    public void onSensorChanged(SensorEvent sensorEvent) {
//                // the values you were calculating originally here were over 10000!
////                x = (int) Math.pow( sensorEvent.values[1], 4 );
////                y = (int) Math.pow( sensorEvent.values[2], 2 );


    @Override
    protected void onResume()
    {
        super.onResume();
        // Register this class as a listener for the accelerometer sensor
        sensorManager.registerListener(this, s,
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        sensorManager.registerListener(this, s,
                SensorManager.SENSOR_DELAY_GAME);
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x -=   (sensorEvent.values[0]/ 0.05f);
            y +=  ( sensorEvent.values[1]/ 0.05f);



        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        Toast.makeText( this, "Accelerometer Dis-engaged", Toast.LENGTH_LONG ).show();
    }

    //    public class rect1 {
//        public int speedx =1;
//        RectF rect = new RectF( 100, 100, 400, 500 );
//        //        rect.centerX();
//        // canvas.translate(x, y);
//        Paint p = new Paint(); // set some paint options
//
//         //   p.setColor(Color.BLUE);
//    }
    public class AnimationAct_layout_001 extends View
    {


        Paint redpaint = new Paint();
        Paint bluepaint = new Paint(  );
        Paint textpaint = new Paint(  );
        public AnimationAct_layout_001(Context context)
        {
            super(context);
            setBackgroundResource( R.drawable.wood );
            textpaint.setColor( Color.BLACK );




        }

        public void drawtext (String s,Canvas cn) {


            LinearLayout layout = new LinearLayout(getContext());
            TextView ctextView = new TextView(getContext());
            ctextView.setVisibility(View.VISIBLE);
            ctextView.setText(s);
            layout.addView(ctextView);
            layout.measure(cn.getWidth(), cn.getHeight());
            layout.layout(440, 400, cn.getWidth(), cn.getHeight());
            layout.draw(cn);

        }




        protected void onDraw(Canvas canvas)
        {


            //  canvas.drawRect(rect,p);

            redpaint.setColor( Color.RED );
            bluepaint.setColor( Color.BLUE );


            RectF rect,rect2;
            rect = new RectF();
            rect2 = new RectF();



// To place the text view somewhere specific:
//canvas.translate(0, 0);

            drawtext( "Please Tilt your Phone ",canvas );

//            RectF rectf[] = new RectF[10];

//            int y =100;
//            for (int k=0; k < rectf.length;k++) {
//
//                rectf[k] = new RectF( );
//
//                    y+=100;
//                rectf[k].set( y-50,y+70,y,y+10 );
//
//            }


            rect.set(200,0,140,300);
            rect2.set(250,250,350,350);

            //   Log.d( "centerx", String.valueOf( rect.exactCenterX() ) );


            canvas.translate(x, y);

//
//            for (int i = 0; i < 2; i++) {
//
//            }

            if (x >= getWidth()  ) {


                x = 1 ;

                Log.d( "rectx", String.valueOf( (x) ) );

            }

            if (x <= 0 ){

                x = getWidth();

                Log.d( "rectx", String.valueOf( (x) ) );
            }

            if (y >= getHeight()) {

                y = 1 ;

                Log.d( "y", String.valueOf( (y) ) );
                Log.d( "heigth", String.valueOf (getHeight())) ;

            }

            if (y <= 0 ){

                y = getHeight();

                Log.d( "bbb", String.valueOf( (y) ) );
                Log.d( "hh", String.valueOf( (getHeight())));
            }

            canvas.drawRect( rect,redpaint );
            canvas.drawRect( rect2,bluepaint );
            textpaint.setTextSize(20);
            canvas.drawText("Please Tilt your Phone Slightly ", rect.centerX(), rect.centerY(), textpaint);
//
//            for (int k=0; k < rectf.length;k++) {
//
//                //mBalls[i] = new Particle(getContext());
//                canvas.drawRect( rectf[k],redpaint );
//
//            }

            invalidate();

        }
    }
}