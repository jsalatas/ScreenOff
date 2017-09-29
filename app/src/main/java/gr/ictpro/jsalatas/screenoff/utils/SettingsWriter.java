package gr.ictpro.jsalatas.screenoff.utils;

import android.provider.Settings;
import gr.ictpro.jsalatas.screenoff.application.ScreenOffApplication;

public class SettingsWriter {
    private static SettingsWriter instance;

    private int screenTimeout;
    private String stayAwake;

    public static synchronized SettingsWriter getInstance() {
        if (instance == null) {
            instance = new SettingsWriter();
        }
        return instance;
    }

    private SettingsWriter() {
        saveInitialTimeout();
    }

    public void restoreTimeout() {
        try {
            Settings.System.putInt(ScreenOffApplication.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, screenTimeout);
            Settings.Global.putString(ScreenOffApplication.getContext().getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN, stayAwake);
        } catch (SecurityException ex) {
            // Do Nothing
        }

    }

    public void decreaseTimeout() {
        try {
            Settings.System.putInt(ScreenOffApplication.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 0);
            Settings.Global.putString(ScreenOffApplication.getContext().getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN, "0");
        } catch (SecurityException ex) {
            // Do Nothing
        }
    }

    private void saveInitialTimeout() {
        try {
            int newValue = Settings.System.getInt(ScreenOffApplication.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
            if (newValue != 0) {
                screenTimeout = newValue;
            }
            stayAwake = Settings.Global.getString(ScreenOffApplication.getContext().getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN);
        } catch (Settings.SettingNotFoundException ex) {
            // Do nothing
        }

    }
}
