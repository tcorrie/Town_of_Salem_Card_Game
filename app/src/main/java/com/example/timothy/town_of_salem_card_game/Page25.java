package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;
import static com.example.timothy.town_of_salem_card_game.Metadata.findDeadPerson;
import static com.example.timothy.town_of_salem_card_game.Metadata.findPerson;
import static com.example.timothy.town_of_salem_card_game.Metadata.incrementNight;

public class Page25 extends AppCompatActivity {
    Spinner lchDrop;
    Button contButton, egButton, mRevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page25);
        lchDrop = findViewById(R.id.lynchDrop);
        contButton = findViewById(R.id.continueButton25);
        egButton = findViewById(R.id.endgameButton25);
        mRevButton = findViewById(R.id.mayorRevealButton);


        List<String> targetList = new ArrayList<>();
        targetList.add("Pass");
        for (Person person: alivePlayers) {
            targetList.add(person.getName());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lchDrop.setAdapter(adapter);

        contButton.setOnClickListener(v -> {
            boolean isThereMafia = !Metadata.mafiaAlive.isEmpty();
            boolean isTheWWAlive = RoleList.stillAlive(RoleList.werewolf);
            boolean isTheSKAlive = RoleList.stillAlive(RoleList.serial_killer);
            boolean isTheWitchAlive = RoleList.stillAlive(RoleList.witch);
            boolean isTheAmnesiacAlive = RoleList.stillAlive(RoleList.amnesiac);
            boolean atLeastTwoPeople = alivePlayers.size()>=2;

            if ((isThereMafia || isTheWWAlive || isTheSKAlive || isTheWitchAlive || isTheAmnesiacAlive) && atLeastTwoPeople) {


                String selection = lchDrop.getSelectedItem().toString();

                if (selection.equals("Pass")) {
                    Metadata.resetMafia();
                    incrementNight();
                    Metadata.resetRoleBlocked();
                    Intent intent = new Intent(Page25.this, Page4.class);
                    startActivity(intent);
                } else {
                    Metadata.resetRoleBlocked();
                    Iterator<Person> i = alivePlayers.iterator();
                    String lynchName = "";
                    String lynchRole = "";
                    while (i.hasNext()) {
                        Person p = i.next();
                        if (p.getName().equals(selection)) {
                            lynchName = p.getName();
                            lynchRole = p.getKeyword();
                            p.clearStatus();
                            p.addStatus("Lynch");
                            if (RoleList.exists(RoleList.executioner) && Objects.equals(p.getName(), Metadata.exeTarget.getName())) {
                                Metadata.changeExeWin();
                                Toast.makeText(Page25.this, "Executioner has successfully lynched their target and has won!", Toast.LENGTH_SHORT).show();
                                Metadata.winners.add(findPerson("Role", "Executioner"));
                            }
                            Metadata.deadPlayers.add(p);
                            i.remove();
                        }
                    }
                    Intent intent;
                    if (lynchRole.equals("Jester")) {
                        Metadata.winners.add(findDeadPerson("Name", lynchName));
                        intent = new Intent(Page25.this, Page27.class);
                    } else {
                        intent = new Intent(Page25.this, Page26.class);
                    }
                    intent.putExtra("lynched", new String[]{lynchName, lynchRole});
                    startActivity(intent);
                }
            }
            else Toast.makeText(Page25.this, "The game is over. Please hit END GAME to continue.", Toast.LENGTH_LONG).show();
        });

        if (!RoleList.stillAlive(RoleList.mayor) || Metadata.mayorRevealed) mRevButton.setVisibility(View.INVISIBLE);
        else mRevButton.setVisibility(View.VISIBLE);

        mRevButton.setOnClickListener(v -> {
            Metadata.mayorReveals();
            Toast.makeText(Page25.this, "Mayor has been revealed", Toast.LENGTH_SHORT).show();
            mRevButton.setVisibility(View.INVISIBLE);
        });

        egButton.setOnClickListener(v -> {
            Intent intent = new Intent(Page25.this, Page28.class);
            startActivity(intent);
        });


    }
}
