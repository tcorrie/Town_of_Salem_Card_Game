package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page5 extends AppCompatActivity {
    TextView pirateName;
    Spinner target;
    RadioGroup selection;
    Button attack, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page5);

        pirateName = findViewById(R.id.pirateName);
        pirateName.setText(Objects.requireNonNull(Metadata.findPerson("Role", "Pirate")).getName());
        target = findViewById(R.id.duelDrop);
        final ArrayAdapter<String> adapter = getStringArrayAdapter();
        target.setAdapter(adapter);

        selection = findViewById(R.id.weaponChoice);
        selection.check(R.id.choiceRapier);


        attack = findViewById(R.id.buttonAttack);
        pass = findViewById(R.id.passButton);

        attack.setOnClickListener(v -> {
            int choice = selection.getCheckedRadioButtonId();
            RadioButton weapon = findViewById(choice);
            String weaponChoice = weapon.getText().toString();
            String pirated = target.getSelectedItem().toString();
            Metadata.lastPirated=Metadata.findPerson("Name",pirated);
            for (Person person:alivePlayers){
                if (Objects.equals(person.getName(),pirated) && person.canBeRoleBlocked()) person.setRoleBlocked(true);

            }
            Intent intent = new Intent(Page5.this, Page6.class);
            intent.putExtra("name", pirated);
            intent.putExtra("weapon", weaponChoice);
            startActivity(intent);
        });

        pass.setOnClickListener(v -> {
            Intent intent = new Intent(Page5.this, Page7.class);
            intent.putExtra("result", "Passed");
            startActivity(intent);
        });



    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter() {
        List<String> targetList = new ArrayList<>();
        for (Person person: alivePlayers) {
            boolean notTheLastPirated = !Objects.equals(person.getName(), Metadata.lastPirated.getName());
            if (!Objects.equals(person.getKeyword(), "Pirate") && notTheLastPirated) targetList.add(person.getName());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
