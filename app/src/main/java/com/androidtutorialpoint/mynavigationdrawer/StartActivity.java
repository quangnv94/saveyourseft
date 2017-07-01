package com.androidtutorialpoint.mynavigationdrawer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Admin on 5/5/2017.
 */

public class StartActivity extends AppCompatActivity {
    private Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        Notification.Builder builder = new Notification.Builder(StartActivity.this);
//
//        builder.setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText("Chọn để mở app nhanh")
//                .setAutoCancel(false);
//        Notification notification = builder.getNotification();
//
//        notification.flags |= Notification.FLAG_NO_CLEAR
//                | Notification.FLAG_ONGOING_EVENT;
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notification);

//        notifyUser(this, "Header", "message");
        sendNotification();
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }

        }, 2500);
    }

    public static void notifyUser(Activity activity, String header,
                                  String message) {
        NotificationManager notificationManager = (NotificationManager) activity
                .getSystemService(Activity.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(
                activity.getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("nq", 1);
        notificationIntent.putExtra("typelogin", bundle);
        TaskStackBuilder stackBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(activity);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder.addParentStack(StartActivity.class);
            stackBuilder.addNextIntent(notificationIntent);
        }

        PendingIntent pIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            pIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(activity)
                    .setContentTitle(header)
                    .setContentText(message)
                    .setDefaults(
                            Notification.DEFAULT_SOUND
                                    | Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pIntent).setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher).build();
        }
        notificationManager.notify(2, notification);
    }

    public void sendNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString("nq", "abc");
        intent.putExtra("typelogin", bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Chọn để mở app nhanh và gửi cảnh báo")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }
}
