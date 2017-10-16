package edu.unc.mnajarian.sensorplot;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mnajarian on 10/15/17.
 */

public class SensorPlot extends AppCompatActivity implements SensorEventListener{

    SensorManager sm = null;
    Sensor s = null;
    // List<Sensor> sl = null;
    long lasttime = 0;
    PlotView pv = null;
    String sensorType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_plot);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        String rm = getIntent().getStringExtra("MSG");
        //Log.i("TAG",rm);
        sensorType = rm;

        float maxRange = (float) 0.0;

        if (rm.equals("accelerometer")){
            // get reference to accelerometer sensor
            s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            maxRange = s.getMaximumRange();
        } else if (rm.equals("proximity")){
            s = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            maxRange = (float) 15.0;
        } else if (rm.equals("light")) {
            s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
            maxRange = (float) 1000;
        }

        // register listener, read every 100 ms
        sm.registerListener(this, s, 100000);

        // get reference to plotview
        pv = (PlotView) findViewById(R.id.pv);

        // set max y value
        TextView maxYValueTv = (TextView) findViewById(R.id.maxYtv);
        maxYValueTv.setText(Float.toString(maxRange));
        pv.setMaxYValue(maxRange);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // for some reason, printing much faster than 1 per second?
        double dt = 0;
        dt = sensorEvent.timestamp - lasttime;
        if (dt >= 100000){

            lasttime = sensorEvent.timestamp;

            if (sensorType.equals("accelerometer")) {
                Float accelValue = (float) Math.sqrt(Math.pow(sensorEvent.values[0], 2) +
                        Math.pow(sensorEvent.values[1], 2) + Math.pow(sensorEvent.values[2], 2));

                pv.addPoint(accelValue);
            }else {
                pv.addPoint(sensorEvent.values[0]);
            }
            pv.invalidate();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void backToMain(View v){
        Intent x = new Intent(this, MainActivity.class);
        startActivity(x);
    }

    public void sensorAnimation (View v){
        Intent x = new Intent(this, SensorAnimation.class);
        x.putExtra("MSG", sensorType); //key, value
        startActivity(x);
    }


}
