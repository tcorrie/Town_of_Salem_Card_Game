package com.example.timothy.town_of_salem_card_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

import static com.example.timothy.town_of_salem_card_game.Metadata.alivePlayers;

public class Page9 extends AppCompatActivity {
    TextView vetName, alertsLeft;
    ToggleButton alertDecision;
    Button nextPage;
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page9);
        vetName = findViewById(R.id.veteranName);
        alertsLeft = findViewById(R.id.alertleftMessage);
        alertDecision = findViewById(R.id.alertdecisionToggle);
        nextPage = findViewById(R.id.nextButton9);

        Person veteran = Metadata.findPerson("Role","Veteran");

        assert veteran != null;
        vetName.setText(veteran.getName());
        alertsLeft.setText(String.format(Locale.US,"Alerts left: %d",veteran.getUses()));

        if (veteran.getUses()>0){
            alertDecision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checked= isChecked;
                }
            });

            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checked){
                        for(Person person: alivePlayers){
                            if (person.getKeyword().equals("Veteran")) {
                                person.addStatus("alert");
                                person.useUse();
                            }

                        }
                    }
                    startActivity(RoleList.toPage(Page9.this,"Godfather"));
                }
            });
        }
        else{
            alertDecision.setVisibility(View.INVISIBLE);
            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(RoleList.toPage(Page9.this,"Godfather"));
                }
            });
        }

    }
}
