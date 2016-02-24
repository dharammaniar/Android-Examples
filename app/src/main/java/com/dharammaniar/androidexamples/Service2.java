package com.dharammaniar.androidexamples;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * @author dharam
 */
public class Service2 extends IntentService {

    private Context mContext;
    private static final String TAG = "Service2";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Service2(String name) {
        super(name);
    }

    public Service2() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        Toast.makeText(mContext, TAG + ", onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(mContext, TAG + ", onDestroy", Toast.LENGTH_SHORT).show();
    }
}
