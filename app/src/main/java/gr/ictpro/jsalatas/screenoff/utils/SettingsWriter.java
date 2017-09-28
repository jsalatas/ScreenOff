package gr.ictpro.jsalatas.screenoff.utils;

import android.provider.Settings;
import gr.ictpro.jsalatas.screenoff.application.ScreenOffApplication;

public class SettingsWriter {
    private static SettingsWriter instance;

    private int screenTimeout;

    public static synchronized SettingsWriter getInstance() {
        if(instance == null) {
            instance = new SettingsWriter();
        }
        return instance;
    }

    private SettingsWriter() {
        initScreenTimeout();
    }

    public void resetScreenTimeout() {
            Settings.System.putInt(ScreenOffApplication.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, screenTimeout);
    }

    public void setScreenTimeout(int s) {
            Settings.System.putInt(ScreenOffApplication.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, s);
    }

    private void initScreenTimeout() {
        try {
            int newValue = Settings.System.getInt(ScreenOffApplication.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
            if(newValue != 0) {
                screenTimeout = newValue;
            }
        } catch (Settings.SettingNotFoundException ex) {
            // Do nothing
        }

    }
}
