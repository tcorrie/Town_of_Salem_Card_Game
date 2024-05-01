package com.example.timothy.town_of_salem_card_game;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Objects;

public class Page1 extends AppCompatActivity {

    Button startGameButton, loadTeamButton;
    Spinner premadeGroupLists;

    AssetManager am;
    ArrayList<String> sampleGames;
    InputStream is;
    BufferedReader br;
    StringBuilder sb;
    String line;
    String[] lines;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        startGameButton = findViewById(R.id.startGameButton);
        loadTeamButton = findViewById(R.id.loadButton);
        premadeGroupLists = findViewById(R.id.preMadeListDrop);

        am = getAssets();

        try {
            sampleGames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(am.list(""))));
            ListIterator<String> li = sampleGames.listIterator();
            while(li.hasNext()){
                if (!li.next().contains(".txt")) li.remove();
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sampleGames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        premadeGroupLists.setAdapter(adapter);


        startGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(Page1.this,Page2.class);
            startActivity(intent);
        });

        loadTeamButton.setOnClickListener(v -> {
            try {
                sb = new StringBuilder();
                is = am.open(premadeGroupLists.getSelectedItem().toString());
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null){
                    sb.append(line).append(System.lineSeparator());
                }
                line = sb.toString();
                lines = line.split(System.lineSeparator());

                for (String l:lines){
                    String[] player = l.split(",");
                    Person person = new Person(player[0], Objects.requireNonNull(RoleList.findRole(player[1])));
                    Metadata.allPlayers.add(person);
                    if (Objects.equals(person.getAlignment(), "Mafia")) Metadata.mafiaAlive.add(person);
                }

            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
            Intent intent = new Intent(Page1.this, Page3.class);
            startActivity(intent);

        });







    }


}
