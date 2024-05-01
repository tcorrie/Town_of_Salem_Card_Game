package com.example.timothy.town_of_salem_card_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page15 extends AppCompatActivity {

    TextView vigName, remArrow, shtLabel;
    Spinner shtDrop;
    Button nButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page15);


        vigName = findViewById(R.id.vigilanteName);
        shtDrop = findViewById(R.id.shootDrop);
        nButton = findViewById(R.id.nextButton15);
        remArrow = findViewById(R.id.remainderarrowLabel);
        shtLabel = findViewById(R.id.shootLabel);

        Person vigilante = Metadata.findPerson("Role", "Vigilante");

        assert vigilante != null;
        vigName.setText(vigilante.getName());
        remArrow.setText(String.format(Locale.US,"Arrows left: %d",vigilante.getUses()));

        List<String> targetList = new ArrayList<>();
        if (!vigilante.isRoleBlocked && vigilante.getUses() > 0){
            targetList.add("Pass");
            for (Person person: alivePlayers) {
                if (!Objects.equals(person.getKeyword(), "Vigilante")) {
                    targetList.add(person.getName());
                }
            }

        }
        else{
            if (vigilante.isRoleBlocked) targetList.add("Roleblocked");
            if (vigilante.getUses()==0){
                shtDrop.setVisibility(View.INVISIBLE);
                shtLabel.setVisibility(View.INVISIBLE);
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shtDrop.setAdapter(adapter);

        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shtDrop.getVisibility()==View.VISIBLE){
                    String selection = shtDrop.getSelectedItem().toString();
                    if (!selection.equals("Roleblocked") && !selection.equals("Pass")) {
                        for (Person person : alivePlayers) {
                            if (Objects.equals(person.getName(), selection)) person.addStatus("shot");
                            if (Objects.equals(person.getKeyword(), "Vigilante")) {
                                person.addTarget(Metadata.findPerson("Name", selection));
                                person.useUse();
                            }
                        }
                    }
                }
                startActivity(RoleList.toPage(Page15.this,"Sheriff"));
            }
        });


    }
}
