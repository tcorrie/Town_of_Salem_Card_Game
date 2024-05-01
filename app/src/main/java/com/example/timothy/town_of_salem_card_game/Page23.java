package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

public class Page23 extends AppCompatActivity {

    TextView overMsg;
    Button nButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page23);
        overMsg = findViewById(R.id.endNightMessage);
        nButton = findViewById(R.id.nextButton23);
        overMsg.setText(String.format(Locale.US,"Night %d is over. Press Next to proceed.", Metadata.night));
        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTheNight();

                Intent intent = new Intent(Page23.this, Page24.class);
                startActivity(intent);
            }
        });
    }
    private void playTheNight(){
        for (Person person: Metadata.alivePlayers){
            String status = person.getStatus();
            if(Objects.equals(person.getKeyword(),"Veteran") && status.contains("alert")){
                for (Person p2: Metadata.alivePlayers){
                    if (p2.getTargets().size()>0) {
                        for (Person pTarg: p2.getTargets()){
                            if (Objects.equals(pTarg.getKeyword(),"Veteran")) p2.addStatus("caught");
                        }
                    }
                }
                person.deleteStatus("hex");
                person.deleteStatus("kill");
                person.deleteStatus("clean");
                person.deleteStatus("stab");
                person.deleteStatus("maul");
                person.deleteStatus("alert");
                person.deleteStatus("plunder");
                person.deleteStatus("shot");
                person.deleteStatus("denied");
            }
            if(status.contains("hex")){
                person.setCursed();
                person.deleteStatus("hex");
            }
            if(status.contains("guard")){
                ArrayList<String> attackedBy = new ArrayList<>();
                if (status.contains("kill")) attackedBy.add("kill");
                if (status.contains("stab")) attackedBy.add("stab");
                if (status.contains("maul")) attackedBy.add("maul");
                if (status.contains("caught")) attackedBy.add("caught");
                if (status.contains("plunder")) attackedBy.add("plunder");
                if (status.contains("shot")) attackedBy.add("shot");
                if (attackedBy.size()>0){
                    int indx = (int)(Math.random()*attackedBy.size());
                    String attackedKW = attackedBy.get(indx);
                    switch (attackedKW){
                        case "kill":
                            Person rMaf = Metadata.randomMafia();
                            for (Person person1: Metadata.alivePlayers){
                                if (Objects.equals(person1.getName(), rMaf.getName())) person1.addStatus("denied");
                                if (Objects.equals(person1.getKeyword(), "Bodyguard")) person1.addStatus("guarded");
                            }
                            person.deleteStatus("guard");
                            person.deleteStatus("kill");
                            break;
                        case "stab":
                            for (Person person1: Metadata.alivePlayers){
                                if (Objects.equals(person1.getKeyword(), "Serial Killer")) person1.addStatus("denied");
                                if (Objects.equals(person1.getKeyword(), "Bodyguard")) person1.addStatus("guarded");
                            }
                            person.deleteStatus("guard");
                            person.deleteStatus("stab");
                            break;
                        case "maul":
                            for (Person person1: Metadata.alivePlayers){
                                if (Objects.equals(person1.getKeyword(), "Werewolf")) person1.addStatus("denied");
                                if (Objects.equals(person1.getKeyword(), "Bodyguard")) person1.addStatus("guarded");
                            }
                            person.deleteStatus("guard");
                            person.deleteStatus("maul");
                            break;
                        case "caught":
                            for (Person person1: Metadata.alivePlayers){
                                if (Objects.equals(person1.getKeyword(), "Veteran")) person1.addStatus("denied");
                                if (Objects.equals(person1.getKeyword(), "Bodyguard")) person1.addStatus("guarded");
                            }
                            person.deleteStatus("guard");
                            person.deleteStatus("caught");
                            break;
                        case "plunder":
                            for (Person person1: Metadata.alivePlayers){
                                if (Objects.equals(person1.getKeyword(), "Pirate")) person1.addStatus("denied");
                                if (Objects.equals(person1.getKeyword(), "Bodyguard")) person1.addStatus("guarded");
                            }
                            person.deleteStatus("guard");
                            person.deleteStatus("plunder");
                            break;
                        case "shot":
                            for (Person person1: Metadata.alivePlayers){
                                if (Objects.equals(person1.getKeyword(), "Vigilante")) person1.addStatus("denied");
                                if (Objects.equals(person1.getKeyword(), "Bodyguard")) person1.addStatus("guarded");
                            }
                            person.deleteStatus("guard");
                            person.deleteStatus("shot");
                            break;
                    }
                }
            }
        }
        for (Person person:Metadata.alivePlayers){
            String status = person.getStatus();
            if(status.contains("heal")){
                person.deleteStatus("kill");
                person.deleteStatus("clean");
                person.deleteStatus("stab");
                person.deleteStatus("maul");
                person.deleteStatus("alert");
                person.deleteStatus("plundered");
                person.deleteStatus("shot");
                person.deleteStatus("denied");
            }
            boolean isTheSK = Objects.equals(person.getKeyword(),"Serial Killer");
            boolean isTransformedWW = Objects.equals(person.getKeyword(),"Werewolf") && Metadata.night%2==0;
            boolean isTheSurvivor = Objects.equals(person.getKeyword(),"Survivor");
            boolean isTheAmnesiac = Objects.equals(person.getKeyword(),"Amnesiac");
            if(isTheSK||isTransformedWW||isTheSurvivor||isTheAmnesiac){
                person.deleteStatus("kill");
                person.deleteStatus("clean");
                person.deleteStatus("stab");
                person.deleteStatus("maul");
                person.deleteStatus("alert");
                person.deleteStatus("plundered");
                person.deleteStatus("shot");
                person.deleteStatus("denied");
            }
        }
        Iterator<Person> i = Metadata.alivePlayers.iterator();
        while(i.hasNext()){
            Person p = i.next();
            boolean killedByMafia = p.getStatus().contains("kill");
            boolean killedBySK = p.getStatus().contains("stab");
            boolean killedByWW = p.getStatus().contains("maul");
            boolean killedByVet = p.getStatus().contains("caught");
            boolean killedByPirate = p.getStatus().contains("plunder");
            boolean killedByVig = p.getStatus().contains("shot");
            boolean killedByBG = p.getStatus().contains("denied");
            boolean killedByGuarding = p.getStatus().contains("guarded");

            boolean dead = killedByMafia || killedBySK || killedByWW || killedByVet || killedByPirate || killedByVig || killedByBG || killedByGuarding;
            if (dead){
                String newStatus="";
                if (killedByMafia) {
                    newStatus = newStatus + "Mafia, ";
                    if (p.getStatus().contains("clean")) {
                        p.cleaned();
                        for (Person person : Metadata.alivePlayers) {
                            if (Objects.equals(person.getKeyword(), "Janitor")) person.useUse();
                        }
                    }
                }
                if (killedBySK) newStatus = newStatus + "Serial Killer, ";
                if (killedByWW) newStatus = newStatus + "Werewolf, ";
                if (killedByVet) newStatus = newStatus + "Veteran, ";
                if (killedByPirate) newStatus = newStatus + "Pirate, ";
                if (killedByVig) newStatus = newStatus + "Vigilante, ";
                if (killedByBG) newStatus = newStatus + "Bodyguard, ";
                if (killedByGuarding) newStatus = newStatus + "Guarding, ";

                newStatus = newStatus.substring(0,newStatus.length()-2);
                p.clearStatus();
                p.addStatus(newStatus);
                Metadata.deadPlayers.add(p);
                i.remove();
            }
        }
        if(Metadata.allCursed()){
            Iterator<Person> j = Metadata.alivePlayers.iterator();
            while(j.hasNext()){
                Person p = j.next();
                if(!Objects.equals(p.getKeyword(),"Witch")){
                    p.clearStatus();
                    p.addStatus("Witch");
                    Metadata.deadPlayers.add(p);
                    j.remove();
                }
            }
        }
        if (!RoleList.stillAlive(RoleList.witch) && RoleList.exists(RoleList.witch) && Metadata.houseRulesWitch){
            Iterator<Person> k = Metadata.alivePlayers.iterator();
            while(k.hasNext()){
                Person p = k.next();
                if(!Objects.equals(p.getKeyword(),"Witch") && p.getCursed()){
                    p.clearStatus();
                    p.addStatus("Witch");
                    Metadata.deadPlayers.add(p);
                    k.remove();
                }
            }
        }
    }
}
