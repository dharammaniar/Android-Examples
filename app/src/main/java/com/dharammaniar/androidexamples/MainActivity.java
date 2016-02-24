package com.dharammaniar.androidexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dharammaniar.androidexamples.services.ServicesActivity;

/**
 *  @author dharam
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button serviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        serviceButton = (Button) findViewById(R.id.activity_main_services);
        serviceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == serviceButton.getId()) {
            Intent servicesIntent = new Intent(MainActivity.this, ServicesActivity.class);
            startActivity(servicesIntent);
        }
    }
}
