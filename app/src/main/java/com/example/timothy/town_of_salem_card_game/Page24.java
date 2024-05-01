package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class Page24 extends AppCompatActivity {

    TextView dpList, exeDLabel;
    Button nButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page24);
        dpList = findViewById(R.id.deadPlayerList);
        nButton = findViewById(R.id.nextButton24);
        exeDLabel = findViewById(R.id.exeTargetDeadLabel);
        dpList.setText("");
        for(Person person: Metadata.deadPlayers){
            String name = person.getName();
            String kw;
            String deadBy = person.getStatus();

            if (person.isCleaned()) kw = "Cleaned";
            else kw = person.getKeyword();

            dpList.append(String.format("%s (%s) [%s]\n",name,kw,deadBy));
            if (!Objects.equals(Metadata.exeTarget.getName(),"None")){
                if (Objects.equals(person.getName(), Metadata.exeTarget.getName()) && RoleList.stillAlive(RoleList.executioner) && !Metadata.exeWin) {
                    exeDLabel.setText(String.format("Executioner's (%s) target (%s) has died and is now a Jester.", Objects.requireNonNull(Metadata.findPerson("Role", "Executioner")).getName(), person.getName()));
                    Metadata.updateRole(Objects.requireNonNull(Metadata.findPerson("Role", "Executioner")).getName(),"Jester");

                }
            }

        }

        nButton.setOnClickListener(v -> {
            for (Person person: Metadata.alivePlayers){
                person.clearTargets();
                person.clearStatus();
                person.setRoleBlocked(false);
            }
            Metadata.resetMafia();
            Intent intent = new Intent(Page24.this, Page25.class);
            startActivity(intent);
        });

    }
}
