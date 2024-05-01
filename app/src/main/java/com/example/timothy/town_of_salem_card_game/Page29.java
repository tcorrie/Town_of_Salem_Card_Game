package com.example.timothy.town_of_salem_card_game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Page29 extends AppCompatActivity {

    TextView description;
    ToggleButton ruleset;
    Button cont;
    boolean checked;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page29);

        description = findViewById(R.id.descRules);
        ruleset = findViewById(R.id.ruleToggle);
        cont = findViewById(R.id.continueButton29);
        description.setText("Standard Rules: The witch must curse everyone for the total-game curse to take effect.");

        ruleset.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked = isChecked;
                if (isChecked) description.setText("House Rules: When the witch dies, everyone that is cursed dies as well.");
                else description.setText("Standard Rules: The witch must curse everyone for the total-game curse to take effect.");
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked) Metadata.houseRulesWitch = true;
                Intent intent = new Intent(Page29.this, Page1.class);
                startActivity(intent);
            }
        });


    }
}