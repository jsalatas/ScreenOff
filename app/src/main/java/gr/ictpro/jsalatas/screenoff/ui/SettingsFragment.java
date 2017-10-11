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

package gr.ictpro.jsalatas.screenoff.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import gr.ictpro.jsalatas.screenoff.R;
import gr.ictpro.jsalatas.screenoff.application.ScreenOffApplication;

@SuppressWarnings("WeakerAccess")
public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        NumberPicker pickerTimeout = (NumberPicker ) view.findViewById(R.id.picker_timeout);
        pickerTimeout.setMinValue(1);
        pickerTimeout.setMaxValue(15);
        pickerTimeout.setValue(ScreenOffApplication.getSettings().getTimeout());
        pickerTimeout.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                ScreenOffApplication.getSettings().setTimeout(newVal);
            }
        });
        return view;
    }
}
