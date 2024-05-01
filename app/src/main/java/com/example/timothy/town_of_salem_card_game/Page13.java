package com.example.timothy.town_of_salem_card_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page13 extends AppCompatActivity {
    TextView wwName;
    Spinner mlDrop1, mlDrop2;
    Button nButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page13);

        wwName = findViewById(R.id.werewolfName);
        mlDrop1 = findViewById(R.id.maulDrop1);
        mlDrop2 = findViewById(R.id.maulDrop2);
        nButton = findViewById(R.id.nextButton13);

        Person werewolf = Metadata.findPerson("Role", "Werewolf");

        assert werewolf != null;
        wwName.setText(werewolf.getName());

        List<String> targetList = new ArrayList<>();
        if (!werewolf.isRoleBlocked){
            targetList.add("Pass");
            for (Person person: alivePlayers) {
                if (!Objects.equals(person.getKeyword(), "Werewolf")) targetList.add(person.getName());
            }

        }
        else targetList.add("Roleblocked");
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mlDrop1.setAdapter(adapter1);
        mlDrop2.setAdapter(adapter2);

        nButton.setOnClickListener(v -> {
            String selection1 = mlDrop1.getSelectedItem().toString();
            String selection2 = mlDrop2.getSelectedItem().toString();
            if (selection1.equals(selection2)) {
                if (!selection1.equals("Pass")) Toast.makeText(Page13.this, "Selections cannot be the same.", Toast.LENGTH_SHORT).show();
                else startActivity(RoleList.toPage(Page13.this,"Witch"));
            }
            else {
                if (!selection1.equals("Roleblocked")) for (Person person : alivePlayers) {
                    if (Objects.equals(person.getName(), selection1))
                        person.addStatus("mauled");
                    if (Objects.equals(person.getName(), selection2))
                        person.addStatus("mauled");
                    if (Objects.equals(person.getKeyword(), "Werewolf")) {
                        person.addTarget(Metadata.findPerson("Name", selection1));
                        person.addTarget(Metadata.findPerson("Name", selection2));
                    }
                }
                startActivity(RoleList.toPage(Page13.this,"Witch"));
            }
        });



    }
}
