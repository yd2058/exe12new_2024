package com.example.exe12new_2024.Receivers;

import static android.content.Context.MODE_PRIVATE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    int rqstcode;

    private static final String CHANNEL_ID = "Your_Channel_ID";
    private static final String CHANNEL_NAME = "Your_Channel_Name";
    private static final int NOTIFICATION_ID = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences rc = context.getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor edit = rc.edit();
        rqstcode = rc.getInt("rqstcode",0);
        edit.putInt("rqstcode", rqstcode+1);
        edit.commit();
        Log.i("AlarmReceiver","start");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intentoutOk = new Intent(context, NotifReactRecieverOk.class);
        PendingIntent pIok = PendingIntent.getBroadcast(context,
                0, intentoutOk, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentoutSz = new Intent(context, NotifReactSnooze.class);
        PendingIntent pISz = PendingIntent.getBroadcast(context,
                0, intentoutSz, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Wake Up")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel,
                        "OK", pIok)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel,
                        "Snooze", pISz)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Log.i("AlarmReceiver","end");
    }

    public void test(){Log.i("test", "success!");}
}