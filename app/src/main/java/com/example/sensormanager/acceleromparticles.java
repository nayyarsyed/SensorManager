package com.example.sensormanager;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.zip.Inflater;


public class acceleromparticles extends Activity {

    public SimulationView mSimulationView;
    private SensorManager mSensorManager;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    private Display mDisplay;
    private WakeLock mWakeLock;
    public int  ui = 1 ;

    /** Called when the activity is first created. */
    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get an instance of the SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get an instance of the PowerManager
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // Get an instance of the WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        mSimulationView = new SimulationView(getApplicationContext());
        //    fl.addView( mSimulationView );
        setContentView( R.layout.dyn);
        FrameLayout jfl = findViewById( R.id.fl );
        jfl.addView( mSimulationView );
        mSimulationView.setBackgroundResource(R.drawable.wood);
       // setContentView(mSimulationView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * when the activity is resumed, we acquire a wake-lock so that the
         * screen stays on, since the user will likely not be fiddling with the
         * screen or buttons.
         */
        //mWakeLock.acquire();

        // Start the simulation
        mSimulationView.startSimulation();
        // mSimulationView.setLayoutParams(new FrameLayout.LayoutParams(700, 300));

    }



    @Override
    protected void onPause() {
        super.onPause();
        /*
         * When the activity is paused, we make sure to stop the simulation,
         * release our sensor resources and wake locks
         */

        // Stop the simulation
        mSimulationView.stopSimulation();
        // and release our wake-lock
        // mWakeLock.release();
    }

    class SimulationView extends FrameLayout implements SensorEventListener {
        // diameter of the balls in meters
        private static final float sBallDiameter = 0.000395f;
        private static final float sBallDiameter2 = sBallDiameter * sBallDiameter;
        private final int mDstWidth;
        private final int mDstHeight;
        private Sensor mAccelerometer;
        private long mLastT;
        private float mXDpi;
        private float mYDpi;
        private float mMetersToPixelsX;
        private float mMetersToPixelsY;
        private float mXOrigin;
        private float mYOrigin;
        private float mSensorX;
        private float mSensorY;
        private float mHorizontalBound;
        private float mVerticalBound;
        public final ParticleSystem mParticleSystem;
        /*
         * Each of our particle holds its previous and current position, its
         * acceleration. for added realism each particle has its own friction
         * coefficient.
         */



        class Particle extends View {
            private float mPosX = (float) Math.random();
            private float mPosY = (float) Math.random();
            private float mVelX;
            private float mVelY;

            public Particle(Context context) {
                super( context );
            }

            public Particle(Context context, AttributeSet attrs) {
                super(context, attrs);
            }

            public Particle(Context context, AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
            }

            public Particle(Context context, AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {
                super(context, attrs, defStyleAttr, defStyleRes);
            }

            public void computePhysics(float sx, float sy, float dT) {

                final float ax = -sx/10;  //viscosity changes
                final float ay = -sy/10;
                mPosX += mVelX * dT + ax * dT * dT/2 ; //original /2
                mPosY += mVelY * dT + ay * dT * dT /2;
                mVelX += ax * dT;
                mVelY += ay * dT;
            }

            public void resolveCollisionWithBounds() {
                final float xmax =  mHorizontalBound; // nyy (float) Math.random();
                final float ymax =  mVerticalBound;    //nyy (float) Math.random();
                final float x = mPosX;
                final float y = mPosY;
                if (x > xmax) {
                    mPosX = xmax;
                    mVelX = 0;
                } else if (x < -xmax) {
                    mPosX = -xmax;
                    mVelX = 0;
                }
                if (y > ymax) {
                    mPosY = ymax;
                    mVelY = 0;
                } else if (y < -ymax) {
                    mPosY = -ymax;
                    mVelY = 0;
                }
            }
        }

        /*
         * A particle system is just a collection of particles
         */
        class ParticleSystem {
            static final int NUM_PARTICLES = 100;
            private Particle mBalls[] = new Particle[NUM_PARTICLES];

            ParticleSystem() {
                /*
                 * Initially our particles have no speed or acceleration
                 */
                for (int i = 0; i < mBalls.length; i++) {
                    mBalls[i] = new Particle(getContext());
                    mBalls[i].setBackgroundResource(R.drawable.b);

                    mBalls[i].setLayerType(LAYER_TYPE_HARDWARE, null);
                    addView(mBalls[i], new ViewGroup.LayoutParams(mDstWidth, mDstHeight));
                }
            }

            /*
             * Update the position of each particle in the system using the
             * Verlet integrator.
             */
            private void updatePositions(float sx, float sy, long timestamp) {
                final long t = timestamp;
                if (mLastT != 0) {
                    final float dT = (float) (t - mLastT) / 1000.f /** (1.0f / 1000000000.0f)*/;
                    final int count = mBalls.length;
                    for (int i = 0; i < count; i++) {
                        Particle ball = mBalls[i];
                        ball.computePhysics(sx, sy, dT);
                    }
                }
                mLastT = t;
            }

            /*
             * Performs one iteration of the simulation. First updating the
             * position of all the particles and resolving the constraints and
             * collisions.
             */
            public void update(float sx, float sy, long now) {
                // update the system's positions
                updatePositions(sx, sy, now);

                // We do no more than a limited number of iterations
                final int NUM_MAX_ITERATIONS = 10;  //nyy changed from 10 to 1

                /*
                 * Resolve collisions, each particle is tested against every
                 * other particle for collision. If a collision is detected the
                 * particle is moved away using a virtual spring of infinite
                 * stiffness.
                 */
                boolean more = true;
                final int count = mBalls.length;
                for (int k = 0; k < NUM_MAX_ITERATIONS && more; k++) {
                    more = false;
                    for (int i = 0; i < count; i++) {
                        Particle curr = mBalls[i];
                        for (int j = i + 1; j < count; j++) {
                            Particle ball = mBalls[j];
                            float dx = ball.mPosX - curr.mPosX;
                            float dy = ball.mPosY - curr.mPosY;
                            float dd = dx * dx + dy * dy;
                            // Check for collisions
                            if (dd <= sBallDiameter2) {
                                /*
                                 * add a little bit of entropy, after nothing is
                                 * perfect in the universe.
                                 */
                                dx += ((float) Math.random() - 0.01f) * 0.0001f; // nyy 0.05 changed to 0.01 will change the animation pattern and will
                                dy += ((float) Math.random() - 0.01f) * 0.0001f; // 0.0001 chnaged to .001
                                dd = dx * dx + dy * dy;
                                // simulate the spring
                                final float d = (float) Math.sqrt(dd);
                                final float c = (0.5f * (sBallDiameter - d)) / d;
                                final float effectX = dx * c;
                                final float effectY = dy * c;
                                curr.mPosX -= effectX/2;
                                curr.mPosY -= effectY/2;
                                ball.mPosX += effectX/2;
                                ball.mPosY += effectY/2;
                                more = true;
                            }
                        }
                        curr.resolveCollisionWithBounds();
                    }
                }
            }

            public int getParticleCount() {
                return mBalls.length;
            }

            public float getPosX(int i) {
                return mBalls[i].mPosX;
            }

            public float getPosY(int i) {
                return mBalls[i].mPosY;
            }
        }

        public void startSimulation() {
            /*
             * It is not necessary to get accelerometer events at a very high
             * rate, by using a slower rate (SENSOR_DELAY_UI), we get an
             * automatic low-pass filter, which "extracts" the gravity component
             * of the acceleration. As an added benefit, we use less power and
             * CPU resources.
             */
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);


        }

        public void stopSimulation() {
            mSensorManager.unregisterListener(this);
            Toast.makeText( getApplicationContext(), "Accelerometer Disengaged", Toast.LENGTH_SHORT ).show();

        }

        public SimulationView(Context context) {
            super(context);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);



            DisplayMetrics metrics = new DisplayMetrics();
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            mXDpi = metrics.xdpi;
            mYDpi = metrics.ydpi;

            // nyy this will reduce the size of the ball oroginal was 0.0254 changed t 0.0854
            mMetersToPixelsX = mXDpi / 0.0354f;
            mMetersToPixelsY = mYDpi / 0.03854f;

            // rescale the ball so it's about 0.5 cm on screen
            mDstWidth = (int) (sBallDiameter * mMetersToPixelsX * 0.5f);
            mDstHeight = (int) (sBallDiameter * mMetersToPixelsY * 0.5f);
            mParticleSystem = new ParticleSystem();

//            Options opts = new Options();
//            opts.inDither = true;
//            opts.inPreferredConfig = Bitmap.Config.RGB_565;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            // compute the origin of the screen relative to the origin of
            // the bitmap
            mXOrigin = (w - mDstWidth) * 0.5f;
            mYOrigin = (h - mDstHeight) * 0.5f;
            mHorizontalBound = ((w / mMetersToPixelsX - sBallDiameter) * 0.3f);
            mVerticalBound = ((h / mMetersToPixelsY - sBallDiameter) * 0.3f);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
                return;
            /*
             * record the accelerometer data, the event's timestamp as well as
             * the current time. The latter is needed so we can calculate the
             * "present" time during rendering. In this application, we need to
             * take into account how the screen is rotated with respect to the
             * sensors (which always return data in a coordinate space aligned
             * to with the screen in its native orientation).
             */

            switch (mDisplay.getRotation()) {
                case Surface.ROTATION_0:
                    mSensorX = event.values[0];
                    mSensorY = event.values[1];
                    break;
                case Surface.ROTATION_90:
                    mSensorX = -event.values[1];
                    mSensorY = event.values[0];
                    break;
                case Surface.ROTATION_180:
                    mSensorX = -event.values[0];
                    mSensorY = -event.values[1];
                    break;
                case Surface.ROTATION_270:
                    mSensorX = event.values[1];
                    mSensorY = -event.values[0];
                    break;


            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            /*
             * Compute the new position of our object, based on accelerometer
             * data and present time.
             */
            final ParticleSystem particleSystem = mParticleSystem;
            final long now = System.currentTimeMillis();
            final float sx = mSensorX;
            final float sy = mSensorY;

            particleSystem.update(sx, sy, now);

            final float xc = mXOrigin;
            final float yc = mYOrigin;
            final float xs = mMetersToPixelsX;
            final float ys = mMetersToPixelsY;
            final int count = particleSystem.getParticleCount();

            for (int i = 0; i < count; i++) {
                /*
                 * We transform the canvas so that the coordinate system matches
                 * the sensors coordinate system with the origin in the center
                 * of the screen and the unit is the meter.
                 */
                final float x = xc + particleSystem.getPosX(i) * xs;
                final float y = yc - particleSystem.getPosY(i) * ys;
                particleSystem.mBalls[i].setTranslationX(x);
                particleSystem.mBalls[i].setTranslationY(y);
            }

            // and make sure to redraw asap
            invalidate();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
