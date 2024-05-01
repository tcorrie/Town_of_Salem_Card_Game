package com.example.timothy.town_of_salem_card_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;



import static com.example.timothy.town_of_salem_card_game.RoleList.none;
import static com.example.timothy.town_of_salem_card_game.RoleList.role_list;

public class Page3 extends AppCompatActivity {



    TextView virtualValueNumber;
    TextView playerList;
    TextView virtualValueLabel;
    Spinner playerListSpinner;
    TextView editNameArea;
    Spinner editRoleSpinner;



    private void editPlayers(){
        playerList.setVisibility(View.INVISIBLE);
        virtualValueNumber.setVisibility(View.INVISIBLE);
        editPlayersButton.setVisibility(View.INVISIBLE);
        startGameButton.setVisibility(View.INVISIBLE);
        virtualValueLabel = (TextView)findViewById(R.id.virtualValueLabel);
        virtualValueLabel.setVisibility(View.INVISIBLE);

        playerListSpinner = (Spinner)findViewById(R.id.playerListSpinner);
        playerListSpinner.setVisibility(View.VISIBLE);

        List<String> game_list = new ArrayList<>();
        Person blank = new Person ("None",none);
        game_list.add(blank.description());
        for (Person person:Metadata.allPlayers){
            game_list.add(person.description());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,game_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerListSpinner.setAdapter(adapter);

        editNameArea = (TextView)findViewById(R.id.editNameArea);
        editRoleSpinner = (Spinner)findViewById(R.id.editRoleSpinner);
        List<Role> roleList = new ArrayList<>(Arrays.asList(role_list));
        ArrayAdapter<Role> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editRoleSpinner.setAdapter(adapter2);

        playerListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (playerListSpinner.getSelectedItemPosition()==0){
                    editNameArea.setVisibility(View.INVISIBLE);
                    editRoleSpinner.setVisibility(View.INVISIBLE);
                    goBackButton.setVisibility(View.VISIBLE);
                    saveChangesButton.setVisibility(View.INVISIBLE);
                }
                else{
                    editNameArea.setVisibility(View.VISIBLE);
                    editRoleSpinner.setVisibility(View.VISIBLE);
                    goBackButton.setVisibility(View.INVISIBLE);
                    saveChangesButton.setVisibility(View.VISIBLE);
                    int i = playerListSpinner.getSelectedItemPosition();
                    Person person = Metadata.allPlayers.get(i-1);
                    editNameArea.setText(person.getName());
                    int j = RoleList.findRoleInt(person.getKeyword());
                    editRoleSpinner.setSelection(j);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    /*
    BUTTON SECTION: The following code handles the buttons and their actions.
    */
   Button editPlayersButton;
   Button startGameButton;
   Button goBackButton;
   Button saveChangesButton;




    public void init(){
        editPlayersButton = (Button)findViewById(R.id.editPlayersButton);
        editPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPlayers();
            }
        });

        startGameButton = (Button)findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Metadata.alivePlayers = (ArrayList<Person>)Metadata.allPlayers.clone();
                Metadata.numPlayers = Metadata.allPlayers.size();
                int extraUses = RoleList.extraUses(Metadata.numPlayers);
                for (Person person: Metadata.alivePlayers){
                    switch (person.getKeyword()){
                        case "Vigilante":
                            person.setUses(1+extraUses);
                            break;
                        case "Investigator":
                        case "Veteran":
                        case "Consigliere":
                        case "Janitor":
                            person.setUses(2+extraUses);
                            break;
                        default:
                            person.setUses(99);
                    }
                }


                Intent intent = new Intent(Page3.this, Page4.class);
                startActivity(intent);

            }
        });

        goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerList.setVisibility(View.VISIBLE);
                virtualValueNumber.setVisibility(View.VISIBLE);
                editPlayersButton.setVisibility(View.VISIBLE);
                startGameButton.setVisibility(View.VISIBLE);
                virtualValueLabel = (TextView)findViewById(R.id.virtualValueLabel);
                virtualValueLabel.setVisibility(View.VISIBLE);
                playerListSpinner.setVisibility(View.INVISIBLE);
                editNameArea.setVisibility(View.INVISIBLE);
                editRoleSpinner.setVisibility(View.INVISIBLE);
                goBackButton.setVisibility(View.INVISIBLE);
                saveChangesButton.setVisibility(View.INVISIBLE);
            }
        });

        saveChangesButton = findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int i = playerListSpinner.getSelectedItemPosition();
                Metadata.allPlayers.set(i-1,new Person(editNameArea.getText().toString(),(Role)editRoleSpinner.getSelectedItem()));
                playerList.setVisibility(View.VISIBLE);
                virtualValueNumber.setVisibility(View.VISIBLE);
                editPlayersButton.setVisibility(View.VISIBLE);
                startGameButton.setVisibility(View.VISIBLE);
                virtualValueLabel = (TextView)findViewById(R.id.virtualValueLabel);
                virtualValueLabel.setVisibility(View.VISIBLE);
                playerListSpinner.setVisibility(View.INVISIBLE);
                editNameArea.setVisibility(View.INVISIBLE);
                editRoleSpinner.setVisibility(View.INVISIBLE);
                goBackButton.setVisibility(View.INVISIBLE);
                saveChangesButton.setVisibility(View.INVISIBLE);
                playerList.setText("");
                for (Person person:Metadata.allPlayers){
                    playerList.append(String.format("%s (%s)\n",person.getName(),person.getKeyword()));
                }

            }
        });








    }

/*
END BUTTON SECTION
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);


        for (Person person: Metadata.allPlayers){
            Metadata.virtualValueSum+=person.getVirtualValue();
        }
        virtualValueNumber = (TextView)findViewById(R.id.virtualValueNumber);
        virtualValueNumber.setText(String.format(Locale.US,"%d",Metadata.getVirtualValueSum()));

        playerList = (TextView)findViewById(R.id.playerList);
        playerList.setText("");
        for (Person person:Metadata.allPlayers){
            playerList.append(String.format("%s (%s)\n",person.getName(),person.getKeyword()));
        }


        init();

    }
}
