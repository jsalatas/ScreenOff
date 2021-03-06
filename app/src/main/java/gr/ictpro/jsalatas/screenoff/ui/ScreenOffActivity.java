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

import android.accessibilityservice.AccessibilityService;
import android.app.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Toast;
import gr.ictpro.jsalatas.screenoff.R;
import gr.ictpro.jsalatas.screenoff.application.ScreenOffApplication;
import gr.ictpro.jsalatas.screenoff.service.GlobalActionService;
import gr.ictpro.jsalatas.screenoff.task.ScreenOffTask;
import gr.ictpro.jsalatas.screenoff.utils.SettingsWriter;

public class ScreenOffActivity extends AppCompatActivity implements PermissionsDialog.PermissionsDialogListener {
    private boolean resetTimer;
    private boolean grantingPermissions = false;
    private static GlobalActionService actionService;

    public static void setActionService(GlobalActionService actionService) {
        ScreenOffActivity.actionService = actionService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ScreenOffActivity.canUseLockScreenGlobalAction()) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ScreenOffActivity.actionService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
                    finishAndRemoveTask();
                }
            }, 500);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                fullScreen();
            }
        });
    }

    public static boolean canUseLockScreenGlobalAction() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && actionService != null;
    }

    private boolean checkPermissions() {
        if(ScreenOffActivity.canUseLockScreenGlobalAction()) {
            // do nothing
        } else if (!Settings.System.canWrite(ScreenOffApplication.getContext())) {
            grantingPermissions = true;

            DialogFragment dialog = new PermissionsDialog();
            dialog.setCancelable(false);
            dialog.show(getFragmentManager(), "PermissionsDialog");

            return false;
        }

        return true;
    }

    private void screenOff() {
        if(ScreenOffActivity.canUseLockScreenGlobalAction()) {
            ScreenOffActivity.actionService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
        } else if (checkPermissions()) {
            final WindowManager.LayoutParams winParams = getWindow().getAttributes();
            winParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
            winParams.buttonBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
            getWindow().setAttributes(winParams);

            resetTimer = false;
            ScreenOffTask task = new ScreenOffTask(this);
            task.execute();
        }
    }

    public void taskCompleted() {
        finish();
    }

    public boolean isResetTimer() {
        return resetTimer;
    }

    public void setResetTimer(boolean resetTimer) {
        this.resetTimer = resetTimer;
    }

    @Override
    protected void onDestroy() {
        if(!ScreenOffActivity.canUseLockScreenGlobalAction()) {
            SettingsWriter.getInstance().restoreTimeout();
        }
        super.onDestroy();
        finish();
    }

    @Override
    public void finish() {
        super.finishAndRemoveTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!grantingPermissions || ScreenOffActivity.canUseLockScreenGlobalAction()) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();
        grantingPermissions = false;
        screenOff();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        resetTimer = true;
        return super.dispatchTouchEvent(event);
    }

    private void fullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetTimer = true;
    }

    @Override
    public void onOkClick() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + this.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ScreenOffApplication.getContext(), R.string.toast_message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCancelClick() {
        finish();
    }
}