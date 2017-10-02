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

package gr.ictpro.jsalatas.screenoff.task;

import android.os.AsyncTask;
import gr.ictpro.jsalatas.screenoff.application.ScreenOffApplication;
import gr.ictpro.jsalatas.screenoff.ui.ScreenOffActivity;
import gr.ictpro.jsalatas.screenoff.utils.SettingsWriter;

public class ScreenOffTask extends AsyncTask<Void, Void, Void> {
    private final ScreenOffActivity screenOffActivity;

    public ScreenOffTask(ScreenOffActivity screenOffActivity) {
        this.screenOffActivity = screenOffActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        synchronized (this) {
            try {
                SettingsWriter.getInstance().decreaseTimeout();

                do {
                    screenOffActivity.setResetTimer(false);
                    wait(ScreenOffApplication.getSettings().getTimeout() * 1000);
                } while (screenOffActivity.isResetTimer());

            } catch (InterruptedException e) {
                // Do Nothing
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        screenOffActivity.taskCompleted();
    }
}
