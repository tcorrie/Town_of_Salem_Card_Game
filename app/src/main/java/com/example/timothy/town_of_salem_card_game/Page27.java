package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page27 extends AppCompatActivity {

    TextView line1, line2;
    Button contButton, egButton;
    Spinner hntDrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page27);
        line1 = findViewById(R.id.hasBeenLynched27);
        line2 = findViewById(R.id.roleWas27);
        contButton = findViewById(R.id.continueButton27);
        egButton = findViewById(R.id.endgameButton27);
        hntDrop = findViewById(R.id.hauntDrop);

        Intent lastIntent = getIntent();
        final String[] lynch = lastIntent.getStringArrayExtra("lynched");
        String name = lynch[0];
        String role = lynch[1];

        line1.setText(String.format("%s has been lynched.",name));
        line2.setText(String.format("%s was the %s.",name,role));

        List<String> targetList = new ArrayList<>();
        targetList.add("Pass");
        for (Person person: alivePlayers) {
            targetList.add(person.getName());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hntDrop.setAdapter(adapter);




        contButton.setOnClickListener(v -> {
            Metadata.incrementNight();
            Metadata.resetMafia();
            String selection = hntDrop.getSelectedItem().toString();
            Iterator<Person> i = alivePlayers.iterator();
            while(i.hasNext()) {
                Person p = i.next();
                if (p.getName().equals(selection)) {
                    p.clearStatus();
                    p.addStatus("Haunt");
                    Metadata.deadPlayers.add(p);
                    i.remove();
                }
            }

            Intent intent = new Intent(Page27.this, Page4.class);
            startActivity(intent);
        });


        egButton.setOnClickListener(v -> {
            String selection = hntDrop.getSelectedItem().toString();
            Iterator<Person> i = alivePlayers.iterator();
            while(i.hasNext()) {
                Person p = i.next();
                if (p.getName().equals(selection)) {
                    p.clearStatus();
                    p.addStatus("Haunt");
                    Metadata.deadPlayers.add(p);
                    i.remove();
                }
            }
            Intent intent = new Intent(Page27.this, Page28.class);
            startActivity(intent);
        });
    }
}
