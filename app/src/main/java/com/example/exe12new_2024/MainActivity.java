package com.example.exe12new_2024;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.exe12new_2024.Receivers.AlarmReceiver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    boolean temp;
    int rqstcode;
    AlarmManager alarmMgr;
    PendingIntent rephour, repday;
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
        SharedPreferences rc = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor edit = rc.edit();
        rqstcode = rc.getInt("rqstcode",0);
        edit.putInt("rqstcode", rqstcode+1);
        edit.commit();

        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Choose time");
        timePickerDialog.show();
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        /**
         * onTimeSet method
         * <p> Return the time of day picked by the user
         * </p>
         *
         * @param view the time picker view that triggered the method
         * @param hourOfDay the hour the user picked
         * @param minute the minute the user picked
         */
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
            }
            setAlarm(calSet);
        }
    };

    /**
     * setAlarm method
     * <p> Set the alarm by the time of day thr user picked
     * </p>
     *
     * @param calSet the Calendar object that represent the tod the user selected
     */
    private void setAlarm(Calendar calSet) {
        rqstcode++;
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("msg",String.valueOf(rqstcode)+" TOD");

        repday = PendingIntent.getBroadcast(this,
                rqstcode, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr.cancel(rephour);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(),AlarmManager.INTERVAL_DAY, repday);
        Toast.makeText(this, String.valueOf(rqstcode)+" Alarm in "+String.valueOf(calSet.getTime())+", Daily", Toast.LENGTH_SHORT).show();
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