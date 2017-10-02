/*
  Screen Off: to turn off and lock the screen of any Android 6.0
  (Marshmallow) or 7.0 (Nougat) device, in a way that it can be
  unlocked without violating the security policy that requires a
  PIN to unlock.
  Copyright (C) 2017 John Salatas

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
