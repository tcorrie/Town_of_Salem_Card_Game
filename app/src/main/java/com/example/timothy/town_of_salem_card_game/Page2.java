package com.example.timothy.town_of_salem_card_game;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.timothy.town_of_salem_card_game.RoleList.none;

public class Page2 extends AppCompatActivity {

/*
WIDGET DEFINITION SECTION: All widgets (except for buttons) are defined here.
*/
    Spinner roleDrop;
    TextView nameEntryTextView;
    TextView confirmationView;


/*
END WIDGET DEFINITION SECTION
*/

/*
BUTTON SECTION: The following code handles the buttons and their actions.
*/
    public Button confirmButton;
    public Button addPlayerButton;

    public void init(){
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Metadata.allPlayers.size()>=1) {
                    Intent intent = new Intent(Page2.this, Page3.class);
                    startActivity(intent);
                }
                else Toast.makeText(Page2.this,"Must have at least one person entered to continue.", Toast.LENGTH_LONG).show();
            }
        });

        addPlayerButton = findViewById(R.id.addButton);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Role role = (Role) roleDrop.getSelectedItem();
                nameEntryTextView = findViewById(R.id.insertName);
                String name = nameEntryTextView.getText().toString();
                if (!name.isEmpty() && !Objects.equals(role.toString(), "Select Role")){ //Check to see if both fields are valid
                    Person person = new Person(name,role);
                    Metadata.allPlayers.add(person);
                    if (Objects.equals(person.getAlignment(), "Mafia")) Metadata.mafiaAlive.add(person);
                    confirmationView = findViewById(R.id.commentBox);
                    confirmationView.setText(String.format("%s the %s has been added to the game.",name,role.toString()));
                    //Reset fields for the next person
                    nameEntryTextView.setText("");
                    roleDrop.setSelection(0);
                }
                else{
                    confirmationView = findViewById(R.id.commentBox);
                    confirmationView.setText("Name and role must be defined first.");
                }

            }

        });
    }

/*
END BUTTON SECTION
*/


/*
onCreate: Function that makes the page. We also add spinner items here, after the setContent View section.
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);


//////////////////////////////////////////////////////////////////////////////
// Add items to the spinner                                                 //
//////////////////////////////////////////////////////////////////////////////
        roleDrop = findViewById(R.id.roleDrop);
        List<Role> role_list = new ArrayList<>();
        role_list.add(none);
        role_list.addAll(Arrays.asList(RoleList.getRoleList()));
        ArrayAdapter<Role> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,role_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleDrop.setAdapter(adapter);
        init();
    }
}
