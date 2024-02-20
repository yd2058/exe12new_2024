package com.example.exe12new_2024;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    boolean temp;
    int rqstcode;
    AlarmManager alarmMgr;
    PendingIntent rephour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences firstrun = getSharedPreferences("sp", MODE_PRIVATE);
        temp = firstrun.getBoolean("firstrun", false);
        if(!temp){
            setrepeathour();
        }
    }

    public void settime(View view) {
    }

    public void out(View view) {finish();}

    public void setrepeathour(){
        SharedPreferences rc = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor edit = rc.edit();
        rqstcode = rc.getInt("rqstcode",0);
        edit.putInt("rqstcode", rqstcode+1);
        edit.commit();

        Calendar calnow = Calendar.getInstance();
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("msg", String.valueOf(rqstcode) + " 1 hour");
        rephour = PendingIntent.getBroadcast(this,
                rqstcode, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,calnow.getTimeInMillis(),AlarmManager.INTERVAL_HOUR,rephour);}
}