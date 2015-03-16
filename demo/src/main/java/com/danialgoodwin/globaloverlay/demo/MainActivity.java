package com.danialgoodwin.globaloverlay.demo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Button mStartOverlayButton;

    private void assignViews() {
        mStartOverlayButton = (Button) findViewById(R.id.startOverlayButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();

        mStartOverlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOverlay();
            }
        });
    }

    private void startOverlay() {
        startService(new Intent(this, SimpleOverlayService.class));
    }

}
