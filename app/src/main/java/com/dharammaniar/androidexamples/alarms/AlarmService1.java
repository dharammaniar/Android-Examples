package com.dharammaniar.androidexamples.alarms;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import java.util.Date;

/**
 * @author dharam
 */
public class AlarmService1 extends IntentService {

    private final static String TAG = "AlarmService1";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AlarmService1(String name) {
        super(name);
    }
    public AlarmService1() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), TAG + ", " + new Date(), Toast.LENGTH_SHORT).show();
    }
}
