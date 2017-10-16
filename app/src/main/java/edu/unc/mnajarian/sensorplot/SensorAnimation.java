package edu.unc.mnajarian.sensorplot;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by mnajarian on 10/15/17.
 */

public class SensorAnimation extends AppCompatActivity implements SensorEventListener {

    SensorManager sm = null;
    Sensor s = null;
    ImageView iv = null;
    String SensorType = null;
    ArrayList<Float> l = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_animation);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        iv = (ImageView) findViewById(R.id.imv);

        SensorType = getIntent().getStringExtra("MSG");
        if (SensorType.equals("accelerometer")){
            // get reference to accelerometer sensor
            s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else if (SensorType.equals("proximity")){
            s = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        } else if (SensorType.equals("light")) {
            s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        // register listener, read every 100 ms
        // sm.registerListener(this, s, 100000);
        sm.registerListener(this, s, 100000000);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (SensorType.equals("light")) {

            if (sensorEvent.values[0] <= 10) {
                iv.setBackgroundResource(R.drawable.lb_0);
                ((AnimationDrawable) iv.getBackground()).start();
            }
            if (sensorEvent.values[0] > 10 && sensorEvent.values[0] <= 25) {
                iv.setBackgroundResource(R.drawable.lb_1);
                ((AnimationDrawable) iv.getBackground()).start();
            }
            if (sensorEvent.values[0] > 25 && sensorEvent.values[0] <= 75) {
                iv.setBackgroundResource(R.drawable.lb_2);
                ((AnimationDrawable) iv.getBackground()).start();
            }
            if (sensorEvent.values[0] > 75) {
                iv.setBackgroundResource(R.drawable.lb_3);
                ((AnimationDrawable) iv.getBackground()).start();
            }

        }
        if (SensorType.equals("accelerometer")){
            Float accelValue = (float) Math.sqrt(Math.pow(sensorEvent.values[0], 2) +
                    Math.pow(sensorEvent.values[1], 2) + Math.pow(sensorEvent.values[2], 2));
            if (l.size() >= 8){
                l.remove(0);
            }
            l.add(accelValue);
            float sum = (float) 0.0;
            for (int i=0; i< l.size(); i++){
                sum += l.get(i);
            }
            float mean = sum/l.size();

            if (mean <= 10.0){
                iv.setBackgroundResource(R.drawable.bc_0);
            }
            if (mean > 10.0 && mean <= 11.0){
                iv.setBackgroundResource(R.drawable.bc_1);
            }
            if (mean > 11.0 && mean <= 12.0){
                iv.setBackgroundResource(R.drawable.bc_2);
            }
            if (mean > 12.0){
                iv.setBackgroundResource(R.drawable.bc_3);
            }
        }
        if (SensorType.equals("proximity")){
            if (sensorEvent.values[0] == 10.0){
                iv.setBackgroundResource(R.drawable.hand_0);
            }
            if (sensorEvent.values[0] == 0.0) {
                iv.setBackgroundResource(R.drawable.hand_1);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void backToMain(View v){
        Intent x = new Intent(this, MainActivity.class);
        startActivity(x);
    }

    public void sensorPlot (View v){
        Intent x = new Intent(this, SensorPlot.class);
        x.putExtra("MSG", SensorType);
        startActivity(x);
    }
}
