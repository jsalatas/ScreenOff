/*
  Screen Off: turn off and lock the screen of any Android 6.0
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

package gr.ictpro.jsalatas.screenoff.application;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    private static final int DEFAULT_TIME_OUT = 5;
    private static final String TIME_OUT_KEY = "timeout";
    private final SharedPreferences prefs;

    Settings() {
        prefs = PreferenceManager.getDefaultSharedPreferences(ScreenOffApplication.getContext());
    }

    public void setTimeout(int timeout) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(TIME_OUT_KEY, timeout);
        editor.apply();
    }

    public int getTimeout() {
        return prefs.getInt(TIME_OUT_KEY, DEFAULT_TIME_OUT);
    }
}
