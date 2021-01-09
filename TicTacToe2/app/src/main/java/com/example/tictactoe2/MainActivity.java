package com.example.tictactoe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar pb = findViewById(R.id.progressBar2);
        pb.setIndeterminate(true);

      /* Intent i = new Intent(MainActivity.this, Game.class);
       startActivity(i);*/

    }
}