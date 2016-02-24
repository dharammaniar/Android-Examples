package com.dharammaniar.androidexamples;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * @author dharam
 */
public class Service1 extends Service {

    private Context mContext;
    private static final String TAG = "Service1";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
