package gr.ictpro.jsalatas.screenoff.task;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

public class ScreenOffTask extends AsyncTask<Void, Void, Void> {
    private ContentResolver contentResolver;
    private TaskCompletedListener taskCompletedListener;

    public ScreenOffTask(ContentResolver contentResolver, TaskCompletedListener taskCompletedListener) {
        this.contentResolver = contentResolver;
        this.taskCompletedListener = taskCompletedListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        synchronized (this) {
            try {
                Settings.System.putInt(contentResolver,
                        Settings.System.SCREEN_OFF_TIMEOUT, 0);

                wait(3000);

            } catch (InterruptedException e) {
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        taskCompletedListener.taskCompleted();
    }
}
