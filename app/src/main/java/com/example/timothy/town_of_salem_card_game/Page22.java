package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
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

public class Page22 extends AppCompatActivity {

    TextView amnName;
    Spinner lrnDrop;
    Button nButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page22);

        amnName = findViewById(R.id.amnesiacName);
        lrnDrop = findViewById(R.id.learnDrop);
        nButton = findViewById(R.id.nextButton22);

        final Person amnesiac = Metadata.findPerson("Role", "Amnesiac");

        assert amnesiac != null;
        amnName.setText(amnesiac.getName());

        List<String> targetList = new ArrayList<>();

        for (Role role: RoleList.role_list) {
            if (!Objects.equals(role.getKeyword(), "Amnesiac")) {
                targetList.add(role.getKeyword());
            }
        }




        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lrnDrop.setAdapter(adapter);

        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = lrnDrop.getSelectedItem().toString();
                    for (Person person: alivePlayers) {
                        if (Objects.equals(person.getKeyword(), "Amnesiac")) {
                            Metadata.updateRole(amnesiac.getName(), selection);
                            break;
                        }
                    }
                    for (Person person: alivePlayers){
                        if (Objects.equals(person.getKeyword(),selection)){
                            int extraUses = RoleList.extraUses(Metadata.numPlayers);
                            switch (person.getKeyword()) {
                                case "Vigilante":
                                    person.setUses(1 + extraUses);
                                    break;
                                case "Investigator":
                                case "Veteran":
                                case "Consigliere":
                                case "Janitor":
                                    person.setUses(2 + extraUses);
                                    break;
                                default:
                                    person.setUses(99);
                                    break;
                            }
                        }
                    }
                Intent intent = new Intent(Page22.this,Page23.class);
                startActivity(intent);
            }
        });











    }
}
