package gr.ictpro.jsalatas.screenoff.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import gr.ictpro.jsalatas.screenoff.R;
import gr.ictpro.jsalatas.screenoff.task.ScreenOffTask;
import gr.ictpro.jsalatas.screenoff.utils.SettingsWriter;

public class ScreenOffActivity extends Activity {
    private boolean resetTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        final WindowManager.LayoutParams winParams = getWindow().getAttributes();

        winParams.screenBrightness = 0.01f;
        winParams.buttonBrightness = 0.01f;

        getWindow().setAttributes(winParams);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                fullScreen();
            }
        });
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
        SettingsWriter.getInstance().resetScreenTimeout();
        super.onDestroy();
        System.exit(0);
    }

    @Override
    public void finish() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.finishAndRemoveTask();
//        } else {
//            super.finish();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();

        resetTimer = false;
        ScreenOffTask task = new ScreenOffTask(this);
        task.execute();
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
}

