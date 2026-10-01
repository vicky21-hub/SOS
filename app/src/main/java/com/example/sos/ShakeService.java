package com.example.sos;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

public class ShakeService extends Service {

    private SensorManager sensorManager;
    private ShakeDetector shakeDetector;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize SensorManager and Accelerometer
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize ShakeDetector with SOS action
        shakeDetector = new ShakeDetector(() -> {
            // Trigger SOS when shake is detected
            SOSUtils.sendSOS(getApplicationContext(), new PreferencesHelper(getApplicationContext()));
        });

        // Register the listener
        if (accelerometer != null) {
            sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    public void onDestroy() {
        // Unregister shake detector
        if (sensorManager != null && shakeDetector != null) {
            sensorManager.unregisterListener(shakeDetector);
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
