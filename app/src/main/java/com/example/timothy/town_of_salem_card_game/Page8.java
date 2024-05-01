package com.example.timothy.town_of_salem_card_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;
import static com.example.timothy.town_of_salem_card_game.Metadata.deadPlayers;

public class Page8 extends AppCompatActivity {

    TextView medName, seaInfo;
    Spinner seaTarName;
    Button seaButton, nexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8);
        medName = findViewById(R.id.mediumName);
        seaTarName = findViewById(R.id.seanceDrop);
        seaInfo = findViewById(R.id.seanceInfo);
        seaButton = findViewById(R.id.seanceButton);
        nexButton = findViewById(R.id.nextButton8);

        Person medium = Metadata.findPerson("Role","Medium");


        assert medium != null;
        medName.setText(medium.getName());


        if(Metadata.deadPlayers.size()>0 && !medium.isRoleBlocked){
            List<String> deadList = new ArrayList<>();
            deadList.add("Pass");
            for (Person person: deadPlayers) {
                deadList.add(person.getName());
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deadList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            seaTarName.setAdapter(adapter);

            seaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "";
                    String theirRole = "";
                    String seanced = seaTarName.getSelectedItem().toString();
                    if (seanced.equals("Pass")) seaInfo.setText((String)"You have chosen to pass.");
                    else {
                        for (Person person : deadPlayers) {
                            if (Objects.equals(person.getName(), seanced)) {
                                status = person.getStatus();
                                theirRole = person.getKeyword();
                            }
                        }
                        showSeanceResult(seaInfo,status,theirRole,seanced);
                    }
                }
            });

            nexButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(RoleList.toPage(Page8.this,"Veteran"));
                }
            });


        }
        else if (Metadata.deadPlayers.size()==0){
            medName.setText(medium.getName());
            seaTarName.setVisibility(View.INVISIBLE);
            seaInfo.setText((String)"No one is dead yet. Pass automatically.");
            seaButton.setVisibility(View.INVISIBLE);
            nexButton = findViewById(R.id.nextButton8);
            nexButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(RoleList.toPage(Page8.this,"Veteran"));
                }
            });

        }
        else{
            medName.setText(Objects.requireNonNull(Metadata.findPerson("Role", "Medium")).getName());
            seaTarName.setVisibility(View.INVISIBLE);
            seaInfo.setText((String)"Roleblocked.");
            seaButton.setVisibility(View.INVISIBLE);
            nexButton = findViewById(R.id.nextButton8);
            nexButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(RoleList.toPage(Page8.this,"Veteran"));
                }
            });
        }



    }

    private void showSeanceResult(TextView tv, String status, String theirRole, String name){
        tv.setText(String.format("%s was a %s.\n",name,theirRole));

        ArrayList<Person> killedBy = new ArrayList<>();
        if (status.contains("Mafia") && Metadata.mafiaAlive.size()>0) killedBy.addAll(Metadata.mafiaAlive);
        if (status.contains("Pirate") && RoleList.stillAlive(RoleList.pirate))  killedBy.add(Metadata.findPerson("Role","Pirate"));
        else if (status.contains("Werewolf") && RoleList.stillAlive(RoleList.werewolf)) killedBy.add(Metadata.findPerson("Role","Werewolf"));
        else if (status.contains("Serial Killer") && RoleList.stillAlive(RoleList.serial_killer)) killedBy.add(Metadata.findPerson("Role","Serial Killer"));
        else if (status.contains("Veteran") && RoleList.stillAlive(RoleList.veteran)) killedBy.add(Metadata.findPerson("Role","Veteran"));
        else if (status.contains("Vigilante") && RoleList.stillAlive(RoleList.vigilante)) killedBy.add(Metadata.findPerson("Role","Vigilante"));

       if (killedBy.size()>0){
           int aliveSize = Metadata.alivePlayers.size();
           int extraOptions = RoleList.extraUses(aliveSize);
           Random rand = new Random();
           Person theKiller = killedBy.get(rand.nextInt(killedBy.size()));
           ArrayList<Integer> indices = new ArrayList<>();


           boolean flag = true;
           while(flag){
               indices = randomIndices(alivePlayers.size(), 2+extraOptions);
               boolean killerFlag = false;
               boolean mediumFlag = false;
               for (int i:indices){
                   if (Objects.equals(alivePlayers.get(i).getName(), theKiller.getName())) killerFlag=true;
                   if (Objects.equals(alivePlayers.get(i).getKeyword(),"Medium")) mediumFlag=true;
               }
               if (killerFlag && !mediumFlag) flag = false;
           }
           tv.append((String)"Potential killers:\n");
           for (int i:indices){
               tv.append(String.format("%s\n",alivePlayers.get(i).getName()));
           }

        }


    }

    private ArrayList<Integer> randomIndices(int sizeList, int numsToGenerate){
        ArrayList<Integer> intList = new ArrayList<>();
        int count = 0;
        while (count<numsToGenerate){
            int random = new Random().nextInt(sizeList);
            if (!intList.contains(random)){
                intList.add(random);
                count++;
            }
        }
        return intList;

    }

}
