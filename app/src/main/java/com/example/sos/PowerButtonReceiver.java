package com.example.sos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class PowerButtonReceiver extends BroadcastReceiver {

    private static final int MAX_INTERVAL = 1000; // 1s between presses
    private static final int REQUIRED_PRESS_COUNT = 4;
    private static final long[] mHits = new long[REQUIRED_PRESS_COUNT];

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis();

            if (mHits[0] >= (SystemClock.uptimeMillis() - MAX_INTERVAL * REQUIRED_PRESS_COUNT)) {

                PreferencesHelper helper = new PreferencesHelper(context);
                if (!helper.isPowerButtonEnabled()) return;

                // Trigger SOS
                MainActivity.sendSOSStatic(context);

                // Stop audio recording
                context.stopService(new Intent(context, AudioRecordService.class));

                // Reset
                for (int i = 0; i < mHits.length; i++) mHits[i] = 0;
            }
        }
    }
}





