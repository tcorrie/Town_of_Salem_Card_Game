package com.example.timothy.town_of_salem_card_game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class Page11 extends AppCompatActivity {
    TextView bmResult, kResult, cResult, invResult;
    Button nexButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page11);

        bmResult = findViewById(R.id.blackmailResult);
        kResult = findViewById(R.id.killResult);
        cResult = findViewById(R.id.cleanResult);
        invResult = findViewById(R.id.investigateResult);
        nexButton = findViewById(R.id.nextButton11);


        Intent lastIntent = getIntent();
        final String[] results = lastIntent.getStringArrayExtra("action");
        String blackmailed = results[0];
        String killed = results[3];
        String cleaned = results[2];
        String investigated = results[1];

        // Set blackmailed text and targets
        if (blackmailed.equals("Pass")){
            bmResult.setText("The blackmailer has decided not to blackmail.");
            Metadata.lastBMed=Metadata.noOne;
        }
        else if (blackmailed.equals("Roleblocked")){
            bmResult.setText("The blackmailer has been roleblocked.");
            Metadata.lastBMed=Metadata.noOne;
        }
        else {
            for (Person person : Metadata.alivePlayers) {
                if (Objects.equals(person.getName(), blackmailed)) {
                    bmResult.setText(String.format("%s has been blackmailed.", blackmailed));
                    Metadata.lastBMed = Metadata.findPerson("Name",blackmailed);
                }
                if (Objects.equals(person.getKeyword(), "Blackmailer")){
                    person.addTarget(Metadata.findPerson("Name", blackmailed));
                }

            }
        }
        // Set killed text and targets
        switch (killed) {
            case "Pass":
                kResult.setText("The mafia has decided not to kill.");
                break;
            case "Roleblocked":
                kResult.setText("The mafia has been roleblocked.");
                break;
            case "N1":
                kResult.setText("The mafia cannot kill night 1.");
                break;
            default:
                Person mafia = Metadata.randomMafia();
                for (Person person : Metadata.alivePlayers) {
                    if (Objects.equals(person.getName(), killed)) {
                        kResult.setText(String.format("The mafia has voted to kill %s.", killed));
                        person.addStatus("kill");
                    }
                    if (Objects.equals(person.getName(), mafia.getName())) {
                        person.addTarget(Metadata.findPerson("Name", killed));
                    }


                }
                break;
        }
        // Set cleaned text and targets
        switch (cleaned) {
            case "no":
                cResult.setText("The target was not cleaned.");
                break;
            case "Roleblocked":
                cResult.setText("The janitor has been roleblocked.");
                break;
            case "yes":
                for (Person person : Metadata.alivePlayers) {
                    if (Objects.equals(person.getName(), killed) && !killed.equals("Pass")) {
                        cResult.setText(String.format("The mafia will attempt to clean %s.", killed));
                        person.addStatus("clean");
                    }
                }
                break;
            default:
                cResult.setText("");
                break;
        }
        // Set consigliere text/target
        switch (investigated) {
            case "Pass":
                invResult.setText("The consigliere has chosen not to investigate.");
                break;
            case "Roleblocked":
                invResult.setText("The consigliere has been roleblocked.");
                break;
            case "":
                invResult.setText("");
                break;
            default:
                for (Person person : Metadata.alivePlayers) {
                    if (Objects.equals(person.getName(), investigated)) {
                        bmResult.setText(String.format("%s is the %s.", investigated, person.getKeyword()));
                    }
                    if (Objects.equals(person.getKeyword(), "Consigliere")) {
                        person.addTarget(Metadata.findPerson("Name", investigated));
                        person.useUse();
                    }
                }
                break;
        }

        nexButton.setOnClickListener(v -> startActivity(RoleList.toPage(Page11.this,"Serial Killer")));

    }
}
