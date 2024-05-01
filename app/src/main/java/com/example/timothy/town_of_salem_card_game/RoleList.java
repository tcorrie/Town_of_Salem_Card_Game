package com.example.timothy.town_of_salem_card_game;


import android.content.Context;
import android.content.Intent;

import java.util.Objects;

@SuppressWarnings("rawtypes")
public class RoleList{
    //Template: Role role = new Role("role", alignment, canBeRoleBlocked, sheriffresult, virtualvalue)
    //Sample: Role mortal = new Role("Mortal", "Neutral", true, "Good", 0)
    static Role amnesiac = new Role("Amnesiac","Neutral",false,"Good",0);
    static Role blackmailer = new Role("Blackmailer","Mafia",true,"Evil",-9);
    static Role bodyguard = new Role("Bodyguard","Town",true,"Good",4);
    static Role consigliere = new Role("Consigliere","Mafia",true,"Evil",-10);
    static Role deputy = new Role("Deputy","Town",true,"Good",4);
    static Role doctor = new Role("Doctor","Town",true,"Good",4);
    static Role executioner = new Role("Executioner","Neutral",true,"Evil",-4);
    static Role godfather = new Role("Godfather","Mafia",true,"Good",-8);
    static Role investigator = new Role("Investigator","Town",true,"Good",6);
    static Role janitor = new Role("Janitor","Mafia",true,"Evil",-8);
    static Role jester = new Role("Jester","Neutral",true,"Evil",-1);
    static Role mafioso = new Role("Mafioso","Mafia",true,"Evil",-6);
    static Role mayor = new Role("Mayor","Town",true,"Good",8);
    static Role medium = new Role("Medium","Town",true,"Good",3);
    static Role peaceful_townie = new Role("Peaceful Townie","Town",true,"Good",-1);
    static Role pirate = new Role("Pirate","Neutral",false,"Evil",-6);
    static Role politician = new Role("Politician","Town",true,"Evil",-2);
    static Role serial_killer = new Role("Serial Killer","Neutral",true,"Evil",-8);
    static Role sheriff = new Role("Sheriff","Town",true,"Good",7);
    static Role spiteful_townie = new Role("Spiteful Townie","Town",true,"Good",-1);
    static Role survivor = new Role("Survivor","Town",true,"Good",4);
    static Role townie = new Role("Townie","Town",true,"Good",1);
    static Role veteran = new Role("Veteran","Town",false,"Good",3);
    static Role vigilante = new Role("Vigilante","Town",true,"Good",5);
    static Role werewolf = new Role("Werewolf","Neutral",true,"Good",-9);
    static Role witch = new Role("Witch","Neutral",true,"Evil",-5);
    //static Role mafia = new Role("Mafia","Mafia",true,"Evil",1);
    static Role none = new Role("Select Role","Neutral",false,"Good",0);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// role_list: all the roles into one list.                                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static Role[] role_list = {amnesiac,blackmailer,bodyguard,consigliere,deputy,doctor,executioner,
            godfather,investigator,janitor,jester,mafioso,mayor,medium,peaceful_townie,pirate,politician,
            serial_killer,sheriff,spiteful_townie,survivor,townie,veteran,vigilante,werewolf,witch};


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// getRoleList: returns the list above.                                                                               //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Role[] getRoleList(){
        return role_list;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// stillAlive: returns true if the person with the inputted role is still alive, false otherwise.                     //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean stillAlive(Role role){
        for(Person player:Metadata.alivePlayers){
            if (player.getKeyword().equals(role.getKeyword())){return true;}
        }
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// exists: returns true if the person with the inputted role existed in the game, false otherwise.                    //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean exists(Role role){
        for(Person player:Metadata.allPlayers){
            if (player.getKeyword().equals(role.getKeyword())){return true;}
        }
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// visitingRoles: contains all of the roles that have some sort of night action (they don't all visit)                //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static Role[] visitingRoles = {pirate,medium,veteran,godfather,mafioso,consigliere,blackmailer,janitor,serial_killer,werewolf,witch,
                vigilante,sheriff,deputy,investigator,doctor,bodyguard,executioner,amnesiac};



////////////////////////////////////////////////////////////////////////////////////////////
// appropriateNight:                                                                      //
//  Accepts a Role object, determines whether it is the right night to wake up or not.    //
////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean appropriateNight(Role role){
        if(role.getKeyword().equals("Pirate") && Metadata.night>=2){return true;}
        else if(role.getKeyword().equals("Medium") && Metadata.night>=3){return true;}
        else if(role.getKeyword().equals("Veteran") && Metadata.night>=2){return true;}
        else if(role.getKeyword().equals("Godfather")){return true;}
        else if(role.getKeyword().equals("Mafioso")){return true;}
        else if(role.getKeyword().equals("Consigliere")){return true;}
        else if(role.getKeyword().equals("Blackmailer")){return true;}
        else if(role.getKeyword().equals("Janitor")){return true;}
        else if(role.getKeyword().equals("Serial Killer") && Metadata.night>=2){return true;}
        else if(role.getKeyword().equals("Werewolf") && Metadata.night%2==0){return true;}
        else if(role.getKeyword().equals("Witch")){return true;}
        else if(role.getKeyword().equals("Vigilante") && Metadata.night>=2){return true;}
        else if(role.getKeyword().equals("Sheriff")){return true;}
        else if(role.getKeyword().equals("Deputy")){return true;}
        else if(role.getKeyword().equals("Investigator")){return true;}
        else if(role.getKeyword().equals("Doctor") && Metadata.night>=2){return true;}
        else if(role.getKeyword().equals("Bodyguard") && Metadata.night>=2){return true;}
        else if(role.getKeyword().equals("Executioner") && Metadata.night==1){return true;}
        else return role.getKeyword().equals("Amnesiac") && Metadata.night >= 3;
    }
////////////////////////////////////////////////////////////////////////////////////////////
// findRole/findRoleInt:                                                                  //
//  Given its keyword as a string, returns the corresponding role object (or Int for fRI).//
////////////////////////////////////////////////////////////////////////////////////////////
    public static Role findRole(String string){
        for (Role r : role_list){
            if (string.equals(r.toString())){
                return r;
            }
        }
        return null;
    }
    public static int findRoleInt(String string){
        for (int i=0;i<role_list.length;i++){
            if (role_list[i].getKeyword().equals(string)){
                return i;
            }
        }
        return -1;
    }
////////////////////////////////////////////////////////////////////////////////////////////
// findPlaceInVisitingList:                                                               //
//  Given its keyword as a string, returns the integer of the role in the visiting list.  //
////////////////////////////////////////////////////////////////////////////////////////////
    public static int findPlaceInVisitingList(String string){
        for (int i=0;i<visitingRoles.length;i++){
            if (visitingRoles[i].getKeyword().equals(string)){
                return i;
            }
        }
        return -1;
    }

////////////////////////////////////////////////////////////////////////////////////////////
// extraUses:                                                                             //
//  When determining the number of uses each limited-use role gets, this function returns //
//  an integer that determined the extra amount of uses of these roles, based on the      //
//  number of players.                                                                    //
////////////////////////////////////////////////////////////////////////////////////////////
    public static int extraUses(int i){
        if (i<10) return 0;
        else if (i<15) return 1;
        else return 2;
    }


    public static Class pageLookup(String role){
        Role r = findRole(role);
        switch (Objects.requireNonNull(r).getKeyword()){
            case "Pirate": return Page5.class;
            case "Medium": return Page8.class;
            case "Veteran": return Page9.class;
            case "Godfather":
            case "Mafioso":
            case "Consigliere":
            case "Blackmailer":
            case "Janitor":
                return Page10.class;
            case "Serial Killer": return Page12.class;
            case "Werewolf": return Page13.class;
            case "Witch": return Page14.class;
            case "Vigilante": return Page15.class;
            case "Sheriff": return Page16.class;
            case "Deputy": return Page17.class;
            case "Investigator": return Page18.class;
            case "Doctor": return Page19.class;
            case "Bodyguard": return Page20.class;
            case "Executioner": return Page21.class;
            case "Amnesiac": return Page22.class;
            default: return Page23.class;
        }
    }
    public static Intent toPage(Context context, String role){
        for (int cursor = findPlaceInVisitingList(role); cursor<visitingRoles.length; cursor++){
            Role r = visitingRoles[cursor];
            boolean rightNight = appropriateNight(r);
            boolean alive = stillAlive(r);
            if (rightNight && alive){
                return new Intent(context, pageLookup(visitingRoles[cursor].getKeyword()));
            }
        }
        return new Intent(context,Page23.class); //This is the end of night page
    }
}