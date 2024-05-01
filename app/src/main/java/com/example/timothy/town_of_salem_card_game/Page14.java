package com.example.timothy.town_of_salem_card_game;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page14 extends AppCompatActivity {

    TextView wchName;
    Spinner crsDrop;
    Button nButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page14);

        wchName = findViewById(R.id.witchName);
        crsDrop = findViewById(R.id.curseDrop);
        nButton = findViewById(R.id.nextButton14);

        Person witch = Metadata.findPerson("Role", "Witch");

        assert witch != null;
        wchName.setText(witch.getName());

        final ArrayAdapter<String> adapter = getStringArrayAdapter(witch);
        crsDrop.setAdapter(adapter);

        nButton.setOnClickListener(v -> {
            String selection = crsDrop.getSelectedItem().toString();
            if (!selection.equals("Roleblocked")){
                for (Person person: alivePlayers){
                    if(Objects.equals(person.getName(), selection)) person.addStatus("hex");
                    if(Objects.equals(person.getKeyword(), "Witch")) person.addTarget(Metadata.findPerson("Name",selection));
                }
            }
            startActivity(RoleList.toPage(Page14.this,"Vigilante"));
        });

    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter(Person witch) {
        List<String> targetList = new ArrayList<>();
        if (!witch.isRoleBlocked){
            targetList.add("Pass");
            for (Person person: alivePlayers) {
                if (!Objects.equals(person.getKeyword(), "Witch") && !person.getCursed()) {
                    targetList.add(person.getName());
                }
            }

        }
        else targetList.add("Roleblocked");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
