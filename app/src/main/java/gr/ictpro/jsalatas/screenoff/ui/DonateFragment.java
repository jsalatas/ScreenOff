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

package gr.ictpro.jsalatas.screenoff.ui;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import gr.ictpro.jsalatas.screenoff.R;

@SuppressWarnings("WeakerAccess")
public class DonateFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.donate_fragment, container, false);
        TextView tvDonate = (TextView) view.findViewById(R.id.tvDonate);
        tvDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://paypal.me/jsalatas");
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        return view;
    }
}
