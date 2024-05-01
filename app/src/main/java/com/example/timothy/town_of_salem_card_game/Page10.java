package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Page10 extends AppCompatActivity {
    TextView bmName, csName, jaName, conLeft, cleLeft, rbInfo;
    TextView bmerLabel, bmLabel, consreLabel, invLabel, janorLabel, kllLabel;
    Spinner bmDrop, invDrop, kDrop;
    Button actButton;
    String blackmailerTarget, consigliereTarget, janitorTarget, mafiaTarget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page10);

        bmName = findViewById(R.id.blackmailerName);
        csName = findViewById(R.id.consigliereName);
        jaName = findViewById(R.id.janitorName);
        conLeft = findViewById(R.id.consigsLeft);
        cleLeft = findViewById(R.id.cleansLeft);
        rbInfo = findViewById(R.id.roleBlockInfo);
        bmDrop = findViewById(R.id.blackmailDrop);
        invDrop = findViewById(R.id.investigateDrop);
        kDrop = findViewById(R.id.killDrop);
        actButton = findViewById(R.id.actionButton);

        //labels
        bmerLabel = findViewById(R.id.blackmailerLabel);
        bmLabel = findViewById(R.id.blackmailLabel);
        consreLabel = findViewById(R.id.consigliereLabel);
        invLabel = findViewById(R.id.consigliereinvLabel);
        janorLabel = findViewById(R.id.janitorLabel);
        kllLabel = findViewById(R.id.killLabel);




        final Person blackmailer = Metadata.findPerson("Role","Blackmailer");
        final Person consigliere = Metadata.findPerson("Role","Consigliere");
        final Person janitor = Metadata.findPerson("Role","Janitor");



        List<String> targetList = new ArrayList<>();
        List<String> bmList = new ArrayList<>();
        List<String> rbList = new ArrayList<>();
        rbList.add("Roleblocked");
        targetList.add("Pass");
        for (Person person: alivePlayers) {
            boolean notTheLastBMed = !Objects.equals(person.getName(), Metadata.lastBMed.getName());
            if (!Objects.equals(person.getAlignment(), "Mafia")) targetList.add(person.getName());
            if (!Objects.equals(person.getAlignment(), "Mafia") && notTheLastBMed) bmList.add(person.getName());
        }
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,bmList);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,targetList);
        final ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,rbList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Blackmailer control
        if (RoleList.stillAlive(RoleList.blackmailer)){
            bmerLabel.setVisibility(View.VISIBLE);
            bmLabel.setVisibility(View.VISIBLE);
            bmDrop.setVisibility(View.VISIBLE);
            assert blackmailer != null;
            bmName.setText(blackmailer.getName());
            if(!blackmailer.isRoleBlocked){
                bmDrop.setAdapter(adapter1);
            }
            else{
                rbInfo.setText((String)"Blackmailer is roleblocked and cannot blackmail tonight.");
                bmDrop.setAdapter(adapter4);
            }
        }
        else{
            bmerLabel.setVisibility(View.INVISIBLE);
            bmLabel.setVisibility(View.INVISIBLE);
            bmDrop.setVisibility(View.INVISIBLE);
        }
// Consigliere control
        if (RoleList.stillAlive(RoleList.consigliere)){
            consreLabel.setVisibility(View.VISIBLE);
            invLabel.setVisibility(View.VISIBLE);
            invDrop.setVisibility(View.VISIBLE);
            assert consigliere != null;
            csName.setText(consigliere.getName());
            if(!consigliere.isRoleBlocked){
                invDrop.setAdapter(adapter2);
                conLeft.setText(String.format(Locale.US,"Investigations left: %d",consigliere.getUses()));
                if (consigliere.getUses()==0) invDrop.setVisibility(View.INVISIBLE);
            }
            else{
                rbInfo.setText((String)"Consigliere is roleblocked and cannot investigate tonight.");
                invDrop.setAdapter(adapter4);
            }
        }
        else{
            consreLabel.setVisibility(View.INVISIBLE);
            invLabel.setVisibility(View.INVISIBLE);
            invDrop.setVisibility(View.INVISIBLE);
        }
// Janitor control
        if (RoleList.stillAlive(RoleList.janitor)){
            janorLabel.setVisibility(View.VISIBLE);
            assert janitor != null;
            jaName.setText(janitor.getName());
            if(!janitor.isRoleBlocked){
                cleLeft.setText(String.format(Locale.US,"Cleans left: %d",janitor.getUses()));
            }
            else{
                rbInfo.setText((String)"Janitor is roleblocked and cannot clean tonight.");
            }
        }
        else{
            janorLabel.setVisibility(View.INVISIBLE);
        }

// Kill control
        if(!Metadata.mafiaRoleBlocked() && Metadata.night>1){
            kDrop.setAdapter(adapter3);
        }
        else if (Metadata.night==1){
            rbInfo.append("\nMafia does not kill night 1.");
            kDrop.setVisibility(View.INVISIBLE);
        }
        else{
            rbInfo.append("\nThe one mafia member is roleblocked and cannot kill tonight.");
            kDrop.setAdapter(adapter4);
        }

        actButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bmDrop.getVisibility()==View.VISIBLE) blackmailerTarget=bmDrop.getSelectedItem().toString();
                else blackmailerTarget="";
                if (invDrop.getVisibility()== View.VISIBLE) consigliereTarget=invDrop.getSelectedItem().toString();
                else consigliereTarget="";


                if (RoleList.stillAlive(RoleList.janitor)){
                    assert janitor != null;
                    if (janitor.getUses()>0 && Metadata.night>1) janitorTarget="yes";
                    else if(janitor.isRoleBlocked) janitorTarget="Roleblocked";
                    else if(Metadata.night==1) janitorTarget="";
                    else janitorTarget="no";
                }
                else janitorTarget="";
                mafiaTarget = "";
                if (kDrop.getVisibility()==View.VISIBLE) mafiaTarget = kDrop.getSelectedItem().toString();
                else mafiaTarget = "N1";
                Intent intent = new Intent(Page10.this, Page11.class);
                intent.putExtra("action", new String[]{blackmailerTarget, consigliereTarget, janitorTarget, mafiaTarget});
                startActivity(intent);
            }
        });

    }
}
