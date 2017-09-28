package gr.ictpro.jsalatas.screenoff.task;

import android.os.AsyncTask;
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
                SettingsWriter.getInstance().setScreenTimeout(0);

                do {
                    screenOffActivity.setResetTimer(false);
                    wait(3000);
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
