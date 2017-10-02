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

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import gr.ictpro.jsalatas.screenoff.R;

@SuppressWarnings("WeakerAccess")
public class PermissionsDialog extends DialogFragment {
    interface PermissionsDialogListener {
        void onOkClick();
        void onCancelClick();
    }

    private PermissionsDialogListener  permissionsDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PermissionsDialogListener) {
            permissionsDialogListener = (PermissionsDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

    }
        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.permissions_dialog, container, false);
        final TextView tvCancel = (TextView)v.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                permissionsDialogListener.onCancelClick();
            }
        });
        TextView tvOk = (TextView)v.findViewById(R.id.tvWriteSettingsPermission);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                permissionsDialogListener.onOkClick();
            }
        });
        return v;
    }

}
