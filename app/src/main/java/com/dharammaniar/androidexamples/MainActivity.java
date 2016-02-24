package com.dharammaniar.androidexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dharammaniar.androidexamples.alarms.AlarmActivity;
import com.dharammaniar.androidexamples.gcm.CommunicationMain;
import com.dharammaniar.androidexamples.services.ServicesActivity;

/**
 *  @author dharam
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button serviceButton;
    private Button alarmButton;
    private Button gcmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        serviceButton = (Button) findViewById(R.id.activity_main_services);
        serviceButton.setOnClickListener(this);
        alarmButton = (Button) findViewById(R.id.activity_main_alarms);
        alarmButton.setOnClickListener(this);
        gcmButton = (Button) findViewById(R.id.activity_main_gcm);
        gcmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == serviceButton.getId()) {
            Intent servicesIntent = new Intent(MainActivity.this, ServicesActivity.class);
            startActivity(servicesIntent);
        }
        if (v.getId() == alarmButton.getId()) {
            Intent alarmIntent = new Intent(MainActivity.this, AlarmActivity.class);
            startActivity(alarmIntent);
        }
        if (v.getId() == gcmButton.getId()) {
            Intent gcmIntent = new Intent(MainActivity.this, CommunicationMain.class);
            startActivity(gcmIntent);
        }
    }
}
