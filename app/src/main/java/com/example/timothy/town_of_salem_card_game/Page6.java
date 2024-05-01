package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Page6 extends AppCompatActivity {
    TextView defender;
    RadioGroup defense;
    Button battle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page6);

        defender = findViewById(R.id.defenderName);
        defense = findViewById(R.id.defenseOptions);
        battle = findViewById(R.id.buttonBattle);

        Intent lastIntent = getIntent();
        final String pirateWeapon = lastIntent.getStringExtra("weapon");
        final String defenderName = lastIntent.getStringExtra("name");

        defense.check(R.id.optionChainmail);
        defender.setText(defenderName);

        battle.setOnClickListener(v -> {
            int defenseChoice = defense.getCheckedRadioButtonId();
            RadioButton method = findViewById(defenseChoice);
            String defenseMethod = method.getText().toString();
            Intent intent = new Intent(Page6.this,Page7.class);
            intent.putExtra("result", "attack");
            intent.putExtra("battle", new String[]{pirateWeapon, defenseMethod});
            intent.putExtra("defender",defenderName);
            startActivity(intent);
        });


    }
}
