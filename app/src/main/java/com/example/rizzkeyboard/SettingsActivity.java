package com.example.rizzkeyboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "RizzKeyboardPrefs";
    private static final String PREF_VIBRATION = "pref_vibration";
    private static final String PREF_SOUND = "pref_sound";
    private static final String PREF_AI_SETTINGS = "pref_ai_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    public void setVibration(boolean vibration) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_VIBRATION, vibration);
        editor.apply();
    }

    public boolean isVibrationEnabled() {
        return sharedPreferences.getBoolean(PREF_VIBRATION, false);
    }

    public void setSound(boolean sound) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_SOUND, sound);
        editor.apply();
    }

    public boolean isSoundEnabled() {
        return sharedPreferences.getBoolean(PREF_SOUND, false);
    }

    public void setAISettings(boolean aiEnabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_AI_SETTINGS, aiEnabled);
        editor.apply();
    }

    public boolean isAIEnabled() {
        return sharedPreferences.getBoolean(PREF_AI_SETTINGS, false);
    }
}