package com.example.timothy.town_of_salem_card_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page12 extends AppCompatActivity {
    TextView skName;
    Spinner stbDrop;
    Button nButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page12);

        skName = findViewById(R.id.serialkillerName);
        stbDrop = findViewById(R.id.stabDrop);
        nButton = findViewById(R.id.nextButton12);

        Person serialKiller = Metadata.findPerson("Role", "Serial Killer");

        assert serialKiller != null;
        skName.setText(serialKiller.getName());

        List<String> targetList = new ArrayList<>();
        if (!serialKiller.isRoleBlocked){
            targetList.add("Pass");
            for (Person person: alivePlayers) {
                if (!Objects.equals(person.getKeyword(), "Serial Killer")) targetList.add(person.getName());
            }

        }
        else targetList.add("Roleblocked");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stbDrop.setAdapter(adapter);

        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = stbDrop.getSelectedItem().toString();
                if (selection.equals("Roleblocked")){
                    for (Person person: alivePlayers){
                        if(Objects.equals(person.getKeyword(), "Pirate")) person.addStatus("stabbed");
                        if(Objects.equals(person.getKeyword(),"Serial Killer")) person.addTarget(Metadata.findPerson("Role","Pirate"));
                    }
                }
                else{
                    for (Person person: alivePlayers){
                        if(Objects.equals(person.getName(), selection)) person.addStatus("stabbed");
                        if(Objects.equals(person.getKeyword(),"Serial Killer")) person.addTarget(Metadata.findPerson("Name",selection));
                    }
                }
                startActivity(RoleList.toPage(Page12.this,"Werewolf"));
            }
        });






    }
}
