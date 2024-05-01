package com.example.timothy.town_of_salem_card_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import static com.example.timothy.town_of_salem_card_game.Metadata.night;

public class Page4 extends AppCompatActivity {

    TextView nightNum;
    Button beginNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);
        nightNum = findViewById(R.id.nightNumber);
        nightNum.setText(String.format(Locale.US, "%d.", night));
        beginNight = findViewById(R.id.beginButton);
        beginNight.setOnClickListener(v -> startActivity(RoleList.toPage(Page4.this,"Pirate")));
    }
}
