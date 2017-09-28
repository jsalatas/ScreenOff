package gr.ictpro.jsalatas.screenoff.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class VoiceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent screenOffActivity = new Intent(getApplicationContext(), ScreenOffActivity.class);
        startActivity(screenOffActivity);

        finish();
    }
}
