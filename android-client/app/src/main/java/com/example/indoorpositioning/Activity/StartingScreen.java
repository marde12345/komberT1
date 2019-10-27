package com.example.indoorpositioning.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.indoorpositioning.BaseActivity;
import com.example.indoorpositioning.FetchData;
import com.example.indoorpositioning.R;

public class StartingScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen);
        enableScrollView();
        setTitle("Menu");
        getSupportActionBar().hide();
    }

    public void onClick(View view) {

        Button button= (Button) view;
        Intent intent;
        switch (button.getId()) {
            case R.id.cvTrain:
                intent = new Intent(StartingScreen.this, Buildings.class);
                startActivity(intent);
                break;
            case R.id.cvLocate:
                intent = new Intent(StartingScreen.this, Locate.class);
                startActivity(intent);
                break;
            case R.id.cvSync:
                new FetchData(this).execute();
                break;
            default:
                break;

        }

    }

}
