package com.example.timothy.town_of_salem_card_game;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Metadata {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// The variables below are miscellaneous yet necessary variables to keep track of the game.                           //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int numPlayers = 0;
    public static ArrayList<Person> allPlayers = new ArrayList<>();
    public static ArrayList<Person> alivePlayers = new ArrayList<>();
    public static ArrayList<Person> mafiaAlive = new ArrayList<>();
    public static int night = 1;
    public static int virtualValueSum = 0;
    public static int pirateWins = 0;
    public static ArrayList<Person> deadPlayers = new ArrayList<>();
    public static ArrayList<Person> winners = new ArrayList<>();
    public static boolean canHealSelf = true;
    public static Person exeTarget = new Person("None", RoleList.none);
    public static boolean exeWin = false;
    public static boolean mayorRevealed = false;
    public static Person lastHealed = new Person("None", RoleList.none);
    public static Person lastPirated = new Person("None", RoleList.none);
    public static Person lastGuarded = new Person("None", RoleList.none);
    public static Person lastBMed = new Person("None", RoleList.none);
    public static Person noOne = new Person ("None", RoleList.none);
    public static boolean houseRulesWitch = false;


    public static void changeExeWin() {exeWin = true;}
    public static int getVirtualValueSum(){return virtualValueSum;}
    public static void changeCanHealSelf() {canHealSelf = false;}
    public static void mayorReveals() {mayorRevealed = true;}
    public static void setPersonHealed(Person p) {lastHealed = p;}
    public static void setExeTarget(Person p) {exeTarget = p;}
    public static void addPirateWin() {pirateWins+=1;}
    public static void incrementNight() {night+=1;}


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// findPerson: inputs a name and returns the associated Person object in alivePlayers, or null if it                   //
// doesn't exist.                                                                                                     //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Person findPerson(String mode, String kw) {
        for (Person player : alivePlayers) {
            if(mode.equals("Name") && Objects.equals(player.getName(), kw)) return player;
            else if(mode.equals("Role") && Objects.equals(player.getKeyword(), kw))  return player;
        }
        return null;
    }

    public static Person findDeadPerson(String mode, String kw) {
        for (Person player : deadPlayers) {
            if(mode.equals("Name") && Objects.equals(player.getName(), kw)) return player;
            else if(mode.equals("Role") && Objects.equals(player.getKeyword(), kw))  return player;
        }
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// randomMafia: returns a random member of the mafia.                                                                 //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Person randomMafia() {
        Random rand = new Random();
        int indx = rand.nextInt(mafiaAlive.size());
        return mafiaAlive.get(indx);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// allCursed: checks to see if every person that isn't the witch is cursed.                                           //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean allCursed() {
        if (RoleList.stillAlive(RoleList.witch)){
            for (Person player : Metadata.alivePlayers) {
                boolean isNotCursed = !player.getCursed();
                boolean isNotTheWitch = !Objects.equals(player.getKeyword(),"Witch");
                if (isNotCursed) {
                    if (isNotTheWitch) return false;
                }
            }
            return true;
        }
        return false;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// mafiaRoleBlocked: if there is one mafia member left and is roleblocked, returns true. otherwise returns false      //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean mafiaRoleBlocked() {
        if (mafiaAlive.size() == 1) {
            for (Person maf : mafiaAlive) {
                return (maf.getRoleBlocked());
            }

        } else {
            return false;
        }
        return false;
    }

    public static void resetMafia(){
        mafiaAlive.clear();
        for (Person person:alivePlayers){
            if (Objects.equals(person.getAlignment(),"Mafia")) mafiaAlive.add(person);
        }
    }

    public static void resetRoleBlocked(){
        for (Person person: alivePlayers){
            person.setRoleBlocked(false);
        }
    }

    public static void updateRole(String personName, String roleKW){
        for (Person person:Metadata.alivePlayers){
            if (Objects.equals(person.getName(),personName)){
                int pos = Metadata.alivePlayers.indexOf(person);
                Role r = RoleList.findRole(roleKW);
                assert r != null;
                Metadata.alivePlayers.set(pos,new Person(person.getName(),r));
            }
        }

    }



}

