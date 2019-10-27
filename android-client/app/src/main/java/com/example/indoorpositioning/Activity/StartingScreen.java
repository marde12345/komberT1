package com.example.indoorpositioning.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.indoorpositioning.BaseActivity;
import com.example.indoorpositioning.FetchData;
import com.example.indoorpositioning.R;

import androidx.cardview.widget.CardView;

public class StartingScreen extends BaseActivity implements View.OnClickListener {
    private CardView cvLocate, cvSubLocate, cvTrain, cvSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen);
        enableScrollView();
        setTitle("Menu");
        getSupportActionBar().hide();

        cvLocate = findViewById(R.id.cvLocate);
        cvTrain = findViewById(R.id.cvTrain);
        cvSync = findViewById(R.id.cvSync);
        cvSubLocate = findViewById(R.id.cvSubLocate);

        cvLocate.setOnClickListener(this);
        cvTrain.setOnClickListener(this);
        cvSync.setOnClickListener(this);
        cvSubLocate.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvLocate:
                startActivity(new Intent(StartingScreen.this, Locate.class));
                break;
            case R.id.cvSubLocate:
                startActivity(new Intent(StartingScreen.this, Locate.class));
                break;
            case R.id.cvTrain:
                startActivity(new Intent(StartingScreen.this, Buildings.class));
                break;
            case R.id.cvSync:
                new FetchData(this).execute();
                break;
            default:
                break;

        }

    }

}
