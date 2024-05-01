package com.example.timothy.town_of_salem_card_game;

import java.util.ArrayList;

public class Person extends Role {
    String name;
    Role role;
    boolean cursed;
    String status;
    ArrayList<Person> targets;
    boolean isRoleBlocked;
    boolean isCleaned;


    public Person(String name_, Role role_){
        super(role_.getKeyword(),role_.getAlignment(),role_.canBeRoleBlocked(),role_.getSheriffResult(),role_.getVirtualValue());
        this.name=name_;
        this.role=role_;
        this.cursed=false;
        this.status="";
        this.targets=new ArrayList<>(); // Most will have one per night, expect for Werewolf, which will have 2
        this.isRoleBlocked=false;
        this.isCleaned=false;

    }

    public String getName(){return this.name;}
    public boolean getCursed(){return this.cursed;}
    public String getStatus(){return this.status;}
    public ArrayList<Person> getTargets(){return this.targets;}
    public void setCursed(){this.cursed=true;}
    public void addStatus(String s){this.status=this.status+s;}
    public void clearStatus(){this.status="";}
    public void addTarget(Person target){this.targets.add(target);}
    public boolean getRoleBlocked(){return this.isRoleBlocked;}
    public void setName(String s){this.name=s;}
    public void setRoleBlocked(boolean bool){this.isRoleBlocked=bool;}
    public void setRole(Role r){this.role=r;}
    public void clearTargets(){this.targets.clear();}
    public void deleteStatus(String str){
        if (this.status.contains(str)){this.status = this.status.replace(str,"");}
    }
    public String description(){return (String.format("%s (%s)",this.name, this.toString()));}
    public void cleaned(){this.isCleaned=true;}
    public boolean isCleaned(){return this.isCleaned;}



}

