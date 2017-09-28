package gr.ictpro.jsalatas.screenoff.application;

import android.app.Application;
import android.content.Context;

public class ScreenOffApplication extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        ScreenOffApplication.context = this.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
