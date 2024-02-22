package com.example.exe12new_2024.Receivers;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class NotifReactSnooze extends BroadcastReceiver {
    int rqstcode;
    PendingIntent alarmIntent;
    AlarmManager alarmMgr;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Snooze","Snooze");
        SharedPreferences rc = context.getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor edit = rc.edit();
        rqstcode = rc.getInt("rqstcode",0);
        edit.putInt("rqstcode", rqstcode+1);
        edit.commit();


        Intent intenttimer = new Intent(context, AlarmReceiver.class);
        intent.putExtra("msg",String.valueOf(rqstcode)+" 5 Minutes");
        alarmIntent = PendingIntent.getBroadcast(context,
                rqstcode, intenttimer, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 5*60*1000, alarmIntent);
        Toast.makeText(context, "Snoozed", Toast.LENGTH_SHORT).show();
    }
}