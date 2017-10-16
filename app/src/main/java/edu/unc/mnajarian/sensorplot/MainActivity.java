package edu.unc.mnajarian.sensorplot;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sl = sm.getSensorList(Sensor.TYPE_ALL);

        // print all sensors
        for (int i = 0; i < sl.size(); i++) {
            Log.i("MyTag", "Sensor " + i + ": " + sl.get(i).getName());
        }

        TextView acceltv = (TextView) findViewById(R.id.accelTv);
        TextView lighttv = (TextView) findViewById(R.id.lightTv);
        TextView proximitytv = (TextView) findViewById(R.id.proximityTv);

        // accelerometer
        if (sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

            StringBuilder accelStr = new StringBuilder("");
            accelStr.append("Status: present\n");

            Sensor accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            accelStr.append("\nMax range: " + accelSensor.getMaximumRange());
            accelStr.append("\nResolution: " + accelSensor.getResolution());
            accelStr.append("\nMin delay: " + accelSensor.getMinDelay());

            acceltv.setText(accelStr.toString());
        } else {
            acceltv.setText("Sensor unavailable");
        }

        // light
        if (sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

            StringBuilder lightStr = new StringBuilder("");
            lightStr.append("Status: present\n");

            Sensor lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

            lightStr.append("\nMax range: " + lightSensor.getMaximumRange());
            lightStr.append("\nResolution: " + lightSensor.getResolution());
            lightStr.append("\nMin delay: " + lightSensor.getMinDelay());

            lighttv.setText(lightStr.toString());
        } else {
            lighttv.setText("Sensor unavailable");
        }


        // proximity
        if (sm.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {

            StringBuilder proximityStr = new StringBuilder("");
            proximityStr.append("Status: present\n");
            Sensor proximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            proximityStr.append("\nMax range: " + proximitySensor.getMaximumRange());
            proximityStr.append("\nResolution: " + proximitySensor.getResolution());
            proximityStr.append("\nMin delay: " + proximitySensor.getMinDelay());

            proximitytv.setText(proximityStr.toString());
        } else {
            proximitytv.setText("Sensor unavailable");
        }

    }


    public void plotAccelerometer(View v){
        // Log.i("TAG", "CLICKED ACCELEROMETER BUTTON");
        Intent x = new Intent(this, SensorPlot.class);
        //TODO: make message dependent on view clicked
        x.putExtra("MSG", "accelerometer"); //key, value
        startActivity(x);
    }

    public void plotProximity(View v){
        Intent x = new Intent(this, SensorPlot.class);
        //TODO: make message dependent on view clicked
        x.putExtra("MSG", "proximity"); //key, value
        startActivity(x);
    }

    public void plotLight(View v){
        Intent x = new Intent(this, SensorPlot.class);
        x.putExtra("MSG", "light");
        startActivity(x);
    }

}
