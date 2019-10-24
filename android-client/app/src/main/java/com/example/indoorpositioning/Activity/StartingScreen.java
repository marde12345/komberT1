package com.example.indoorpositioning.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.indoorpositioning.FetchData;
import com.example.indoorpositioning.R;

public class StartingScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen);
    }

    public void onClick(View view) {

        Button button= (Button) view;
        Intent intent;
        switch (button.getId()) {
            case R.id.learn_button:
                intent = new Intent(StartingScreen.this, Buildings.class);
                startActivity(intent);
                break;
            case R.id.locate_button:
                intent = new Intent(StartingScreen.this, Locate.class);
                startActivity(intent);
                break;
            case R.id.sync_button:
                new FetchData(this).execute();
                break;
            default:
                break;

        }

    }

}
