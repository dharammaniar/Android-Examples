package com.dharammaniar.androidexamples.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dharammaniar.androidexamples.R;

/**
 * @author dharam
 */
public class ServicesActivity extends AppCompatActivity implements View.OnClickListener{

    private Button service1;
    private Button service2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        initViews();
    }

    private void initViews() {
        service1 = (Button) findViewById(R.id.activity_services_service1);
        service1.setOnClickListener(this);
        service2 = (Button) findViewById(R.id.activity_services_service2);
        service2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == service1.getId()) {
            Intent service1Intent = new Intent(ServicesActivity.this, Service1.class);
            startService(service1Intent);
        }
        if (v.getId() == service2.getId()) {
            Intent service1Intent = new Intent(ServicesActivity.this, Service2.class);
            startService(service1Intent);
        }
    }
}
