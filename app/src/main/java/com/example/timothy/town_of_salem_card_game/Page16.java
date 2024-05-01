package com.example.timothy.town_of_salem_card_game;

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

public class Page16 extends AppCompatActivity {

    TextView shrName, intRes;
    Spinner intDrop;
    Button nButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page16);

        shrName = findViewById(R.id.sheriffName);
        intDrop = findViewById(R.id.interrogateDrop);
        nButton = findViewById(R.id.nextButton16);
        intRes = findViewById(R.id.interrogateResult);

        Person sheriff = Metadata.findPerson("Role", "Sheriff");
        assert sheriff != null;
        shrName.setText(sheriff.getName());
        List<String> targetList = new ArrayList<>();

        if (!sheriff.isRoleBlocked){
            targetList.add("Pass");
            for (Person person: alivePlayers) {
                if (!Objects.equals(person.getKeyword(), "Sheriff")) {
                    targetList.add(person.getName());
                }
            }

        }
        else targetList.add("Roleblocked");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intDrop.setAdapter(adapter);
        intDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String focus = intDrop.getSelectedItem().toString();
                if (focus.equals("Pass")) intRes.setText((String)"You are not interrogating anyone.");
                else if (focus.equals("Roleblocked")) intRes.setText((String)"Roleblocked: cannot interrogate tonight.");
                else {
                    if(Metadata.night %2 == 0 && Objects.equals(Objects.requireNonNull(Metadata.findPerson("Name", focus)).getKeyword(),"Werewolf")){
                        intRes.setText((String)"Your target is Evil.");
                    }
                    else intRes.setText(String.format("Your target is %s.", Objects.requireNonNull(Metadata.findPerson("Name", focus)).getSheriffResult()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing here. Should always have a selection.
            }
        });



        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = intDrop.getSelectedItem().toString();
                if (!selection.equals("Roleblocked")){
                    for (Person person: alivePlayers){
                        if(Objects.equals(person.getKeyword(), "Sheriff")) person.addTarget(Metadata.findPerson("Name",selection));
                    }

                }
                startActivity(RoleList.toPage(Page16.this,"Investigator"));
            }
        });




    }
}
