package com.example.sos;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class PowerButtonService extends Service {

    private PowerButtonReceiver receiver;
    private static final String CHANNEL_ID = "PowerButtonServiceChannel";
    private static final int NOTIFICATION_ID = 101;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        // Start foreground service with persistent notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("SOS Monitoring Active")
                .setContentText("Listening for power button presses.")
                .setSmallIcon(android.R.drawable.ic_dialog_alert) // Replace with your icon
                .setOngoing(true)
                .build();

        startForeground(NOTIFICATION_ID, notification);

        receiver = new PowerButtonReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  // API 26+
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Battery Monitor Service Channel",  // Or "Power Button Detection"
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

}

