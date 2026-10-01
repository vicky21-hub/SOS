package com.example.sos;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.File;

public class AudioRecordService extends Service {
    private MediaRecorder recorder;
    private File audioFile;

    private static final String CHANNEL_ID = "sos_record_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        showRecordingNotification();
    }

    private void startRecording() {
        try {
            File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "SOSRecordings");
            if (!path.exists() && !path.mkdirs()) {
                Log.e("AudioRecordService", "❌ Failed to create recording directory.");
                return;
            }

            String fileName = "sos_record_" + System.currentTimeMillis() + ".3gp";
            audioFile = new File(path, fileName);

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(audioFile.getAbsolutePath());
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.prepare();
            recorder.start();

            Log.d("AudioRecordService", "✅ Recording started: " + audioFile.getAbsolutePath());

            // Auto stop after 60 seconds
            new Handler().postDelayed(this::stopSelf, 60000);

        } catch (Exception e) {
            Log.e("AudioRecordService", "❌ Recording failed: " + e.getMessage(), e);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && "STOP_RECORD".equals(intent.getAction())) {
            stopSelf();
            return START_NOT_STICKY;
        }

        startRecording();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (recorder != null) {
            try {
                recorder.stop();
            } catch (Exception ignored) {}
            recorder.release();
            recorder = null;
        }
        if (audioFile != null && audioFile.exists()) {
            try {
                MediaScannerConnection.scanFile(
                        this,
                        new String[]{audioFile.getAbsolutePath()},
                        null,
                        null
                );
            } catch (Exception ignored) {}
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showRecordingNotification() {
        Intent stopIntent = new Intent(this, AudioRecordService.class);
        stopIntent.setAction("STOP_RECORD");

        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent,
                PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Recording Emergency Audio")
                .setContentText("Tap to stop")
                .setSmallIcon(R.drawable.ic_microphone)
                .addAction(R.drawable.ic_stop, "Stop", stopPendingIntent)
                .setOngoing(true)
                .build();

        try {
            startForeground(1002, notification);
        } catch (Exception e) {
            Log.e("AudioRecordService", "Foreground service start failed", e);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "SOS Audio Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Used for SOS background audio recording");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
