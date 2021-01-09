package com.example.tictactoe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlayerNames extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://playernames-default-rtdb.firebaseio.com/");
    DatabaseReference dbRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_names);

        EditText p1Name = findViewById(R.id.player1);
        EditText p2Name = findViewById(R.id.player2);

        Button cont = findViewById(R.id.contbtn);



        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n1 = p1Name.getText().toString();
                String n2 = p2Name.getText().toString();

//
//                dbRef.child("Players").push();
//                dbRef.child("Players").child("1").setValue(n1);
//                dbRef.child("Players").child("2").setValue(n2);


                Intent i = new Intent(PlayerNames.this, Game.class);
                i.putExtra("player1", n1);
                i.putExtra("player2", n2);
                startActivity(i);
            }
        });

    }
}