package com.example.timothy.town_of_salem_card_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page21 extends AppCompatActivity {

    TextView exeName;
    Spinner tgtDrop;
    Button nButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page21);


        exeName = findViewById(R.id.executionerName);
        tgtDrop = findViewById(R.id.targetDrop);
        nButton = findViewById(R.id.nextButton21);

        Person executioner = Metadata.findPerson("Role", "Executioner");

        assert executioner != null;
        exeName.setText(executioner.getName());


        List<String> targetList = new ArrayList<>();

        targetList.add("Pass");
        for (Person person: alivePlayers) {
            if (!Objects.equals(person.getKeyword(), "Executioner")) {
                targetList.add(person.getName());
            }
        }



        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tgtDrop.setAdapter(adapter);

        nButton.setOnClickListener(v -> {
            String selection = tgtDrop.getSelectedItem().toString();
            if (!selection.equals("Pass")){
                for (Person person: alivePlayers){
                    if(Objects.equals(person.getName(), selection)) Metadata.setExeTarget(person);
                }

            }
            startActivity(RoleList.toPage(Page21.this,"Amnesiac"));
        });


    }
}
