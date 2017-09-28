package gr.ictpro.jsalatas.screenoff.service;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import gr.ictpro.jsalatas.screenoff.ui.VoiceActivity;

class ScreenOffSession extends VoiceInteractionSession {
    ScreenOffSession(Context context) {
        super(context);
    }

    @Override
    public void onHandleAssist(Bundle data, AssistStructure structure, AssistContent content) {
        super.onHandleAssist(data, structure, content);

        Intent voiceActivity = new Intent(getContext(), VoiceActivity.class);
        startVoiceActivity(voiceActivity);
    }
}
