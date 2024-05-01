package com.example.timothy.town_of_salem_card_game;

import android.annotation.SuppressLint;
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

public class Page18 extends AppCompatActivity {

    TextView invName, invLeft, invResult, invLabel;
    Spinner invDrop;
    Button nButton;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page18);


        invName = findViewById(R.id.investigatorName);
        invDrop = findViewById(R.id.investigateDrop);
        nButton = findViewById(R.id.nextButton18);
        invLeft = findViewById(R.id.investigationsLeft);
        invResult = findViewById(R.id.investigationResult);
        invLabel = findViewById(R.id.investigateLabel);

        final Person investigator = Metadata.findPerson("Role", "Investigator");

        assert investigator != null;
        invName.setText(investigator.getName());
        invLeft.setText(String.format("Investigations left: %s",investigator.getUses()));

        List<String> targetList = new ArrayList<>();
        if (!investigator.isRoleBlocked && investigator.getUses() > 0){
            targetList.add("Pass");
            for (Person person: alivePlayers) {
                if (!Objects.equals(person.getKeyword(), "Investigator")) {
                    targetList.add(person.getName());
                }
            }

        }
        else{
            if (investigator.isRoleBlocked) targetList.add("Roleblocked");
            if (investigator.getUses()==0) {
                invDrop.setVisibility(View.INVISIBLE);
                invLabel.setVisibility(View.INVISIBLE);
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        invDrop.setAdapter(adapter);

        invDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (invDrop.getVisibility() == View.VISIBLE) {
                    String selection = invDrop.getSelectedItem().toString();
                    if (selection.equals("Pass")) invResult.setText("You have decided not to investigate.");

                    else if (selection.equals("Roleblocked")) invResult.setText("You are roleblocked and cannot investigate tonight.");
                    else
                        invResult.setText(String.format("%s is the %s.", selection, Objects.requireNonNull(Metadata.findPerson("Name", selection)).getKeyword()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invDrop.getVisibility()==View.VISIBLE) {
                    String selection = invDrop.getSelectedItem().toString();
                    if (!selection.equals("Roleblocked") && !selection.equals("Pass")) {
                        for (Person person : alivePlayers) {
                            if (Objects.equals(person.getKeyword(), "Investigator")) {
                                person.addTarget(Metadata.findPerson("Name", selection));
                                person.useUse();
                            }
                        }
                    }
                }
                startActivity(RoleList.toPage(Page18.this,"Doctor"));
            }
        });


    }
}
