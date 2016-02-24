package com.dharammaniar.androidexamples.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dharammaniar.androidexamples.R;

import java.util.Calendar;
import java.util.Date;

/**
 * @author dharam
 */
public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private Button alarmService1;
    private Button alarmService2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        initViews();
    }

    private void initViews() {
        alarmService1 = (Button) findViewById(R.id.activity_alarm_alarmService1);
        alarmService1.setOnClickListener(this);
        alarmService2 = (Button) findViewById(R.id.activity_alarm_alarmService2);
        alarmService2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == alarmService1.getId()) {
            Intent alarmServiceIntent = new Intent(getApplicationContext(), AlarmService1.class);

            PendingIntent mAlarmSender = PendingIntent.getService(getApplicationContext(), 12345, alarmServiceIntent, 0);

            // We want the alarm to go off at the start of the next minute
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MINUTE, 1);
            calendar.set(Calendar.SECOND, 0);
            calendar.getTimeInMillis();
            long scheduleTime = calendar.getTimeInMillis();

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.cancel(mAlarmSender);
            am.set(AlarmManager.RTC_WAKEUP, scheduleTime, mAlarmSender);
            Toast.makeText(getApplicationContext(), "Scheduled One Time Alarm for " + new Date(scheduleTime), Toast.LENGTH_SHORT).show();
        }

        if (v.getId() == alarmService2.getId()) {
            Intent alarmServiceIntent = new Intent(getApplicationContext(), AlarmService1.class);

            PendingIntent mAlarmSender = PendingIntent.getService(getApplicationContext(), 12345, alarmServiceIntent, 0);

            // We want the alarm to go off at the start of the next minute
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MINUTE, 1);
            calendar.set(Calendar.SECOND, 0);
            calendar.getTimeInMillis();
            long scheduleTime = calendar.getTimeInMillis();

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.cancel(mAlarmSender);
            am.setRepeating(AlarmManager.RTC_WAKEUP, scheduleTime, 60 * 1000, mAlarmSender);
            Toast.makeText(getApplicationContext(), "Scheduled Repeating Alarm for " + new Date(scheduleTime), Toast.LENGTH_SHORT).show();
        }
    }
}
