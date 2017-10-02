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

import android.app.Application;
import android.content.Context;

public class ScreenOffApplication extends Application{
    private static Context context;
    private static Settings settings;
    @Override
    public void onCreate() {
        super.onCreate();
        ScreenOffApplication.context = this.getApplicationContext();
        ScreenOffApplication.settings = new Settings();
    }

    public static Context getContext() {
        return context;
    }
    public static Settings getSettings() { return settings; }
}
