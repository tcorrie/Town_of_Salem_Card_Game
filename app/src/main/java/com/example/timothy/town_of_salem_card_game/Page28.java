package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class Page28 extends AppCompatActivity {
    TextView winners;
    Button eButton28;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page28);

        winners = findViewById(R.id.winresult);
        eButton28 = findViewById(R.id.exitButton);



        if (Metadata.pirateWins>=2){
            if (RoleList.stillAlive(RoleList.pirate)) Metadata.winners.add(Metadata.findPerson("Role","Pirate"));
            else Metadata.winners.add(Metadata.findDeadPerson("Role","Pirate"));
        }

        if (Metadata.night<3 && RoleList.stillAlive(RoleList.amnesiac)) Metadata.winners.add(Metadata.findPerson("Role","Amnesiac"));



        if (Metadata.alivePlayers.size()==1){
            String alignment = Metadata.alivePlayers.get(0).getAlignment();
            String roleKW = Metadata.alivePlayers.get(0).getKeyword();

            if (alignment.equals("Town")){
                for (Person person:Metadata.allPlayers){
                    if (Objects.equals(person.getAlignment(),"Town")) Metadata.winners.add(person);
                }
            }
            else if (alignment.equals("Mafia")){
                for (Person person:Metadata.allPlayers){
                    if (Objects.equals(person.getAlignment(),"Mafia")) Metadata.winners.add(person);
                }
            }
            else if (roleKW.equals("Serial Killer")) Metadata.winners.add(Metadata.findPerson("Role", "Serial Killer"));
            else if (roleKW.equals("Werewolf")) Metadata.winners.add(Metadata.findPerson("Role", "Werewolf"));
            else if (roleKW.equals("Witch")) Metadata.winners.add(Metadata.findPerson("Role", "Witch"));
        }
        else if (Metadata.alivePlayers.size()==0) assert true;
        else{
            if (RoleList.stillAlive(RoleList.serial_killer)) Metadata.winners.add(Metadata.findPerson("Role", "Serial Killer"));
            else if (RoleList.stillAlive(RoleList.werewolf)) Metadata.winners.add(Metadata.findPerson("Role", "Werewolf"));
            else {
                int townAlive = 0;
                int mafiaAlive = 0;
                for (Person person:Metadata.alivePlayers){
                    if (Objects.equals(person.getAlignment(),"Town")) townAlive++;
                    if (Objects.equals(person.getAlignment(),"Mafia")) mafiaAlive++;
                }
                if (mafiaAlive>=townAlive){
                    for (Person person:Metadata.allPlayers){
                        if (Objects.equals(person.getAlignment(),"Mafia")) Metadata.winners.add(person);
                    }
                }
                else {
                    for (Person person:Metadata.allPlayers){
                        if (Objects.equals(person.getAlignment(),"Town")) Metadata.winners.add(person);
                    }
                }
            }
        }

        if (Metadata.winners.size()>=1) {
            winners.append("Winners:\n\n");
            for (Person person : Metadata.winners) {
                winners.append(String.format("%s\n", person.description()));
            }
        }
        else winners.append("The game has ended in a draw.");
        eButton28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Page28.this, Page29.class));
                System.exit(0);
            }
        });






    }
}
