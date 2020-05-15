package com.example.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.TelephonyManager;

import java.util.Objects;

public class CallReceiver extends BroadcastReceiver {
    public boolean incomingCall = false;
    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).equals("android.intent.action.PHONE_STATE")) {
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (phoneState != null && phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                incomingCall = true;
            } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                incomingCall = false;
            }
            if (incomingCall == true) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
