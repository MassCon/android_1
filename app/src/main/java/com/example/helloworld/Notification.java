package com.example.helloworld;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class Notification {

    private Context context;
    private static final String CHANNEL_ID = "my_channel_id";

    public Notification(Context context) {
        this.context = context;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "My Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("NotificationPermission")
    public void showNotification(String title, String message) {
        RemoteViews customView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);

        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent action1 = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        customView.setOnClickPendingIntent(R.id.button1, action1);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.charger)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(customView);

        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.notify(1, builder.build());
    }
}