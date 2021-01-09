package com.example.tictactoe2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Game extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://playernames-default-rtdb.firebaseio.com/");
    DatabaseReference dbRef = database.getReference();

    Button [] buttons = new Button[9];

    int p1ScoreCount, p2ScoreCount, rountCount;
    boolean activePlayer;
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView p1n = findViewById(R.id.p1);
        TextView p2n = findViewById(R.id.p2);


        p1n.setText(getIntent().getStringExtra("player1"));
        p2n.setText(getIntent().getStringExtra("player2"));
        p2n.setTextColor(Color.parseColor("#61b15a"));


//        Query pp = dbRef.child("Players");
//
//
//
//        pp.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot nsnap: dataSnapshot.getChildren()) {
//                        String p1 = (String) nsnap.child("1").getValue();
//                        String p2 = (String) nsnap.child("2").getValue();
//
//                        p1n.setText(p1);
//                        p2n.setText(p2);
//                    }
//
//                }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(Game.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        XoGame();


    }




    public void XoGame(){
        Button resetGame = findViewById(R.id.resetb);

        TextView p1s = findViewById(R.id.p1score);
        TextView p2s = findViewById(R.id.p2score);

        TextView p1n = findViewById(R.id.p1);
        TextView p2n = findViewById(R.id.p2);

        for(int i=0; i<buttons.length; i++){
            String buttonID = "btn"+i;
            int resourseID = getResources().getIdentifier(buttonID,"id", getPackageName());
            buttons[i] = (Button) findViewById(resourseID);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!((Button)v).getText().toString().equals(""))
                        return;

                    String buttonID = v.getResources().getResourceEntryName(v.getId());
                    int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

                    if(activePlayer){
                        p1n.setTextColor(Color.parseColor("#000000"));
                        p2n.setTextColor(Color.parseColor("#61b15a"));
                        ((Button)v).setText("X");
                        ((Button)v).setTextColor(Color.parseColor("#333333"));
                        gameState[gameStatePointer] = 0;

                    }
                    else{
                        p2n.setTextColor(Color.parseColor("#000000"));
                        p1n.setTextColor(Color.parseColor("#61b15a"));
                        ((Button)v).setText("O");
                        ((Button)v).setTextColor(Color.parseColor("#ff4682"));
                        gameState[gameStatePointer] = 1;

                    }
                    rountCount++;

                    if(checkWinner()) {
                        if (activePlayer) {
                            p1ScoreCount++;
                            updatePlayerScore();
                            Toast t1 = Toast.makeText(Game.this, p1n.getText().toString()+" Won!", Toast.LENGTH_LONG);
                            t1.setGravity(Gravity.CENTER, 0, 0);
                            t1.show();
                            //make all other buttons disabled
                            for(int i=0; i<buttons.length; i++){

                                    buttons[i].setEnabled(false);
                            }

                           // playAgain();
                        }
                        else{
                            p2ScoreCount++;
                            updatePlayerScore();
                            Toast t1 = Toast.makeText(Game.this, p2n.getText().toString()+" Won!", Toast.LENGTH_LONG);
                            t1.setGravity(Gravity.CENTER, 0, 0);
                            t1.show();
                            for(int i=0; i<buttons.length; i++){

                                    buttons[i].setEnabled(false);


                            }
                           // playAgain();

                        }
                    }
                    else if (rountCount == 9){
                       // playAgain();
                        Toast t1 = Toast.makeText(Game.this, "No Winner!", Toast.LENGTH_LONG);
                        t1.setGravity(Gravity.CENTER, 0, 0);
                        t1.show();
                    }
                    else{
                        activePlayer = !activePlayer;
                    }


                    resetGame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i=0; i<buttons.length; i++){
                                buttons[i].setEnabled(true);
                            }
                            playAgain();

                        }
                    });


                }
                public boolean checkWinner(){
                    boolean winnerResult = false;

                    for(int [] winPoaition : winPositions){
                        if(gameState[winPoaition[0]] == gameState[winPoaition[1]] &&
                                gameState[winPoaition[1]] == gameState[winPoaition[2]] &&
                                gameState[winPoaition[0]] != 2){
                            winnerResult = true;

                        }
                    }
                    return winnerResult;
                }
                public void updatePlayerScore(){

                    p1s.setText(Integer.toString(p1ScoreCount));
                    p2s.setText(Integer.toString(p2ScoreCount));

                }
                public void playAgain(){
                    rountCount = 0;
                    activePlayer = true;
                    for(int i=0; i<buttons.length; i++){
                        gameState[i] = 2;
                        buttons[i].setText("");
                        buttons[i].setEnabled(true);
                    }
                }
            });
        }
    }

}