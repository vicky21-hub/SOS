package com.example.sos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            context.startService(new Intent(context, ShakeService.class));
        }

        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            int level = intent.getIntExtra("level", -1);
            if (level <= 15) {
                alert(context, "Battery Low!");
            } else if (level >= 95) {
                alert(context, "Battery Full!");
            }
        } else if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
            alert(context, "Power Connected");
        } else if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
            alert(context, "Power Disconnected");
        }
    }

    private void alert(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        MediaPlayer mp = MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI);
        if (mp != null) {
            mp.start();
        }
    }
}
