package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//Before start implement sensoreventlistener
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView sensorText;
    private SensorManager sensorManager;
    private TextView sensorAccelerometerText;
    private TextView sensorProximityText;
    private Sensor sensorAccelerometer;
    private Sensor sensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorText=findViewById(R.id.sensor_list);
        sensorAccelerometerText=findViewById(R.id.sensor_accelerometer);
        sensorProximityText=findViewById(R.id.sensor_proximity);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorProximity=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        //Get all sensor data
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        //Ambil satu satu dan gabung menggunakan string builder
        StringBuilder sensorTx=new StringBuilder();
        for(Sensor sensor:sensorList){
            sensorTx.append(sensor.getName()+"\n");
        }
        sensorText.setText(sensorTx.toString());

        //Cek sensor ada atau tidak
        if(sensorAccelerometer==null){
            Toast.makeText(this,"Accelerometer Sensor Not Available !",Toast.LENGTH_SHORT)
                    .show();
        }
        if(sensorProximity==null){
            Toast.makeText(this,"Proximity Sensor Not Available !",Toast.LENGTH_SHORT)
                    .show();
        }
    }

    //Register sensor
    @Override
    protected void onStart() {
        super.onStart();
        if(sensorProximity!=null){
            sensorManager.registerListener(this, sensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorAccelerometer!=null){
            sensorManager.registerListener(this, sensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    //Unregister sensor when activity stopped
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    //Semua sensor masuk ke sini
    //Definisi mana sensor yang berjalan
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float value = sensorEvent.values[0];
        if(sensorType==Sensor.TYPE_ACCELEROMETER){
            sensorAccelerometerText.setText(String.format("Accelerometer Sensor : %1$.2f", value));
        }
        if(sensorType==Sensor.TYPE_PROXIMITY){
            sensorProximityText.setText(String.format("Proximity Sensor : %1$.2f", value));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}