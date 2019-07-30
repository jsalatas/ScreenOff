package gr.ictpro.jsalatas.screenoff.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import gr.ictpro.jsalatas.screenoff.ui.ScreenOffActivity;

public class GlobalActionService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // do nothing
    }

    @Override
    public void onInterrupt() {
        // do nothing
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenOffActivity.setActionService(this);
    }

    @Override
    public void onDestroy() {
        ScreenOffActivity.setActionService(null);
        super.onDestroy();
    }
}
