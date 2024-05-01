package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Page7 extends AppCompatActivity {
    TextView duelResult;
    Button nextPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page7);

        duelResult = findViewById(R.id.messageResult);
        nextPerson = findViewById(R.id.nextButton7);
        Intent lastIntent = getIntent();
        String pirateAction = lastIntent.getStringExtra("result");
        String defenderName = lastIntent.getStringExtra("defender");

        if (pirateAction.equals("attack")){
            String weapon = lastIntent.getStringArrayExtra("battle")[0];
            String defense = lastIntent.getStringArrayExtra("battle")[1];
            boolean pirateVictory;
            if (weapon.equals("Rapier") && defense.equals("Chainmail")) pirateVictory = true;
            else if (weapon.equals("Pistol") && defense.equals("Backpedal")) pirateVictory = true;
            else pirateVictory = weapon.equals("Scimitar") && defense.equals("Sidestep");
            if (pirateVictory){
                duelResult.setText(String.format("The pirate has won the battle against %s.", defenderName));
                for (Person person:Metadata.alivePlayers){
                    if (person.getName().equals(defenderName)) person.addStatus("plunder");
                    if (person.getKeyword().equals("Pirate")){
                        person.addTarget(Metadata.findPerson("Name",defenderName));
                    }
                }
                Metadata.addPirateWin();

            }
            else duelResult.setText(String.format("The pirate has lost the battle against %s.", defenderName));
            duelResult.append(String.format("\n\nPirate Wins: %s",Metadata.pirateWins));
        }
        else if (pirateAction.equals("Passed")) duelResult.setText((String)"The pirate decided not to attack.");

        nextPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RoleList.toPage(Page7.this,"Medium"));
            }
        });

    }
}
