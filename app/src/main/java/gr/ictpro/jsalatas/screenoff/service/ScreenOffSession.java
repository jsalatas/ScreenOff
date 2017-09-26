package gr.ictpro.jsalatas.screenoff.service;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import gr.ictpro.jsalatas.screenoff.ui.ScreenOffActivity;

public class ScreenOffSession extends VoiceInteractionSession {
    public ScreenOffSession(Context context) {
        super(context);
    }

    @Override
    public void onHandleAssist(Bundle data, AssistStructure structure, AssistContent content) {
        super.onHandleAssist(data, structure, content);
        Intent screenOffActivity = new Intent(getContext(), ScreenOffActivity.class);
        startVoiceActivity(screenOffActivity);
    }
}
