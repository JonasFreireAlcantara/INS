package com.study.ins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.study.ins.linearalgebra.Operations;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView accelerometerX;
    private TextView accelerometerY;
    private TextView accelerometerZ;
    private TextView accelerometerNorm;
    private TextView speed;
    private float[] speedVector;

    private float deltaTime;
    private long previousEventTimestamp;

    public void findTextViews() {
        this.accelerometerX = findViewById(R.id.accelerometerX);
        this.accelerometerY = findViewById(R.id.accelerometerY);
        this.accelerometerZ = findViewById(R.id.accelerometerZ);
        this.accelerometerNorm = findViewById(R.id.accelerometerNorm);
        this.speed = findViewById(R.id.speed);

        this.speedVector = new float[3];
    }

    public void initializeAccelerometerSensors() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findTextViews();
        this.initializeAccelerometerSensors();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            this.updateDeltaTimeFromPreviousEvent(sensorEvent);
            this.updateSpeed(sensorEvent.values, this.deltaTime);

//            this.accelerometerX.setText("X: " + sensorEvent.values[0]);
//            this.accelerometerY.setText("Y: " + sensorEvent.values[1]);
//            this.accelerometerZ.setText("Z: " + sensorEvent.values[2]);
//            this.accelerometerNorm.setText("Total: " + norm);

            double speedNorm = Operations.norm(this.speedVector);

            this.speed.setText("Speed: " + speedNorm);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void updateDeltaTimeFromPreviousEvent(SensorEvent sensorEvent) {
        long actualEventTimestamp = sensorEvent.timestamp;

        long delta = actualEventTimestamp - this.previousEventTimestamp;
        this.deltaTime = delta / 1_000_000_000f;

        this.previousEventTimestamp = actualEventTimestamp;
    }

    private void updateSpeed(float[] acceleration, float deltaTime) {
        float[] deltaSpeed = Operations.multiplyByConstant(acceleration, deltaTime);

        this.speedVector = Operations.add(this.speedVector, deltaSpeed);
    }
}