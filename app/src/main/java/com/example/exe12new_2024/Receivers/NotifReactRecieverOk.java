package com.example.exe12new_2024.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotifReactRecieverOk extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Wake up","Grab a Brush and put a little makeup");
    }
}