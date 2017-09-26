package gr.ictpro.jsalatas.screenoff.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.voice.VoiceInteractionSession;
import android.service.voice.VoiceInteractionSessionService;
import android.util.Log;

public class ScreenOffSessionService extends VoiceInteractionSessionService {
    public ScreenOffSessionService() {
    }

    @Override
    public VoiceInteractionSession onNewSession(Bundle args) {
        return new ScreenOffSession(this);
    }
}
