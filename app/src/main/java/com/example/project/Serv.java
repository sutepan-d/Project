package com.example.project;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import java.util.Objects;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.TelephonyManager;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class Serv extends Service {
    MediaPlayer mediaPlayer;
    long startTime;
    public Serv() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent back = new Intent(this, MainActivity.class);
        PendingIntent notif = PendingIntent.getActivity(this, 0, back, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "MyNote").setSmallIcon(R.drawable.ic_launcher_background).setContentTitle("Подъём").setContentText("").setContentIntent(notif);
        Notification note = nBuilder.build();
        if (notificationManager != null) {
            notificationManager.notify(4578612, note);
        }
        class CallReceiver extends BroadcastReceiver {
            private boolean incomingCall = false;
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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
