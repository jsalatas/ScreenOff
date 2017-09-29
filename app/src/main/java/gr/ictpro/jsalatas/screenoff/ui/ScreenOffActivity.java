package gr.ictpro.jsalatas.screenoff.ui;

import android.app.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Toast;
import gr.ictpro.jsalatas.screenoff.R;
import gr.ictpro.jsalatas.screenoff.application.ScreenOffApplication;
import gr.ictpro.jsalatas.screenoff.task.ScreenOffTask;
import gr.ictpro.jsalatas.screenoff.utils.SettingsWriter;

public class ScreenOffActivity extends AppCompatActivity implements PermissionsDialog.PermissionsDialogListener {
    private boolean resetTimer;
    private boolean grantingPermissions = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    private boolean checkPermissions() {
        if (!Settings.System.canWrite(ScreenOffApplication.getContext())) {
            grantingPermissions = true;

            DialogFragment dialog = new PermissionsDialog();
            dialog.setCancelable(false);
            dialog.show(getFragmentManager(), "PermissionsDialog");

            return false;
        }
        return true;
    }

    private void screenOff() {
        if (checkPermissions()) {
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
        SettingsWriter.getInstance().restoreTimeout();
        super.onDestroy();
        System.exit(0);
    }

    @Override
    public void finish() {
        super.finishAndRemoveTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!grantingPermissions) {
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