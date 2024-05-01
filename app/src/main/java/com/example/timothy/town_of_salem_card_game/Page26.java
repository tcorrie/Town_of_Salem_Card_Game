package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page26 extends AppCompatActivity {

    TextView line1, line2;
    Button contButton, egButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page26);
        line1 = findViewById(R.id.hasbeenlynched26);
        line2 = findViewById(R.id.rolewas26);
        contButton = findViewById(R.id.continueButton26);
        egButton = findViewById(R.id.endgameButton26);

        Intent lastIntent = getIntent();
        final String[] lynch = lastIntent.getStringArrayExtra("lynched");
        String name = lynch[0];
        String role = lynch[1];

        line1.setText(String.format("%s has been lynched.",name));
        line2.setText(String.format("%s was the %s.",name,role));
        Metadata.resetMafia();



        contButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Metadata.incrementNight();
                boolean isThereMafia = Metadata.mafiaAlive.size()>0;
                boolean isTheWWAlive = RoleList.stillAlive(RoleList.werewolf);
                boolean isTheSKAlive = RoleList.stillAlive(RoleList.serial_killer);
                boolean isTheWitchAlive = RoleList.stillAlive(RoleList.witch);
                boolean isTheAmnesiacAlive = RoleList.stillAlive(RoleList.amnesiac);
                boolean atLeastTwoPeople = alivePlayers.size()>=2;
                if ((isThereMafia || isTheWWAlive || isTheSKAlive || isTheWitchAlive || isTheAmnesiacAlive) && atLeastTwoPeople) {
                    Intent intent = new Intent(Page26.this, Page4.class);
                    startActivity(intent);
                }
                else Toast.makeText(Page26.this,"The game has ended. Please tap END GAME to finish.", Toast.LENGTH_LONG).show();
            }
        });


        egButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Page26.this, Page28.class);
                startActivity(intent);
            }
        });


    }
}
