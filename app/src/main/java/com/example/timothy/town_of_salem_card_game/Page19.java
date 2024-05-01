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
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page19 extends AppCompatActivity {

    TextView docName;
    Spinner hlDrop;
    Button nButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page19);


        docName = findViewById(R.id.doctorName);
        hlDrop = findViewById(R.id.healDrop);
        nButton = findViewById(R.id.nextButton19);

        final Person doctor = Metadata.findPerson("Role", "Doctor");

        assert doctor != null;
        docName.setText(doctor.getName());

        List<String> targetList = new ArrayList<>();
        if (!doctor.isRoleBlocked){
            targetList.add("Pass");
            for (Person person: alivePlayers) {
                boolean notTheDoctor = !Objects.equals(person.getKeyword(), "Doctor");
                boolean canStillHealSelf = Metadata.canHealSelf;
                boolean notTheRevealedMayor = !Objects.equals(person.getKeyword(), "Mayor") || !Metadata.mayorRevealed;
                boolean notTheLastHealed = !Objects.equals(Metadata.lastHealed.getName(), person.getName());
                if ((notTheDoctor || canStillHealSelf) && notTheRevealedMayor && notTheLastHealed) {
                    targetList.add(person.getName());
                }
            }

        }
        else targetList.add("Roleblocked");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hlDrop.setAdapter(adapter);

        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = hlDrop.getSelectedItem().toString();
                if(Objects.equals(doctor.getName(),selection)) Metadata.changeCanHealSelf();
                if (!selection.equals("Roleblocked") && !selection.equals("Pass")){
                    for (Person person: alivePlayers){
                        if(Objects.equals(person.getName(), selection)) {
                            person.addStatus("heal");
                            Metadata.setPersonHealed(person);
                        }
                        if(Objects.equals(person.getKeyword(), "Doctor")) {
                            person.addTarget(Metadata.findPerson("Name",selection));
                            Metadata.lastHealed = Metadata.findPerson("Name",selection);
                        }
                    }

                }
                else Metadata.lastHealed=Metadata.noOne;
                startActivity(RoleList.toPage(Page19.this,"Bodyguard"));
            }
        });


    }
}

