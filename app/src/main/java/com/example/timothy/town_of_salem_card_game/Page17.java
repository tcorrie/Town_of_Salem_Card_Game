package com.example.timothy.town_of_salem_card_game;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page17 extends AppCompatActivity {

    TextView depName, intRes;
    Spinner intDrop;
    Button nButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page17);

        depName = findViewById(R.id.deputyName);
        intDrop = findViewById(R.id.interrogateDrop2);
        nButton = findViewById(R.id.nextButton17);
        intRes = findViewById(R.id.interrogateResult2);

        Person deputy = Metadata.findPerson("Role", "Deputy");
        assert deputy != null;
        depName.setText(deputy.getName());

        final ArrayAdapter<String> adapter = getStringArrayAdapter(deputy);
        intDrop.setAdapter(adapter);
        intDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String focus = intDrop.getSelectedItem().toString();
                if (focus.equals("Pass")) intRes.setText(R.string.noInterrogateSheriff);
                else if (focus.equals("Roleblocked")) intRes.setText(R.string.roleblockedSheriff);
                else {
                    if(Metadata.night %2 == 0 && Objects.equals(Objects.requireNonNull(Metadata.findPerson("Name", focus)).getKeyword(),"Werewolf")){
                        intRes.setText(R.string.sheriffTargetEvil);
                    }
                    else intRes.setText(String.format("Your target is %s", Objects.requireNonNull(Metadata.findPerson("Name", focus)).getSheriffResult()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing here. Should always have a selection.
            }
        });



        nButton.setOnClickListener(v -> {
            String selection = intDrop.getSelectedItem().toString();
            if (!selection.equals("Roleblocked")){
                for (Person person: alivePlayers){
                    if(Objects.equals(person.getKeyword(), "Deputy")) person.addTarget(Metadata.findPerson("Name",selection));
                }

            }
            startActivity(RoleList.toPage(Page17.this,"Investigator"));
        });




    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter(Person deputy) {
        List<String> targetList = new ArrayList<>();

        if (!deputy.isRoleBlocked){
            targetList.add("Pass");
            for (Person person: alivePlayers) {
                if (!Objects.equals(person.getKeyword(), "Deputy")) {
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

