package com.jjickjjicks.wizclock.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class NotificationService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "Service has been called.");
        Toast.makeText(getApplicationContext(), "Service Called!", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
