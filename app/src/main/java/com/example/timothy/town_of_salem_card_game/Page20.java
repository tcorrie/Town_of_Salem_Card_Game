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

public class Page20 extends AppCompatActivity {

    TextView bgName;
    Spinner grdDrop;
    Button nButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page20);


        bgName = findViewById(R.id.bodyguardName);
        grdDrop = findViewById(R.id.guardDrop);
        nButton = findViewById(R.id.nextButton20);

        final Person bodyguard = Metadata.findPerson("Role", "Bodyguard");

        assert bodyguard != null;
        bgName.setText(bodyguard.getName());

        List<String> targetList = new ArrayList<>();
        if (!bodyguard.isRoleBlocked){
            targetList.add("Pass");

            for (Person person: alivePlayers) {
                boolean notTheLastGuarded = !Objects.equals(Metadata.lastGuarded.getName(), person.getName());
                if (!Objects.equals(person.getKeyword(), "Bodyguard") && notTheLastGuarded) {
                    targetList.add(person.getName());
                }
            }

        }
        else targetList.add("Roleblocked");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grdDrop.setAdapter(adapter);

        nButton.setOnClickListener(v -> {
            String selection = grdDrop.getSelectedItem().toString();
            if (!selection.equals("Roleblocked") && !selection.equals("Pass")){
                for (Person person: alivePlayers){
                    if(Objects.equals(person.getName(), selection)) person.addStatus("guard");
                    if(Objects.equals(person.getKeyword(), "Bodyguard")) person.addTarget(Metadata.findPerson("Name",selection));
                    Metadata.lastGuarded = Metadata.findPerson("Name",selection);
                }

            }
            else Metadata.lastGuarded=Metadata.noOne;
            startActivity(RoleList.toPage(Page20.this,"Executioner"));
        });


    }
}
