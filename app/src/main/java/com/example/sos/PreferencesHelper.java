package com.example.sos;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

    private static final String PREF_NAME = "sos_app_prefs";

    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_SOS_MESSAGE = "sos_message";
    private static final String KEY_SHAKE_ENABLED = "shake_enabled";
    private static final String KEY_POWER_BUTTON_ENABLED = "power_button_enabled";
    private static final String KEY_POWER_PRESS_COUNT = "power_press_count";


    private final SharedPreferences sharedPreferences;

    private final SharedPreferences.Editor editor;

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Phone Number
    public void savePhoneNumber(String number) {
        editor.putString(KEY_PHONE_NUMBER, number).apply();
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, null);
    }

    public void clearPhoneNumber() {
        editor.remove(KEY_PHONE_NUMBER).apply();
    }

    // SOS Message
    public void saveSOSMessage(String message) {
        editor.putString(KEY_SOS_MESSAGE, message).apply();
    }

    public String getSOSMessage() {
        return sharedPreferences.getString(KEY_SOS_MESSAGE, "I need help! This is an emergency.");
    }

    // Shake Detection
    public void setShakeEnabled(boolean enabled) {
        editor.putBoolean(KEY_SHAKE_ENABLED, enabled).apply();
    }

    public boolean isShakeEnabled() {
        return sharedPreferences.getBoolean(KEY_SHAKE_ENABLED, true);
    }

    // Power Button Trigger
    public void setPowerButtonEnabled(boolean enabled) {
        editor.putBoolean(KEY_POWER_BUTTON_ENABLED, enabled).apply();
    }

    public boolean isPowerButtonEnabled() {
        return sharedPreferences.getBoolean(KEY_POWER_BUTTON_ENABLED, true); // ✅ Fixed: was using invalid variable
    }

    // Power Button Press Count
    public void setPowerPressCount(int count) {
        editor.putInt(KEY_POWER_PRESS_COUNT, count).apply();
    }

    public int getPowerPressCount() {
        return sharedPreferences.getInt(KEY_POWER_PRESS_COUNT, 4);
    }
}
